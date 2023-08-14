package mm.com.InternetMandalay.service;


import mm.com.InternetMandalay.response.AbnormalCaseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface AbnormalCaseService {
    AbnormalCaseDTO update(MultipartFile file, String title, String description);
    AbnormalCaseDTO get();
}
