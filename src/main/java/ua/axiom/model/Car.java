package ua.axiom.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Car {
    public enum Class{
        BUDGET(0.75F),
        BUSINESS(1.F),
        PREMIUM(1.5F);

        public float multiplier;

        Class(float multiplier) {
            this.multiplier = multiplier;
        }
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40)
    private String modelName;

    private float multiplier;

    @Enumerated(EnumType.ORDINAL)
    private Class aClass;

    public Car(String name, Class aClass) {
        this.modelName = name;
        this.aClass = aClass;
        this.multiplier = aClass.multiplier;
    }
}
