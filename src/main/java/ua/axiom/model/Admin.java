package ua.axiom.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Data
@SuperBuilder
@Entity
@Table(name = "ADMINS")
public class Admin extends User {

    public Admin() {}

    @Override
    public Collection<Role> getAuthorities() {
        return Collections.singletonList(Role.ADMIN);
    }

    @Override
    protected void setNotNullableFields(Object... data) {

    }
}