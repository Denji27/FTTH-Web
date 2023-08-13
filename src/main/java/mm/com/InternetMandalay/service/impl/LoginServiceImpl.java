//package mm.com.InternetMandalay.service.impl;
//
//import mm.com.InternetMandalay.exception.LoginException;
//import mm.com.InternetMandalay.exception.BadRequestException;
//import mm.com.InternetMandalay.request.LoginRequest;
//import mm.com.InternetMandalay.response.LoginResponse;
//import mm.com.InternetMandalay.response.LogoutResponse;
//import mm.com.InternetMandalay.request.TokenRequest;
//import mm.com.InternetMandalay.service.LoginService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class LoginServiceImpl implements LoginService {
//    @Autowired
//    RestTemplate restTemplate;
//
//    @Value("${keycloak.auth-server-url}")
//    private String issueUrl;
//
//    @Value("${keycloak.resource}")
//    private String clientId;
//
//    @Value("${keycloak.credentials.secret}")
//    private String clientSecret;
//
//    @Value("${auth-grant-type}")
//    private String authGrantType;
//
//    @Value("${refresh-token-grant-type}")
//    private String rtGrantType;
//
//    @Value("${token-url}")
//    private String tokenEndpoint;
//
//    @Value("${logout-url}")
//    private String logoutEndpoint;
//
//
//    @Override
//    public ResponseEntity<LoginResponse> login(LoginRequest request) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//            map.add("client_id", clientId);
//            map.add("client_secret", clientSecret);
//            map.add("grant_type", authGrantType);
//            map.add("username", request.getUsername());
//            map.add("password", request.getPassword());
//            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map,headers);
//            ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
//                    tokenEndpoint,
//                    httpEntity,
//                    LoginResponse.class
//            );
//            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
//        }catch (Exception e){
//            throw new LoginException("Your Username or Password is wrong! Please enter your username and password again");
//        }
//    }
//
//    @Override
//    public ResponseEntity<LogoutResponse> logout(TokenRequest tokenRequest) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("client_id", clientId);
//        map.add("client_secret", clientSecret);
//        map.add("refresh_token", tokenRequest.getRefreshToken());
//
//        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map,headers);
//
//        ResponseEntity<LogoutResponse> response = restTemplate.postForEntity(
//                logoutEndpoint,
//                httpEntity,
//                LogoutResponse.class
//        );
//
//        LogoutResponse res = new LogoutResponse();
//        if(response.getStatusCode().is2xxSuccessful()) {
//            res.setMessage("Logged out successfully");
//        }
//        return new ResponseEntity<>(res,HttpStatus.OK);
//    }
//
//    @Override
//    public ResponseEntity<?> refreshToken(TokenRequest tokenRequest) {
//        try{
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//            map.add("client_id", clientId);
//            map.add("client_secret", clientSecret);
//            map.add("grant_type", rtGrantType);
//            map.add("refresh_token", tokenRequest.getRefreshToken());
//            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map,headers);
//            ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
//                    tokenEndpoint,
//                    httpEntity,
//                    LoginResponse.class);
//            return new ResponseEntity<>(response.getBody(),HttpStatus.OK);
//        }catch (Exception e){
//            throw new BadRequestException("Session Expired");
//        }
//    }
//}
