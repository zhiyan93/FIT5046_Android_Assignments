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
@Table(name = "REPORT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r")
    , @NamedQuery(name = "Report.findByReportId", query = "SELECT r FROM Report r WHERE r.reportId = :reportId")
    , @NamedQuery(name = "Report.findByReportDate", query = "SELECT r FROM Report r WHERE r.reportDate = :reportDate")
    , @NamedQuery(name = "Report.findByCalConsum", query = "SELECT r FROM Report r WHERE r.calConsum = :calConsum")
    , @NamedQuery(name = "Report.findByCalBurned", query = "SELECT r FROM Report r WHERE r.calBurned = :calBurned")
    , @NamedQuery(name = "Report.findBySteps", query = "SELECT r FROM Report r WHERE r.steps = :steps")
    , @NamedQuery(name = "Report.findByCalGoal", query = "SELECT r FROM Report r WHERE r.calGoal = :calGoal")})
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "REPORT_ID")
    private Integer reportId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REPORT_DATE")
    @Temporal(TemporalType.DATE)
    private Date reportDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAL_CONSUM")
    private double calConsum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAL_BURNED")
    private double calBurned;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STEPS")
    private int steps;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAL_GOAL")
    private double calGoal;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private Usr userId;

    public Report() {
    }

    public Report(Integer reportId) {
        this.reportId = reportId;
    }

    public Report(Integer reportId, Date reportDate, double calConsum, double calBurned, int steps, double calGoal) {
        this.reportId = reportId;
        this.reportDate = reportDate;
        this.calConsum = calConsum;
        this.calBurned = calBurned;
        this.steps = steps;
        this.calGoal = calGoal;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public double getCalConsum() {
        return calConsum;
    }

    public void setCalConsum(double calConsum) {
        this.calConsum = calConsum;
    }

    public double getCalBurned() {
        return calBurned;
    }

    public void setCalBurned(double calBurned) {
        this.calBurned = calBurned;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public double getCalGoal() {
        return calGoal;
    }

    public void setCalGoal(double calGoal) {
        this.calGoal = calGoal;
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
        hash += (reportId != null ? reportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Report)) {
            return false;
        }
        Report other = (Report) object;
        if ((this.reportId == null && other.reportId != null) || (this.reportId != null && !this.reportId.equals(other.reportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ass1_pkg.Report[ reportId=" + reportId + " ]";
    }
    
}
