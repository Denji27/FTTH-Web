package mm.com.InternetMandalay.controller;

import mm.com.InternetMandalay.request.ContactInfoUpdate;
import mm.com.InternetMandalay.service.ContactInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/contact-info")
public class ContactInfoController {
    @Autowired
    private ContactInfoService contactInfoService;

    @PutMapping("/update")
    @Secured("ROLE_admin")
    public ResponseEntity<?> update(@RequestBody ContactInfoUpdate contactInfoUpdate){
        return ResponseEntity.ok(contactInfoService.update(contactInfoUpdate));
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(contactInfoService.get());
    }
}
