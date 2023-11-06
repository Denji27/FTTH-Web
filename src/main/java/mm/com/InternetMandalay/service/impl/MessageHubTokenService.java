package mm.com.InternetMandalay.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import mm.com.InternetMandalay.exception.MessageHubException;
import mm.com.InternetMandalay.exception.ParsingJsonException;
import mm.com.InternetMandalay.request.MessageHubLoginRequest;
import mm.com.InternetMandalay.response.MessageHubLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.criteria.CriteriaBuilder;
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        MessageHubLoginRequest request = new MessageHubLoginRequest();
        request.setUserNameOrEmailAddress(messageHubAccount);
        request.setPassword(messageHubPassword);

        HttpEntity<MessageHubLoginRequest> requestHttpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<?> response = restTemplate.exchange(
                messageHubAuthApi,
                HttpMethod.POST,
                requestHttpEntity,
                String.class
        );
        if (response.getStatusCode().is2xxSuccessful()){
            String responseBody = response.getBody().toString();
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                MessageHubLoginResponse messageHubLoginResponse = objectMapper.readValue(responseBody, MessageHubLoginResponse.class);
                redisTemplate.delete("Message_hub_token");
                redisTemplate.opsForValue().set("Message_hub_token", messageHubLoginResponse.getResult().getAccessToken(), 24, TimeUnit.HOURS);
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
