package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.PaymentRequest;
import mm.com.InternetMandalay.repository.PaymentRequestRepo;
import mm.com.InternetMandalay.service.PaymentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentRequestServiceImpl implements PaymentRequestService {
    @Autowired
    private PaymentRequestRepo paymentRequestRepo;

    @Override
    public String requestForPayment(PaymentRequest paymentRequest) {
        paymentRequestRepo.save(paymentRequest);
        return "Request successfully";
    }

    @Override
    public List<PaymentRequest> getAllPaymentRequestOfCustomers() {
        return paymentRequestRepo.findAll();
    }

    @Override
    public void deleteAllRequest() {
        paymentRequestRepo.deleteAll();
    }
}
