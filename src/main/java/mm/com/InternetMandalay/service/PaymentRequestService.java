package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.entity.PaymentRequest;

import java.util.List;

public interface PaymentRequestService {
    public String requestForPayment(PaymentRequest paymentRequest);
    public List<PaymentRequest> getAllPaymentRequestOfCustomers();
    public void deleteAllRequest();

}
