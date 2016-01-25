package stevens.edu.infotrail.services;

import stevens.edu.infotrail.dao.DAO;
import stevens.edu.infotrail.dao.daoFactory.DAOFactory;
import stevens.edu.infotrail.domain.entity.*;
import stevens.edu.infotrail.helper.EntityToJSON;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author xshi90
 * @version %I%,%G%
 */
@RequestScoped
public class InfoService {
    private static Logger logger = Logger.getLogger(InfoService.class.getCanonicalName());
    @Inject
    private DAOFactory daoFactory;
    @Inject
    private EntityToJSON entityToJSON;
    private JsonArrayBuilder jArrayBuilder;
    private int itemCount;
    private StringBuffer errInfo;

    public InfoService() {
        this.itemCount = 0;
        this.errInfo = new StringBuffer("");
        this.jArrayBuilder = Json.createArrayBuilder();
    }

    public JsonArrayBuilder getjArrayBuilder() {
        return jArrayBuilder;
    }

    public void setjArrayBuilder(JsonArrayBuilder jArrayBuilder) {
        this.jArrayBuilder = jArrayBuilder;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public StringBuffer getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(StringBuffer errInfo) {
        this.errInfo = errInfo;
    }

    /**
     * @param itemId item ID
     */
    public void findById(String itemId) {
        Organization org = findOrg(itemId);
        if (org != null) {
            jArrayBuilder.add(entityToJSON.organizationToJSON(org));
            itemCount++;
            return;
        }
        Building building = findBuilding(itemId);
        if (building != null) {
            jArrayBuilder.add(entityToJSON.buildingToJSON(building));
            itemCount++;
            return;
        }
        Floor floor = findFloor(itemId);
        if (floor != null) {
            jArrayBuilder.add(entityToJSON.floorToJSON(floor));
            itemCount++;
            return;
        }
        Room room = findRoom(itemId);
        if (room != null) {
            jArrayBuilder.add(entityToJSON.roomToJSON(room));
            itemCount++;
            return;
        }
        Item item = findItem(itemId);
        if (item != null) {
            jArrayBuilder.add(entityToJSON.itemToJSON(item));
            itemCount++;
            return;
        }
        Events event = findEvent(itemId);
        if (event != null) {
            jArrayBuilder.add(entityToJSON.eventToJSON(event));
            itemCount++;
            return;
        }
        Media media = findMedia(itemId);
        if (media != null) {
            jArrayBuilder.add(entityToJSON.MediaToJSON(media));
            itemCount++;
            return;
        }
        Spot spot = findSpot(itemId);
        if (spot != null) {
            jArrayBuilder.add(entityToJSON.SpotToJSON(spot));
            itemCount++;
            return;
        }
        List<Beacon> beacons = findBeacon(itemId);
        if (beacons != null && beacons.size() > 0) {
            for (Beacon b : beacons) {
                jArrayBuilder.add(entityToJSON.beaconToJSON(b));
                itemCount++;
            }
        }
    }

    /**
     * @param str String value
     * @return Integer
     */
    private int toInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return Integer.MIN_VALUE;
        }
    }

    /**
     * @param id ID
     * @return Organization
     */
    public Organization findOrg(String id) {
        int tmpId = toInteger(id);
        try {
            return (tmpId != Integer.MIN_VALUE) ? daoFactory.getOrDAO().find(tmpId) : null;
        } catch (DAO.DaoExn ex) {
            logger.info("Not Found. ID = " + id);
            errInfo.append("DaoExn: Not Found. Organization ID = ").append(id).append(" | ");
            return null;
        }
    }

    /**
     * @param id ID
     * @return Building
     */
    public Building findBuilding(String id) {
        int tmpId = toInteger(id);
        try {
            return (tmpId != Integer.MIN_VALUE) ? daoFactory.getBuildingDAO().find(tmpId) : null;
        } catch (DAO.DaoExn ex) {
            logger.info("Not Found. ID = " + id);
            errInfo.append("DaoExn: Not Found. Building ID = ").append(id).append(" | ");
            return null;
        }
    }

    /**
     * @param id ID
     * @return Floor
     */
    public Floor findFloor(String id) {
        int tmpId = toInteger(id);
        try {
            return (tmpId != Integer.MIN_VALUE) ? daoFactory.getFloorDAO().find(tmpId) : null;
        } catch (DAO.DaoExn ex) {
            logger.info("Not Found. ID = " + id);
            errInfo.append("DaoExn: Not Found. Floor ID = ").append(id).append(" | ");
            return null;
        }
    }

    /**
     * @param id ID
     * @return Room
     */
    public Room findRoom(String id) {
        int tmpId = toInteger(id);
        try {
            return (tmpId != Integer.MIN_VALUE) ? daoFactory.getRoomDAO().find(tmpId) : null;
        } catch (DAO.DaoExn ex) {
            logger.info("Not Found. ID = " + id);
            errInfo.append("DaoExn: Not Found. Room ID = ").append(id).append(" | ");
            return null;
        }
    }

    /**
     * @param id ID
     * @return Item
     */
    public Item findItem(String id) {
        int tmpId = toInteger(id);
        try {
            return (tmpId != Integer.MIN_VALUE) ? daoFactory.getItemDAO().find(tmpId) : null;
        } catch (DAO.DaoExn ex) {
            logger.info("Not Found. ID = " + id);
            errInfo.append("DaoExn: Not Found. Item ID = ").append(id).append(" | ");
            return null;
        }
    }

    /**
     * @param id ID
     * @return Event
     */
    public Events findEvent(String id) {
        int tmpId = toInteger(id);
        try {
            return (tmpId != Integer.MIN_VALUE) ? daoFactory.getEventDAO().find(tmpId) : null;
        } catch (DAO.DaoExn ex) {
            logger.info("Not Found. ID = " + id);
            errInfo.append("DaoExn: Not Found. Event ID = ").append(id).append(" | ");
            return null;
        }
    }

    /**
     * @param ids ID
     * @return List of beacons
     */
    public List<Beacon> findBeacon(String ids) {
        if (ids.length() < 1) {
            return null;
        }
        List<Beacon> res = new ArrayList<>();
        String[] b_ids = ids.split("\\.");
        try {
            if (b_ids.length == 1) {
                List<Beacon> beacons = daoFactory.getBeaconDAO().findByUuid(b_ids[0]);
                if (beacons.size() >= 1) {
                    for (Beacon b : beacons) {
                        res.add(b);
                    }
                    return res;
                } else {
                    logger.info("Not Found. Beacon UUID = " + b_ids[0]);
                }
                int tmpId = toInteger(b_ids[0]);
                Beacon bOne = daoFactory.getBeaconDAO().find(tmpId);
                if (bOne != null) {
                    res.add(bOne);
                    return res;
                } else {
                    logger.info("Not Found. Beacon ID = " + tmpId);
                    return null;
                }
            } else if (b_ids.length == 2) {
                List<Beacon> tmpbeacons = daoFactory.getBeaconDAO().findByUMa(b_ids[0], Integer.parseInt(b_ids[1]));
                if (tmpbeacons.size() >= 1) {
                    for (Beacon b : tmpbeacons) {
                        res.add(b);
                    }
                    return res;
                } else {
                    logger.info("Not Found. Beacon number = " + b_ids[0] + b_ids[1]);
                    return null;
                }
            } else if (b_ids.length == 3) {
                Beacon beacon = daoFactory.getBeaconDAO().findByIds(b_ids[0], Integer.parseInt(b_ids[1]), Integer.parseInt(b_ids[2]));
                if (beacon != null) {
                    res.add(beacon);
                    return res;
                } else {
                    logger.info("Not Found. Beacon number = " + b_ids[0] + b_ids[1] + b_ids[2]);
                    return null;
                }
            }
            return res;
        } catch (DAO.DaoExn ex) {
            logger.info("Not Found. ID = " + ids);
            errInfo.append("DaoExn: Not Found. Beacon ID = ").append(ids).append(" | ");
            return null;
        }
    }

    /**
     * @param id ID
     * @return amazonS3
     */
    public Media findMedia(String id) {
        int tmpId = toInteger(id);
        try {
            return (tmpId != Integer.MIN_VALUE) ? daoFactory.getMediaDAO().find(tmpId) : null;
        } catch (DAO.DaoExn ex) {
            logger.info("Not Found. ID = " + id);
            errInfo.append("DaoExn: Not Found. amazonS3 ID = ").append(id).append(" | ");
            return null;
        }
    }

    /**
     * @param id ID
     * @return Spot
     */
    public Spot findSpot(String id) {
        int tmpId = toInteger(id);
        try {
            return (tmpId != Integer.MIN_VALUE) ? daoFactory.getSpotDAO().find(tmpId) : null;
        } catch (DAO.DaoExn ex) {
            logger.info("Not Found. ID = " + id);
            errInfo.append("DaoExn: Not Found. Spot ID = ").append(id).append(" | ");
            return null;
        }
    }

    /**
     * @param requestID  RequestID
     * @param itemsCount number of Item
     * @param error      String
     * @return JSON object
     */
    public JsonObject generateResponseJSON(String requestID, int itemsCount, String error) {
        JsonObject err = Json.createObjectBuilder().add("code", 0)
                .add("type", "")
                .add("description", error).build();

        return Json.createObjectBuilder().add("requestID", requestID)
                .add("itemsCount", itemsCount)
                .add("items", jArrayBuilder.build())
                .add("error", err).build();
    }
}
