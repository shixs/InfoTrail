package stevens.edu.infotrail.dao.impl;

import stevens.edu.infotrail.dao.DAO;
import stevens.edu.infotrail.domain.entity.FloorBoundary;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author xshi90
 * @version %I%,%G%
 */
public class FloorBoundariesDAOImpl extends DAOImpl<FloorBoundary> {
    @PersistenceContext
    private EntityManager em;

    /**
     * An abstract method that need to be implemented by all subclasses.
     *
     * @return EntityManager
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Generic constructor
     *
     * @param em entity manager
     */
    public FloorBoundariesDAOImpl(EntityManager em) {
        super(FloorBoundary.class);
        this.em = em;
    }

    public FloorBoundariesDAOImpl() {
        super(FloorBoundary.class);
    }

    /**
     * Implemented the create method which is declared in DAO interface.
     * Create and insert new records in a table.
     *
     * @param entity entity
     * @throws DaoExn
     */
    @Override
    public void create(FloorBoundary entity) throws DaoExn {
        super.create(entity);
    }

    /**
     * Implemented the update method which is declared in DAO interface.
     * update data into the database.
     *
     * @param entity entity
     * @throws DaoExn
     */
    @Override
    public void update(FloorBoundary entity) throws DaoExn {
        super.update(entity);
    }

    /**
     * Implemented the remove method which is declared in DAO interface.
     * Delete records in a table.
     *
     * @param entity entity
     * @throws DaoExn
     */
    @Override
    public void remove(FloorBoundary entity) throws DaoExn {
        super.remove(entity);
    }

    /**
     * Implemented the find method which is declared in DAO interface.
     * Retrieve data from a table by giving the id.
     *
     * @param id object id
     * @return object
     * @throws DaoExn
     */
    @Override
    public FloorBoundary find(Object id) throws DaoExn {
        return super.find(id);
    }

    public List<FloorBoundary> findByFloorId(int floorId) throws DaoExn {

        TypedQuery<FloorBoundary> query = this.em.createNamedQuery("FloorBoundary.findByFloorId", FloorBoundary.class).setParameter("floorId", floorId);
        List<FloorBoundary> boundaries = query.getResultList();
        if (boundaries.size() < 1) {
            throw new DaoExn("Boundaries not found: floor id = " + floorId);
        } else {
            return boundaries;
        }
    }

    /**
     * Implemented the findAll method which is declared in DAO interface.
     * Retrieve all records from a table.
     *
     * @return list of objects
     * @throws DaoExn
     */
    @Override
    public List<FloorBoundary> findAll() throws DaoExn {
        return super.findAll();
    }

    /**
     * Implemented the count method which is declared in DAO interface.
     * Return the number of records.
     *
     * @return Integer
     * @throws DaoExn
     */
    @Override
    public int count() throws DaoExn {
        return super.count();
    }

    /**
     * Implemented the findRange method which is declared in DAO interface.
     * Retrieve all records from a table, based on given range.
     *
     * @param range range
     * @return list of objects
     * @throws DaoExn
     */
    @Override
    public List<FloorBoundary> findRange(int[] range) throws DaoExn {
        return super.findRange(range);
    }
}
