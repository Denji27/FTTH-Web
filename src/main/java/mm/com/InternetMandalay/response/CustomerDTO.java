package mm.com.InternetMandalay.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CustomerDTO implements Serializable {
    private String ftthAccount;
    private String customerName;
    private String customerAddress;
    private String contactPhone;
    private String productCode;
    private String monthAdv;
    private String totalMoney;
    private String d2dName;
    private String d2dPhoneNumber;
    private String billBlock;
}
