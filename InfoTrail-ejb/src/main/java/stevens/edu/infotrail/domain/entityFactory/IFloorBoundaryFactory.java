
package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Floor;
import stevens.edu.infotrail.domain.entity.FloorBoundary;

/**
 *
 * @author xshi90
 */
public interface IFloorBoundaryFactory {
    FloorBoundary createFloorBoundary(int boundaryId, int floorX, int floorY, Floor floorId);
    
    FloorBoundary getFloorBoundary();
}
