package mm.com.InternetMandalay.repository;

import mm.com.InternetMandalay.entity.AbnormalCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbnormalCaseRepo extends JpaRepository<AbnormalCase, Integer> {
    AbnormalCase getAbnormalCaseById(String id);
    Boolean existsById(String id);
}
