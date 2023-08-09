package mm.com.InternetMandalay.controller;

import mm.com.InternetMandalay.entity.AbnormalCase;
import mm.com.InternetMandalay.entity.PaymentInstruction;
import mm.com.InternetMandalay.request.AbnormalCaseUpdate;
import mm.com.InternetMandalay.service.AbnormalCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/abnormal-case")
public class AbnormalCaseController {
    @Autowired
    private AbnormalCaseService abnormalCaseService;

    @PostMapping("/create")
    @Secured("ROLE_admin")
    ResponseEntity<?> create(){
        return ResponseEntity.ok(abnormalCaseService.create());
    }

    @PutMapping("/update")
    @Secured("ROLE_admin")
    ResponseEntity<?> update(@RequestBody AbnormalCaseUpdate abnormalCaseUpdate){
        return ResponseEntity.ok(abnormalCaseService.update(abnormalCaseUpdate));
    }

    @GetMapping("/get")
    ResponseEntity<?> get(){
        return ResponseEntity.ok(abnormalCaseService.get());
    }
}
