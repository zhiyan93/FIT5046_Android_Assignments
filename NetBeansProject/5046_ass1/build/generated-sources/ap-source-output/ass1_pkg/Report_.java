package ass1_pkg;

import ass1_pkg.Usr;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-30T16:31:51")
@StaticMetamodel(Report.class)
public class Report_ { 

    public static volatile SingularAttribute<Report, Double> calGoal;
    public static volatile SingularAttribute<Report, Integer> reportId;
    public static volatile SingularAttribute<Report, Date> reportDate;
    public static volatile SingularAttribute<Report, Double> calConsum;
    public static volatile SingularAttribute<Report, Double> calBurned;
    public static volatile SingularAttribute<Report, Integer> steps;
    public static volatile SingularAttribute<Report, Usr> userId;

}