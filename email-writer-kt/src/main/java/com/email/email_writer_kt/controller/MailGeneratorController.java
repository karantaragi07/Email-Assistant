package com.email.email_writer_kt.controller;


import com.email.email_writer_kt.model.MailGeneratorModel;
import com.email.email_writer_kt.service.MailGeneratorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/mail")
public class MailGeneratorController {

    private final MailGeneratorService mailGeneratorService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateMail(@RequestBody MailGeneratorModel mailGeneratorModel) {
        String response = mailGeneratorService.generateMailReply(mailGeneratorModel);
        return ResponseEntity.ok(response);
    }
}

