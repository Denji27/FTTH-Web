package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.response.CustomerDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface CustomerService {
    void uploadData2(InputStream excelFile) throws IOException;
//    List<CustomerDTO> search(String contactPhone, String ftthAccount, String otp);
    List<CustomerDTO> find(String contactPhone, String ftthAccount);
    void isValidMockOtp(String contactPhone, String otp);
    void isValidOtp(String contactPhone, String otp);
    String getMockOtp(String contactPhone);
    String getOtp(String contactPhone);
    void resetCustomerData();
    void validateDatabase();
}
