package mm.com.InternetMandalay.service.impl;

import mm.com.InternetMandalay.entity.Customer;
import mm.com.InternetMandalay.entity.PaymentRequest;
import mm.com.InternetMandalay.exception.BadRequestException;
import mm.com.InternetMandalay.exception.NotFoundException;
import mm.com.InternetMandalay.repository.CustomerRepo;
import mm.com.InternetMandalay.repository.PaymentRequestRepo;
import mm.com.InternetMandalay.response.CustomerDTO;
import mm.com.InternetMandalay.service.PaymentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentRequestServiceImpl implements PaymentRequestService {
    @Autowired
    private PaymentRequestRepo paymentRequestRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public String submitPaymentReuqest(String contactPhone, String ftthAccount) {
        if(contactPhone.isBlank() & ftthAccount.isBlank()){
            throw new BadRequestException("You haven't enter information yet, please enter both ftthAccount and contactPhone before submitting!");
        }
        if(!contactPhone.isBlank() & ftthAccount.isBlank()){
            throw new BadRequestException("You haven't enter ftthAccount yet, please enter both ftthAccount and contactPhone before submitting!");
        }
        if(contactPhone.isBlank() & !ftthAccount.isBlank()){
            throw new BadRequestException("You haven't enter contactPhone yet, please enter both ftthAccount and contactPhone before submitting!");
        }
        if (customerRepo.findCustomerByFtthAccountAndContactPhone(ftthAccount, contactPhone).size() == 0){
            throw new BadRequestException("Your information is not correct! Your account is not existing in our system, contact us via hotline to get a support!");
        }
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setContactPhone(contactPhone);
        paymentRequest.setFtthAccount(ftthAccount);
        paymentRequestRepo.save(paymentRequest);
        return "Request successfully";
    }

    @Override
    public List<CustomerDTO> checkCustomerInformation(String contactPhone, String ftthAccount, String otp) {
        if (!otp.equals("12345") || otp.isBlank()){
            throw new BadRequestException("The OTP is invalid");
        }
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
    public String getOtp(String phoneNumber) {
        if (phoneNumber.isBlank()){
            throw new NotFoundException("You haven't entered phone number yet!");
        }
        List<Customer> customers = customerRepo.findCustomerByContactPhone(phoneNumber);
        if (customers.size() == 0){
            throw new NotFoundException("Phone number is not included in our system!");
        }
        return "The otp has been sent to your phone number, please check it and fill it to the blank for OTP";
    }

    @Override
    public List<PaymentRequest> getAllPaymentRequestOfCustomers() {
        return paymentRequestRepo.findAll();
    }

    @Override
    public void deleteAllRequest() {
        paymentRequestRepo.deleteAll();
    }


}
