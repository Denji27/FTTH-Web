package mm.com.InternetMandalay.repository;

import mm.com.InternetMandalay.entity.PromotionImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionImageRepo extends JpaRepository<PromotionImage, Integer> {
    PromotionImage getPromotionImageById(Integer id);
}
