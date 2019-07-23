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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 42901
 */
@Entity
@Table(name = "CONSUMPTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consumption.findAll", query = "SELECT c FROM Consumption c")
    , @NamedQuery(name = "Consumption.findByConsumId", query = "SELECT c FROM Consumption c WHERE c.consumId = :consumId")
    , @NamedQuery(name = "Consumption.findByConsumDate", query = "SELECT c FROM Consumption c WHERE c.consumDate = :consumDate")
    , @NamedQuery(name = "Consumption.findByQuantity", query = "SELECT c FROM Consumption c WHERE c.quantity = :quantity")
,@NamedQuery (name = "Consumption.findByCategoryAndQuantity", query = "SELECT c FROM Consumption c WHERE c.quantity = :quantity AND c.foodId.category = :category") })
public class Consumption implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONSUM_ID")
    private Integer consumId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONSUM_DATE")
    @Temporal(TemporalType.DATE)
    private Date consumDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUANTITY")
    private double quantity;
    @JoinColumn(name = "FOOD_ID", referencedColumnName = "FOOD_ID")
    @ManyToOne(optional = false)
    private Food foodId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private Usr userId;

    public Consumption() {
    }

    public Consumption(Integer consumId) {
        this.consumId = consumId;
    }

    public Consumption(Integer consumId, Date consumDate, double quantity) {
        this.consumId = consumId;
        this.consumDate = consumDate;
        this.quantity = quantity;
    }

    public Integer getConsumId() {
        return consumId;
    }

    public void setConsumId(Integer consumId) {
        this.consumId = consumId;
    }

    public Date getConsumDate() {
        return consumDate;
    }

    public void setConsumDate(Date consumDate) {
        this.consumDate = consumDate;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Food getFoodId() {
        return foodId;
    }

    public void setFoodId(Food foodId) {
        this.foodId = foodId;
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
        hash += (consumId != null ? consumId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consumption)) {
            return false;
        }
        Consumption other = (Consumption) object;
        if ((this.consumId == null && other.consumId != null) || (this.consumId != null && !this.consumId.equals(other.consumId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ass1_pkg.Consumption[ consumId=" + consumId + " ]";
    }
  
    
}
