package mm.com.InternetMandalay.repository;

import mm.com.InternetMandalay.entity.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactInfoRepo extends JpaRepository<ContactInfo, String> {
    ContactInfo getContactInfoById(String id);
    Boolean existsContactInfoById(String id);
}
