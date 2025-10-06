package com.microsv.auth_service.util;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthenticationUtil {
    public static void checkPassword(String rawPassword, String encodedPassword) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean checkPass = passwordEncoder.matches(rawPassword, encodedPassword);
        if (!checkPass) {
            throw new BadCredentialsException("Incorrect email or password");
        }
    }
}
