package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.NewCustomer;
import mm.com.InternetMandalay.repository.NewCustomerRepo;
import mm.com.InternetMandalay.request.NewCustomerRequest;
import mm.com.InternetMandalay.service.NewCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewCustomerServiceImpl implements NewCustomerService {
    @Autowired
    private NewCustomerRepo newCustomerRepo;

    @Override
    public NewCustomer create(NewCustomerRequest newCustomerRequest) {
        NewCustomer newCustomer = NewCustomer.builder()
                .name(newCustomerRequest.getName())
                .phoneNumber(newCustomerRequest.getPhoneNumber())
                .serviceName(newCustomerRequest.getServiceName())
                .address(newCustomerRequest.getAddress())
                .build();
        return newCustomerRepo.save(newCustomer);
    }

    @Override
    public List<NewCustomer> getAll() {
        return newCustomerRepo.findAll();
    }

    @Override
    public void deleteAll() {
        newCustomerRepo.deleteAll();
    }


}
