package mm.com.InternetMandalay.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCustomerRequest {
    private String name;
    private String phoneNumber;
    private String serviceName;
    private String address;
}
