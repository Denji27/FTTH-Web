package mm.com.InternetMandalay.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import mm.com.InternetMandalay.entity.Customer;
import mm.com.InternetMandalay.entity.RepairRequest;
import mm.com.InternetMandalay.exception.BadRequestException;
import mm.com.InternetMandalay.exception.MessageHubException;
import mm.com.InternetMandalay.exception.NotFoundException;
import mm.com.InternetMandalay.exception.ParsingJsonException;
import mm.com.InternetMandalay.messageHubResponse.SuccessfulSmsResponse;
import mm.com.InternetMandalay.repository.CustomerRepo;
import mm.com.InternetMandalay.repository.RepairRequestRepo;
import mm.com.InternetMandalay.request.SmsRequest;
import mm.com.InternetMandalay.response.CustomerDTO;
import mm.com.InternetMandalay.service.RepairRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class RepairRequestServiceImpl implements RepairRequestService {
    @Value("${MessageHub.Token.Key}")
    private String messageHubToken;

    @Value("${MessageHub.Sms.BrandName}")
    private String source;

    @Value("${MessageHub.Sms-api}")
    private String messageHubSmsApi;

    @Autowired
    private RepairRequestRepo repairRequestRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @CacheEvict(value = "Repair-request", allEntries = true)
    @Override
    @Transactional
    public String submitRepairRequest(String contactPhone, String ftthAccount) {
        if(contactPhone.isBlank() & ftthAccount.isBlank()){
            throw new BadRequestException("You haven't enter information yet, please enter both ftthAccount and contactPhone before submitting!");
        }
        if(!contactPhone.isBlank() & ftthAccount.isBlank()){
            throw new BadRequestException("You haven't enter ftthAccount yet, please enter both ftthAccount and contactPhone before submitting!");
        }
        if(contactPhone.isBlank() & !ftthAccount.isBlank()){
            throw new BadRequestException("You haven't enter contactPhone yet, please enter both ftthAccount and contactPhone before submitting!");
        }
//        if (customerRepo.findCustomerByFtthAccountAndContactPhone(ftthAccount, contactPhone).size() == 0){
//            throw new BadRequestException("Your information is not correct! Your account is not existing in our system, contact us via hotline to get a support!");
//        }
        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setFtthAccount(ftthAccount);
        repairRequest.setContactPhone(contactPhone);
        repairRequestRepo.save(repairRequest);
        return "Request successfully";
    }

//    @Cacheable(value = "Customer", key = "#contactPhone + ':' + #ftthAccount")
//    @Override
//    public List<CustomerDTO> checkCustomerInformation(String contactPhone, String ftthAccount) {
//        List<CustomerDTO> customerDTOList = new ArrayList<>();
//        if(ftthAccount.isBlank() & contactPhone.isBlank()){
//            throw new NotFoundException("You haven't entered your account or phone number!");
//        }
//        if(ftthAccount.isBlank() & !contactPhone.isBlank()){
//            List<Customer> customers = customerRepo.findCustomerByContactPhone(contactPhone);
//            if (customers.size() == 0){
//                throw new NotFoundException("Your account is not exist");
//            }
//            for (Customer customer : customers){
//                CustomerDTO customerDto = CustomerDTO.builder()
//                        .ftthAccount(customer.getFtthAccount())
//                        .customerName(customer.getCustomerName())
//                        .customerAddress(customer.getCustomerAddress())
//                        .contactPhone(customer.getContactPhone())
//                        .productCode(customer.getProductCode())
//                        .monthAdv(customer.getMonthAdv())
//                        .totalMoney(customer.getTotalMoney())
//                        .d2dName(customer.getD2dName())
//                        .d2dPhoneNumber(customer.getD2dPhoneNumber())
//                        .billBlock(customer.getBillBlock())
//                        .build();
//                customerDTOList.add(customerDto);
//            }
//
//            return customerDTOList;
//        }
//        if (!ftthAccount.isBlank() & contactPhone.isBlank()){
//            List<Customer> customers = customerRepo.findCustomerByFtthAccount(ftthAccount);
//            if (customers.size() == 0){
//                throw new NotFoundException("Your account is not exist");
//            }
//            for (Customer customer : customers){
//                CustomerDTO customerDto = CustomerDTO.builder()
//                        .ftthAccount(customer.getFtthAccount())
//                        .customerName(customer.getCustomerName())
//                        .customerAddress(customer.getCustomerAddress())
//                        .contactPhone(customer.getContactPhone())
//                        .productCode(customer.getProductCode())
//                        .monthAdv(customer.getMonthAdv())
//                        .totalMoney(customer.getTotalMoney())
//                        .d2dName(customer.getD2dName())
//                        .d2dPhoneNumber(customer.getD2dPhoneNumber())
//                        .billBlock(customer.getBillBlock())
//                        .build();
//                customerDTOList.add(customerDto);
//            }
//            return customerDTOList;
//        }
//        List<Customer> customers = customerRepo.findCustomerByFtthAccountAndContactPhone(ftthAccount, contactPhone);
//        if (customers.size() == 0){
//            throw new NotFoundException("Your account is not exist");
//        }
//        for (Customer customer : customers){
//            CustomerDTO customerDto = CustomerDTO.builder()
//                    .ftthAccount(customer.getFtthAccount())
//                    .customerName(customer.getCustomerName())
//                    .customerAddress(customer.getCustomerAddress())
//                    .contactPhone(customer.getContactPhone())
//                    .productCode(customer.getProductCode())
//                    .monthAdv(customer.getMonthAdv())
//                    .totalMoney(customer.getTotalMoney())
//                    .d2dName(customer.getD2dName())
//                    .d2dPhoneNumber(customer.getD2dPhoneNumber())
//                    .billBlock(customer.getBillBlock())
//                    .build();
//            customerDTOList.add(customerDto);
//        }
//        return customerDTOList;
//    }
//
//    @Override
//    public String getMockOtp(String contactPhone) {
//        if (contactPhone.isBlank()){
//            throw new NotFoundException("You haven't entered phone number yet!");
//        }
//        List<Customer> customers = customerRepo.findCustomerByContactPhone(contactPhone);
//        if (customers.size() == 0){
//            throw new NotFoundException("Phone number is not included in our system!");
//        }
//        return "The otp has been sent to your phone number, please check it and fill it to the blank for OTP";
//    }
//
//    private String generateOtp(){
//        return new DecimalFormat("000000").format(new Random().nextInt(999999));
//    }
//
//    @Override
//    public String getOtp(String contactPhone) {
//        if (contactPhone.isBlank()){
//            throw new NotFoundException("You haven't entered phone number yet!");
//        }
//        List<Customer> customers = customerRepo.findCustomerByContactPhone(contactPhone);
//        if (customers.size() == 0){
//            throw new NotFoundException("Phone number is not included in our system!");
//        }
//        redisTemplate.delete(contactPhone);
//        redisTemplate.opsForValue().set(contactPhone, generateOtp(), 120, TimeUnit.SECONDS);
//        System.out.println("OTP: " + redisTemplate.opsForValue().get(contactPhone));
//
//        String tokenForMessageHubApi = redisTemplate.opsForValue().get(messageHubToken);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer " + tokenForMessageHubApi);
//
//        SmsRequest smsRequest = new SmsRequest();
//        smsRequest.setSource(source);
//        smsRequest.setContent("Your OTP is " + redisTemplate.opsForValue().get(contactPhone) + ". Your OTP expires in 2 minutes. Please don’t reply");
//        // todo set phone No again
//        smsRequest.setDest(contactPhone);
//
//        HttpEntity<SmsRequest> requestHttpEntity = new HttpEntity<>(smsRequest, headers);
//        // todo: check the request again
//        ResponseEntity<?> response = restTemplate.exchange(
//                messageHubSmsApi,
//                HttpMethod.POST,
//                requestHttpEntity,
//                String.class
//        );
//        if (response.getStatusCode().is2xxSuccessful()){
//            String responseBody = response.getBody().toString();
//            System.out.println("response body of mess hub" + responseBody);
//            try{
//                ObjectMapper objectMapper = new ObjectMapper();
//                SuccessfulSmsResponse successfulSmsResponse = objectMapper.readValue(responseBody, SuccessfulSmsResponse.class);
//                if (successfulSmsResponse.getErrorCode() == 0){
//                    return "The otp has been sent to your phone number, please check it and fill it to the blank for OTP";
//                }else {
//                    throw new MessageHubException(successfulSmsResponse.getErrorCode()+"");
//                }
//            }catch (Exception e){
//                throw new ParsingJsonException("In sending sms Error parsing JSON response: " + e.getMessage());
//            }
//        }
//        else {
//            int messError = response.getStatusCodeValue();
//            throw new MessageHubException("Request failed with status code: " + messError);
//        }
//    }
//
//    @Override
//    public void isValidMockOtp(String contactPhone, String otp) {
//        if (contactPhone.isBlank() || otp.isBlank()){
//            throw new BadRequestException("Please enter both your phone number and otp");
//        }
//        if (!otp.equals("12345")){
//            throw new BadRequestException("The OTP is in valid");
//        }
//    }
//
//    @Override
//    public void isValidOtp(String contactPhone, String otp) {
//        if (contactPhone.isBlank() || otp.isBlank()){
//            throw new BadRequestException("Please enter both your phone number and otp");
//        }
//        if (redisTemplate.opsForValue().get(contactPhone) == null){
//            throw new BadRequestException("The OTP is expired or you haven't get OTP yet, please get OTP and fill in the blank");
//        }
//        if (!otp.equals(redisTemplate.opsForValue().get(contactPhone))){
//            throw new BadRequestException("The OTP is in valid");
//        }
//    }

    @Cacheable(value = "Repair-request", key = "'repair_request' + #root.methodName")
    @Override
    public List<RepairRequest> getAllRepairRequest() {
        return repairRequestRepo.findAll();
    }

    @CacheEvict(value = "Repair-request", allEntries = true)
    @Override
    public void deleteAllRequest() {
        repairRequestRepo.deleteAll();
    }
}
