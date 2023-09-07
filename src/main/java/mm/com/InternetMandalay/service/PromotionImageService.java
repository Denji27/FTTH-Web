package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.entity.PromotionImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PromotionImageService {
    PromotionImage upload(MultipartFile file);
    void delete(Integer id);
    List<PromotionImage> getAll();
}
