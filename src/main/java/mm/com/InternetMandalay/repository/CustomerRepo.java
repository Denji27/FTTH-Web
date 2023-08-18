package mm.com.InternetMandalay.repository;

import mm.com.InternetMandalay.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    List<Customer> findCustomerByFtthAccount(String ftthAccount);
    List<Customer> findCustomerByContactPhone(String contactPhone);
    List<Customer> findCustomerByFtthAccountAndContactPhone(String ftthAccount, String contactPhone);
}
