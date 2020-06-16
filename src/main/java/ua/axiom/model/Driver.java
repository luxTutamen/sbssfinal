package ua.axiom.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@NoArgsConstructor
@Table(name = "DRIVERS")
public class Driver extends User {

    @ManyToOne(cascade=CascadeType.ALL)
    private Car car;

    @OneToOne
    @Nullable
    private Order currentOrder;

    @NotNull
    private BigDecimal balance;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(Role.DRIVER);
    }

    @Override
    protected void setNotNullableFields(Object... data) {
        this.car = new Car("generic car", Car.Class.BUSINESS);
        this.balance = new BigDecimal("0.00");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
