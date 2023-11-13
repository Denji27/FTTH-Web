package mm.com.InternetMandalay.messageHubResponse;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Result {
    public String transactionId;
    public Date submittedDateTime;
    public int numberSegment;
    public List<RemainBalance> remainBalance;
}
