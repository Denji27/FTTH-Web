package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.PaymentInstruction;
import mm.com.InternetMandalay.repository.PaymentInstructionRepo;
import mm.com.InternetMandalay.request.PaymentInstructionUpdate;
import mm.com.InternetMandalay.service.PaymentInstructionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PaymentInstructionServiceImpl implements PaymentInstructionService {
    @Autowired
    private PaymentInstructionRepo paymentInstructionRepo;

    @Value("${PaymentInstruction.Id}")
    private String id;


    @Override
    public PaymentInstruction create() {
        PaymentInstruction paymentInstruction = PaymentInstruction.builder()
                .id(id)
                .title("Default Title")
                .image("Default Image")
                .description("Default Description")
                .build();
        return paymentInstructionRepo.save(paymentInstruction);
    }

    @Override
    @CacheEvict(value = "PaymentInstruction", allEntries = true)
    public PaymentInstruction update(PaymentInstructionUpdate paymentInstructionUpdate) {
        PaymentInstruction paymentInstruction = paymentInstructionRepo.getPaymentInstructionById(id);
        paymentInstruction.setTitle(paymentInstructionUpdate.getTitle());
        paymentInstruction.setImage(paymentInstructionUpdate.getImage());
        paymentInstruction.setDescription(paymentInstructionUpdate.getDescription());
        return paymentInstructionRepo.save(paymentInstruction);
    }

    @Override
    @Cacheable(value = "PaymentInstruction")
    public PaymentInstruction get() {
        return paymentInstructionRepo.getPaymentInstructionById(id);
    }


}
