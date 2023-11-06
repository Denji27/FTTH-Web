package mm.com.InternetMandalay.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import mm.com.InternetMandalay.entity.Customer;
import mm.com.InternetMandalay.exception.BadRequestException;
import mm.com.InternetMandalay.exception.MessageHubException;
import mm.com.InternetMandalay.exception.NotFoundException;
import mm.com.InternetMandalay.exception.ParsingJsonException;
import mm.com.InternetMandalay.messageHubResponse.SuccessfulSmsResponse;
import mm.com.InternetMandalay.repository.CustomerRepo;
import mm.com.InternetMandalay.request.SmsRequest;
import mm.com.InternetMandalay.response.CustomerDTO;
import mm.com.InternetMandalay.service.CustomerService;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Value("${MessageHub.Token.Key}")
    private String messageHubToken;

    @Value("${MessageHub.Sms.BrandName}")
    private String messageHubBrandName;

    @Value("${MessageHub.Sms-api}")
    private String messageHubSmsApi;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

//    @Async
    @CacheEvict(value = "Customer", allEntries = true)
    @Override
    public void uploadData2(InputStream excelFile) {
        long startTime = System.nanoTime();
        List<Customer> customers = new ArrayList<>();
        try{
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // Skip the header row
                    continue;
                }
                Customer customer = new Customer();
                customer.setFtthAccount(row.getCell(0).getStringCellValue());
                if (row.getCell(1) == null){
                    customer.setCustomerName("");
                }else {
                    customer.setCustomerName(row.getCell(1).getStringCellValue());
                }
                if (row.getCell(2) == null){
                    customer.setCustomerAddress("");
                }else {
                    customer.setCustomerAddress(row.getCell(2).getStringCellValue());
                }
                customer.setContactPhone(row.getCell(3).getStringCellValue());
                if (row.getCell(4) == null){
                    customer.setProductCode("");
                } else {
                    customer.setProductCode(row.getCell(4).getStringCellValue());
                }
                if (row.getCell(5) == null){
                    customer.setMonthAdv("");
                } else {
                    customer.setMonthAdv(row.getCell(5).getStringCellValue());
                }
                if (row.getCell(6) == null){
                    customer.setTotalMoney("");
                } else {
                    CellType totalMoneyType = row.getCell(6).getCellType();
                    if (totalMoneyType == CellType.STRING){
                        customer.setTotalMoney(row.getCell(6).getStringCellValue());
                    }
                    if (totalMoneyType == CellType.NUMERIC){
                        Integer value = (int) row.getCell(6).getNumericCellValue();
                        customer.setTotalMoney(value.toString());
                    }
                }
                if (row.getCell(7) == null){
                    customer.setD2dName("");
                }else {
                    customer.setD2dName(row.getCell(7).getStringCellValue());
                }
                if (row.getCell(8) == null){
                    customer.setD2dPhoneNumber("");
                } else{
                    customer.setD2dPhoneNumber(row.getCell(8).getStringCellValue());
                }
                if (row.getCell(9) == null){
                    customer.setBillBlock("");
                } else {
                    customer.setBillBlock(row.getCell(9).getStringCellValue());
                }
                customers.add(customer);
            }
            workbook.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        int batchSize = 10000;
        for (int i=0; i< customers.size(); i += batchSize){
            int endIndex = Math.min(i + batchSize, customers.size());
            List<Customer> batch = customers.subList(1, endIndex);
            customerRepo.saveAll(batch);
        }
        long endTime = System.nanoTime();
        long duration = startTime - endTime;
        System.out.println("Time" + duration);
    }

    @Cacheable(value = "Customer", key = "#contactPhone + ':' + #ftthAccount")
    @Override
    public List<CustomerDTO> find(String contactPhone, String ftthAccount){
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        if(ftthAccount.isBlank() & contactPhone.isBlank()){
            throw new NotFoundException("You haven't entered your account or phone number!");
        }
        if(ftthAccount.isBlank() & !contactPhone.isBlank()){
            List<Customer> customers = customerRepo.findCustomerByContactPhone(contactPhone);
            if (customers.size() == 0){
                throw new NotFoundException("Your account is not exist");
            }
            for (Customer customer : customers){
                CustomerDTO customerDto = CustomerDTO.builder()
                        .ftthAccount(customer.getFtthAccount())
                        .customerName(customer.getCustomerName())
                        .customerAddress(customer.getCustomerAddress())
                        .contactPhone(customer.getContactPhone())
                        .productCode(customer.getProductCode())
                        .monthAdv(customer.getMonthAdv())
                        .totalMoney(customer.getTotalMoney())
                        .d2dName(customer.getD2dName())
                        .d2dPhoneNumber(customer.getD2dPhoneNumber())
                        .billBlock(customer.getBillBlock())
                        .build();
                customerDTOList.add(customerDto);
            }

            return customerDTOList;
        }
        if (!ftthAccount.isBlank() & contactPhone.isBlank()){
            List<Customer> customers = customerRepo.findCustomerByFtthAccount(ftthAccount);
            if (customers.size() == 0){
                throw new NotFoundException("Your account is not exist");
            }
            for (Customer customer : customers){
                CustomerDTO customerDto = CustomerDTO.builder()
                        .ftthAccount(customer.getFtthAccount())
                        .customerName(customer.getCustomerName())
                        .customerAddress(customer.getCustomerAddress())
                        .contactPhone(customer.getContactPhone())
                        .productCode(customer.getProductCode())
                        .monthAdv(customer.getMonthAdv())
                        .totalMoney(customer.getTotalMoney())
                        .d2dName(customer.getD2dName())
                        .d2dPhoneNumber(customer.getD2dPhoneNumber())
                        .billBlock(customer.getBillBlock())
                        .build();
                customerDTOList.add(customerDto);
            }
            return customerDTOList;
        }
        List<Customer> customers = customerRepo.findCustomerByFtthAccountAndContactPhone(ftthAccount, contactPhone);
        if (customers.size() == 0){
            throw new NotFoundException("Your account is not exist");
        }
        for (Customer customer : customers){
            CustomerDTO customerDto = CustomerDTO.builder()
                    .ftthAccount(customer.getFtthAccount())
                    .customerName(customer.getCustomerName())
                    .customerAddress(customer.getCustomerAddress())
                    .contactPhone(customer.getContactPhone())
                    .productCode(customer.getProductCode())
                    .monthAdv(customer.getMonthAdv())
                    .totalMoney(customer.getTotalMoney())
                    .d2dName(customer.getD2dName())
                    .d2dPhoneNumber(customer.getD2dPhoneNumber())
                    .billBlock(customer.getBillBlock())
                    .build();
            customerDTOList.add(customerDto);
        }
        return customerDTOList;
    }

    @Override
    public void isValidMockOtp(String contactPhone, String otp) {
        if (contactPhone.isBlank() || otp.isBlank()){
            throw new BadRequestException("Please enter both your phone number and otp");
        }
        if (!otp.equals("12345")){
            throw new BadRequestException("The OTP is in valid");
        }
    }


