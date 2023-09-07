package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.entity.AbnormalCaseImage;
import org.springframework.web.multipart.MultipartFile;

public interface AbnormalCaseImageService {
    AbnormalCaseImage uploadImage(MultipartFile file);
}
