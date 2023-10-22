package ru.nino.mybar.entity.user;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.nino.mybar.entity.IdEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "custom_user")
public class User extends IdEntity implements UserDetails {

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Authorities> authorities = new ArrayList<>();
    private String name;
    private String password;
    private boolean enabled = true;

    public User() {

    }

    public User(UserDetails userDetails) {
        this.name = userDetails.getUsername();
        this.password = userDetails.getPassword();
        this.enabled = userDetails.isEnabled();
        this.authorities = userDetails.getAuthorities().stream()
                .map(grantedAuthority -> new Authorities(grantedAuthority.getAuthority()))
                .toList();


    }

    public User(String name, String password, boolean enabled, List<Authorities> authorities) {
        this.name = name;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public static UserInfoBuilder builder() {
        return new UserInfoBuilder();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public static class UserInfoBuilder {
        private String name;
        private String password;
        private boolean enabled;
        private List<Authorities> authorities;

        UserInfoBuilder() {
        }

        public UserInfoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserInfoBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserInfoBuilder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public UserInfoBuilder authorities(Authorities... authorities) {
            this.authorities = Arrays.stream(authorities)
                    .toList();
            return this;
        }

        public UserInfoBuilder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities.stream()
                    .map(grantedAuthority -> new Authorities(grantedAuthority.getAuthority()))
                    .toList();
            return this;
        }

        public User build() {
            return new User(name, password, enabled, authorities);
        }

        public String toString() {
            return "UserInfo.UserInfoBuilder(name=" + this.name + ", password=" + this.password + ", enabled=" + this.enabled + ", authorities=" + this.authorities + ")";
        }
    }
}
