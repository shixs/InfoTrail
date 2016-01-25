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
 * Entity class Floor.
 * 
 * @author Xingsheng
 * @version %I%,%G%
 */
@Entity
@Table(name = "FLOOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Floor.findAll", query = "SELECT f FROM Floor f"),
    @NamedQuery(name = "Floor.findByFloorId", query = "SELECT f FROM Floor f WHERE f.floorId = :floorId"),
    @NamedQuery(name = "Floor.findByName", query = "SELECT f FROM Floor f WHERE f.name = :name"),
    @NamedQuery(name = "Floor.findByBuildingId", query = "SELECT f FROM Floor f WHERE f.buildingId.buildingId = :buildingId")})

public class Floor implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "floorId")
    private List<FloorBoundary> floorBoundaryList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "floor_id")
    private Integer floorId;
    @Size(max = 25)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(name = "info")
    private String info;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "floorId")
    private List<Room> roomList;
    @OneToMany(mappedBy = "floorId")
    private List<Events> eventsList;
    @JoinColumn(name = "building_id", referencedColumnName = "building_id")
    @ManyToOne(optional = false)
    private Building buildingId;

    public Floor() {
    }

    public Floor(Integer floorId) {
        this.floorId = floorId;
    }

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @XmlTransient
    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    @XmlTransient
    public List<Events> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<Events> eventsList) {
        this.eventsList = eventsList;
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
        hash += (floorId != null ? floorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Floor)) {
            return false;
        }
        Floor other = (Floor) object;
        if ((this.floorId == null && other.floorId != null) || (this.floorId != null && !this.floorId.equals(other.floorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Floor";
    }

    @XmlTransient
    public List<FloorBoundary> getFloorBoundaryList() {
        return floorBoundaryList;
    }

    public void setFloorBoundaryList(List<FloorBoundary> floorBoundaryList) {
        this.floorBoundaryList = floorBoundaryList;
    }
    
}
