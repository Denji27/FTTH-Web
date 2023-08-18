package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.response.CustomerDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface CustomerService {
    void uploadData(InputStream excelFile) throws IOException;
    List<CustomerDTO>  search(String contactPhone, String ftthAccount);
    void resetCustomerData();
}
