package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.entity.Promotion;
import mm.com.InternetMandalay.request.PromotionUpdate;
import mm.com.InternetMandalay.response.PromotionDTO;

import java.util.List;

public interface PromotionService {
    PromotionDTO create();
    PromotionDTO update(Integer id, PromotionUpdate promotionUpdate);
    void delete(Integer id);
    List<PromotionDTO> getAll();
}
