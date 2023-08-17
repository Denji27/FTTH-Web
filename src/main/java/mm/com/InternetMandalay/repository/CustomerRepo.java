package mm.com.InternetMandalay.repository;

import mm.com.InternetMandalay.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    Customer findCustomerByFtthAccount(String ftthAccount);
    Customer findCustomerByContactPhone(String contactPhone);
    Customer findCustomerByFtthAccountAndContactPhone(String ftthAccount, String contactPhone);
}
