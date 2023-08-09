package mm.com.InternetMandalay.controller;

import mm.com.InternetMandalay.request.PaymentInstructionUpdate;
import mm.com.InternetMandalay.service.PaymentInstructionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-instruction")
public class PaymentInstructionController {
    @Autowired
    private PaymentInstructionService paymentInstructionService;

    @PostMapping("/create")
    @Secured("ROLE_admin")
    public ResponseEntity<?> create(){
        return ResponseEntity.ok(paymentInstructionService.create());
    }

    @PutMapping("/update")
    @Secured("ROLE_admin")
    public ResponseEntity<?> update(@RequestBody PaymentInstructionUpdate paymentInstructionUpdate){
        return ResponseEntity.ok(paymentInstructionService.update(paymentInstructionUpdate));
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(paymentInstructionService.get());
    }
}
