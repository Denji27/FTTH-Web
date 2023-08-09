package mm.com.InternetMandalay.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionUpdate {
    private String name;
    private String speed;
    private Integer price;
    private String promotion;
}
