package ua.axiom.model.objects;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

    @NotNull
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User driver;

    @NotNull
    @Digits(integer=9, fraction=2)
    private BigDecimal price;

}
