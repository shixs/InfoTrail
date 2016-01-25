package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Beacon;
import stevens.edu.infotrail.domain.entity.Item;

/**
 * BeaconFactory implements methods that allow an application to create
 * a beacon object.
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
public class BeaconFactory implements IBeaconFactory {
    @Override
    public Beacon createBeacon(int beaconId, String uuid, int major, int minor, String location, Item item) {
        Beacon beacon = new Beacon();
        beacon.setBeaconId(beaconId);
        beacon.setUuid(uuid);
        beacon.setMajor(major);
        beacon.setMinor(minor);
        beacon.setLocation(location);
        beacon.setItemId(item);
        return beacon;
    }

    @Override
    public Beacon getBeacon() {
        return new Beacon();
    }
}
