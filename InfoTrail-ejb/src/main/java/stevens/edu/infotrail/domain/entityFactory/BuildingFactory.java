package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Building;
import stevens.edu.infotrail.domain.entity.Organization;

/**
 * @author xshi90
 * @version %I%,%G%
 */
public class BuildingFactory implements IBuildingFactory {
    @Override
    public Building createBuilding(int buildingId, String name, String address1, String address2, String city, String state, String country, String info, String zipcode, double latitude, double longitude, Organization org) {
        Building building = new Building();
        building.setBuildingId(buildingId);
        building.setName(name);
        building.setAddress1(address1);
        building.setAddress2(address2);
        building.setCity(city);
        building.setState(state);
        building.setCountry(country);
        building.setInfo(info);
        building.setZipcode(zipcode);
        building.setLatitude(latitude);
        building.setLongitude(longitude);
        building.setOrgId(org);
        return building;
    }

    @Override
    public Building getBuilding() {
        return new Building();
    }
}
