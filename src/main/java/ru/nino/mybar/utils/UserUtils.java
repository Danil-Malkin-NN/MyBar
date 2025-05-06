package ru.nino.mybar.utils;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.Objects;

public class UserUtils {

    public static String getEmail(OAuth2AuthenticationToken user) {
        return Objects.requireNonNull(user.getPrincipal()
                                                      .getAttribute("email"))
                .toString();
    }
}
