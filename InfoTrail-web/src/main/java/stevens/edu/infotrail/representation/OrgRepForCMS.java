package stevens.edu.infotrail.representation;

import stevens.edu.infotrail.domain.entity.Media;
import stevens.edu.infotrail.domain.entity.Organization;

import javax.enterprise.context.RequestScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xshi90
 * @version %I%,%G%
 */
@RequestScoped
public class OrgRepForCMS extends OrgRep{
    private List<Media> medias;

    public OrgRepForCMS(){
        super();
        this.medias = new ArrayList<>();
    }
    public OrgRepForCMS(Organization organization, List<Media> medias){
        super(organization);
        this.medias = new ArrayList<>();
        this.medias = (medias.size() > 0) ? medias:new ArrayList<Media>();
    }

    @Override
    public String toString(){
        return "Organization";
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }
}
