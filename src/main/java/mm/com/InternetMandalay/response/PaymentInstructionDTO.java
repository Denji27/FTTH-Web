package mm.com.InternetMandalay.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentInstructionDTO implements Serializable {
    private String title;
    private String description;
}
