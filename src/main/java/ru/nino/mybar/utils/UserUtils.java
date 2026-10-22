package ru.nino.mybar.utils;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public class UserUtils {

    public static String getEmail(OAuth2AuthenticationToken user) {
        Object email = user.getPrincipal().getAttribute("email");
        if (email != null && !email.toString().isBlank()) {
            return email.toString();
        }
        Object username = user.getPrincipal().getAttribute("preferred_username");
        if (username != null && !username.toString().isBlank()) {
            return username.toString();
        }
        return user.getName();
    }
}
