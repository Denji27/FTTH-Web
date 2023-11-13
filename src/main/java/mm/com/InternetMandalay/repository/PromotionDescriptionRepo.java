package mm.com.InternetMandalay.repository;

import mm.com.InternetMandalay.entity.PromotionDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionDescriptionRepo extends JpaRepository<PromotionDescription, String> {
    PromotionDescription getPromotionDescriptionById(String id);
}
