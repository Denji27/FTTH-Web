package mm.com.InternetMandalay.controller;

import mm.com.InternetMandalay.request.ContactInfoUpdate;
import mm.com.InternetMandalay.service.ContactInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact-info")
public class ContactInfoController {
    @Autowired
    private ContactInfoService contactInfoService;

    @PostMapping("/create")
    @Secured("ROLE_admin")
    public ResponseEntity<?> create(){
        return ResponseEntity.ok(contactInfoService.create());
    }

    @PutMapping("/update")
    @Secured("ROLE_admin")
    @CacheEvict(value = "ContactInfo")
    public ResponseEntity<?> update(@RequestBody ContactInfoUpdate contactInfoUpdate){
        return ResponseEntity.ok(contactInfoService.update(contactInfoUpdate));
    }

    @GetMapping("/get")
    @Cacheable(value = "ContactInfo")
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(contactInfoService.get());
    }
}
