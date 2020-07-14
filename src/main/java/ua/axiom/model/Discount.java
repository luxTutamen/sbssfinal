package ua.axiom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    public enum DiscountType{B_DAY, RANDOM}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int discountPercentage;

    private DiscountType type;

    @ManyToOne
    private Client client;

    public Discount(Client client, int discountPercentage, DiscountType random) {
        this.client = client;
        this.discountPercentage = discountPercentage;
        this.type = random;
    }

    public BigDecimal getMultiplier() {
        return new BigDecimal(discountPercentage / 100.F);
    }

}
