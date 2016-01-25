package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Spot;

/**
 * @author xshi90
 * @version %I%,%G%
 */
public class SpotFactory implements ISpotFactory {

    @Override
    public Spot createSpot(int spotID, String name, String description) {
        Spot spot = new Spot();
        spot.setSpotId(spotID);
        spot.setName(name);
        spot.setDescription(description);
        return spot;
    }

    @Override
    public Spot getSpot() {
        return new Spot();
    }

}
