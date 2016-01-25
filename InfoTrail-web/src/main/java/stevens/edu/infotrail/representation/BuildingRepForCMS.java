package stevens.edu.infotrail.representation;

import stevens.edu.infotrail.domain.entity.Building;
import stevens.edu.infotrail.domain.entity.Media;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xshi90
 * @version %I%,%G%
 */
public class BuildingRepForCMS extends BuildingRep{

    private List<Media> medias;
    private int orgId;

    public BuildingRepForCMS(){
        super();
        this.medias = new ArrayList<>();
        this.orgId = 0;
    }

    public BuildingRepForCMS(Building building,List<Media> medias){
        super(building);
        this.orgId = (building != null && building.getOrgId() != null)?building.getOrgId().getOrgId():0;
        this.medias = new ArrayList<>();
        this.medias = (medias.size() > 0) ? medias:new ArrayList<Media>();
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString(){
        return "Building";
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }
}
