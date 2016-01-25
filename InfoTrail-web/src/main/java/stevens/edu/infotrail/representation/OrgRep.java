package stevens.edu.infotrail.representation;

import java.util.ArrayList;
import java.util.List;
import stevens.edu.infotrail.domain.entity.Boundaries;
import stevens.edu.infotrail.domain.entity.Organization;

/**
 *
 * @author Xingsheng
 */
public class OrgRep {
    private int orgId;
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private List<String> boundaries;
    private static final double map_scale = 1.135646688;

    public OrgRep(){
        this.orgId = 0;
        this.name = "";
        this.address1 = "";
        this.address2 = "";
        this.city = "";
        this.state = "";
        this.zip = "";
        this.boundaries = new ArrayList<>();
    }
    
    public OrgRep(Organization organization){
        this.orgId = organization.getOrgId();
        this.name = organization.getName();
        this.address1 = organization.getAddress1();
        this.address2 = organization.getAddress2();
        this.city = organization.getCity();
        this.state = organization.getState();
        this.zip = organization.getZip();
        String temp;
        this.boundaries = new ArrayList<>();
        for(Boundaries b:organization.getBoundaries()){
            temp = b.getLatitude()+","+b.getLongitude();
            this.boundaries.add(temp);
        }
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public List<String> getBoundaries() {
        return boundaries;
    }

    public void setBoundaries(List<String> boundaries) {
        this.boundaries = boundaries;
    }
    
    @Override
    public String toString(){
        return "Organization";
    }
}
