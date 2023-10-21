package ru.nino.mybar.entity.user;

import jakarta.persistence.Entity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import ru.nino.mybar.entity.IdEntity;

@Data
@Entity
public class Authorities extends IdEntity implements GrantedAuthority {
    private String authorities;

    public Authorities() {
    }

    public Authorities(String authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getAuthority() {
        return authorities;
    }
}
