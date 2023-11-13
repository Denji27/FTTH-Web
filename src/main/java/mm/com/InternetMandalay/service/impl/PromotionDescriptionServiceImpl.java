package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.PromotionDescription;
import mm.com.InternetMandalay.repository.PromotionDescriptionRepo;
import mm.com.InternetMandalay.response.PromotionDescriptionDto;
import mm.com.InternetMandalay.service.PromotionDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionDescriptionServiceImpl implements PromotionDescriptionService {
    @Autowired
    private PromotionDescriptionRepo promotionDescriptionRepo;
    @Override
    public PromotionDescriptionDto update(String content) {
        if (promotionDescriptionRepo.existsById("ProDesId")){
            PromotionDescription description = promotionDescriptionRepo.getPromotionDescriptionById("ProDesId");
            description.setDescription(content);
            promotionDescriptionRepo.save(description);
            PromotionDescriptionDto dto = new PromotionDescriptionDto();
            dto.setContent(content);
            return dto;
        } else {
            PromotionDescription description = new PromotionDescription();
            description.setId("ProDesId");
            description.setDescription(content);
            promotionDescriptionRepo.save(description);
            PromotionDescriptionDto dto = new PromotionDescriptionDto();
            dto.setContent(content);
            return dto;
        }
    }

    @Override
    public PromotionDescriptionDto get() {
        PromotionDescriptionDto dto = new PromotionDescriptionDto();
        dto.setContent(promotionDescriptionRepo.getPromotionDescriptionById("ProDesId").getDescription());
        return dto;
    }
}
