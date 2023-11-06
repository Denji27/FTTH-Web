package mm.com.InternetMandalay.response;

import lombok.Data;

@Data
public class Result {
    public String accessToken;
    public int expireInSeconds;
    public boolean isError;
    public String message;
}
