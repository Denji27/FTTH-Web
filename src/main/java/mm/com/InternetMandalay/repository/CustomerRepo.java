package mm.com.InternetMandalay.repository;

import mm.com.InternetMandalay.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    Customer findCustomerByPhoneNumber(String phoneNumber);
    Customer findCustomerByAccount(String account);
    Customer findCustomerByAccountAndPhoneNumber(String account, String phoneNumber);
}
