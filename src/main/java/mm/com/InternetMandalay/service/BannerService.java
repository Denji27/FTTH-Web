package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.entity.Banner;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BannerService {
    Banner upload(MultipartFile file);
    void delete(Integer id);
    List<Banner> getAll();
}
