package stevens.edu.infotrail.dao.impl;

import stevens.edu.infotrail.domain.entity.Building;
import stevens.edu.infotrail.domain.entity.Floor;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * FloorDAOImpl is implemented as a subclass of the DAOImpl class.
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
@Stateless
public class FloorDAOImpl extends DAOImpl<Floor> {

    @PersistenceContext
    private EntityManager em;

    public FloorDAOImpl() {
        super(Floor.class);
    }

    public FloorDAOImpl(EntityManager em) {
        super(Floor.class);
        this.em = em;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public int count() throws DaoExn {
        return super.count();
    }

    @Override
    public List<Floor> findAll() throws DaoExn {
        return super.findAll();
    }

    @Override
    public Floor find(Object id) throws DaoExn {
        return super.find(id);
    }

    public Floor findByName(String name) throws DaoExn {
        //Named query is defined in the Beacon entity class.
        TypedQuery<Floor> query = this.em.createNamedQuery(
                "Floor.findByName", Floor.class).setParameter("name", name);
        List<Floor> floors = query.getResultList();
        if (floors.size() > 1) {
            throw new DaoExn("Duplicate floor records: floor name = " + name);
        } else if (floors.size() < 1) {
            throw new DaoExn("Floor not found: floor name = " + name);
        } else {
            return floors.get(0);
        }
    }

    /**
     * Returns all floors according to the specified building ID.
     *
     * @param building_id Building ID
     * @return list of floors
     */
    public List<Floor> findByBuildingId(int building_id) throws DaoExn {
        //TypedQuery<Floor> query = em.createNamedQuery("Floor.findByBuildingId", Floor.class).setParameter("buildingId", building_id);

        TypedQuery<Floor> query = this.em.createNamedQuery(
                "Floor.findByBuildingId", Floor.class).setParameter("buildingId", building_id);
        List<Floor> floors = query.getResultList();
        if (floors.size() < 1) {
            throw new DaoExn("Floors not found: building ID = " + building_id);
        } else {
            return floors;
        }

    }

    @Override
    public void remove(Floor entity) throws DaoExn {
        super.remove(entity);
    }

    @Override
    public void update(Floor entity) throws DaoExn {
        super.update(entity);
    }

    @Override
    public void create(Floor entity) throws DaoExn {
        super.create(entity);
    }
}
