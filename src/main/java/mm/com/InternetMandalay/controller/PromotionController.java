package mm.com.InternetMandalay.controller;

import mm.com.InternetMandalay.request.PromotionUpdate;
import mm.com.InternetMandalay.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/promotion")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;

    @PostMapping("/create")
    @Secured("ROLE_admin")
    public ResponseEntity<?> create(){
        return ResponseEntity.ok(promotionService.create());
    }

    @PutMapping("/update")
    @Secured("ROLE_admin")
    public ResponseEntity<?> update(@RequestParam Integer id, @RequestBody PromotionUpdate promotionUpdate){
        return ResponseEntity.ok(promotionService.update(id, promotionUpdate));
    }

    @DeleteMapping("/delete")
    @Secured("ROLE_admin")
    public void delete(@RequestParam Integer id){
        promotionService.delete(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(promotionService.getAll());
    }
}
