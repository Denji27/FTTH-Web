package mm.com.InternetMandalay.repository;

import mm.com.InternetMandalay.entity.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRequestRepo extends JpaRepository<PaymentRequest, Long> {
}
