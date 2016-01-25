package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Building;
import stevens.edu.infotrail.domain.entity.Floor;

/**
 * The IFloorFactory interface provides methods for creating floor
 * object.
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
public interface IFloorFactory {
    /**
     * Returns a new floor created with the given info.
     *
     * @param floorId    Floor ID
     * @param name       Floor name
     * @param info       Floor information
     * @param building   Building
     * @return Floor
     */
    Floor createFloor(int floorId, String name, String info, Building building);

    Floor getFloor();
}
