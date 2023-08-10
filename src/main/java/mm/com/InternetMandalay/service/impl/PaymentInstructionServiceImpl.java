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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class PaymentInstructionServiceImpl implements PaymentInstructionService {
    @Autowired
    private PaymentInstructionRepo paymentInstructionRepo;

    @Value("${PaymentInstruction.Id}")
    private String id;


//    @Override
//    public PaymentInstruction create() {
//        PaymentInstruction paymentInstruction = PaymentInstruction.builder()
//                .id(id)
//                .title("Default Title")
//                .image("Default Image")
//                .description("Default Description")
//                .build();
//        return paymentInstructionRepo.save(paymentInstruction);
//    }

    @Override
    @CacheEvict(value = "PaymentInstruction", allEntries = true)
    public PaymentInstructionDTO update(MultipartFile file, String title, String description) {
        if (!paymentInstructionRepo.existsById(id)){
            PaymentInstruction paymentInstruction = new PaymentInstruction();
            paymentInstruction.setId(id);
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (fileName.contains("..")){
                System.out.println("not a a valid file");
                throw new RuntimeException();
            }
            try {
                paymentInstruction.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            paymentInstruction.setDescription(description);
            paymentInstruction.setTitle(title);
            paymentInstructionRepo.save(paymentInstruction);
            PaymentInstructionDTO pm = new PaymentInstructionDTO();
            pm.setTitle(paymentInstruction.getTitle());
            pm.setImage(paymentInstruction.getImage());
            pm.setDescription(paymentInstruction.getDescription());
            return pm;
        }
        PaymentInstruction paymentInstruction = paymentInstructionRepo.getPaymentInstructionById(id);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")){
            System.out.println("not a a valid file");
            throw new RuntimeException();
        }
        try {
            paymentInstruction.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        paymentInstruction.setDescription(description);
        paymentInstruction.setTitle(title);
        paymentInstructionRepo.save(paymentInstruction);
        PaymentInstructionDTO pm = new PaymentInstructionDTO();
        pm.setTitle(paymentInstruction.getTitle());
        pm.setImage(paymentInstruction.getImage());
        pm.setDescription(paymentInstruction.getDescription());
        return pm;
    }

    @Override
    @Cacheable(value = "PaymentInstruction")
    public PaymentInstructionDTO get() {
        PaymentInstruction paymentInstruction = paymentInstructionRepo.getPaymentInstructionById(id);
        PaymentInstructionDTO pm = new PaymentInstructionDTO();
        pm.setTitle(paymentInstruction.getTitle());
        pm.setImage(paymentInstruction.getImage());
        pm.setDescription(paymentInstruction.getDescription());
        return pm;
    }


}
