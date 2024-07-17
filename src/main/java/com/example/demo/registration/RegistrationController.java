package com.example.demo.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;


    @GetMapping("/login")
    public String showLoginForm(){
        return"login";
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token){
        registrationService.confirmToken(token);
        return ResponseEntity.ok("your account has been verified sucesfull");
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request){
        registrationService.register(request);
        return ResponseEntity.ok("User registrarion succesfully. Go check your email to verify account");
    }




}