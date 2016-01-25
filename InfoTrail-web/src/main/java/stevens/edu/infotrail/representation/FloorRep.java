package stevens.edu.infotrail.representation;

import java.util.ArrayList;
import java.util.List;
import stevens.edu.infotrail.domain.entity.Events;
import stevens.edu.infotrail.domain.entity.Floor;
import stevens.edu.infotrail.domain.entity.FloorBoundary;
import stevens.edu.infotrail.domain.entity.Room;

/**
 *
 * @author Xingsheng
 */
public class FloorRep {
    private int floorId;
    private String name;
    private String info;
    private List<String> boundaryList;
    private List<String> roomList;
    private List<String> eventsList;
    private String buildingName;
    
    public FloorRep(){
        this.floorId = 0;
        this.name = "";
        this.info = "";
        this.boundaryList = new ArrayList<>();
        this.eventsList = new ArrayList<>();
        this.roomList = new ArrayList<>();
        this.buildingName = "";
    }
    
    public FloorRep(Floor floor){
        this.floorId = floor.getFloorId();
        this.name = floor.getName().replace(" ", "");
        this.info = floor.getInfo();
        this.boundaryList = new ArrayList<>();
        this.eventsList = new ArrayList<>();
        this.roomList = new ArrayList<>();
        for(FloorBoundary floorBoundary:floor.getFloorBoundaryList()){
            String tmp = floorBoundary.getFloorX()+","+floorBoundary.getFloorY();
            this.boundaryList.add(tmp);
        }

        String tempName = "";
        for(Events e:floor.getEventsList()){
            tempName = e.getEventName();
            if(tempName != null){
                this.eventsList.add(tempName);
            }
        }
        for(Room r:floor.getRoomList()){
            tempName = r.getRoomName();
            if(tempName != null){
                this.roomList.add(tempName);
            }
        }
        this.buildingName = floor.getBuildingId().getName();
    }

    public List<String> getBoundaryList() {
        return boundaryList;
    }

    public void setBoundaryList(List<String> boundaryList) {
        this.boundaryList = boundaryList;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
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

    public List<String> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<String> roomList) {
        this.roomList = roomList;
    }

    public List<String> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<String> eventsList) {
        this.eventsList = eventsList;
    }

    public String getBuilding() {
        return buildingName;
    }

    public void setBuilding(String buildingName) {
        this.buildingName = buildingName;
    }
    
    @Override
    public String toString(){
        return "Floor";
    }
}
