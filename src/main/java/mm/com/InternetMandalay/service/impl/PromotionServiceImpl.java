package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.Promotion;
import mm.com.InternetMandalay.repository.PromotionRepo;
import mm.com.InternetMandalay.request.PromotionUpdate;
import mm.com.InternetMandalay.response.PromotionDTO;
import mm.com.InternetMandalay.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepo promotionRepo;

    @Override
    @CacheEvict(value = "Promotion", allEntries = true)
    public PromotionDTO create() {
        Promotion promotion = Promotion.builder()
                .name("name")
                .speed("speed")
                .price(0)
                .promotion("promotion")
                .build();
        promotionRepo.save(promotion);
        PromotionDTO pro = new PromotionDTO();
        pro.setName(promotion.getName());
        pro.setSpeed(promotion.getSpeed());
        pro.setPrice(promotion.getPrice());
        pro.setPromotion(promotion.getPromotion());
        return pro;
    }

    @Override
    @CacheEvict(value = "Promotion", allEntries = true)
    public PromotionDTO update(Integer id, PromotionUpdate promotionUpdate) {
        Promotion promotion = promotionRepo.getPromotionById(id);
        promotion.setName(promotionUpdate.getName());
        promotion.setSpeed(promotionUpdate.getSpeed());
        promotion.setPrice(promotionUpdate.getPrice());
        promotion.setPromotion(promotionUpdate.getPromotion());
        promotionRepo.save(promotion);
        PromotionDTO pro = new PromotionDTO();
        pro.setName(promotion.getName());
        pro.setSpeed(promotion.getSpeed());
        pro.setPrice(promotion.getPrice());
        pro.setPromotion(promotion.getPromotion());
        return pro;
    }

    @Override
    @CacheEvict(value = "Promotion", allEntries = true)
    public void delete(Integer id) {
        promotionRepo.deleteById(id);
    }

    @Override
    @Cacheable(value = "Promotion")
    public List<PromotionDTO> getAll() {
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
