package mm.com.InternetMandalay.request;

import lombok.Data;

@Data
public class SmsRequest {
    public String source;
    public String dest;
    public String content;
}
