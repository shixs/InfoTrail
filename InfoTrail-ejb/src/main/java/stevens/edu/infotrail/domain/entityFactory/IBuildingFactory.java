package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Building;
import stevens.edu.infotrail.domain.entity.Organization;

/**
 * @author xshi90
 * @version %I%,%G%
 */
public interface IBuildingFactory {
    public Building createBuilding(int buildingId,
                                   String name,
                                   String address1,
                                   String address2,
                                   String city,
                                   String state,
                                   String country,
                                   String info,
                                   String zipcode,
                                   double latitude,
                                   double longitude,
                                   Organization org);

    public Building getBuilding();
}