//    @Override
//    public List<CustomerDTO> search(String contactPhone, String ftthAccount, String otp) {
//        isValidOtp(contactPhone, otp);
//        return find(contactPhone, ftthAccount);
//    }

    @Override
    public String getOtp(String contactPhone) {
        if (contactPhone.isBlank()){
            throw new NotFoundException("You haven't entered phone number yet!");
        }
        List<Customer> customers = customerRepo.findCustomerByContactPhone(contactPhone);
        if (customers.size() == 0){
            throw new NotFoundException("Phone number is not included in our system!");
        }
        redisTemplate.delete(contactPhone);
        redisTemplate.opsForValue().set(contactPhone, generateOtp(), 120, TimeUnit.SECONDS);
        System.out.println("OTP: " + redisTemplate.opsForValue().get(contactPhone));

        String tokenForMessageHubApi = redisTemplate.opsForValue().get(messageHubToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenForMessageHubApi);

        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setBrandName(messageHubBrandName);
        smsRequest.setSendNow(true);
        smsRequest.setScheduleDate(null);
        smsRequest.setContent("This is a test message");
        // todo set phone No again
        smsRequest.setPhoneNumber("09686454616");

        HttpEntity<SmsRequest> requestHttpEntity = new HttpEntity<>(smsRequest, headers);
        // todo: check the request again
        ResponseEntity<?> response = restTemplate.exchange(
                messageHubSmsApi,
                HttpMethod.POST,
                requestHttpEntity,
                String.class
        );
        if (response.getStatusCode().is2xxSuccessful()){
            String responseBody = response.getBody().toString();
            System.out.println("response body of mess hub" + responseBody);
            try{
                ObjectMapper objectMapper = new ObjectMapper();
                SuccessfulSmsResponse successfulSmsResponse = objectMapper.readValue(responseBody, SuccessfulSmsResponse.class);
                if (successfulSmsResponse.getResult().isSuccessful() == true){
                    return "The otp has been sent to your phone number, please check it and fill it to the blank for OTP";
                }else {
                    throw new MessageHubException(successfulSmsResponse.getResult().getErrorCode() + ": " + successfulSmsResponse.getResult().getErrorMessage());
                }
            }catch (Exception e){
                throw new ParsingJsonException("In sending sms Error parsing JSON response: " + e.getMessage());
            }
        }
        int messError = response.getStatusCodeValue();
        throw new MessageHubException("Request failed with status code: " + messError);
    }

    private String generateOtp(){
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }

    @Override
    public void isValidOtp(String contactPhone, String otp){
        if (contactPhone.isBlank() || otp.isBlank()){
            throw new BadRequestException("Please enter both your phone number and otp");
        }
        if (redisTemplate.opsForValue().get(contactPhone) == null){
            throw new BadRequestException("The OTP is expired or you haven't get OTP yet, please get OTP and fill in the blank");
        }
        if (!otp.equals(redisTemplate.opsForValue().get(contactPhone))){
            throw new BadRequestException("The OTP is in valid");
        }
    }

    @Override
    public String getMockOtp(String contactPhone) {
        if (contactPhone.isBlank()){
            throw new NotFoundException("You haven't entered phone number yet!");
        }
        List<Customer> customers = customerRepo.findCustomerByContactPhone(contactPhone);
        if (customers.size() == 0){
            throw new NotFoundException("Phone number is not included in our system!");
        }
        return "The otp has been sent to your phone number, please check it and fill it to the blank for OTP";
    }

    @CacheEvict(value = "Customer", allEntries = true)
    @Override
    public void resetCustomerData() {
        customerRepo.deleteAll();
    }

    @Override
    public void validateDatabase() {
        if (customerRepo.findAll().size() == 0){
            throw new BadRequestException("There is no data in database anymore!");
        }
    }

}
