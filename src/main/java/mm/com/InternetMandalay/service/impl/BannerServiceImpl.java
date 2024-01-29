package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.Banner;
import mm.com.InternetMandalay.exception.BadRequestException;
import mm.com.InternetMandalay.repository.BannerRepo;
import mm.com.InternetMandalay.service.BannerService;
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
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerRepo bannerRepo;
    @CacheEvict(value = "Banner", allEntries = true)
    @Override
    @Transactional
    public Banner upload(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")){
            throw new BadRequestException("not a a valid file");
        }
        Banner banner = new Banner();
        banner.setImage(writeFileLocal(file));
        return bannerRepo.save(banner);
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

    @CacheEvict(value = "Banner", allEntries = true)
    @Override
    public void delete(Integer id) {
        Banner bannerImage = bannerRepo.getBannerById(id);
        String filePath = "/root/tomcat/webapps/image/" + bannerImage.getImage().substring(35);
        File fileToDelete = new File(filePath);
        fileToDelete.delete();
        bannerRepo.delete(bannerImage);
    }

    @Cacheable(value = "Banner", key = "'Banner_' + #root.methodName")
    @Override
    public List<Banner> getAll() {
        return bannerRepo.findAll();
    }
}
