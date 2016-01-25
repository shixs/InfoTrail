package stevens.edu.infotrail.domain.entity;

import java.io.Serializable;
import java.util.List;  
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
 * Entity class Building.
 * 
 * @author Xingsheng
 * @version %I%,%G%
 */
@Entity
@Table(name = "BUILDING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Building.findAll", query = "SELECT b FROM Building b"),
    @NamedQuery(name = "Building.findByBuildingId", query = "SELECT b FROM Building b WHERE b.buildingId = :buildingId"),
    @NamedQuery(name = "Building.findByName", query = "SELECT b FROM Building b WHERE b.name = :name"),
    @NamedQuery(name = "Building.findByAddress1", query = "SELECT b FROM Building b WHERE b.address1 = :address1"),
    @NamedQuery(name = "Building.findByAddress2", query = "SELECT b FROM Building b WHERE b.address2 = :address2"),
    @NamedQuery(name = "Building.findByCity", query = "SELECT b FROM Building b WHERE b.city = :city"),
    @NamedQuery(name = "Building.findByOrgId", query = "SELECT b FROM Building b WHERE b.orgId.orgId = :orgId"),
    @NamedQuery(name = "Building.findByState", query = "SELECT b FROM Building b WHERE b.state = :state"),
    @NamedQuery(name = "Building.findByCountry", query = "SELECT b FROM Building b WHERE b.country = :country"),
    @NamedQuery(name = "Building.findByZipcode", query = "SELECT b FROM Building b WHERE b.zipcode = :zipcode")})
public class Building implements Serializable {
    @JoinColumn(name = "org_id", referencedColumnName = "org_id")
    @ManyToOne
    private Organization orgId;
    @OneToMany(mappedBy = "buildingId")
    private List<BldgBoundaries> bldgBoundariesList;
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "building_id")
    private Integer buildingId;
    @Size(max = 50)
    @Column(name = "name")
    private String name;
    @Size(max = 50)
    @Column(name = "address1")
    private String address1;
    @Size(max = 50)
    @Column(name = "address2")
    private String address2;
    @Size(max = 25)
    @Column(name = "city")
    private String city;
    @Size(max = 2)
    @Column(name = "state")
    private String state;
    @Size(max = 25)
    @Column(name = "country")
    private String country;
    @Lob
    @Size(max = 65535)
    @Column(name = "info")
    private String info;
    @Size(max = 5)
    @Column(name = "zipcode")
    private String zipcode;
    
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @OneToMany(mappedBy = "buildingId")
    private List<Events> eventsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buildingId")
    private List<Floor> floorList;

    public Building() {
    }

    public Building(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @XmlTransient
    public List<Events> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<Events> eventsList) {
        this.eventsList = eventsList;
    }

    @XmlTransient
    public List<Floor> getFloorList() {
        return floorList;
    }

    public void setFloorList(List<Floor> floorList) {
        this.floorList = floorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (buildingId != null ? buildingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Building)) {
            return false;
        }
        Building other = (Building) object;
        if ((this.buildingId == null && other.buildingId != null) || (this.buildingId != null && !this.buildingId.equals(other.buildingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Building";
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

    @XmlTransient
    public List<BldgBoundaries> getBldgBoundariesList() {
        return bldgBoundariesList;
    }

    public void setBldgBoundariesList(List<BldgBoundaries> bldgBoundariesList) {
        this.bldgBoundariesList = bldgBoundariesList;
    }

    public Organization getOrgId() {
        return orgId;
    }

    public void setOrgId(Organization orgId) {
        this.orgId = orgId;
    }
    
}
