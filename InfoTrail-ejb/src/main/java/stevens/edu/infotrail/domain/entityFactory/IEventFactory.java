package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Building;
import stevens.edu.infotrail.domain.entity.Events;
import stevens.edu.infotrail.domain.entity.Floor;
import stevens.edu.infotrail.domain.entity.Room;

/**
 * The IEventFactory interface provides methods for creating events
 * object.
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
public interface IEventFactory {
    Events createEvent(int eventId, String eventName, String organizer, String info, Room room, Floor floor, Building building);

    Events getEvent();
}
