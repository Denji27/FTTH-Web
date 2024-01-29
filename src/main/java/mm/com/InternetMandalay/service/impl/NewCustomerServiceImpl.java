package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.NewCustomer;
import mm.com.InternetMandalay.repository.NewCustomerRepo;
import mm.com.InternetMandalay.request.NewCustomerRequest;
import mm.com.InternetMandalay.service.NewCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewCustomerServiceImpl implements NewCustomerService {
    @Autowired
    private NewCustomerRepo newCustomerRepo;

    @CacheEvict(value = "New-Customer", allEntries = true)
    @Override
    @Transactional
    public NewCustomer create(String collaboratorCode ,NewCustomerRequest newCustomerRequest) {
        NewCustomer newCustomer = new NewCustomer();
            newCustomer.setCollaboratorCode(collaboratorCode);
            newCustomer.setName(newCustomerRequest.getName());
            newCustomer.setPhoneNumber(newCustomerRequest.getPhoneNumber());
            newCustomer.setServiceName(newCustomerRequest.getServiceName());
            newCustomer.setAddress(newCustomerRequest.getAddress());
        return newCustomerRepo.save(newCustomer);
    }

    @Cacheable(value = "New-Customer", key = "'new_cus_' + #root.methodName")
    @Override
    public List<NewCustomer> getAll() {
        return newCustomerRepo.findAll();
    }

    @CacheEvict(value = "New-Customer", allEntries = true)
    @Override
    public void deleteAll() {
        newCustomerRepo.deleteAll();
    }


}
