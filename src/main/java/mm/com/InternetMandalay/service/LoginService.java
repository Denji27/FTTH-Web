package mm.com.InternetMandalay.service;

import mm.com.InternetMandalay.request.LoginRequest;
import mm.com.InternetMandalay.response.LoginResponse;
import mm.com.InternetMandalay.response.LogoutResponse;
import mm.com.InternetMandalay.request.TokenRequest;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    ResponseEntity<LoginResponse> login(LoginRequest request);
    ResponseEntity<LogoutResponse> logout(TokenRequest tokenRequest);
    ResponseEntity<?> refreshToken(TokenRequest tokenRequest);
}
