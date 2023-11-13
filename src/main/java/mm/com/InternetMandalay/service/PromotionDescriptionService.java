package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.response.PromotionDescriptionDto;

public interface PromotionDescriptionService {
    PromotionDescriptionDto update(String content);
    PromotionDescriptionDto get();
}
