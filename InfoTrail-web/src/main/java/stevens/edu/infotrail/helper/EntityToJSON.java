package stevens.edu.infotrail.helper;

import com.amazonaws.services.s3.model.S3Object;
import stevens.edu.infotrail.amazonS3.AmazonS3Helper;
import stevens.edu.infotrail.dao.DAO;
import stevens.edu.infotrail.dao.daoFactory.DAOFactory;
import stevens.edu.infotrail.domain.entity.*;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.awt.*;
import java.util.List;

/**
 * @author Xingsheng
 * @version %I%,%G%
 */
@RequestScoped
public class EntityToJSON {

    @Inject
    private AmazonS3Helper awsS3;

    @Inject
    private DAOFactory daoFactory;

    public EntityToJSON() {
        awsS3 = new AmazonS3Helper();
    }

    /**
     * @param beacon beacon entity
     * @return TreeNode
     */
    public JsonObject beaconToJSON(Beacon beacon) {

        String[] temp = (beacon.getLocation() != null) ? beacon.getLocation().split("-") : new String[0];
        JsonObject indoorLocation;
        if (temp.length > 0) {
            indoorLocation = Json.createObjectBuilder().add("objectType", "IndoorLocation")
                    .add("x", temp[0])
                    .add("y", temp[1]).build();
        } else {
            indoorLocation = Json.createObjectBuilder().add("objectType", "IndoorLocation")
                    .add("x", "")
                    .add("y", "").build();
        }

        return Json.createObjectBuilder().add("objectType", beacon.toString())
                .add("itemID", beacon.getBeaconId())
                .add("parentIndoorPlaceID", beacon.getItemId().getRoomId().getRoomId())
                .add("uuid", beacon.getUuid())
                .add("major", beacon.getMajor())
                .add("minor", beacon.getMinor())
                .add("indoorLocation", indoorLocation)
                .add("item", beacon.getItemId().getItemName()).build();
    }

    /**
     * @param item Item entity
     * @return TreeNode
     */
    public JsonObject itemToJSON(Item item) {
        //amazonS3 JSON Data
        JsonArrayBuilder tempMJsonBuilder = Json.createArrayBuilder();
        JsonArrayBuilder tempMJsonBuilder2 = Json.createArrayBuilder();
        JsonArrayBuilder tempMJsonBuilder3 = Json.createArrayBuilder();

        try {
            List<Media> medias = daoFactory.getMediaDAO().findByParentId(item.getItemId());
            for (Media m : medias) {
                switch (m.getType()) {
                    case "photo":
                        Image image = awsS3.getImage(m.getName());
                        int height = (image != null) ? image.getHeight(null) : 0;
                        int width = (image != null) ? image.getWidth(null) : 0;
                        String type = (image != null) ? m.getName().split("\\.")[1] : "";
                        tempMJsonBuilder.add(photo(m.getLink(), type, height, width));
                        break;
                    case "video":
                        S3Object video = awsS3.getObject(m.getName());
                        String vType = (video != null) ? m.getName().split("\\.")[1] : "";
                        tempMJsonBuilder2.add(video(m.getLink(), vType, m.getName(),0,0));
                        break;
                    case "web link":
                        tempMJsonBuilder3.add(webLink(m.getLink(), m.getName(), m.getDescription()));
                        break;
                    default:
                        break;
                }
            }
        } catch (DAO.DaoExn daoExn) {
            if (daoExn.getMessage().contains("amazonS3")) {
                tempMJsonBuilder.build();
            }
        }

        return Json.createObjectBuilder().add("objectType", item.toString())
                .add("itemID", item.getItemId())
                .add("parentID", item.getRoomId().getRoomId())
                .add("name", item.getItemName())
                .add("description", item.getItemInfo())
                .add("MediaTypePhoto",tempMJsonBuilder.build())
                .add("MediaTypeVideo",tempMJsonBuilder2.build())
                .add("MediaTypeWebLink",tempMJsonBuilder3.build()).build();
    }

