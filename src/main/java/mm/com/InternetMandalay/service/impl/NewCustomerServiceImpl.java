package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.NewCustomer;
import mm.com.InternetMandalay.repository.NewCustomerRepo;
import mm.com.InternetMandalay.request.NewCustomerRequest;
import mm.com.InternetMandalay.service.NewCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewCustomerServiceImpl implements NewCustomerService {
    @Autowired
    private NewCustomerRepo newCustomerRepo;

    @Override
    @CacheEvict(value = "NewCustomer")
    public NewCustomer create(String collaboratorCode ,NewCustomerRequest newCustomerRequest) {
        NewCustomer newCustomer = new NewCustomer();
            newCustomer.setCollaboratorCode(collaboratorCode);
            newCustomer.setName(newCustomerRequest.getName());
            newCustomer.setPhoneNumber(newCustomerRequest.getPhoneNumber());
            newCustomer.setServiceName(newCustomerRequest.getServiceName());
            newCustomer.setAddress(newCustomerRequest.getAddress());

        return newCustomerRepo.save(newCustomer);
    }

    @Override
    @Cacheable(value = "NewCustomer")
    public List<NewCustomer> getAll() {
        return newCustomerRepo.findAll();
    }

    @Override
    @CacheEvict(value = "NewCustomer")
    public void deleteAll() {
        newCustomerRepo.deleteAll();
    }


}
