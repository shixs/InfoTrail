package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Beacon;
import stevens.edu.infotrail.domain.entity.Item;

/**
 * The IBeaconFactory interface provides methods for creating beacon
 * object.
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
public interface IBeaconFactory {

    /**
     * Returns a new beacon created with the given info.
     *
     * @param beaconId Beacon ID
     * @param uuid     Beacon UUID
     * @param major    Beacon major
     * @param minor    Beacon minor
     * @param location Beacon location
     * @param item     Item
     * @return Beacon
     */
    Beacon createBeacon(int beaconId, String uuid, int major, int minor, String location, Item item);

    Beacon getBeacon();
}
