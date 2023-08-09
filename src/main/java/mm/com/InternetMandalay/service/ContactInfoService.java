package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.entity.ContactInfo;
import mm.com.InternetMandalay.request.ContactInfoUpdate;

public interface ContactInfoService {
    ContactInfo create();
    ContactInfo update(ContactInfoUpdate contactInfoUpdate);
    ContactInfo get();
}
