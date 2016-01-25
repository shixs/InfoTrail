package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Media;

/**
 * @author xshi90
 * @version %I%,%G%
 */
public class MediaFactory implements IMediaFactory {

    @Override
    public Media createMedia(String link, String type, int parentID, String name, String description) {
        Media media = new Media();
        media.setLink(link);
        media.setType(type);
        media.setParentId(parentID);
        media.setName(name);
        media.setDescription(description);
        return media;
    }

    @Override
    public Media getMedia() {
        return new Media();
    }

}
