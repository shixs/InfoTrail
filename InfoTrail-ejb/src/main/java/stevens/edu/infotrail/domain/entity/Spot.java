package stevens.edu.infotrail.domain.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xshi90
 */
@Entity
@Table(name = "SPOT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spot.findAll", query = "SELECT s FROM Spot s"),
    @NamedQuery(name = "Spot.findBySpotId", query = "SELECT s FROM Spot s WHERE s.spotId = :spotId"),
    @NamedQuery(name = "Spot.findByName", query = "SELECT s FROM Spot s WHERE s.name = :name")})
public class Spot implements Serializable {
    @Size(max = 45)
    @Column(name = "type")
    private String type;
    @JoinColumn(name = "beacon_id", referencedColumnName = "beacon_id")
    @ManyToOne
    private Beacon beaconId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "spot_id")
    private Integer spotId;
    @Size(max = 25)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;

    public Spot() {
    }

    public Spot(Integer spotId) {
        this.spotId = spotId;
    }

    public Integer getSpotId() {
        return spotId;
    }

    public void setSpotId(Integer spotId) {
        this.spotId = spotId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spotId != null ? spotId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spot)) {
            return false;
        }
        Spot other = (Spot) object;
        if ((this.spotId == null && other.spotId != null) || (this.spotId != null && !this.spotId.equals(other.spotId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spot";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Beacon getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(Beacon beaconId) {
        this.beaconId = beaconId;
    }
    
}
