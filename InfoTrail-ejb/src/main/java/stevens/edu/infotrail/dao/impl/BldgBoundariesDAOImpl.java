package stevens.edu.infotrail.dao.impl;

import stevens.edu.infotrail.domain.entity.BldgBoundaries;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author xshi90
 * @version %I%,%G%
 */
public class BldgBoundariesDAOImpl extends DAOImpl<BldgBoundaries> {
    @PersistenceContext
    private EntityManager em;

    public BldgBoundariesDAOImpl(){
        super(BldgBoundaries.class);
    }

    public BldgBoundariesDAOImpl(EntityManager em){
        super(BldgBoundaries.class);
        this.em = em;
    }

    /**
     * Implemented the create method which is declared in DAO interface.
     * Create and insert new records in a table.
     *
     * @param entity entity
     * @throws DaoExn
     */
    @Override
    public void create(BldgBoundaries entity) throws DaoExn {
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
    public void update(BldgBoundaries entity) throws DaoExn {
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
    public void remove(BldgBoundaries entity) throws DaoExn {
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
    public BldgBoundaries find(Object id) throws DaoExn {
        return super.find(id);
    }

    public List<BldgBoundaries> findByBuildingId(int buildingId) throws DaoExn{
        TypedQuery<BldgBoundaries> query = this.em.createNamedQuery("BldgBoundaries.findByBuildingId", BldgBoundaries.class).setParameter("buildingId", buildingId);
        List<BldgBoundaries> boundaries = query.getResultList();
        if (boundaries.size() < 1) {
            throw new DaoExn("Boundaries not found: building id = " + buildingId);
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
    public List<BldgBoundaries> findAll() throws DaoExn {
        return super.findAll();
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
    public List<BldgBoundaries> findRange(int[] range) throws DaoExn {
        return super.findRange(range);
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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
