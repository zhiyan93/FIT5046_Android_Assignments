package ass1_pkg;

import ass1_pkg.Consumption;
import ass1_pkg.Credential;
import ass1_pkg.Report;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-30T16:31:51")
@StaticMetamodel(Usr.class)
public class Usr_ { 

    public static volatile SingularAttribute<Usr, String> address;
    public static volatile SingularAttribute<Usr, Character> gender;
    public static volatile CollectionAttribute<Usr, Consumption> consumptionCollection;
    public static volatile SingularAttribute<Usr, String> postcode;
    public static volatile SingularAttribute<Usr, Double> weight;
    public static volatile SingularAttribute<Usr, Integer> userId;
    public static volatile SingularAttribute<Usr, Integer> stepsMile;
    public static volatile SingularAttribute<Usr, String> firstName;
    public static volatile CollectionAttribute<Usr, Report> reportCollection;
    public static volatile SingularAttribute<Usr, String> surname;
    public static volatile SingularAttribute<Usr, Date> dob;
    public static volatile CollectionAttribute<Usr, Credential> credentialCollection;
    public static volatile SingularAttribute<Usr, String> email;
    public static volatile SingularAttribute<Usr, Integer> activityLevel;
    public static volatile SingularAttribute<Usr, Double> height;

}