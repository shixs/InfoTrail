package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Boundaries;
import stevens.edu.infotrail.domain.entity.Organization;

/**
 * The IBoundariesFactory interface provides methods for creating boundaries
 * object.
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
public interface IBoundariesFactory {
    Boundaries createBoundaries(int boundairesId, double latitude, double longitude, Organization org);

    Boundaries getBoundaries();
}
