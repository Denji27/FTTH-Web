package mm.com.InternetMandalay.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import mm.com.InternetMandalay.exception.MessageHubException;
import mm.com.InternetMandalay.exception.ParsingJsonException;
import mm.com.InternetMandalay.response.MessageHubLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Service
public class MessageHubTokenService {
    @Value("${MessageHub.Auth-api}")
    private String messageHubAuthApi;

    @Value("${MessageHub.Account}")
    private String messageHubAccount;

    @Value("${MessageHub.Password}")
    private String messageHubPassword;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(initialDelay = 0, fixedDelay = 82800000)
    public void saveMessageHubToken(){
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type","client_credentials");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String username = "mdybranch";
        String password = "07398986-6c07-47f3-a442-a923ee58ca26";
        String authString = username + ":" + password;
        byte[] authBytes = authString.getBytes(StandardCharsets.UTF_8);
        byte[] encodedBytes = Base64.getEncoder().encode(authBytes);
        String authHeader = "Basic " + new String(encodedBytes);
        headers.set("Authorization", authHeader);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

        ResponseEntity<?> response = restTemplate.exchange(
                "https://mpg-ids.mytel.com.mm/auth/realms/eis/protocol/openid-connect/token",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        if (response.getStatusCode().is2xxSuccessful()){
            String responseBody = response.getBody().toString().replace("\"not-before-policy\":0,","");
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                MessageHubLoginResponse messageHubLoginResponse = objectMapper.readValue(responseBody, MessageHubLoginResponse.class);
                redisTemplate.delete("Message_hub_token");
                redisTemplate.opsForValue().set("Message_hub_token", messageHubLoginResponse.getAccess_token(), 24, TimeUnit.HOURS);
                System.out.println("Token: " + redisTemplate.opsForValue().get("Message_hub_token"));
            } catch (Exception e){
                throw new ParsingJsonException("Error parsing JSON response: " + e.getMessage());
            }
        } else {
            int messError = response.getStatusCodeValue();
            throw new MessageHubException("Request failed with status code: " + messError);
        }
    }
}
