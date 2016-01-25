package stevens.edu.infotrail.representation;

import java.util.ArrayList;
import java.util.List;
import stevens.edu.infotrail.domain.entity.Beacon;
import stevens.edu.infotrail.domain.entity.Item;
import stevens.edu.infotrail.domain.entity.Spot;

/**
 * Try to create new representations for all entity resources.
 * 
 * @author Xingsheng
 * @version %I%,%G%
 */
public class BeaconRep {
    private int beaconId;
    private String uuid;
    private int major;
    private int minor;
    private String location;
    private String itemName;
    private int itemId;
    private int roomId;
    private List<String> spotNames;
    public BeaconRep(){
        this.beaconId = 0;
        this.uuid = "";
        this.major = 0;
        this.minor = 0;
        this.location = "";
        this.itemName = "";
        this.itemId = 0;
        this.roomId = 0;
        this.spotNames = new ArrayList<>();
    }

    public BeaconRep(Beacon beacon){
        this.beaconId = beacon.getBeaconId();
        this.uuid = beacon.getUuid();
        this.major = beacon.getMajor();
        this.minor = beacon.getMinor();
        this.location = beacon.getLocation();
        this.itemName = beacon.getItemId().getItemName();
        this.itemId = beacon.getItemId().getItemId();
        this.roomId = beacon.getItemId().getRoomId().getRoomId();

        this.spotNames = new ArrayList<>();
        String tmp;
        for(Spot s:beacon.getSpotList()){
            tmp = s.getName();
            this.spotNames.add(tmp);
        }
    }
    
    public int getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(int beaconId) {
        this.beaconId = beaconId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }   

    public Integer getitemId() {
        return itemId;
    }

    public void setitemId(Integer itemId) {
        this.itemId = itemId;
    }
    
    public Integer getroomId() {
        return roomId;
    }

    public void setroomId(Integer roomId) {
        this.roomId = roomId;
    }
    public List<String> getSpotNames() {
        return spotNames;
    }

    public void setSpotNames(List<String> spotNames) {
        this.spotNames = spotNames;
    }
    
    @Override
    public String toString(){
        return "Beacon";
    }
}
