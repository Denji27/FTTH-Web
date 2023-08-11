package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.ContactInfo;
import mm.com.InternetMandalay.repository.ContactInfoRepo;
import mm.com.InternetMandalay.request.ContactInfoUpdate;
import mm.com.InternetMandalay.response.ContactInfoDTO;
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
    public ContactInfoDTO create() {
        ContactInfo contactInfo = ContactInfo.builder()
                .id(id)
                .hotline("hotline")
                .otherInfos("some other information")
                .build();
        contactInfoRepo.save(contactInfo);
        ContactInfoDTO con = new ContactInfoDTO();
        con.setHotline(contactInfo.getHotline());
        con.setOtherInfos(contactInfo.getOtherInfos());
        return con;
    }

    @Override
    public ContactInfoDTO update(ContactInfoUpdate contactInfoUpdate) {
        ContactInfo contactInfo = contactInfoRepo.getContactInfoById(id);
        contactInfo.setHotline(contactInfoUpdate.getHotline());
        contactInfo.setOtherInfos(contactInfoUpdate.getOtherInfos());
        contactInfoRepo.save(contactInfo);
        ContactInfoDTO con = new ContactInfoDTO();
        con.setHotline(contactInfo.getHotline());
        con.setOtherInfos(contactInfo.getOtherInfos());
        return con;
    }

    @Override
    public ContactInfoDTO get() {
        ContactInfo contactInfo = contactInfoRepo.getContactInfoById(id);
        ContactInfoDTO con = new ContactInfoDTO();
        con.setHotline(contactInfo.getHotline());
        con.setOtherInfos(contactInfo.getOtherInfos());
        return con;
    }
}
