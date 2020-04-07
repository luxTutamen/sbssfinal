package ua.axiom.model.objects;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40)
    private String modelName;

    private float multiplier;

    public Car(String name, float mult) {
        this.modelName = name;
        this.multiplier = mult;
    }
}
