package mm.com.InternetMandalay.repository;

import mm.com.InternetMandalay.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepo extends JpaRepository<Banner, Integer> {
    Banner getBannerById(Integer id);
}
