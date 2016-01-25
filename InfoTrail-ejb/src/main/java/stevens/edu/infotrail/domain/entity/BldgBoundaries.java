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
 *
 * @author Xingsheng
 */
@Entity
@Table(name = "BLDG_BOUNDARIES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BldgBoundaries.findAll", query = "SELECT b FROM BldgBoundaries b"),
    @NamedQuery(name = "BldgBoundaries.findByBoundryId", query = "SELECT b FROM BldgBoundaries b WHERE b.boundryId = :boundryId"),
    @NamedQuery(name = "BldgBoundaries.findByBuildingId", query = "SELECT b FROM BldgBoundaries b WHERE b.buildingId.buildingId = :buildingId"),
    @NamedQuery(name = "BldgBoundaries.findByLatitude", query = "SELECT b FROM BldgBoundaries b WHERE b.latitude = :latitude"),
    @NamedQuery(name = "BldgBoundaries.findByLongitude", query = "SELECT b FROM BldgBoundaries b WHERE b.longitude = :longitude")})
public class BldgBoundaries implements Serializable {
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
    @JoinColumn(name = "building_id", referencedColumnName = "building_id")
    @ManyToOne
    private Building buildingId;

    public BldgBoundaries() {
    }

    public BldgBoundaries(Integer boundryId) {
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

    public Building getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Building buildingId) {
        this.buildingId = buildingId;
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
        if (!(object instanceof BldgBoundaries)) {
            return false;
        }
        BldgBoundaries other = (BldgBoundaries) object;
        if ((this.boundryId == null && other.boundryId != null) || (this.boundryId != null && !this.boundryId.equals(other.boundryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BldgBoundaries";
    }
    
}
