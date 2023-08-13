package mm.com.InternetMandalay.controller;

import mm.com.InternetMandalay.service.AbnormalCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/abnormal-case")
public class AbnormalCaseController {
    @Autowired
    private AbnormalCaseService abnormalCaseService;

    @PutMapping("/update")
    @Secured("ROLE_admin")
    public ResponseEntity<?> update(@RequestParam("file") MultipartFile file,
                                    @RequestParam("title") String title,
                                    @RequestParam("description") String description)
    {
        return ResponseEntity.ok(abnormalCaseService.update(file, title, description));
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(abnormalCaseService.get());
    }
}
