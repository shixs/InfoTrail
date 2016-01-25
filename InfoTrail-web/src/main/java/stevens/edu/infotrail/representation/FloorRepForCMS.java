package stevens.edu.infotrail.representation;

import stevens.edu.infotrail.domain.entity.Floor;
import stevens.edu.infotrail.domain.entity.Media;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author xshi90
 * @version %I%,%G%
 */
public class FloorRepForCMS extends FloorRep {
    private static Logger logger = Logger.getLogger(FloorRepForCMS.class.getCanonicalName());
    private List<Media> medias;
    public FloorRepForCMS(){
        super();
        this.medias = new ArrayList<>();
    }
    public FloorRepForCMS(Floor floor,List<Media> medias){
        super(floor);
        logger.info(String.valueOf(medias.size()));
        this.medias = new ArrayList<>();
        this.medias = (medias.size() > 0) ? medias:new ArrayList<Media>();
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }
}
