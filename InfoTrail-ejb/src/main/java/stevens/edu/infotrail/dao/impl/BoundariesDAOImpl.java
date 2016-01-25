package stevens.edu.infotrail.dao.impl;

import stevens.edu.infotrail.domain.entity.Boundaries;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * BoundariesDAOImpl is implemented as a subclass of the DAOImpl class.
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
@Stateless
public class BoundariesDAOImpl extends DAOImpl<Boundaries> {

    @PersistenceContext
    private EntityManager em;

    public BoundariesDAOImpl() {
        super(Boundaries.class);
    }

    public BoundariesDAOImpl(EntityManager em) {
        super(Boundaries.class);
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
    public List<Boundaries> findAll() throws DaoExn {
        return super.findAll();
    }

    @Override
    public Boundaries find(Object id) throws DaoExn {
        return super.find(id);
    }

    /**
     * Returns boundaries of an organization based on the organization ID.
     *
     * @param orgId Organization ID
     * @return list of boundaries
     * @throws DaoExn
     */
    public List<Boundaries> findByOrgId(int orgId) throws DaoExn {
        TypedQuery<Boundaries> query = this.em.createNamedQuery("Boundaries.findByOrgId", Boundaries.class).setParameter("orgId", orgId);
        List<Boundaries> boundaries = query.getResultList();
        if (boundaries.size() < 1) {
            throw new DaoExn("Boundaries not found: organization id = " + orgId);
        } else {
            return boundaries;
        }
    }

    @Override
    public void remove(Boundaries entity) throws DaoExn {
        super.remove(entity);
    }

    @Override
    public void update(Boundaries entity) throws DaoExn {
        super.update(entity);
    }

    @Override
    public void create(Boundaries entity) throws DaoExn {
        super.create(entity);
    }
}
