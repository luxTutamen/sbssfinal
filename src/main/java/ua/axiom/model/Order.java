package ua.axiom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "ORDERS")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    public enum Status{PENDING, TAKEN, FINISHED};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User client;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User driver;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @Digits(integer=9, fraction=2)
    private BigDecimal price;

    @NotNull
    private Date date;

    @NotNull
    private Car.Class cClass;

    @NotNull
    private String departure;

    @NotNull
    private String destination;

    private boolean confirmedByClient;

    private boolean confirmedByDriver;

    public static List<OrderInputDescription> getOrderInputDescriptions() {
        return OrderInputDescription.inputDescription;
    }

    private static class OrderInputDescription {
        static List<OrderInputDescription> inputDescription = Arrays.asList(
                new OrderInputDescription("from", "text"),
                new OrderInputDescription("where", "text")
        );

        public String name;
        public String type;

        public OrderInputDescription(String name, String type) {
            this.name = name;
            this.type = type;
        }
    }

}
