package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Media;

/**
 * The IMediaFactory interface provides methods for creating media
 * object.
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
public interface IMediaFactory {
    Media createMedia(String link, String type, int parentID, String name, String description);

    Media getMedia();
}
