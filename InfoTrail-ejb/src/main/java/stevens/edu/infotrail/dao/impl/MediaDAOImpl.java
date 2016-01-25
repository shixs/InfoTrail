package stevens.edu.infotrail.dao.impl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import stevens.edu.infotrail.domain.entity.Media;

/**
 *
 * @author Xingshen
 * @version %I%,%G%
 */
@Stateless
public class MediaDAOImpl extends DAOImpl<Media> {

    @PersistenceContext
    private EntityManager em;
    
    public MediaDAOImpl(){
        super(Media.class);
        
    }
    public MediaDAOImpl(EntityManager em) {
        super(Media.class);
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
    public List<Media> findRange(int[] range) throws DaoExn {
        return super.findRange(range); 
    }

    @Override
    public List<Media> findAll() throws DaoExn {
        return super.findAll(); 
    }

    @Override
    public Media find(Object id) throws DaoExn {
        return super.find(id); 
    }

    public List<Media> findByParentId(int parentId) throws DaoExn {
        //Named query is defined in the amazonS3 entity class.
        TypedQuery<Media> query = this.em.createNamedQuery(
                "Media.findByParentId", Media.class).setParameter("parentId", parentId);
        List<Media> medias = query.getResultList();
        if (medias.size() < 1) {
            throw new DaoExn("Media not found: parent ID = " + parentId);
        } else {
            return medias;
        }
    }

    @Override
    public void remove(Media entity) throws DaoExn {
        super.remove(entity); 
    }

    @Override
    public void update(Media entity) throws DaoExn {
        super.update(entity); 
    }

    @Override
    public void create(Media entity) throws DaoExn {
        super.create(entity); 
    }
 
}
