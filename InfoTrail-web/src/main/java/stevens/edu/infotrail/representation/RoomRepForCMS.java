package stevens.edu.infotrail.representation;

import stevens.edu.infotrail.domain.entity.Media;
import stevens.edu.infotrail.domain.entity.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xshi90
 * @version %I%,%G%
 */
public class RoomRepForCMS extends RoomRep{
    private List<Media> medias;
    public RoomRepForCMS(){
        super();
        this.medias = new ArrayList<>();
    }

    public RoomRepForCMS(Room room,List<Media> medias){
        super(room);
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
        return "Room";
    }
}
