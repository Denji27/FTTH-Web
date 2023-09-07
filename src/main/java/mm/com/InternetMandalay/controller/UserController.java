package mm.com.InternetMandalay.controller;

import mm.com.InternetMandalay.entity.PaymentRequest;
import mm.com.InternetMandalay.request.NewCustomerRequest;
import mm.com.InternetMandalay.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
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
    private PaymentRequestService paymentRequestService;
    @Autowired
    private PromotionImageService promotionImageService;
    @Autowired
    private RepairRequestService repairRequestService;
    @Autowired
    private BannerService bannerService;

    @GetMapping("/abnormal-case")
    public ResponseEntity<?> getAbnormalCase(){
        return ResponseEntity.ok(abnormalCaseService.get());
    }

    @GetMapping("/contact-info")
    public ResponseEntity<?> getContactInfo(){
        return ResponseEntity.ok(contactInfoService.get());
    }

    @GetMapping("/customer/search")
    public ResponseEntity<?> searchCustomer(@RequestParam String contactPhone, @RequestParam String ftthAccount, @RequestParam String otp){
        return ResponseEntity.ok(customerService.search(contactPhone, ftthAccount, otp));
    }

    @GetMapping("/customer/otp")
    public ResponseEntity<?> getCustomerOtp(@RequestParam String contactPhone){
        return ResponseEntity.ok(customerService.getOtp(contactPhone));
    }

    @PostMapping("/new-customer/register")
    public ResponseEntity<?> createNewCustomer(@RequestParam(defaultValue = "none") String collaboratorCode, @RequestBody NewCustomerRequest newCustomerRequest){
        return ResponseEntity.ok(newCustomerService.create(collaboratorCode,newCustomerRequest));
    }

    @PostMapping("/payment-request/submit")
    public ResponseEntity<?> submit(@RequestParam String contactPhone, @RequestParam String ftthAccount){
        return ResponseEntity.ok(paymentRequestService.submitPaymentReuqest(contactPhone, ftthAccount));
    }

    @GetMapping("/payment-request/otp")
    public ResponseEntity<?> getPaymentRequestOtp(@RequestParam String contactPhone){
        return ResponseEntity.ok(paymentRequestService.getOtp(contactPhone));
    }

    @PostMapping("/payment-request/check")
    public ResponseEntity<?> checkInformation(@RequestParam String contactPhone, @RequestParam String ftthAccount, @RequestParam String otp){
        return ResponseEntity.ok(paymentRequestService.checkCustomerInformation(contactPhone, ftthAccount, otp));
    }

    @GetMapping("/payment-instruction")
    public ResponseEntity<?> getPaymentInstruction(){
        return ResponseEntity.ok(paymentInstructionService.get());
    }

    @GetMapping("/promotion-image")
    public ResponseEntity<?> getAllPromotionImage(){
        return ResponseEntity.ok(promotionImageService.getAll());
    }

    @GetMapping("/banner")
    public ResponseEntity<?> getAllBanner(){
        return ResponseEntity.ok(bannerService.getAll());
    }

    @PostMapping("/repair-request/submit")
    public ResponseEntity<?> submitRepair(@RequestParam String contactPhone, @RequestParam String ftthAccount){
        return ResponseEntity.ok(repairRequestService.submitRepairRequest(contactPhone, ftthAccount));
    }

    @PostMapping("/repair-request/check")
    public ResponseEntity<?> checkRepair(@RequestParam String contactPhone, @RequestParam String ftthAccount, @RequestParam String otp){
        return ResponseEntity.ok(repairRequestService.checkCustomerInformation(contactPhone, ftthAccount, otp));
    }

    @GetMapping("/repair-request/otp")
    public ResponseEntity<?> getRepairOtp(@RequestParam String contactPhone){
        return ResponseEntity.ok(repairRequestService.getOtp(contactPhone));
    }
}
