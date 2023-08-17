package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.request.SearchRequest;
import mm.com.InternetMandalay.response.CustomerDTO;

import java.io.IOException;
import java.io.InputStream;

public interface CustomerService {
    void uploadData(InputStream excelFile) throws IOException;
    CustomerDTO search(SearchRequest searchRequest);
    void resetCustomerData();
}
