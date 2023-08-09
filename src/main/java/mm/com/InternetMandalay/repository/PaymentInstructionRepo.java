package mm.com.InternetMandalay.repository;

import mm.com.InternetMandalay.entity.PaymentInstruction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInstructionRepo extends JpaRepository<PaymentInstruction, Integer> {
    PaymentInstruction findPaymentInstructionById(String id);
    PaymentInstruction getPaymentInstructionById(String id);
}
