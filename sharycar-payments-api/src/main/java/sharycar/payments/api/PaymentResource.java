package sharycar.payments.api;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import sharycar.payments.persistence.Payment;
import sharycar.payments.bussines.PaymentHelper;
@Path("/payments")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentResource {

    @PersistenceContext
    private EntityManager em;

    /**
     *  Get all payments
     */
    @GET
    public Response getPayments() {

        TypedQuery<Payment> query = em.createNamedQuery("Payment.findAll", Payment.class);

        List<Payment> payments = query.getResultList();

        return Response.ok(payments).build();

    }

    @POST
    @Path("/add")
    public Response addPayment(Payment payment) {
        try {
            // Try executing transaction
            new PaymentHelper(payment).executePayment();

            payment.setId(null);
            payment.setTransactionTime(new Date());

            em.getTransaction().begin();
            em.persist(payment);
            em.getTransaction().commit();

        } catch (Exception e) {
            Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Payment error").build();
        }

        return Response.status(Response.Status.CREATED).entity(payment).build();
    }

    /**
     * Get payments for user.
     * @param uname
     * @return
     */
    @GET
    @Path("/user/{uname}")
    public Response getUserPayments(@PathParam("uname") Integer uname) {

        try {
            Query query = em.createQuery("SELECT p FROM Payment p WHERE p.username = :uname");
            query.setParameter("uname", uname);
            return Response.ok(query.getResultList()).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    /**
     * Get payment by id.
     * @param paymentId
     * @return
     */
    @GET
    @Path("/{paymentId}")
    public Response getPaymentById(@PathParam("paymentId") Integer paymentId) {

        try {
            Query query = em.createQuery("SELECT p FROM Payment p WHERE p.id = :paymentId");
            query.setParameter("paymentId", paymentId);
            return Response.ok(query.getResultList()).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }


}
