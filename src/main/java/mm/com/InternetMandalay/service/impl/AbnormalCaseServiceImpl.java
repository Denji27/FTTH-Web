package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.AbnormalCase;
import mm.com.InternetMandalay.exception.BadRequestException;
import mm.com.InternetMandalay.repository.AbnormalCaseRepo;
import mm.com.InternetMandalay.response.AbnormalCaseDTO;
import mm.com.InternetMandalay.service.AbnormalCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class AbnormalCaseServiceImpl implements AbnormalCaseService {
    @Autowired
    private AbnormalCaseRepo abnormalCaseRepo;

    @Value("${AbnormalCase.Id}")
    private String id;

    @Override
    public AbnormalCaseDTO update(MultipartFile file, String title, String description) {
        if (!abnormalCaseRepo.existsById(id)){
            AbnormalCase abnormalCase = new AbnormalCase();
            abnormalCase.setId(id);
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (fileName.contains("..")){
                throw new BadRequestException("not a a valid file");
            }
            try {
                abnormalCase.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
                throw new BadRequestException("access error");
            }
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
        try {
            abnormalCase.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        abnormalCase.setDescription(description);
        abnormalCase.setTitle(title);
        abnormalCaseRepo.save(abnormalCase);
        AbnormalCaseDTO ab = new AbnormalCaseDTO();
        ab.setTitle(abnormalCase.getTitle());
        ab.setImage(abnormalCase.getImage());
        ab.setDescription(abnormalCase.getDescription());
        return ab;
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
