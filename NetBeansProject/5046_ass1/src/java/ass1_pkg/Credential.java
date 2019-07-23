/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ass1_pkg;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 42901
 */
@Entity
@Table(name = "CREDENTIAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Credential.findAll", query = "SELECT c FROM Credential c")
    , @NamedQuery(name = "Credential.findByLoginName", query = "SELECT c FROM Credential c WHERE c.loginName = :loginName")
    , @NamedQuery(name = "Credential.findByPwHash", query = "SELECT c FROM Credential c WHERE c.pwHash = :pwHash")
    // , @NamedQuery(name = "Credential.findByUserId", query = "SELECT c FROM Credential c WHERE c.userId = :userId")
    , @NamedQuery(name = "Credential.findBySignupDate", query = "SELECT c FROM Credential c WHERE c.signupDate = :signupDate")})
public class Credential implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "LOGIN_NAME")
    private String loginName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "PW_HASH")
    private String pwHash;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SIGNUP_DATE")
    @Temporal(TemporalType.DATE)
    private Date signupDate;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private Usr userId;

    public Credential() {
    }

    public Credential(String loginName) {
        this.loginName = loginName;
    }

    public Credential(String loginName, String pwHash, Date signupDate) {
        this.loginName = loginName;
        this.pwHash = pwHash;
        this.signupDate = signupDate;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPwHash() {
        return pwHash;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    public Date getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(Date signupDate) {
        this.signupDate = signupDate;
    }

    public Usr getUserId() {
        return userId;
    }

    public void setUserId(Usr userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loginName != null ? loginName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Credential)) {
            return false;
        }
        Credential other = (Credential) object;
        if ((this.loginName == null && other.loginName != null) || (this.loginName != null && !this.loginName.equals(other.loginName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ass1_pkg.Credential[ loginName=" + loginName + " ]";
    }
    
}
