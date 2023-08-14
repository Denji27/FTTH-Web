package mm.com.InternetMandalay.controller;

import mm.com.InternetMandalay.request.NewCustomerRequest;
import mm.com.InternetMandalay.request.SearchRequest;
import mm.com.InternetMandalay.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AbnormalCaseService abnormalCaseService;
    @Autowired
    private ContactInfoService contactInfoService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private NewCustomerService newCustomerService;
    @Autowired
    private PaymentInstructionService paymentInstructionService;
    @Autowired
    private PromotionService promotionService;

    @GetMapping("/abnormal-case")
    public ResponseEntity<?> getAbnormalCase(){
        return ResponseEntity.ok(abnormalCaseService.get());
    }

    @GetMapping("/contact-info")
    public ResponseEntity<?> getContactInfo(){
        return ResponseEntity.ok(contactInfoService.get());
    }

    @GetMapping("/customer/search")
    public ResponseEntity<?> searchCustomer(@RequestBody SearchRequest searchRequest){
        return ResponseEntity.ok(customerService.search(searchRequest));
    }

    @PostMapping("/new-customer/register")
    public ResponseEntity<?> createNewCustomer(@RequestParam(defaultValue = "none") String collaboratorCode, @RequestBody NewCustomerRequest newCustomerRequest){
        return ResponseEntity.ok(newCustomerService.create(collaboratorCode,newCustomerRequest));
    }

    @GetMapping("/payment-instruction")
    public ResponseEntity<?> getPaymentInstruction(){
        return ResponseEntity.ok(paymentInstructionService.get());
    }

    @GetMapping("/promotions")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(promotionService.getAllDTO());
    }
}
