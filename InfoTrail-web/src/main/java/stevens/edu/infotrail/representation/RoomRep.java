package stevens.edu.infotrail.representation;

import java.util.ArrayList;
import java.util.List;
import stevens.edu.infotrail.domain.entity.Events;
import stevens.edu.infotrail.domain.entity.Item;
import stevens.edu.infotrail.domain.entity.Room;

/**
 *
 * @author Xingsheng
 */
public class RoomRep {
    private int roomId;
    private String roomName;
    private String roomInfo;
    private String roomType;
    private int roomX1;
    private int roomY1;
    private int roomX2;
    private int roomY2;
    private String floorName;
    private List<String> itemList;
    private List<String> eventsList;
    
    public RoomRep(){
        this.roomId = 0;
        this.roomName = "";
        this.roomInfo = "";
        this.roomType = "";
        this.roomX1 = 0;
        this.roomY1 = 0;
        this.roomX2 = 0;
        this.roomY2 = 0;
        this.floorName = "";
        this.eventsList = new ArrayList<>();
        this.itemList = new ArrayList<>();
    }

    public RoomRep(Room room){
        this.roomId = room.getRoomId();
        this.roomName = room.getRoomName();
        this.roomInfo = room.getRoomInfo();
        this.roomType = room.getRoomType();
        this.roomX1 = (room.getRoomX1() != null) ? room.getRoomX1() : 0;
        this.roomY1 = (room.getRoomY1() != null) ? room.getRoomY1() : 0;
        this.roomX2 = (room.getRoomX2() != null) ? room.getRoomX2() : 0;
        this.roomY2 = (room.getRoomY2() != null) ? room.getRoomY2() : 0;
        this.floorName = room.getFloorId().getName();
        this.eventsList = new ArrayList<>();
        this.itemList = new ArrayList<>();
        for(Events e:room.getEventsList()){
            this.eventsList.add(e.getEventName());
        }
        for(Item i:room.getItemList()){
            this.itemList.add(i.getItemName());
        }
    }
    
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
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

    public int getRoomX1() {
        return roomX1;
    }

    public void setRoomX1(int roomX1) {
        this.roomX1 = roomX1;
    }

    public int getRoomY1() {
        return roomY1;
    }

    public void setRoomY1(int roomY1) {
        this.roomY1 = roomY1;
    }

    public int getRoomX2() {
        return roomX2;
    }

    public void setRoomX2(int roomX2) {
        this.roomX2 = roomX2;
    }

    public int getRoomY2() {
        return roomY2;
    }

    public void setRoomY2(int roomY2) {
        this.roomY2 = roomY2;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getFloor() {
        return floorName;
    }

    public void setFloor(String floorName) {
        this.floorName = floorName;
    }

    public List<String> getItemList() {
        return itemList;
    }

    public void setItemList(List<String> itemList) {
        this.itemList = itemList;
    }

    public List<String> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<String> eventsList) {
        this.eventsList = eventsList;
    }
    
    @Override
    public String toString(){
        return "Room";
    }
}
