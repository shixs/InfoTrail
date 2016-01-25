package stevens.edu.infotrail.dao.impl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import stevens.edu.infotrail.domain.entity.Item;

/**
 * ItemDAOImpl is implemented as a subclass of the DAOImpl class.
 * 
 * @author Xingsheng
 * @version %I%,%G%
 */
@Stateless
public class ItemDAOImpl extends DAOImpl<Item> {

    @PersistenceContext//(unitName = "infotrail_domain")
    private EntityManager em;
        
    public ItemDAOImpl() {
        super(Item.class);
    }
    
    public ItemDAOImpl(EntityManager em){
        super(Item.class);
        this.em = em;
    }
    @Override
    public int count() throws DaoExn {
        return super.count(); 
    }

    @Override
    public List<Item> findAll() throws DaoExn {
        return super.findAll(); 
    }

    @Override
    public Item find(Object id) throws DaoExn {
        return super.find(id); 
    }

    @Override
    public void remove(Item entity) throws DaoExn {
        super.remove(entity); 
    }

    @Override
    public void update(Item entity) throws DaoExn {
        super.update(entity); 
    }

    @Override
    public void create(Item entity) throws DaoExn {
        super.create(entity); 
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
