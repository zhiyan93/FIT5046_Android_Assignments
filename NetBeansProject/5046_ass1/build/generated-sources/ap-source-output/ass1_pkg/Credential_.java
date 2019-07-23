package ass1_pkg;

import ass1_pkg.Usr;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-30T16:31:51")
@StaticMetamodel(Credential.class)
public class Credential_ { 

    public static volatile SingularAttribute<Credential, Date> signupDate;
    public static volatile SingularAttribute<Credential, String> loginName;
    public static volatile SingularAttribute<Credential, String> pwHash;
    public static volatile SingularAttribute<Credential, Usr> userId;

}