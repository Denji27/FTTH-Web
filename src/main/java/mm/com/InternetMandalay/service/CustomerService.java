package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.entity.Customer;
import mm.com.InternetMandalay.request.SearchRequest;

import java.io.IOException;
import java.io.InputStream;

public interface CustomerService {
    void uploadData(InputStream excelFile) throws IOException;
    Customer search(SearchRequest searchRequest);
    void resetCustomerData();
}
