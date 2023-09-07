package mm.com.InternetMandalay.repository;

import mm.com.InternetMandalay.entity.AbnormalCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AbnormalCaseRepo extends JpaRepository<AbnormalCase, Integer> {
    AbnormalCase getAbnormalCaseById(String id);
    Boolean existsById(String id);
}
