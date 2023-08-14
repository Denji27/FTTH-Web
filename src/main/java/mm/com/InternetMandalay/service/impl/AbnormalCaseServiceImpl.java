package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.AbnormalCase;
import mm.com.InternetMandalay.exception.BadRequestException;
import mm.com.InternetMandalay.repository.AbnormalCaseRepo;
import mm.com.InternetMandalay.response.AbnormalCaseDTO;
import mm.com.InternetMandalay.service.AbnormalCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class AbnormalCaseServiceImpl implements AbnormalCaseService {
    @Autowired
    private AbnormalCaseRepo abnormalCaseRepo;

    @Value("${AbnormalCase.Id}")
    private String id;

    private final Logger log = LoggerFactory.getLogger(AbnormalCaseServiceImpl.class);


    @Override
    public AbnormalCaseDTO update(MultipartFile file, String title, String description) {
        if (!abnormalCaseRepo.existsById(id)){
            AbnormalCase abnormalCase = new AbnormalCase();
            abnormalCase.setId(id);
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (fileName.contains("..")){
                throw new BadRequestException("not a a valid file");
            }
            abnormalCase.setImage(writeFileLocal(file));
            abnormalCase.setDescription(description);
            abnormalCase.setTitle(title);
            abnormalCaseRepo.save(abnormalCase);
            AbnormalCaseDTO ab = new AbnormalCaseDTO();
            ab.setTitle(abnormalCase.getTitle());
            ab.setImage(abnormalCase.getImage());
            ab.setDescription(abnormalCase.getDescription());
            return ab;
        }
        AbnormalCase abnormalCase = abnormalCaseRepo.getAbnormalCaseById(id);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")){
            System.out.println("not a a valid file");
            throw new RuntimeException();
        }
        abnormalCase.setImage(writeFileLocal(file));
        abnormalCase.setDescription(description);
        abnormalCase.setTitle(title);
        abnormalCaseRepo.save(abnormalCase);
        AbnormalCaseDTO ab = new AbnormalCaseDTO();
        ab.setTitle(abnormalCase.getTitle());
        ab.setImage(abnormalCase.getImage());
        ab.setDescription(abnormalCase.getDescription());
        return ab;
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
    public AbnormalCaseDTO get() {
        AbnormalCase abnormalCase = abnormalCaseRepo.getAbnormalCaseById(id);
        AbnormalCaseDTO ab = new AbnormalCaseDTO();
        ab.setTitle(abnormalCase.getTitle());
        ab.setImage(abnormalCase.getImage());
        ab.setDescription(abnormalCase.getDescription());
        return ab;
    }

}
