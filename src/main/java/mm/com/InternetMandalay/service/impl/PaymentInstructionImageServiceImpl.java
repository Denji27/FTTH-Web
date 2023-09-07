package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.PaymentInstructionImage;
import mm.com.InternetMandalay.exception.BadRequestException;
import mm.com.InternetMandalay.repository.PaymentInstructionImageRepo;
import mm.com.InternetMandalay.service.PaymentInstructionImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class PaymentInstructionImageServiceImpl implements PaymentInstructionImageService {
    @Autowired
    private PaymentInstructionImageRepo imageRepo;

    @Override
    public PaymentInstructionImage uploadImage(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")){
            throw new BadRequestException("not a a valid file");
        }
        PaymentInstructionImage image = new PaymentInstructionImage();
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
}
