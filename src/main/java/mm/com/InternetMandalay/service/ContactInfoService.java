package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.request.ContactInfoUpdate;
import mm.com.InternetMandalay.response.ContactInfoDTO;

public interface ContactInfoService {
    ContactInfoDTO update(ContactInfoUpdate contactInfoUpdate);
    ContactInfoDTO get();
}
