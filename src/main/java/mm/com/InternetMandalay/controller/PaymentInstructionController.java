package mm.com.InternetMandalay.controller;

import mm.com.InternetMandalay.service.PaymentInstructionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/payment-instruction")
public class PaymentInstructionController {
    @Autowired
    private PaymentInstructionService paymentInstructionService;

//    @PostMapping("/create")
//    @Secured("ROLE_admin")
//    public ResponseEntity<?> create(){
//        return ResponseEntity.ok(paymentInstructionService.create());
//    }

    @PutMapping("/update")
    @Secured("ROLE_admin")
    public ResponseEntity<?> update(@RequestParam("file") MultipartFile file,
                                    @RequestParam("title") String title,
                                    @RequestParam("description") String description)
    {
        return ResponseEntity.ok(paymentInstructionService.update(file, title, description));
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(paymentInstructionService.get());
    }
}
