package mm.com.InternetMandalay.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AbnormalCaseUpdate {
    private String title;
    private String image;
    private String description;
}
