package mm.com.InternetMandalay.response;

import lombok.Data;

@Data
public class MessageHubLoginResponse {
    private String access_token;
    private int expires_in;
    private int refresh_expires_in;
    private String refresh_token;
    private String token_type;
    private String session_state;
}
