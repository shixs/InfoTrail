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
 * Entity class Room.
 * 
 * @author Xingsheng
 * @version %I%,%G%
 */
@Entity
@Table(name = "ROOM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Room.findAll", query = "SELECT r FROM Room r"),
    @NamedQuery(name = "Room.findByRoomId", query = "SELECT r FROM Room r WHERE r.roomId = :roomId"),
    @NamedQuery(name = "Room.findByRoomName", query = "SELECT r FROM Room r WHERE r.roomName = :roomName"),
    @NamedQuery(name = "Room.findByRoomType", query = "SELECT r FROM Room r WHERE r.roomType = :roomType"),
    @NamedQuery(name = "Room.findByRoomX", query = "SELECT r FROM Room r WHERE r.roomX1 = :roomX1"),
    @NamedQuery(name = "Room.findByFloorId", query = "SELECT r FROM Room r WHERE r.floorId.floorId = :floorId"),
    @NamedQuery(name = "Room.findByRoomY", query = "SELECT r FROM Room r WHERE r.roomY1 = :roomY1")})
public class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "room_id")
    private Integer roomId;
    @Size(max = 25)
    @Column(name = "room_name")
    private String roomName;
    @Lob
    @Size(max = 65535)
    @Column(name = "room_info")
    private String roomInfo;
    @Size(max = 25)
    @Column(name = "room_type")
    private String roomType;
    @Column(name = "room_x1")
    private Integer roomX1;
    @Column(name = "room_y1")
    private Integer roomY1;
    @Column(name = "room_x2")
    private Integer roomX2;
    @Column(name = "room_y2")
    private Integer roomY2;
    @JoinColumn(name = "floor_id", referencedColumnName = "floor_id")
    @ManyToOne(optional = false)
    private Floor floorId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomId")
    private List<Item> itemList;
    @OneToMany(mappedBy = "roomId")
    private List<Events> eventsList;

    public Room() {
    }

    public Integer getRoomX2() {
        return roomX2;
    }

    public void setRoomX2(Integer roomX2) {
        this.roomX2 = roomX2;
    }

    public Integer getRoomY2() {
        return roomY2;
    }

    public void setRoomY2(Integer roomY2) {
        this.roomY2 = roomY2;
    }

    public Room(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(String roomInfo) {
        this.roomInfo = roomInfo;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getRoomX1() {
        return roomX1;
    }

    public void setRoomX1(Integer roomX) {
        this.roomX1 = roomX;
    }

    public Integer getRoomY1() {
        return roomY1;
    }

    public void setRoomY1(Integer roomY) {
        this.roomY1 = roomY;
    }

    public Floor getFloorId() {
        return floorId;
    }

    public void setFloorId(Floor floorId) {
        this.floorId = floorId;
    }

    @XmlTransient
    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @XmlTransient
    public List<Events> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<Events> eventsList) {
        this.eventsList = eventsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomId != null ? roomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Room)) {
            return false;
        }
        Room other = (Room) object;
        if ((this.roomId == null && other.roomId != null) || (this.roomId != null && !this.roomId.equals(other.roomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Room";
    }
    
}
