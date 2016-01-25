package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Building;
import stevens.edu.infotrail.domain.entity.Events;
import stevens.edu.infotrail.domain.entity.Floor;
import stevens.edu.infotrail.domain.entity.Room;

/**
 * EventFactory implements methods that allow an application to create
 * a Events object.
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
public class EventFactory implements IEventFactory {
    @Override
    public Events createEvent(int eventId, String eventName, String organizer, String info, Room room, Floor floor, Building building) {
        Events event = new Events();
        event.setEventId(eventId);
        event.setEventName(eventName);
        event.setEventOrganizer(organizer);
        event.setEventInfo(info);
        event.setRoomId(room);
        event.setFloorId(floor);
        event.setBuildingId(building);
        return event;
    }

    @Override
    public Events getEvent() {
        return new Events();
    }


}