    /**
     * @param building Building entity
     * @return TreeNode
     */
    public JsonObject buildingToJSON(Building building) {
        int tempEventsCount = building.getEventsList().size();
        JsonArrayBuilder tempEJsonBuilder = Json.createArrayBuilder();
        if (tempEventsCount >= 1) {
            for (int i = 0; i < tempEventsCount; i++) {
                JsonObject current = Json.createObjectBuilder().add("event " + i, building.getEventsList().get(i).getEventName()).build();
                tempEJsonBuilder.add(current);
            }
        }
        int tempFloorCount = building.getFloorList().size();
        JsonArrayBuilder tempFJsonBuilder = Json.createArrayBuilder();
        if (tempFloorCount >= 1) {
            for (int i = 0; i < tempFloorCount; i++) {
                JsonObject current = Json.createObjectBuilder().add("floor " + i, building.getFloorList().get(i).getName()).build();
                tempFJsonBuilder.add(current);
            }
        }
        int tempBoundaryCount = building.getBldgBoundariesList().size();
        JsonArrayBuilder tempBoJsonBuilder = Json.createArrayBuilder();
        if (tempBoundaryCount >= 1) {
            for (int i = 0; i < tempBoundaryCount; i++) {
                JsonObject current = Json.createObjectBuilder().add("lat", building.getBldgBoundariesList().get(i).getLatitude())
                        .add("lon", building.getBldgBoundariesList().get(i).getLongitude()).build();
                tempBoJsonBuilder.add(current);
            }
        }
        //amazonS3 JSON Data
        JsonArrayBuilder tempMJsonBuilder = Json.createArrayBuilder();
        JsonArrayBuilder tempMJsonBuilder2 = Json.createArrayBuilder();
        JsonArrayBuilder tempMJsonBuilder3 = Json.createArrayBuilder();

        try {
            List<Media> medias = daoFactory.getMediaDAO().findByParentId(building.getBuildingId());
            for (Media m : medias) {
                switch (m.getType()) {
                    case "photo":
                        Image image = awsS3.getImage(m.getName());
                        int height = (image != null) ? image.getHeight(null) : 0;
                        int width = (image != null) ? image.getWidth(null) : 0;
                        String type = (image != null) ? m.getName().split("\\.")[1] : "";
                        tempMJsonBuilder.add(photo(m.getLink(), type, height, width));
                        break;
                    case "video":
                        S3Object video = awsS3.getObject(m.getName());
                        String vType = (video != null) ? m.getName().split("\\.")[1] : "";
                        tempMJsonBuilder2.add(video(m.getLink(), vType, m.getName(),0,0));
                        break;
                    case "web link":
                        tempMJsonBuilder3.add(webLink(m.getLink(), m.getName(), m.getDescription()));
                        break;
                    default:
                        break;
                }
            }
        } catch (DAO.DaoExn daoExn) {
            if (daoExn.getMessage().contains("amazonS3")) {
                tempMJsonBuilder.build();
            }
        }
        JsonArrayBuilder tmpArray = Json.createArrayBuilder();
        List<Floor> floors = building.getFloorList();
        if (floors != null) {
            for (Floor f : floors) {
                List<Room> rooms = f.getRoomList();
                if (rooms != null) {
                    for (Room r : rooms) {
                        List<Item> items = r.getItemList();
                        if (items != null) {
                            for (Item it : items) {
                                List<Beacon> beacons = it.getBeaconList();
                                if (beacons != null) {
                                    for (Beacon b : beacons) {
                                        List<Spot> spots = b.getSpotList();
                                        if (spots != null) {
                                            for (Spot sp : spots) {

                                                JsonObject spotJO = Json.createObjectBuilder().add("objectType", sp.toString())
                                                        .add("itemID", sp.getSpotId())
                                                        .add("parentID", sp.getBeaconId().getItemId().getRoomId().getRoomId())
                                                        .add("name", sp.getName())
                                                        .add("description", sp.getDescription())
                                                        .build();
                                                tmpArray.add(spotJO);
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }

        return Json.createObjectBuilder().add("objectType", building.toString())
                .add("itemID", building.getBuildingId())
                .add("parentID", building.getOrgId().getOrgId())
                .add("name", building.getName())
                .add("address", building.getAddress1() + building.getAddress2())
                .add("city", building.getCity())
                .add("state", building.getState())
                .add("country", building.getCountry())
                .add("description", building.getInfo())
                .add("zipcode", building.getZipcode())
                .add("latitude", building.getLatitude())
                .add("longitude", building.getLongitude())
                .add("spots", tmpArray.build())
                .add("outdoorBoundary", tempBoJsonBuilder.build())
                .add("MediaTypePhoto",tempMJsonBuilder.build())
                .add("MediaTypeVideo",tempMJsonBuilder2.build())
                .add("MediaTypeWebLink",tempMJsonBuilder3.build())
                .add("events", tempEJsonBuilder.build())
                .add("floors", tempFJsonBuilder.build()).build();
    }

    /**
     * @param floor Floor entity
     * @return JSON object
     */
    public JsonObject floorToJSON(Floor floor) {
        int tempEventsCount = floor.getEventsList().size();
        JsonArrayBuilder tempEJsonBuilder = Json.createArrayBuilder();
        if (tempEventsCount >= 1) {
            for (int i = 0; i < tempEventsCount; i++) {
                JsonObject current = Json.createObjectBuilder().add("event " + i, floor.getEventsList().get(i).getEventName()).build();
                tempEJsonBuilder.add(current);
            }
        }
        int tempRoomCount = floor.getRoomList().size();
        JsonArrayBuilder tempRJsonBuilder = Json.createArrayBuilder();
        if (tempRoomCount >= 1) {
            for (int i = 0; i < tempRoomCount; i++) {
                JsonObject current = Json.createObjectBuilder().add("room " + i, floor.getRoomList().get(i).getRoomName()).build();
                tempRJsonBuilder.add(current);
            }
        }

        int tempBoundaryCount = floor.getFloorBoundaryList().size();
        JsonArrayBuilder tempBoJsonBuilder = Json.createArrayBuilder();
        if (tempBoundaryCount >= 1) {
            for (int i = 0; i < tempBoundaryCount; i++) {
                JsonObject current = Json.createObjectBuilder().add("x", floor.getFloorBoundaryList().get(i).getFloorX())
                        .add("y", floor.getFloorBoundaryList().get(i).getFloorY()).build();
                tempBoJsonBuilder.add(current);
            }
        }

        //amazonS3 JSON Data
        JsonArrayBuilder tempMJsonBuilder = Json.createArrayBuilder();
        JsonArrayBuilder tempMJsonBuilder2 = Json.createArrayBuilder();
        JsonArrayBuilder tempMJsonBuilder3 = Json.createArrayBuilder();

        try {
            List<Media> medias = daoFactory.getMediaDAO().findByParentId(floor.getFloorId());
            for (Media m : medias) {
                switch (m.getType()) {
                    case "photo":
                        Image image = awsS3.getImage(m.getName());
                        int height = (image != null) ? image.getHeight(null) : 0;
                        int width = (image != null) ? image.getWidth(null) : 0;
                        String type = (image != null) ? m.getName().split("\\.")[1] : "";
                        tempMJsonBuilder.add(photo(m.getLink(), type, height, width));
                        break;
                    case "video":
                        S3Object video = awsS3.getObject(m.getName());
                        String vType = (video != null) ? m.getName().split("\\.")[1] : "";
                        tempMJsonBuilder2.add(video(m.getLink(), vType, m.getName(),0,0));
                        break;
                    case "web link":
                        tempMJsonBuilder3.add(webLink(m.getLink(), m.getName(), m.getDescription()));
                        break;
                    default:
                        break;
                }
            }
        } catch (DAO.DaoExn daoExn) {
            if (daoExn.getMessage().contains("amazonS3")) {
                tempMJsonBuilder.build();
            }
        }

        return Json.createObjectBuilder().add("objectType", floor.toString())
                .add("itemID", floor.getFloorId())
                .add("parentID", floor.getBuildingId().getBuildingId())
                .add("name", floor.getName())
                .add("description", floor.getInfo())
                .add("indoorBoundary",tempBoJsonBuilder.build())
                .add("events", tempEJsonBuilder.build())
                .add("rooms", tempRJsonBuilder.build())
                .add("MediaTypePhoto",tempMJsonBuilder.build())
                .add("MediaTypeVideo",tempMJsonBuilder2.build())
                .add("MediaTypeWebLink",tempMJsonBuilder3.build()).build();
    }

    private JsonObject photo(String link, String format, int height, int width) {
        return Json.createObjectBuilder().add("objectType", "photo")
                .add("link", link)
                .add("format", format)
                .add("height", height)
                .add("width", width).build();
    }

    private JsonObject video(String link, String format, String name,int height, int width) {
        return Json.createObjectBuilder().add("objectType", "video")
                .add("name", name)
                .add("link", link)
                .add("format", format)
                .add("height", height)
                .add("width", width).build();
    }

    private JsonObject webLink(String link, String name, String description) {
        return Json.createObjectBuilder().add("objectType", "web link")
                .add("name", name)
                .add("link", link)
                .add("description", description).build();
    }

    /**
     * @param room Room entity
     * @return JSON object
     */
    public JsonObject roomToJSON(Room room) {
        int tempEventsCount = room.getEventsList().size();
        JsonArrayBuilder tempEJsonBuilder = Json.createArrayBuilder();
        if (tempEventsCount >= 1) {
            for (int i = 0; i < tempEventsCount; i++) {
                JsonObject current = Json.createObjectBuilder().add("event " + i, room.getEventsList().get(i).getEventName()).build();
                tempEJsonBuilder.add(current);
            }
        }
        int tempItemCount = room.getItemList().size();
        JsonArrayBuilder tempIJsonBuilder = Json.createArrayBuilder();
        if (tempItemCount >= 1) {
            for (int i = 0; i < tempItemCount; i++) {
                JsonObject current = Json.createObjectBuilder().add("item " + i, room.getItemList().get(i).getItemName()).build();
                tempIJsonBuilder.add(current);
            }
        }

        //amazonS3 JSON Data
        JsonArrayBuilder tempMJsonBuilder = Json.createArrayBuilder();
        JsonArrayBuilder tempMJsonBuilder2 = Json.createArrayBuilder();
        JsonArrayBuilder tempMJsonBuilder3 = Json.createArrayBuilder();

        try {
            List<Media> medias = daoFactory.getMediaDAO().findByParentId(room.getRoomId());
            for (Media m : medias) {
                switch (m.getType()) {
                    case "photo":
                        Image image = awsS3.getImage(m.getName());
                        int height = (image != null) ? image.getHeight(null) : 0;
                        int width = (image != null) ? image.getWidth(null) : 0;
                        String type = (image != null) ? m.getName().split("\\.")[1] : "";
                        tempMJsonBuilder.add(photo(m.getLink(), type, height, width));
                        break;
                    case "video":
                        S3Object video = awsS3.getObject(m.getName());
                        String vType = (video != null) ? m.getName().split("\\.")[1] : "";
                        tempMJsonBuilder2.add(video(m.getLink(), vType, m.getName(),0,0));
                        break;
                    case "web link":
                        tempMJsonBuilder3.add(webLink(m.getLink(), m.getName(), m.getDescription()));
                        break;
                    default:
                        break;
                }
            }
        } catch (DAO.DaoExn daoExn) {
            if (daoExn.getMessage().contains("amazonS3")) {
                tempMJsonBuilder.build();
            }
        }
        int roomX1 = (room.getRoomX1() == null) ? 0 : room.getRoomX1();
        int roomY1 = (room.getRoomY1() == null) ? 0 : room.getRoomY1();
        int roomX2 = (room.getRoomX2() == null) ? 0 : room.getRoomX2();
        int roomY2 = (room.getRoomY2() == null) ? 0 : room.getRoomY2();

        return Json.createObjectBuilder().add("objectType", room.toString())
                .add("itemID", room.getRoomId())
                .add("parentID", room.getFloorId().getFloorId())
                .add("name", room.getRoomName())
                .add("description", room.getRoomInfo())
                .add("roomType", room.getRoomType())
                .add("roomX1", roomX1)
                .add("roomY1", roomY1)
                .add("roomX2", roomX2)
                .add("roomY2", roomY2)
                .add("MediaTypePhoto",tempMJsonBuilder.build())
                .add("MediaTypeVideo", tempMJsonBuilder2.build())
                .add("MediaTypeWebLink",tempMJsonBuilder3.build())
                .add("floor", room.getFloorId().getName())
                .add("events", tempEJsonBuilder.build())
                .add("items", tempIJsonBuilder.build()).build();
    }

    /**
     * @param event Event entity
     * @return JSON object
     */
    public JsonObject eventToJSON(Events event) {
        //amazonS3 JSON Data
        JsonArrayBuilder tempMJsonBuilder = Json.createArrayBuilder();
        JsonArrayBuilder tempMJsonBuilder2 = Json.createArrayBuilder();
        JsonArrayBuilder tempMJsonBuilder3 = Json.createArrayBuilder();

        try {
            List<Media> medias = daoFactory.getMediaDAO().findByParentId(event.getEventId());
            for (Media m : medias) {
                switch (m.getType()) {
                    case "photo":
                        Image image = awsS3.getImage(m.getName());
                        int height = (image != null) ? image.getHeight(null) : 0;
                        int width = (image != null) ? image.getWidth(null) : 0;
                        String type = (image != null) ? m.getName().split("\\.")[1] : "";
                        tempMJsonBuilder.add(photo(m.getLink(), type, height, width));
                        break;
                    case "video":
                        S3Object video = awsS3.getObject(m.getName());
                        String vType = (video != null) ? m.getName().split("\\.")[1] : "";
                        tempMJsonBuilder2.add(video(m.getLink(), vType, m.getName(),0,0));
                        break;
                    case "web link":
                        tempMJsonBuilder3.add(webLink(m.getLink(), m.getName(), m.getDescription()));
                        break;
                    default:
                        break;
                }
            }
        } catch (DAO.DaoExn daoExn) {
            if (daoExn.getMessage().contains("amazonS3")) {
                tempMJsonBuilder.build();
            }
        }
        /*JsonObject res = Json.createObjectBuilder().add("MediaTypePhoto",tempMJsonBuilder.build())
                .add("MediaTypeVideo",tempMJsonBuilder2.build())
                .add("MediaTypeWebLink",tempMJsonBuilder3.build()).build();*/
        return Json.createObjectBuilder().add("objectType", event.toString())
                .add("itemID", event.getEventId())
                .add("parentID", event.getRoomId().getRoomId())
                .add("name", event.getEventName())
                .add("organizer", event.getEventOrganizer())
                .add("description", event.getEventInfo())
                .add("startDate", "to be replaced with a real date")
                .add("endDate", "to be replaced with a real date")
                .add("floor", event.getFloorId().getName())
                .add("room", event.getRoomId().getRoomName())
                .add("building", event.getBuildingId().getName())
                .add("MediaTypePhoto",tempMJsonBuilder.build())
                .add("MediaTypeVideo",tempMJsonBuilder2.build())
                .add("MediaTypeWebLink",tempMJsonBuilder3.build()).build();
    }

    /**
     * @param organization Organization entity
     * @return JSON object
     */
    public JsonObject organizationToJSON(Organization organization) {
        int tempBoundaryCount = organization.getBoundaries().size();
        JsonArrayBuilder tempBoJsonBuilder = Json.createArrayBuilder();
        if (tempBoundaryCount >= 1) {
            for (int i = 0; i < tempBoundaryCount; i++) {
                JsonObject current = Json.createObjectBuilder().add("lat", organization.getBoundaries().get(i).getLatitude())
                        .add("lon", organization.getBoundaries().get(i).getLongitude()).build();
                tempBoJsonBuilder.add(current);
            }
        }
        //amazonS3 JSON Data
        JsonArrayBuilder tempMJsonBuilder = Json.createArrayBuilder();
        JsonArrayBuilder tempMJsonBuilder2 = Json.createArrayBuilder();
        JsonArrayBuilder tempMJsonBuilder3 = Json.createArrayBuilder();

        try {
            List<Media> medias = daoFactory.getMediaDAO().findByParentId(organization.getOrgId());
            for (Media m : medias) {
                switch (m.getType()) {
                    case "photo":
                        Image image = awsS3.getImage(m.getName());
                        int height = (image != null) ? image.getHeight(null) : 0;
                        int width = (image != null) ? image.getWidth(null) : 0;
                        String type = (image != null) ? m.getName().split("\\.")[1] : "";
                        tempMJsonBuilder.add(photo(m.getLink(), type, height, width));
                        break;
                    case "video":
                        S3Object video = awsS3.getObject(m.getName());
                        String vType = (video != null) ? m.getName().split("\\.")[1] : "";
                        tempMJsonBuilder2.add(video(m.getLink(), vType, m.getName(),0,0));
                        break;
                    case "web link":
                        tempMJsonBuilder3.add(webLink(m.getLink(), m.getName(), m.getDescription()));
                        break;
                    default:
                        break;
                }
            }
        } catch (DAO.DaoExn daoExn) {
            if (daoExn.getMessage().contains("amazonS3")) {
                tempMJsonBuilder.build();
            }
        }

        return Json.createObjectBuilder().add("objectType", organization.toString())
                .add("itemID", organization.getOrgId())
                .add("parentID", "")
                .add("name", organization.getName())
                .add("address", organization.getAddress1() + organization.getAddress2())
                .add("city", organization.getCity())
                .add("state", organization.getState())
                .add("zipcode", organization.getZip())
                .add("outdoorBoundary", tempBoJsonBuilder.build())
                .add("MediaTypePhoto",tempMJsonBuilder.build())
                .add("MediaTypeVideo",tempMJsonBuilder2.build())
                .add("MediaTypeWebLink",tempMJsonBuilder3.build()).build();
    }

    /**
     * @param media amazonS3 entity
     * @return JSON object
     */
    public JsonObject MediaToJSON(Media media) {

        return Json.createObjectBuilder().add("objectType", media.toString())
                .add("itemID", media.getMediaId())
                .add("parentID", media.getParentId())
                .add("type", media.getType())
                .add("name", media.getName())
                .add("link", media.getLink())
                .add("description", media.getDescription())
                .build();
    }

    /**
     * @param spot Spot entity
     * @return JSON object
     */
    public JsonObject SpotToJSON(Spot spot) {

        return Json.createObjectBuilder().add("objectType", spot.toString())
                .add("itemID", spot.getSpotId())
                .add("parentID", spot.getBeaconId().getItemId().getRoomId().getRoomId())
                .add("name", spot.getName())
                .add("description", spot.getDescription())
                .build();
    }

}
