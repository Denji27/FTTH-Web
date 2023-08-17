package mm.com.InternetMandalay.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchRequest {
    private String contactPhone;
    private String ftthAccount;
}
