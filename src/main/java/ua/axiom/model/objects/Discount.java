package ua.axiom.model.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    public Discount(Client client, float v, DiscountType random) {
        this.client = client;
        this.multiplier = v;
        this.type = random;
    }

    public enum DiscountType{B_DAY, RANDOM}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Client client;

    private float multiplier;

    private DiscountType type;
}
