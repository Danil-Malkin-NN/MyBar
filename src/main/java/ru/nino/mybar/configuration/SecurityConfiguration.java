package ru.nino.mybar.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import ru.nino.mybar.entity.user.Authorities;
import ru.nino.mybar.entity.user.User;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder builder = new MvcRequestMatcher.Builder(introspector);
        http
                .httpBasic(Customizer.withDefaults()) // Включает поддержку Basic Auth
                .authorizeHttpRequests(
                        (authorize) -> authorize
                                .requestMatchers(builder.pattern(HttpMethod.POST, "/register/**"))
                                .permitAll()
                                .requestMatchers(builder.pattern(HttpMethod.GET, "/**"))
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.defaultSuccessUrl("http://mybar.dvmalkin.online/mybar"));

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


        User user = User.builder()
                .name("user")
                .password(passwordEncoder().encode("user"))
                .authorities(new Authorities("USER"))
                .enabled(true)
                .build();
        User admin = User.builder()
                .name("admin")
                .password(passwordEncoder().encode("admin"))
                .authorities(new Authorities("USER"), new Authorities("ADMIN"))
                .enabled(true)
                .build();


        userDetailsService.createUser(user);
        userDetailsService.createUser(admin);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
