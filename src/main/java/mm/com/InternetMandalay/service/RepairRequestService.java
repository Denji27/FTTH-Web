package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.entity.RepairRequest;
import mm.com.InternetMandalay.response.CustomerDTO;

import java.util.List;

public interface RepairRequestService {
    String submitRepairRequest(String contactPhone, String ftthAccount);
    List<CustomerDTO> checkCustomerInformation(String contactPhone, String ftthAccount);
    String getMockOtp(String contactPhone);
    String getOtp(String contactPhone);
    void isValidMockOtp(String contactPhone, String otp);
    void isValidOtp(String contactPhone, String otp);
    List<RepairRequest> getAllRepairRequest();
    void deleteAllRequest();
}
