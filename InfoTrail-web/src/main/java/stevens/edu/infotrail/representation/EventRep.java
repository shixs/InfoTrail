package stevens.edu.infotrail.representation;

import stevens.edu.infotrail.domain.entity.Events;

/**
 *
 * @author Xingsheng
 */
public class EventRep {
    private int eventId;
    private String eventName;
    private String organizer;
    private String eventInfo;
    private String floorName;
    private String roomName;
    private String buildingName;
    
    public EventRep(){
        this.eventId = 0;
        this.eventName = "";
        this.organizer = "";
        this.eventInfo = "";
        this.floorName = "";
        this.roomName = "";
        this.buildingName = "";
    }
    
    public EventRep(Events event){

        this.eventId = event.getEventId();
        this.eventName = event.getEventName();
        this.organizer = event.getEventOrganizer();
        this.eventInfo = event.getEventInfo();
        this.floorName = event.getFloorId().getName();
        this.roomName = event.getRoomId().getRoomName();
        this.buildingName = event.getBuildingId().getName();
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(String eventInfo) {
        this.eventInfo = eventInfo;
    }

    public String getFloor() {
        return floorName;
    }

    public void setFloor(String floorName) {
        this.floorName = floorName;
    }

    public String getRoom() {
        return roomName;
    }

    public void setRoom(String roomName) {
        this.roomName = roomName;
    }

    public String getBuilding() {
        return buildingName;
    }

    public void setBuilding(String buildingName) {
        this.buildingName = buildingName;
    }
    
    @Override
    public String toString(){
        return "Event";
    } 
}
