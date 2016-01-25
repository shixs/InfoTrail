package stevens.edu.infotrail.dao.impl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import stevens.edu.infotrail.domain.entity.Organization;

/**
 * OrganizationDAOImpl is implemented as a subclass of the DAOImpl class.
 * 
 * @author Xingsheng
 * @version %I%,%G%
 */
@Stateless
public class OrganizationDAOImpl extends DAOImpl<Organization> {

    @PersistenceContext(unitName = "infotrail_domain")
    private EntityManager em;
    
    public OrganizationDAOImpl() {
        super(Organization.class);
    }

    public OrganizationDAOImpl(EntityManager em) {
        super(Organization.class);
        this.em = em;
    }
    
    @Override
    public int count() throws DaoExn {
        return super.count(); 
    }

    @Override
    public List<Organization> findAll() throws DaoExn {
        return super.findAll(); 
    }

    @Override
    public Organization find(Object id) throws DaoExn {
        return super.find(id); 
    }

    @Override
    public void remove(Organization entity) throws DaoExn {
        super.remove(entity);
    }

    @Override
    public void update(Organization entity) throws DaoExn {
        super.update(entity); 
    }

    @Override
    public void create(Organization entity) throws DaoExn {
        super.create(entity); 
    }
 
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }  
}
