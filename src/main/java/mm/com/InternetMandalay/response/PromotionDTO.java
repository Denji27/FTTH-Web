package mm.com.InternetMandalay.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class PromotionDTO implements Serializable {
    private String name;
    private String speed;
    private Integer price;
    private String promotion;
}
