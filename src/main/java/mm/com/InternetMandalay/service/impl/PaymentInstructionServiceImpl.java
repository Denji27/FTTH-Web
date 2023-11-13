package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.PaymentInstruction;
import mm.com.InternetMandalay.repository.PaymentInstructionRepo;
import mm.com.InternetMandalay.response.PaymentInstructionDTO;
import mm.com.InternetMandalay.service.PaymentInstructionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PaymentInstructionServiceImpl implements PaymentInstructionService {
    @Autowired
    private PaymentInstructionRepo paymentInstructionRepo;

    @Value("${PaymentInstruction.Id}")
    private String id;

    private final Logger log = LoggerFactory.getLogger(PaymentInstructionServiceImpl.class);

    @CacheEvict(value = "Payment-Instruction", allEntries = true)
    @Override
    public PaymentInstructionDTO update(String title, String description) {
        if (!paymentInstructionRepo.existsById(id)){
            return this.paymentMapper(this.createPaymentInstruction(title, description));
        }else {
            PaymentInstruction paymentInstruction = paymentInstructionRepo.getPaymentInstructionById(id);
            paymentInstruction.setDescription(description);
            paymentInstruction.setTitle(title);
            paymentInstructionRepo.save(paymentInstruction);
            return this.paymentMapper(paymentInstruction);
        }
    }

    @Cacheable(value = "Payment-Instruction", key = "'PMsInternetMandalay'")
    @Override
    public PaymentInstructionDTO get() {
        PaymentInstruction paymentInstruction = paymentInstructionRepo.getPaymentInstructionById(id);
        PaymentInstructionDTO pm = new PaymentInstructionDTO();
        pm.setTitle(paymentInstruction.getTitle());
        pm.setDescription(paymentInstruction.getDescription());
        return pm;
    }

    private PaymentInstruction createPaymentInstruction(String title, String description){
        PaymentInstruction paymentInstruction = new PaymentInstruction();
        paymentInstruction.setId(this.id);
        paymentInstruction.setDescription(description);
        paymentInstruction.setTitle(title);
        return paymentInstructionRepo.save(paymentInstruction);
    }
    private PaymentInstructionDTO paymentMapper(PaymentInstruction paymentInstruction){
        PaymentInstructionDTO pm = new PaymentInstructionDTO();
        pm.setTitle(paymentInstruction.getTitle());
        pm.setDescription(paymentInstruction.getDescription());
        return pm;
    }
}
