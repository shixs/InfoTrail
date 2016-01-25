package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Building;
import stevens.edu.infotrail.domain.entity.Floor;

/**
 * FloorFactory implements methods that allow an application to create
 * a floor object.
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
public class FloorFactory implements IFloorFactory {

    @Override
    public Floor createFloor(int floorId, String name, String info, Building building) {
        Floor floor = new Floor();
        floor.setFloorId(floorId);
        floor.setName(name);
        floor.setInfo(info);
        floor.setBuildingId(building);
        return floor;
    }

    @Override
    public Floor getFloor() {
        return new Floor();
    }

}
