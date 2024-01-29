package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.PromotionImage;
import mm.com.InternetMandalay.exception.BadRequestException;
import mm.com.InternetMandalay.repository.PromotionImageRepo;
import mm.com.InternetMandalay.service.PromotionImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class PromotionImageServiceImpl implements PromotionImageService {
    @Autowired
    private PromotionImageRepo imageRepo;

    @CacheEvict(value = "Promotion", allEntries = true)
    @Override
    @Transactional
    public PromotionImage upload(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")){
            throw new BadRequestException("not a a valid file");
        }
        PromotionImage image = new PromotionImage();
        image.setImage(writeFileLocal(file));
        return imageRepo.save(image);
    }

    private String writeFileLocal(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        String filePath = UUID.randomUUID() + "-" + UUID.randomUUID().toString().substring(0, 8);
        try {
            File storeFile = new File("/root/tomcat/webapps/image/" + filePath);
            file.transferTo(storeFile);
            return "https://internetmandalay.com/image/" + filePath;
        } catch (IOException e) {
            return null;
        }
    }

    @CacheEvict(value = "Promotion", allEntries = true)
    @Override
    public void delete(Integer id) {
        PromotionImage image = imageRepo.getPromotionImageById(id);
        String filePath = "/root/tomcat/webapps/image/" + image.getImage().substring(35);
        File fileToDelete = new File(filePath);
        fileToDelete.delete();
        imageRepo.delete(image);
    }

    @Cacheable(value = "Promotion", key = "'Promotion' + #root.methodName")
    @Override
    public List<PromotionImage> getAll() {
        return imageRepo.findAll();
    }
}
