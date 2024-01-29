package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.AbnormalCase;
import mm.com.InternetMandalay.repository.AbnormalCaseRepo;
import mm.com.InternetMandalay.response.AbnormalCaseDTO;
import mm.com.InternetMandalay.service.AbnormalCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AbnormalCaseServiceImpl implements AbnormalCaseService {
    @Autowired
    private AbnormalCaseRepo abnormalCaseRepo;

    @Value("${AbnormalCase.Id}")
    private String id;

    private final Logger log = LoggerFactory.getLogger(AbnormalCaseServiceImpl.class);

    @CacheEvict(value = "Abnormal-case", allEntries = true)
    @Override
    @Transactional
    public AbnormalCaseDTO update(String title, String description) {
        if (!abnormalCaseRepo.existsById(id)){
            return this.abnormalMapper(this.createAbnormalCase(title, description));
        }
        else {
            AbnormalCase abnormalCase = abnormalCaseRepo.getAbnormalCaseById(id);
            abnormalCase.setDescription(description);
            abnormalCase.setTitle(title);
            abnormalCaseRepo.save(abnormalCase);
            return this.abnormalMapper(abnormalCase);
        }
    }

    @Cacheable(value = "Abnormal-case", key = "'ACsInternetMandalay'")
    @Override
    public AbnormalCaseDTO get() {
        AbnormalCase abnormalCase = abnormalCaseRepo.getAbnormalCaseById(id);
        AbnormalCaseDTO ab = new AbnormalCaseDTO();
        ab.setTitle(abnormalCase.getTitle());
        ab.setDescription(abnormalCase.getDescription());
        return ab;
    }

    private AbnormalCase createAbnormalCase(String title, String description){
        AbnormalCase abnormalCase = new AbnormalCase();
        abnormalCase.setId(this.id);
        abnormalCase.setDescription(description);
        abnormalCase.setTitle(title);
        return abnormalCaseRepo.save(abnormalCase);
    }

    private AbnormalCaseDTO abnormalMapper(AbnormalCase abnormalCase){
        AbnormalCaseDTO ab = new AbnormalCaseDTO();
        ab.setTitle(abnormalCase.getTitle());
        ab.setDescription(abnormalCase.getDescription());
        return ab;
    }
}
