package com.example.demo.registration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/rejestracja")
public class RejestracjaController {

    private final RegistrationService registrationService;

    @Autowired
    public RejestracjaController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String showRegistrationPage(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "rejestracja";
    }

    @PostMapping
    public String registerUser(@ModelAttribute RegistrationRequest request, Model model) {
        try {
            log.info("Rejestracj uzytkonika: " + request.getEmail());
            registrationService.register(request);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "rejestracja";
        }
    }

    @GetMapping("/confirm")
    public String confirmUser(@RequestParam("token") String token, Model model) {
        try {
            registrationService.confirmToken(token);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
}
