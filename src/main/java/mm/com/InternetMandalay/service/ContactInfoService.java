package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.entity.ContactInfo;
import mm.com.InternetMandalay.request.ContactInfoUpdate;
import mm.com.InternetMandalay.response.ContactInfoDTO;

public interface ContactInfoService {
    ContactInfoDTO create();
    ContactInfoDTO update(ContactInfoUpdate contactInfoUpdate);
    ContactInfoDTO get();
}
