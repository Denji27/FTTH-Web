package mm.com.InternetMandalay.controller;

import mm.com.InternetMandalay.entity.NewCustomer;
import mm.com.InternetMandalay.request.NewCustomerRequest;
import mm.com.InternetMandalay.service.NewCustomerService;
import mm.com.InternetMandalay.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/new-customer")
public class NewCustomerController {
    @Autowired
    private NewCustomerService newCustomerService;

    @Autowired
    private ExcelUtils excelUtils;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam(defaultValue = "none") String collaboratorCode, @RequestBody NewCustomerRequest newCustomerRequest){
        return ResponseEntity.ok(newCustomerService.create(collaboratorCode,newCustomerRequest));
    }

    @GetMapping("/get-all")
    @Secured("ROLE_admin")
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(newCustomerService.getAll());
    }

    @DeleteMapping("/delete-all")
    @Secured("ROLE_admin")
    public ResponseEntity<?> delete(){
        newCustomerService.deleteAll();
        return ResponseEntity.ok("Delete New Customer List Successfully!");
    }

    @RequestMapping("/download")
    @Secured("ROLE_admin")
    public ResponseEntity<?> download(){
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
}
