package stevens.edu.infotrail.representation;

import stevens.edu.infotrail.domain.entity.Events;
import stevens.edu.infotrail.domain.entity.Media;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xshi90
 * @version %I%,%G%
 */
public class EventRepForCMS extends EventRep{
    private List<Media> medias;
    public EventRepForCMS(){
        super();
        this.medias = new ArrayList<>();
    }
    public EventRepForCMS(Events event,List<Media> medias){
        super(event);
        this.medias = new ArrayList<>();
        this.medias = (medias.size() > 0) ? medias:new ArrayList<Media>();
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    @Override
    public String toString(){
        return "Event";
    }
}
