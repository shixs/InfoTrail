package stevens.edu.infotrail.domain.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity class Beacon.
 * 
 * @author Xingsheng
 * @version %I%,%G%
 */
@Entity
@Table(name = "BEACON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Beacon.findAll", query = "SELECT b FROM Beacon b"),
    @NamedQuery(name = "Beacon.findABeacon", query = "SELECT b FROM Beacon b WHERE b.uuid = :uuid AND b.major = :major AND b.minor = :minor"),
    @NamedQuery(name = "Beacon.findBeaconUMa", query = "SELECT b FROM Beacon b WHERE b.uuid = :uuid AND b.major = :major"),
    @NamedQuery(name = "Beacon.findByBeaconId", query = "SELECT b FROM Beacon b WHERE b.beaconId = :beaconId"),
    @NamedQuery(name = "Beacon.findByUuid", query = "SELECT b FROM Beacon b WHERE b.uuid = :uuid"),
    @NamedQuery(name = "Beacon.findByMajor", query = "SELECT b FROM Beacon b WHERE b.major = :major"),
    @NamedQuery(name = "Beacon.findByMinor", query = "SELECT b FROM Beacon b WHERE b.minor = :minor"),
    @NamedQuery(name = "Beacon.findByLocation", query = "SELECT b FROM Beacon b WHERE b.location = :location")})
public class Beacon implements Serializable {
    @OneToMany(mappedBy = "beaconId")
    private List<Spot> spotList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "beacon_id")
    private Integer beaconId;
    @Size(max = 45)
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "major")
    private Integer major;
    @Column(name = "minor")
    private Integer minor;
    @Size(max = 45)
    @Column(name = "location")
    private String location;
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    @ManyToOne
    private Item itemId;

    public Beacon() {
    }

    public Beacon(Integer beaconId) {
        this.beaconId = beaconId;
    }

    public Integer getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(Integer beaconId) {
        this.beaconId = beaconId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getMinor() {
        return minor;
    }

    public void setMinor(Integer minor) {
        this.minor = minor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (beaconId != null ? beaconId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Beacon)) {
            return false;
        }
        Beacon other = (Beacon) object;
        if ((this.beaconId == null && other.beaconId != null) || (this.beaconId != null && !this.beaconId.equals(other.beaconId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return "Beacon";
    }

    @XmlTransient
    public List<Spot> getSpotList() {
        return spotList;
    }

    public void setSpotList(List<Spot> spotList) {
        this.spotList = spotList;
    }
    
}
