package stevens.edu.infotrail.domain.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity class Organization.
 * 
 * @author Xingsheng
 * @version %I%,%G%
 */
@Entity
@Table(name = "ORGANIZATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Organization.findAll", query = "SELECT o FROM Organization o"),
    @NamedQuery(name = "Organization.findByOrgId", query = "SELECT o FROM Organization o WHERE o.orgId = :orgId"),
    @NamedQuery(name = "Organization.findByName", query = "SELECT o FROM Organization o WHERE o.name = :name"),
    @NamedQuery(name = "Organization.findByAddress1", query = "SELECT o FROM Organization o WHERE o.address1 = :address1"),
    @NamedQuery(name = "Organization.findByAddress2", query = "SELECT o FROM Organization o WHERE o.address2 = :address2"),
    @NamedQuery(name = "Organization.findByCity", query = "SELECT o FROM Organization o WHERE o.city = :city"),
    @NamedQuery(name = "Organization.findByState", query = "SELECT o FROM Organization o WHERE o.state = :state"),
    @NamedQuery(name = "Organization.findByZip", query = "SELECT o FROM Organization o WHERE o.zip = :zip")})
public class Organization implements Serializable {
    @OneToMany(mappedBy = "orgId")
    private List<Building> buildingList;
    @Lob
    @Size(max = 65535)
    @Column(name = "info")
    private String info;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "org_id")
    private Integer orgId;
    @Size(max = 50)
    @Column(name = "name")
    private String name;
    @Size(max = 50)
    @Column(name = "address1")
    private String address1;
    @Size(max = 25)
    @Column(name = "address2")
    private String address2;
    @Size(max = 25)
    @Column(name = "city")
    private String city;
    @Size(max = 2)
    @Column(name = "state")
    private String state;
    @Size(max = 5)
    @Column(name = "zip")
    private String zip;
    @OneToMany(mappedBy = "orgId")
    private List<Boundaries> boundaries;

    public Organization() {
    }

    public Organization(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @XmlTransient
    public List<Boundaries> getBoundaries() {
        return boundaries;
    }

    public void setBoundaries(List<Boundaries> boundaries) {
        this.boundaries = boundaries;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orgId != null ? orgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Organization)) {
            return false;
        }
        Organization other = (Organization) object;
        if ((this.orgId == null && other.orgId != null) || (this.orgId != null && !this.orgId.equals(other.orgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Organization";
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @XmlTransient
    public List<Building> getBuildingList() {
        return buildingList;
    }

    public void setBuildingList(List<Building> buildingList) {
        this.buildingList = buildingList;
    }
    
}
