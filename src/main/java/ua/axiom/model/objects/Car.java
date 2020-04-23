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

    public static class ClassTDO {
        public Class aClass;
        public float multiplier;

        public ClassTDO(Class name, float multiplier) {
            this.aClass = name;
            this.multiplier = multiplier;
        }

        public static List<ClassTDO> getCarClassTDOList() {
            return Arrays.asList(
                    new ClassTDO(Class.BUDGET, 0.75F),
                    new ClassTDO(Class.BUSINESS, 1.1F),
                    new ClassTDO(Class.PREMIUM, 1.6F)
            );
        }
    }

    public static class CarTDO {
        public String modelName;
        public float multiplier;
        public String aClass;

        public static CarTDO carToDTO(Car car) {
            CarTDO tdo = new CarTDO();
            tdo.aClass = car.aClass.toString();
            tdo.modelName = car.modelName;
            tdo.multiplier = car.multiplier;

            return tdo;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40)
    private String modelName;

    private float multiplier;

    @Enumerated(EnumType.ORDINAL)
    private Class aClass;

    public Car(String name, Class aClass, float mult) {
        this.modelName = name;
        this.aClass = aClass;
        this.multiplier = mult;
    }
}
