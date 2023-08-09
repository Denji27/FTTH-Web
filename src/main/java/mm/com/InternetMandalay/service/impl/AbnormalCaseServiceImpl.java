package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.AbnormalCase;
import mm.com.InternetMandalay.repository.AbnormalCaseRepo;
import mm.com.InternetMandalay.request.AbnormalCaseUpdate;
import mm.com.InternetMandalay.service.AbnormalCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AbnormalCaseServiceImpl implements AbnormalCaseService {
    @Autowired
    private AbnormalCaseRepo abnormalCaseRepo;

    @Value("${AbnormalCase.Id}")
    private String id;

    @Override
    public AbnormalCase create() {
        AbnormalCase abnormalCase = AbnormalCase.builder()
                .id(id)
                .title("Default Title")
                .image("Default Image")
                .description("Default Description")
                .build();
        return abnormalCaseRepo.save(abnormalCase);
    }

    @Override
    public AbnormalCase update(AbnormalCaseUpdate abnormalCaseUpdate) {
        AbnormalCase abnormalCase = abnormalCaseRepo.getAbnormalCaseById(id);
        abnormalCase.setTitle(abnormalCaseUpdate.getTitle());
        abnormalCase.setImage(abnormalCaseUpdate.getImage());
        abnormalCase.setDescription(abnormalCaseUpdate.getDescription());
        return abnormalCaseRepo.save(abnormalCase);
    }

    @Override
    public AbnormalCase get() {
        return abnormalCaseRepo.getAbnormalCaseById(id);
    }
}
