package mm.com.InternetMandalay.request;

import lombok.Data;

@Data
public class SmsRequest {
    public String brandName;
    public boolean isSendNow;
    public Object scheduleDate;
    public String content;
    public String phoneNumber;
}
