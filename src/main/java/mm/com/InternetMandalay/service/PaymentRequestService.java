package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.entity.PaymentRequest;
import mm.com.InternetMandalay.response.CustomerDTO;

import java.util.List;

public interface PaymentRequestService {
    public String submitPaymentReuqest(String contactPhone, String ftthAccount);
    public List<CustomerDTO> checkCustomerInformation(String contactPhone, String ftthAccount);
    public List<PaymentRequest> getAllPaymentRequestOfCustomers();
    public void deleteAllRequest();

}
