package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.entity.PaymentInstruction;
import mm.com.InternetMandalay.response.PaymentInstructionDTO;
import org.springframework.web.multipart.MultipartFile;

public interface PaymentInstructionService {
    PaymentInstructionDTO update(String title, String description);
    PaymentInstructionDTO get();
}
