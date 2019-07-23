package ass1_pkg;

import ass1_pkg.Food;
import ass1_pkg.Usr;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-30T16:31:51")
@StaticMetamodel(Consumption.class)
public class Consumption_ { 

    public static volatile SingularAttribute<Consumption, Double> quantity;
    public static volatile SingularAttribute<Consumption, Food> foodId;
    public static volatile SingularAttribute<Consumption, Integer> consumId;
    public static volatile SingularAttribute<Consumption, Usr> userId;
    public static volatile SingularAttribute<Consumption, Date> consumDate;

}