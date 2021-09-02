package com.twillio.sendgrid.controller;

import com.sendgrid.Response;
import com.twillio.sendgrid.model.EmailRequest;
import com.twillio.sendgrid.service.EmailService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;
    @PostMapping("/send-email")
    public ResponseEntity<Void> sendEmail(@RequestBody EmailRequest emailRequest) throws TemplateException, IOException {
        emailService.sendHTML(emailRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/send-email")
    public String sendEmailTest() {
        //emailService.sendHTML(emailRequest);
        return  "OK";
    }
}
