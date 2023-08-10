package mm.com.InternetMandalay.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class AbnormalCaseDTO implements Serializable {
    private String title;
    private String image;
    private String description;
}
