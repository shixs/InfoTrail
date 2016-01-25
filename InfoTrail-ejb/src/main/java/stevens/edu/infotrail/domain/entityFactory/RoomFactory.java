package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Floor;
import stevens.edu.infotrail.domain.entity.Room;

/**
 * RoomFactory implements methods that allow an application to create
 * a Room object.
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
public class RoomFactory implements IRoomFactory {

    @Override
    public Room createRoom(int roomId, String roomName, String roomInfo, String roomType, int roomX1, int roomY1,int roomX2, int roomY2, Floor floor) {
        Room room = new Room();
        room.setRoomId(roomId);
        room.setRoomName(roomName);
        room.setRoomInfo(roomInfo);
        room.setRoomType(roomType);
        room.setRoomX1(roomX1);
        room.setRoomY1(roomY1);
        room.setRoomX2(roomX2);
        room.setRoomY2(roomY2);
        room.setFloorId(floor);
        return room;
    }

    @Override
    public Room getRoom() {
        return new Room();
    }

}
