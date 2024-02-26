package com.example.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class EmailGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailGeneratorApplication.class, args);
    }
}

@RestController
class EmailController {

    @GetMapping("/generateEmail")
    public ResponseEntity<?> generateEmail(@RequestParam("input1") String input1,
                                           @RequestParam("input2") String input2,
                                           @RequestParam("input3") String input3,
                                           @RequestParam("input4") String input4,
                                           @RequestParam("input5") String input5) {
        String email = generateEmailFromExpression(input1, input2, input3, input4, input5);
        if (email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid expression or inputs.");
        }
        EmailResponse emailResponse = new EmailResponse();
        emailResponse.addData(new EmailData(email, email));
        return ResponseEntity.ok().body(emailResponse);
    }

    private String generateEmailFromExpression(String input1, String input2, String input3, String input4, String input5) {
        try {
            String[] input2Words = input2.split(" ");
            String email = input1.substring(0, 1).toLowerCase() + "." +
                    (input2Words.length > 1 ?
                            input2Words[input2Words.length - 2].substring(0, 1).toLowerCase() +
                                    input2Words[input2Words.length - 1].toLowerCase() :
                            input2.replaceAll(" ", "").toLowerCase()) +
                    "@" + input3.toLowerCase() + "." + input4.toLowerCase() + "." + input5.toLowerCase();
            return email;
        } catch (Exception e) {
            return null;
        }
    }
}

class EmailResponse {
    private List<EmailData> data;

    public EmailResponse() {
        this.data = new ArrayList<>();
    }

    public List<EmailData> getData() {
        return data;
    }

    public void addData(EmailData emailData) {
        this.data.add(emailData);
    }
}

class EmailData {
    private String id;
    private String value;

    public EmailData(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
