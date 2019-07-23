package ass1_pkg;

import ass1_pkg.Consumption;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-30T16:31:51")
@StaticMetamodel(Food.class)
public class Food_ { 

    public static volatile SingularAttribute<Food, String> servingUnit;
    public static volatile SingularAttribute<Food, Double> servingAmt;
    public static volatile SingularAttribute<Food, String> foodName;
    public static volatile CollectionAttribute<Food, Consumption> consumptionCollection;
    public static volatile SingularAttribute<Food, Integer> foodId;
    public static volatile SingularAttribute<Food, Double> fat;
    public static volatile SingularAttribute<Food, String> category;
    public static volatile SingularAttribute<Food, Double> calorieAmt;

}