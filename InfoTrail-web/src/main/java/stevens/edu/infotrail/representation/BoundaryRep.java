package stevens.edu.infotrail.representation;

import stevens.edu.infotrail.domain.entity.Boundaries;

/**
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
public class BoundaryRep {
    private double latitude;
    private double longitude;
    private String organizationName;
    
    public BoundaryRep(){
        this.latitude = 0;
        this.longitude = 0;
        this.organizationName = "";
    }

    public BoundaryRep(Boundaries boundaries){
        this.latitude = boundaries.getLatitude();
        this.longitude = boundaries.getLongitude();
        this.organizationName = boundaries.getOrgId().getName();
    }
    
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getOrganization() {
        return organizationName;
    }

    public void setOrganization(String organizationName) {
        this.organizationName = organizationName;
    }
    
    @Override
    public String toString(){
        return "Boundary";
    }
}
