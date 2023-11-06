package mm.com.InternetMandalay.request;

import lombok.Data;

@Data
public class MessageHubLoginRequest {
    private String userNameOrEmailAddress;
    private String password;
}
