package ua.axiom.model.objects;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Car {
    public enum Class{BUDGET, BUSINESS, PREMIUM};

    private static class ClassTDO{
        public Class aClass;
        public float multiplier;

        public ClassTDO(Class name, float multiplier) {
            this.aClass = name;
            this.multiplier = multiplier;
        }
    }

    public static List<ClassTDO> getCarClassTDOList() {
        return Arrays.asList(
                new ClassTDO(Class.BUDGET, 0.75F),
                new ClassTDO(Class.BUSINESS, 1.1F),
                new ClassTDO(Class.PREMIUM, 1.6F)
        );
    }

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
