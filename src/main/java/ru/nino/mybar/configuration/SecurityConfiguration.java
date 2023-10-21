package ru.nino.mybar.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.nino.mybar.entity.user.Authorities;
import ru.nino.mybar.entity.user.UserInfo;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest()
                        .hasAuthority("USER")
                )
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(PostgresUserDetailsService myUserService) {
        createDefaultUser(myUserService);
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(myUserService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    private void createDefaultUser(PostgresUserDetailsService userDetailsService) {


        UserInfo user = UserInfo.builder()
                .name("user")
                .password(passwordEncoder().encode("user"))
                .authorities(new Authorities("USER"))
                .build();
        UserInfo admin = UserInfo.builder()
                .name("admin")
                .password(passwordEncoder().encode("admin"))
                .authorities(new Authorities("USER"), new Authorities("ADMIN"))
                .build();


        userDetailsService.createUser(user);
        userDetailsService.createUser(admin);

    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
