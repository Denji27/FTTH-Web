package mm.com.InternetMandalay.controller;

import mm.com.InternetMandalay.request.SearchRequest;
import mm.com.InternetMandalay.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/upload")
    @Secured("ROLE_admin")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file){
        try {
            customerService.uploadData(file.getInputStream());
            return ResponseEntity.ok("Excel data imported successfully!! :D");
        } catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to import Excel data: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCustomer(@RequestBody SearchRequest searchRequest){
        return ResponseEntity.ok(customerService.search(searchRequest));
    }

    @DeleteMapping("/reset-customer-data")
    @Secured("ROLE_admin")
    public ResponseEntity<?> reset(){
        customerService.resetCustomerData();
        return ResponseEntity.ok("All Customer Data has been cleared!");
    }
}
