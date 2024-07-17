package com.example.demo.registration;

import com.example.demo.appuser.AppUser;
import com.example.demo.appuser.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    private final AppUserRepository appUserRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;

    public void register(RegistrationRequest request) {
        AppUser user = new AppUser();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setVerificationToken(UUID.randomUUID().toString());
        user.setEnabled(false);

        appUserRepository.save(user);
        logger.info("User registered: " + request.getEmail());

        String verificationUrl = "http://localhost:8080/api/v1/registration/confirm?token=" + user.getVerificationToken();
        emailService.send(request.getEmail(), buildEmail(verificationUrl), null);
    }

    public void confirmToken(String token) {
        Optional<AppUser> user = appUserRepository.findByVerificationToken(token);
        if (user.isPresent()) {
            AppUser appUser = user.get();
            appUser.setEnabled(true);
            appUser.setVerificationToken(null);
            appUserRepository.save(appUser);
            logger.info("User verified: {}", appUser.getEmail());
        } else {
            throw new IllegalStateException("Token not found");
        }
    }

    private String buildEmail(String link) {
        return "Click the link to verify your registration: " + link;
    }
}
