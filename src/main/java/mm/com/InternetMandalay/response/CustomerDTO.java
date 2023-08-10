package mm.com.InternetMandalay.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CustomerDTO implements Serializable {
    private String customerId;
    private String name;
    private String account;
    private String phoneNumber;
    private String serviceName;
    private Integer numberOfPaidMonths;
    private Date extensionDate;
    private Date internetBlockingDate;
}
