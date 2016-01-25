package stevens.edu.infotrail.representation;

import java.util.ArrayList;
import java.util.List;
import stevens.edu.infotrail.domain.entity.BldgBoundaries;
import stevens.edu.infotrail.domain.entity.Building;
import stevens.edu.infotrail.domain.entity.Events;
import stevens.edu.infotrail.domain.entity.Floor;

/**
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
public class BuildingRep {
    private int buildingId;
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String country;
    private String info;
    private String zipcode;
    private Double latitude;
    private Double longitude;
    private List<String> boundaries;
    private List<String> eventsList;
    private List<String> floorList;
    
    public BuildingRep(){
        this.buildingId = 0;
        this.name = "";
        this.address1 = "";
        this.address2 = "";
        this.city = "";
        this.state = "";
        this.country = "";
        this.info = "";
        this.zipcode = "";
        this.latitude = 0d;
        this.longitude = 0d;
        this.boundaries = new ArrayList<>();
        this.eventsList = new ArrayList<>();
        this.floorList = new ArrayList<>();
    }

    public BuildingRep(Building building){
        this.buildingId = building.getBuildingId();
        this.name = building.getName();
        this.address1 = building.getAddress1();
        this.address2 = building.getAddress2();
        this.city = building.getCity();
        this.state = building.getState();
        this.country = building.getCountry();
        this.info = building.getInfo();
        this.zipcode = building.getZipcode();
        this.latitude = building.getLatitude();
        this.longitude = building.getLongitude();
        this.boundaries = new ArrayList<>();
        this.eventsList = new ArrayList<>();
        this.floorList = new ArrayList<>();
        for(BldgBoundaries bb:building.getBldgBoundariesList()){
            String temp = bb.getLatitude()+","+bb.getLongitude();
            this.boundaries.add(temp);
        }
        for(Events e:building.getEventsList()){
            this.eventsList.add(e.getEventName());
        }
        for(Floor f:building.getFloorList()){
            this.floorList.add(f.getName());
        }
    }
    
    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<String> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<String> eventsList) {
        this.eventsList = eventsList;
    }

    public List<String> getFloorList() {
        return floorList;
    }

    public void setFloorList(List<String> floorList) {
        this.floorList = floorList;
    }

    public List<String> getBoundaries() {
        return boundaries;
    }

    public void setBoundaries(List<String> boundaries) {
        this.boundaries = boundaries;
    }
    
    @Override
    public String toString(){
        return "Building";
    }    
}
