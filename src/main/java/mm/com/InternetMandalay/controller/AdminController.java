package mm.com.InternetMandalay.controller;

import mm.com.InternetMandalay.entity.NewCustomer;
import mm.com.InternetMandalay.request.ContactInfoUpdate;
import mm.com.InternetMandalay.request.PromotionUpdate;
import mm.com.InternetMandalay.service.*;
import mm.com.InternetMandalay.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
//@CrossOrigin
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
    @PutMapping("/abnormal-case/update")
    @Secured("ROLE_admin")
    public ResponseEntity<?> updateAbnormalCase(@RequestParam("file") MultipartFile file,
                                    @RequestParam("title") String title,
                                    @RequestParam("description") String description)
    {
        return ResponseEntity.ok(abnormalCaseService.update(file, title, description));
    }
    @GetMapping("/abnormal-case/get")
    @Secured("ROLE_admin")
    public ResponseEntity<?> getAbnormalCase(){
        return ResponseEntity.ok(abnormalCaseService.get());
    }

    @PutMapping("/contact-info/update")
    @Secured("ROLE_admin")
    public ResponseEntity<?> updateContactInfo(@RequestBody ContactInfoUpdate contactInfoUpdate){
        return ResponseEntity.ok(contactInfoService.update(contactInfoUpdate));
    }

    @GetMapping("/contact-info/get")
    @Secured("ROLE_admin")
    public ResponseEntity<?> getContactInfo(){
        return ResponseEntity.ok(contactInfoService.get());
    }

    @PostMapping("/customer/upload")
    @Secured("ROLE_admin")
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

    @DeleteMapping("/customer/reset-customer-data")
    @Secured("ROLE_admin")
    public ResponseEntity<?> resetCustomerData(){
        customerService.resetCustomerData();
        return ResponseEntity.ok("All Customer Data has been cleared!");
    }

    @GetMapping("/new-customer/get-all")
    @Secured("ROLE_admin")
    public ResponseEntity<?> getAllNewCustomers(){
        return ResponseEntity.ok(newCustomerService.getAll());
    }

    @DeleteMapping("/new-customer/delete-all")
    @Secured("ROLE_admin")
    public ResponseEntity<?> deleteAllNewCustomer(){
        newCustomerService.deleteAll();
        return ResponseEntity.ok("Delete New Customer List Successfully!");
    }

    @RequestMapping("/new-customer/download")
    @Secured("ROLE_admin")
    public ResponseEntity<?> downloadNewCustomerFile(){
        List<NewCustomer> customers = newCustomerService.getAll();
        ByteArrayInputStream excelStream = excelUtils.newCustomersToExcelFile(customers);
        HttpHeaders headers = new HttpHeaders();
        headers.add(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=customer.xlsx"
        );
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new InputStreamResource(excelStream));
    }

    @PutMapping("/payment-instruction/update")
    @Secured("ROLE_admin")
    public ResponseEntity<?> updatePaymentInstruction(@RequestParam("file") MultipartFile file,
                                    @RequestParam("title") String title,
                                    @RequestParam("description") String description)
    {
        return ResponseEntity.ok(paymentInstructionService.update(file, title, description));
    }

    @GetMapping("/payment-instruction/get")
    @Secured("ROLE_admin")
    public ResponseEntity<?> getPaymentInstruction(){
        return ResponseEntity.ok(paymentInstructionService.get());
    }

    @PostMapping("/promotion/create")
    @Secured("ROLE_admin")
    public ResponseEntity<?> create(){
        return ResponseEntity.ok(promotionService.create());
    }

    @PutMapping("/promotion/update")
    @Secured("ROLE_admin")
    public ResponseEntity<?> update(@RequestParam Integer id, @RequestBody PromotionUpdate promotionUpdate){
        return ResponseEntity.ok(promotionService.update(id, promotionUpdate));
    }

    @DeleteMapping("/promotion/delete")
    @Secured("ROLE_admin")
    public void delete(@RequestParam Integer id){
        promotionService.delete(id);
    }

    @GetMapping("/promotion/get-all")
    @Secured("ROLE_admin")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(promotionService.getAll());
    }
}
