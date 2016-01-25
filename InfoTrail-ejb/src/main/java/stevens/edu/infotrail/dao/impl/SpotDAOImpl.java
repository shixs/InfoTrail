package stevens.edu.infotrail.dao.impl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import stevens.edu.infotrail.domain.entity.Spot;

/**
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
@Stateless
public class SpotDAOImpl extends DAOImpl<Spot> {

    @PersistenceContext
    private EntityManager em;
    public SpotDAOImpl(){
        super(Spot.class);
    }
    public SpotDAOImpl(EntityManager em) {
        super(Spot.class);
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
    public List<Spot> findRange(int[] range) throws DaoExn {
        return super.findRange(range); 
    }

    @Override
    public List<Spot> findAll() throws DaoExn {
        return super.findAll(); 
    }

    @Override
    public Spot find(Object id) throws DaoExn {
        return super.find(id); 
    }

    @Override
    public void remove(Spot entity) throws DaoExn {
        super.remove(entity); 
    }

    @Override
    public void update(Spot entity) throws DaoExn {
        super.update(entity); 
    }

    @Override
    public void create(Spot entity) throws DaoExn {
        super.create(entity); 
    }
    
    
}
