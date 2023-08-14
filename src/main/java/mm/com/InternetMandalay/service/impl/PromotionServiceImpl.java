package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.Promotion;
import mm.com.InternetMandalay.repository.PromotionRepo;
import mm.com.InternetMandalay.request.PromotionUpdate;
import mm.com.InternetMandalay.response.PromotionDTO;
import mm.com.InternetMandalay.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepo promotionRepo;

    @Override
    public Promotion create() {
        Promotion promotion = Promotion.builder()
                .name("name")
                .speed("speed")
                .price(0)
                .promotion("promotion")
                .build();
        return promotionRepo.save(promotion);
    }

    @Override
    public Promotion update(Integer id, PromotionUpdate promotionUpdate) {
        Promotion promotion = promotionRepo.getPromotionById(id);
        promotion.setName(promotionUpdate.getName());
        promotion.setSpeed(promotionUpdate.getSpeed());
        promotion.setPrice(promotionUpdate.getPrice());
        promotion.setPromotion(promotionUpdate.getPromotion());
        return promotionRepo.save(promotion);
    }

    @Override
    public void delete(Integer id) {
        promotionRepo.deleteById(id);
    }

    @Override
    public List<Promotion> getAll() {
        return promotionRepo.findAll();
    }

    @Override
    public List<PromotionDTO> getAllDTO() {
        List<Promotion> promotions = promotionRepo.findAll();
        List<PromotionDTO> promotionDTOS = new ArrayList<>();
        for (Promotion promotion : promotions){
            PromotionDTO pro = new PromotionDTO();
            pro.setName(promotion.getName());
            pro.setSpeed(promotion.getSpeed());
            pro.setPrice(promotion.getPrice());
            pro.setPromotion(promotion.getPromotion());
            promotionDTOS.add(pro);
        }
        return promotionDTOS;
    }
}
