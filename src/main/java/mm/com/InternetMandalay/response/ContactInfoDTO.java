package mm.com.InternetMandalay.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ContactInfoDTO implements Serializable {
    private String hotline;
    private String otherInfos;
}
