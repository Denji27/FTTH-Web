package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.PaymentInstruction;
import mm.com.InternetMandalay.repository.PaymentInstructionRepo;
import mm.com.InternetMandalay.response.PaymentInstructionDTO;
import mm.com.InternetMandalay.service.PaymentInstructionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

@Service
public class PaymentInstructionServiceImpl implements PaymentInstructionService {
    @Autowired
    private PaymentInstructionRepo paymentInstructionRepo;

    @Value("${PaymentInstruction.Id}")
    private String id;

    private final Logger log = LoggerFactory.getLogger(PaymentInstructionServiceImpl.class);

    @Override
    public PaymentInstructionDTO update(MultipartFile file, String title, String description) {
        if (!paymentInstructionRepo.existsById(id)){
            PaymentInstruction paymentInstruction = new PaymentInstruction();
            paymentInstruction.setId(id);
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (fileName.contains("..")){
                System.out.println("not a a valid file");
                throw new RuntimeException();
            }
            paymentInstruction.setImage(writeFileLocal(file));
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
        paymentInstruction.setImage(writeFileLocal(file));
        paymentInstruction.setDescription(description);
        paymentInstruction.setTitle(title);
        paymentInstructionRepo.save(paymentInstruction);
        PaymentInstructionDTO pm = new PaymentInstructionDTO();
        pm.setTitle(paymentInstruction.getTitle());
        pm.setImage(paymentInstruction.getImage());
        pm.setDescription(paymentInstruction.getDescription());
        return pm;
    }

    public String writeFileLocal(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        String filePath = UUID.randomUUID() + "-" + UUID.randomUUID().toString().substring(0, 8);

        try {
            File storeFile = new File("/root/tomcat/webapps/image" + filePath);
            file.transferTo(storeFile);
            log.info("Create file {} successfully", file.getOriginalFilename());
            return "https://internetmandalay.com/image/ubuntu-4.png" + filePath;
        } catch (IOException e) {
            log.error("An error occurred while uploading the file, ", e);
            return null;
        }
    }

    @Override
    public PaymentInstructionDTO get() {
        PaymentInstruction paymentInstruction = paymentInstructionRepo.getPaymentInstructionById(id);
        PaymentInstructionDTO pm = new PaymentInstructionDTO();
        pm.setTitle(paymentInstruction.getTitle());
        pm.setImage(paymentInstruction.getImage());
        pm.setDescription(paymentInstruction.getDescription());
        return pm;
    }


}
