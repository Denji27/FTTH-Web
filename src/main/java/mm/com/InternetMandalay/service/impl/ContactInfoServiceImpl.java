package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.ContactInfo;
import mm.com.InternetMandalay.repository.ContactInfoRepo;
import mm.com.InternetMandalay.request.ContactInfoUpdate;
import mm.com.InternetMandalay.service.ContactInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ContactInfoServiceImpl implements ContactInfoService {
    @Autowired
    private ContactInfoRepo contactInfoRepo;

    @Value("${ContactInfo.Id}")
    private String id;

    @Override
    public ContactInfo create() {
        ContactInfo contactInfo = ContactInfo.builder()
                .id(id)
                .hotline("hotline")
                .otherInfos("some other information")
                .build();
        return contactInfoRepo.save(contactInfo);
    }

    @Override
    public ContactInfo update(ContactInfoUpdate contactInfoUpdate) {
        ContactInfo contactInfo = contactInfoRepo.getContactInfoById(id);
        contactInfo.setHotline(contactInfoUpdate.getHotline());
        contactInfo.setOtherInfos(contactInfoUpdate.getOtherInfos());
        return contactInfoRepo.save(contactInfo);
    }

    @Override
    public ContactInfo get() {
        return contactInfoRepo.getContactInfoById(id);
    }
}
