package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.entity.AbnormalCase;
import mm.com.InternetMandalay.request.AbnormalCaseUpdate;

public interface AbnormalCaseService {
    AbnormalCase create();
    AbnormalCase update(AbnormalCaseUpdate abnormalCaseUpdate);
    AbnormalCase get();
}
