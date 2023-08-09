package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.entity.NewCustomer;
import mm.com.InternetMandalay.request.NewCustomerRequest;

import java.util.List;

public interface NewCustomerService {
    NewCustomer create(String collaboratorCode,NewCustomerRequest newCustomerRequest);
    List<NewCustomer> getAll();
    void deleteAll();
}
