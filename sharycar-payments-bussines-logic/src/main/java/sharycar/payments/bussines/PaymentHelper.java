package sharycar.payments.bussines;

import sharycar.payments.persistence.Payment;

public class PaymentHelper {

    private Payment payment;

    public PaymentHelper(Payment payment) {
        this.payment = payment;
    }
    public Payment executePayment() throws Exception {

        // @TODO implement
        return this.payment;

    }

}
