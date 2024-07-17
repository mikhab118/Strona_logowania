package com.example.demo.registration;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistrationRequest {
    private String email;
    private String username;
    private String password;
}
