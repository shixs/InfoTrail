package stevens.edu.infotrail.representation;

import stevens.edu.infotrail.domain.entity.Spot;

/**
 *
 * @author xshi90
 */
public class SpotRep {
    private int id;
    private String name;
    private String type;
    private String description;

    public SpotRep(){
        this.id = 0;
        this.name = "";
        this.description = "";
        this.type = "";
    }
    
    public SpotRep(Spot spot){
        this.id = spot.getSpotId();
        this.name = (spot.getName() != null) ? spot.getName():"";
        this.description = (spot.getDescription() != null) ? spot.getDescription() : "";
        this.type = (spot.getType() != null) ? spot.getType():"";
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
