package stevens.edu.infotrail.dao.impl;

import stevens.edu.infotrail.domain.entity.Building;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * BuildingDAOImpl is implemented as a subclass of the DAOImpl class.
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
@Stateless
public class BuildingDAOImpl extends DAOImpl<Building> {

    @PersistenceContext
    private EntityManager em;

    public BuildingDAOImpl() {
        super(Building.class);
    }

    public BuildingDAOImpl(EntityManager em) {
        super(Building.class);
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
    public List<Building> findAll() throws DaoExn {
        return super.findAll();
    }

    /**
     * Returns the building with that specified name.
     *
     * @param name Building name
     * @return Building
     * @throws DaoExn
     */
    public Building findByName(String name) throws DaoExn {
        TypedQuery<Building> query = this.em.createNamedQuery("Building.findByName", Building.class).setParameter("name", name);
        List<Building> buildings = query.getResultList();
        if (buildings.size() > 1) {
            throw new DaoExn("Duplicate building records: building name = " + name);
        } else if (buildings.size() < 1) {
            throw new DaoExn("Building not found: building name = " + name);
        } else {
            return buildings.get(0);
        }
    }

    public List<Building> findByOrgId(int orgId) throws DaoExn {
        TypedQuery<Building> query = this.em.createNamedQuery("Building.findByOrgId", Building.class).setParameter("orgId", orgId);
        List<Building> buildings = query.getResultList();
        if (buildings.size() < 1) {
            throw new DaoExn("Building not found: orgId = " + orgId);
        } else {
            return buildings;
        }
    }

    @Override
    public Building find(Object id) throws DaoExn {
        return super.find(id);
    }

    @Override
    public void remove(Building entity) throws DaoExn {
        super.remove(entity);
    }

    @Override
    public void update(Building entity) throws DaoExn {
        super.update(entity);
    }

    @Override
    public void create(Building entity) throws DaoExn {
        super.create(entity);
    }
}
