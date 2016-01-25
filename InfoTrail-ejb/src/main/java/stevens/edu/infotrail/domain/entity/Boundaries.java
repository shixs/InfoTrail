package stevens.edu.infotrail.domain.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity class Boundaries.
 * 
 * @author Xingsheng
 * @version %I%,%G%
 */
@Entity
@Table(name = "BOUNDARIES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Boundaries.findAll", query = "SELECT b FROM Boundaries b"),
    @NamedQuery(name = "Boundaries.findByOrgId", query = "SELECT b FROM Boundaries b WHERE b.orgId.orgId = :orgId"),
    @NamedQuery(name = "Boundaries.findByBoundryId", query = "SELECT b FROM Boundaries b WHERE b.boundryId = :boundryId"),
    @NamedQuery(name = "Boundaries.findByLatitude", query = "SELECT b FROM Boundaries b WHERE b.latitude = :latitude"),
    @NamedQuery(name = "Boundaries.findByLongitude", query = "SELECT b FROM Boundaries b WHERE b.longitude = :longitude")})
public class Boundaries implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "boundry_id")
    private Integer boundryId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @JoinColumn(name = "org_id", referencedColumnName = "org_id")
    @ManyToOne
    private Organization orgId;

    public Boundaries() {
    }

    public Boundaries(Integer boundryId) {
        this.boundryId = boundryId;
    }

    public Integer getBoundryId() {
        return boundryId;
    }

    public void setBoundryId(Integer boundryId) {
        this.boundryId = boundryId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Organization getOrgId() {
        return orgId;
    }

    public void setOrgId(Organization orgId) {
        this.orgId = orgId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (boundryId != null ? boundryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Boundaries)) {
            return false;
        }
        Boundaries other = (Boundaries) object;
        if ((this.boundryId == null && other.boundryId != null) || (this.boundryId != null && !this.boundryId.equals(other.boundryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Boundaries";
    }
    
}
