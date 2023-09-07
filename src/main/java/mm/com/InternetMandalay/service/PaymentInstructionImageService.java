package mm.com.InternetMandalay.service;


import mm.com.InternetMandalay.entity.PaymentInstructionImage;
import org.springframework.web.multipart.MultipartFile;

public interface PaymentInstructionImageService {
    PaymentInstructionImage uploadImage(MultipartFile file);
}
