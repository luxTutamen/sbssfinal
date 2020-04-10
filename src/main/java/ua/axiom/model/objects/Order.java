package ua.axiom.model.objects;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "ORDERS")
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

    @NotNull
    private Date date;

    public static List<OrderInputDescription> getOrderInputDescriptions() {
        return OrderInputDescription.inputDescription;
    }

    private static class OrderInputDescription {
        static List<OrderInputDescription> inputDescription = Arrays.asList(
                new OrderInputDescription("from", "text"),
                new OrderInputDescription("where", "text"),
                new OrderInputDescription("class", "text")

        );

        public String name;
        public String type;

        public OrderInputDescription(String name, String type) {
            this.name = name;
            this.type = type;
        }
    }

}
