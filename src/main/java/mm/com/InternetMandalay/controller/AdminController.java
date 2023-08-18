package mm.com.InternetMandalay.controller;

import mm.com.InternetMandalay.entity.NewCustomer;
import mm.com.InternetMandalay.entity.PaymentRequest;
import mm.com.InternetMandalay.request.ContactInfoUpdate;
import mm.com.InternetMandalay.request.PromotionUpdate;
import mm.com.InternetMandalay.service.*;
import mm.com.InternetMandalay.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private PromotionService promotionService;
    @Autowired
    private PaymentRequestService paymentRequestService;

    @PostMapping("/abnormal-case/update")
    public ResponseEntity<?> updateAbnormalCase(@RequestParam("file") MultipartFile file,
                                    @RequestParam("title") String title,
                                    @RequestParam("description") String description)
    {
        return ResponseEntity.ok(abnormalCaseService.update(file, title, description));
    }
    @GetMapping("/abnormal-case/get")
    public ResponseEntity<?> getAbnormalCase(){
        return ResponseEntity.ok(abnormalCaseService.get());
    }

    @PostMapping("/contact-info/update")
    public ResponseEntity<?> updateContactInfo(@RequestBody ContactInfoUpdate contactInfoUpdate){
        return ResponseEntity.ok(contactInfoService.update(contactInfoUpdate));
    }

    @GetMapping("/contact-info/get")
    public ResponseEntity<?> getContactInfo(){
        return ResponseEntity.ok(contactInfoService.get());
    }

    @PostMapping("/customer/upload")
    public ResponseEntity<String> uploadCustomerExcel(@RequestParam("file") MultipartFile file){
        try {
            customerService.uploadData(file.getInputStream());
            return ResponseEntity.ok("Excel data imported successfully!! :D");
        } catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to import Excel data: " + e.getMessage());
        }
    }

    @PostMapping("/customer/reset-customer-data")
    public ResponseEntity<?> resetCustomerData(){
        customerService.resetCustomerData();
        return ResponseEntity.ok("All Customer Data has been cleared!");
    }

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
//        return ResponseEntity
//                .ok()
//                .headers(headers)
//                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
//                .body(new InputStreamResource(excelStream));
    }

    @PostMapping("/payment-instruction/update")
    public ResponseEntity<?> updatePaymentInstruction(@RequestParam("file") MultipartFile file,
                                    @RequestParam("title") String title,
                                    @RequestParam("description") String description)
    {
        return ResponseEntity.ok(paymentInstructionService.update(file, title, description));
    }

    @GetMapping("/payment-instruction/get")
    public ResponseEntity<?> getPaymentInstruction(){
        return ResponseEntity.ok(paymentInstructionService.get());
    }

    @PostMapping("/promotion/create")
    public ResponseEntity<?> create(){
        return ResponseEntity.ok(promotionService.create());
    }

    @PostMapping("/promotion/update")
    public ResponseEntity<?> update(@RequestParam Integer id, @RequestBody PromotionUpdate promotionUpdate){
        return ResponseEntity.ok(promotionService.update(id, promotionUpdate));
    }

    @PostMapping("/promotion/delete")
    public void delete(@RequestParam Integer id){
        promotionService.delete(id);
    }

    @GetMapping("/promotion/get-all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(promotionService.getAll());
    }

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
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
//                .body(new InputStreamResource(excelStream));
    }
}
