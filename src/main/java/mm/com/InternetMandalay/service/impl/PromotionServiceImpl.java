package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.Promotion;
import mm.com.InternetMandalay.repository.PromotionRepo;
import mm.com.InternetMandalay.request.PromotionUpdate;
import mm.com.InternetMandalay.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepo promotionRepo;

    @Override
    @CacheEvict(value = "Promotion")
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
    @CacheEvict(value = "Promotion")
    public Promotion update(Integer id, PromotionUpdate promotionUpdate) {
        Promotion promotion = promotionRepo.getPromotionById(id);
        promotion.setName(promotionUpdate.getName());
        promotion.setSpeed(promotionUpdate.getSpeed());
        promotion.setPrice(promotionUpdate.getPrice());
        promotion.setPromotion(promotionUpdate.getPromotion());
        return promotionRepo.save(promotion);
    }

    @Override
    @CacheEvict(value = "Promotion")
    public void delete(Integer id) {
        promotionRepo.deleteById(id);
    }

    @Override
    @Cacheable(value = "Promotion")
    public List<Promotion> getAll() {
        return promotionRepo.findAll();
    }
}
