package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Spot;

/**
 * @author Xingsheng
 * @version %I%,%G%
 */
public interface ISpotFactory {
    Spot createSpot(int spotID, String name, String description);

    Spot getSpot();
}
