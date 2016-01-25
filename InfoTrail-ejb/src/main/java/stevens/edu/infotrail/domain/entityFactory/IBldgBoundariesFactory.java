package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.BldgBoundaries;
import stevens.edu.infotrail.domain.entity.Building;

/**
 * @author xshi90
 * @version %I%,%G%
 */
public interface IBldgBoundariesFactory {
    BldgBoundaries createBldgBoundaries(int boundairesId, double latitude, double longitude, Building building);

    BldgBoundaries getBldgBoundaries();
}
