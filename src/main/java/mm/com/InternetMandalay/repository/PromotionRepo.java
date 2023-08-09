package mm.com.InternetMandalay.repository;

import mm.com.InternetMandalay.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepo extends JpaRepository<Promotion, Integer> {
    Promotion getPromotionById(Integer id);
}
