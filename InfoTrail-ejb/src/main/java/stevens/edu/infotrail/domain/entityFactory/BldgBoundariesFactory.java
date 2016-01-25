package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.BldgBoundaries;
import stevens.edu.infotrail.domain.entity.Building;

/**
 * @author xshi90
 * @version %I%,%G%
 */
public class BldgBoundariesFactory implements IBldgBoundariesFactory {
    @Override
    public BldgBoundaries createBldgBoundaries(int boundairesId, double latitude, double longitude, Building building) {
        BldgBoundaries b = new BldgBoundaries();
        b.setBoundryId(boundairesId);
        b.setLatitude(latitude);
        b.setLongitude(longitude);
        b.setBuildingId(building);
        return b;
    }

    @Override
    public BldgBoundaries getBldgBoundaries() {
        return new BldgBoundaries();
    }
}
