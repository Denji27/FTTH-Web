package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.entity.PaymentRequest;
import mm.com.InternetMandalay.response.CustomerDTO;

import java.util.List;

public interface PaymentRequestService {
    public String submitPaymentReuqest(String contactPhone, String ftthAccount);
    public List<CustomerDTO> checkCustomerInformation(String contactPhone, String ftthAccount);
    void validateMockOtp(String contactPhone, String otp);
    void validateOtp(String contactPhone, String otp);
    String getMockOtp(String contactPhone);
    String getOtp(String contactPhone);
    public List<PaymentRequest> getAllPaymentRequestOfCustomers();
    public void deleteAllRequest();

}
