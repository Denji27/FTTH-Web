package mm.com.InternetMandalay.messageHubResponse;

import lombok.Data;

@Data
public class Result {
    public DataResult dataResult;
    public boolean isSuccessful;
    public String errorCode;
    public String errorMessage;
}
