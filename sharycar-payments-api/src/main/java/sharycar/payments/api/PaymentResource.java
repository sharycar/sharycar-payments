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

import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.logs.cdi.LogParams;
import org.eclipse.microprofile.metrics.Histogram;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.annotation.Timed;
import sharycar.payments.persistence.Payment;
import sharycar.payments.bussines.PaymentHelper;
@Path("/payments")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log(LogParams.METRICS)
public class PaymentResource {



    @PersistenceContext
    private EntityManager em;



    /**
     * testing purpose
     * @return
     */
    @GET
    @Path("version")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUrl() {
        return Response.ok("1.0.0").build();
    }


    /**
     *  Get all payments
     */
    @Metered(name = "requests")
    @GET
    public Response getPayments() {

        TypedQuery<Payment> query = em.createNamedQuery("Payment.findAll", Payment.class);

        List<Payment> payments = query.getResultList();

        return Response.ok(payments).build();

    }
    @Timed(name = "payment_processing_time")
    @POST
    @Path("/add")
    @Log(value = LogParams.METRICS, methodCall = false)
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
     * @param uid
     * @return
     */

    @Metric(name = "get_user_payments")
    @Log(value = LogParams.METRICS, methodCall = false)
    @GET
    @Path("/user/{uid}")
    public Response getUserPayments(@PathParam("uid") Integer uid) {

        try {
            Query query = em.createQuery("SELECT p FROM Payment p WHERE p.user_id = :uid");
            query.setParameter("uid", uid);
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
    @Metric(name = "requests")
    @GET
    @Path("/{paymentId}")
    @Log(value = LogParams.METRICS, methodCall = false)
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
