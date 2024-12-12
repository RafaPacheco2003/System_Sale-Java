package system.sales.system_sales.Modal.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import system.sales.system_sales.Modal.EmailService;


@Service
public class EmailServiceImpl implements EmailService{


    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void sendEmail() {
        // TODO Auto-generated method stub
       SimpleMailMessage message= new SimpleMailMessage();

       message.setFrom("rodrigorafaelchipacheco@gmail.com");
       message.setTo("rodrigorafaelchipacheco@gmail.com");
       message.setSubject("Prueba");
       message.setText("Contenido");

       javaMailSender.send(message);
    }
    
}
