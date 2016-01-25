package stevens.edu.infotrail.dao.daoFactory;

import stevens.edu.infotrail.dao.impl.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * DAOFactory class provides methods to obtain certain dao class.
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
@Stateless
public class DAOFactory {

    @PersistenceContext(unitName = "infotrail_domain")
    private EntityManager em;

    public EventDAOImpl getEventDAO() {
        return new EventDAOImpl(em);
    }

    public BeaconDAOImpl getBeaconDAO() {
        return new BeaconDAOImpl(em);
    }

    public OrganizationDAOImpl getOrDAO() {
        return new OrganizationDAOImpl(em);
    }

    public BuildingDAOImpl getBuildingDAO() {
        return new BuildingDAOImpl(em);
    }

    public FloorDAOImpl getFloorDAO() {
        return new FloorDAOImpl(em);
    }

    public RoomDAOImpl getRoomDAO() {
        return new RoomDAOImpl(em);
    }

    public ItemDAOImpl getItemDAO() {
        return new ItemDAOImpl(em);
    }

    public BoundariesDAOImpl getBoundariesDAO() {
        return new BoundariesDAOImpl(em);
    }

    public MediaDAOImpl getMediaDAO() {
        return new MediaDAOImpl(em);
    }

    public SpotDAOImpl getSpotDAO() {
        return new SpotDAOImpl(em);
    }

    public BldgBoundariesDAOImpl getBldgBoundariesDAO(){
        return new BldgBoundariesDAOImpl(em);
    }

    public FloorBoundariesDAOImpl getFloorBoundariesDAO(){
        return new FloorBoundariesDAOImpl(em);
    }
}
