package ua.axiom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

@Data
@Entity
@SuperBuilder
@Table(name = "CLIENTS")
@NoArgsConstructor
@AllArgsConstructor
public class Client extends User {

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date lastDiscountGiven;

    @NotNull
    private BigDecimal money;

    @OneToMany(targetEntity = Discount.class, fetch = FetchType.LAZY)
    private Collection<Discount> discount;

    private boolean receivedBDayPromoToday;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(Role.CLIENT);
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

    @Override
    protected void setNotNullableFields(Object... data) {
        this.money = new BigDecimal("0.");
        this.birthDate = new Date();
        this.lastDiscountGiven = new Date();
        discount = new LinkedList<>();
    }
}
