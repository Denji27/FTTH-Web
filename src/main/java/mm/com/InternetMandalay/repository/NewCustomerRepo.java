package mm.com.InternetMandalay.repository;

import mm.com.InternetMandalay.entity.NewCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewCustomerRepo extends JpaRepository<NewCustomer, Integer> {
}
