package com.example.demo.appuser;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String verificationToken;
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
}
