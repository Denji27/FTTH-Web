package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.entity.Promotion;
import mm.com.InternetMandalay.request.PromotionUpdate;

import java.util.List;

public interface PromotionService {
    Promotion create();
    Promotion update(Integer id, PromotionUpdate promotionUpdate);
    void delete(Integer id);
    List<Promotion> getAll();
}
