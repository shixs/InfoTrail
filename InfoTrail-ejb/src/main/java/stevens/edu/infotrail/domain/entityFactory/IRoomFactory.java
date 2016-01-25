package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Floor;
import stevens.edu.infotrail.domain.entity.Room;

/**
 * The IRoomFactory interface provides methods for creating floor
 * object.
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
public interface IRoomFactory {
    Room createRoom(int roomId, String roomName, String roomInfo, String roomType, int roomX1, int roomY1, int roomX2, int roomY2, Floor floor);

    Room getRoom();
}
