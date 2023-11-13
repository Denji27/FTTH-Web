package mm.com.InternetMandalay.controller;

import mm.com.InternetMandalay.entity.NewCustomer;
import mm.com.InternetMandalay.entity.PaymentRequest;
import mm.com.InternetMandalay.entity.RepairRequest;
import mm.com.InternetMandalay.request.ContactInfoUpdate;
import mm.com.InternetMandalay.service.*;
import mm.com.InternetMandalay.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@Secured("ROLE_admin")
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AbnormalCaseService abnormalCaseService;
    @Autowired
    private ContactInfoService contactInfoService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private NewCustomerService newCustomerService;
    @Autowired
    private ExcelUtils excelUtils;
    @Autowired
    private PaymentInstructionService paymentInstructionService;
    @Autowired
    private PaymentRequestService paymentRequestService;
    @Autowired
    private AbnormalCaseImageService abImageService;
    @Autowired
    private PaymentInstructionImageService piImageService;
    @Autowired
    private PromotionImageService promotionImageService;
    @Autowired
    private PromotionDescriptionService promotionDescriptionService;
    @Autowired
    private RepairRequestService repairRequestService;
    @Autowired
    private BannerService bannerService;

    /** Abnormal Case */
    @PostMapping("/abnormal-case/update")
    public ResponseEntity<?> updateAbnormalCase(
                                    @RequestParam("title") String title,
                                    @RequestParam("description") String description)
    {
        return ResponseEntity.ok(abnormalCaseService.update(title, description));
    }
    @GetMapping("/abnormal-case/get")
    public ResponseEntity<?> getAbnormalCase(){
        return ResponseEntity.ok(abnormalCaseService.get());
    }

    @PostMapping("/abnormal-case-image/upload")
    public ResponseEntity<?> uploadAbImage(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(abImageService.uploadImage(file));
    }

    /** Contact Info */
    @PostMapping("/contact-info/update")
    public ResponseEntity<?> updateContactInfo(@RequestBody ContactInfoUpdate contactInfoUpdate){
        return ResponseEntity.ok(contactInfoService.update(contactInfoUpdate));
    }

    @GetMapping("/contact-info/get")
    public ResponseEntity<?> getContactInfo(){
        return ResponseEntity.ok(contactInfoService.get());
    }

    /** Customer */
    @PostMapping("/customer/upload")
    public ResponseEntity<String> uploadCustomerExcel(@RequestParam("file") MultipartFile file){
        try {
            customerService.uploadData2(file.getInputStream());
            return ResponseEntity.ok("Excel data imported successfully!! :D");
        } catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to import Excel data: " + e.getMessage());
        }
    }

    @PostMapping("/customer/reset-customer-data")
    public ResponseEntity<?> resetCustomerData(){
        customerService.validateDatabase();
        customerService.resetCustomerData();
        return ResponseEntity.ok("All Customer Data has been cleared!");
    }

    /** New Customer */
    @GetMapping("/new-customer/get-all")
    public ResponseEntity<?> getAllNewCustomers(){
        return ResponseEntity.ok(newCustomerService.getAll());
    }

    @PostMapping("/new-customer/delete-all")
    public ResponseEntity<?> deleteAllNewCustomer(){
        newCustomerService.deleteAll();
        return ResponseEntity.ok("Delete New Customer List Successfully!");
    }

    @GetMapping("/new-customer/download")
    public ResponseEntity<?> downloadNewCustomerFile(){
        List<NewCustomer> customers = newCustomerService.getAll();
        ByteArrayInputStream excelStream = excelUtils.newCustomersToExcelFile(customers);
        HttpHeaders headers = new HttpHeaders();
        ByteArrayResource resource = new ByteArrayResource(excelStream.readAllBytes());
        headers.add(
                "Content-Disposition",
                "attachment; filename=customer.xlsx"
        );
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    /** Payment Instruction */
    @PostMapping("/payment-instruction/update")
    public ResponseEntity<?> updatePaymentInstruction(
                                    @RequestParam("title") String title,
                                    @RequestParam("description") String description)
    {
        return ResponseEntity.ok(paymentInstructionService.update(title, description));
    }

    @GetMapping("/payment-instruction/get")
    public ResponseEntity<?> getPaymentInstruction(){
        return ResponseEntity.ok(paymentInstructionService.get());
    }

    @PostMapping("/payment-instruction-image/upload")
    public ResponseEntity<?> uploadPiImage(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(piImageService.uploadImage(file));
    }

    /** Promotion */
    @PostMapping("/promotion-image/upload")
    public ResponseEntity<?> uploadProImage(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(promotionImageService.upload(file));
    }

    @PostMapping("/promotion-image/delete")
    public void deleteProImage(@RequestParam Integer id){
        promotionImageService.delete(id);
    }

    @GetMapping("/promotion-image/get-all")
    public ResponseEntity<?> getAllProImage(){
        return ResponseEntity.ok(promotionImageService.getAll());
    }

    /** Promotion Description */
    @PostMapping("/promotion-description/update")
    public ResponseEntity<?> update(@RequestParam String content){
        return ResponseEntity.ok(promotionDescriptionService.update(content));
    }

    @GetMapping("/promotion-description/get")
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(promotionDescriptionService.get());
    }

    /** Payment Request */
    @GetMapping("/payment-requests/get-all")
    public ResponseEntity<?> getAllPaymentRequestOfCustomer(){
        return ResponseEntity.ok(paymentRequestService.getAllPaymentRequestOfCustomers());
    }

    @PostMapping("/payment-requests/clear")
    public ResponseEntity<?> clearAllPaymentRequests(){
        paymentRequestService.deleteAllRequest();
        return ResponseEntity.ok("All payment requests have been cleared, there is no data about payment request in system anymore");
    }

    @GetMapping("/payment-requests/download")
    public ResponseEntity<?> downloadPaymentRequestList() {
        List<PaymentRequest> paymentRequests = paymentRequestService.getAllPaymentRequestOfCustomers();
        ByteArrayInputStream excelStream = excelUtils.paymentRequestListToExcelFile(paymentRequests);
        HttpHeaders headers = new HttpHeaders();
        ByteArrayResource resource = new ByteArrayResource(excelStream.readAllBytes());
        headers.add(
                "Content-Disposition",
                "attachment; filename=PaymentRequest.xlsx"
        );
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    /** Banner */
    @PostMapping("/banner/upload")
    public ResponseEntity<?> uploadBanner(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(bannerService.upload(file));
    }

    @PostMapping("/banner/delete")
    public void deleteBanner(@RequestParam Integer id){
        bannerService.delete(id);
    }

    @GetMapping("/banner/get-all")
    public ResponseEntity<?> getAllBanner(){
        return ResponseEntity.ok(bannerService.getAll());
    }

    /** Repair Request */
    @GetMapping("/repair-request/get-all")
    public ResponseEntity<?> getAllRepairRequests(){
        return ResponseEntity.ok(repairRequestService.getAllRepairRequest());
    }

    @PostMapping("/repair-request/clear")
    public ResponseEntity<?> clearAllRepairRequest(){
        repairRequestService.deleteAllRequest();
        return ResponseEntity.ok("All repair requests have been cleared, there is no data about repair request in system anymore");
    }

    @GetMapping("/repair-request/download")
    public ResponseEntity<?> downloadRepairRequestList(){
        List<RepairRequest> repairRequests = repairRequestService.getAllRepairRequest();
        ByteArrayInputStream excelStream = excelUtils.repairRequestListToExcelFile(repairRequests);
        HttpHeaders headers = new HttpHeaders();
        ByteArrayResource resource = new ByteArrayResource(excelStream.readAllBytes());
        headers.add(
                "Content-Disposition",
                "attachment; filename=RepairRequest.xlsx"
        );
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
