package mm.com.InternetMandalay.messageHubResponse;

import lombok.Data;

@Data
public class SuccessfulSmsResponse{
    public int errorCode;
    public Result result;
    public String message;
}


