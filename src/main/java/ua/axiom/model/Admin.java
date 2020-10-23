package ua.axiom.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Collections;

@Data
@SuperBuilder
@Entity
@EqualsAndHashCode(callSuper = true)
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