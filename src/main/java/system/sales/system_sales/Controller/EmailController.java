package system.sales.system_sales.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import system.sales.system_sales.Modal.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {
    

    @Autowired
    EmailService emailService;

    @GetMapping
    public ResponseEntity<?> sendEmail(){
        emailService.sendEmail();
        return new ResponseEntity("Coorre enviado", HttpStatus.ACCEPTED);
    }
}
