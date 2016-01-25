package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Boundaries;
import stevens.edu.infotrail.domain.entity.Organization;

/**
 * BoundariesFactory implements methods that allow an application to create
 * a Boundaries object.
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
public class BoundariesFactory implements IBoundariesFactory {

    @Override
    public Boundaries createBoundaries(int boundairesId, double latitude, double longitude, Organization org) {
        Boundaries b = new Boundaries();
        b.setBoundryId(boundairesId);
        b.setLatitude(latitude);
        b.setLongitude(longitude);
        b.setOrgId(org);
        return b;
    }

    @Override
    public Boundaries getBoundaries() {
        return new Boundaries();
    }

}
