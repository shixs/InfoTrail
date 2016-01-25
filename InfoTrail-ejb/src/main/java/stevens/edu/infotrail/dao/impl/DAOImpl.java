package stevens.edu.infotrail.dao.impl;

import stevens.edu.infotrail.dao.DAO;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

/**
 * DAOImpl is the abstract base class for providing methods which allow an
 * application to access to the persistence storage.
 * DAOImpl declares an abstract methods, getEntityManager, which need to be implemented
 * by all subclasses.
 *
 * @param <T>
 * @author Xingsheng
 * @version %I%,%G%
 */
public abstract class DAOImpl<T> implements DAO<T> {

    private Class<T> entityClass;

    /**
     * Generic constructor
     *
     * @param entityClass entityClass
     */
    public DAOImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public DAOImpl() {
    }

    /**
     * An abstract method that need to be implemented by all subclasses.
     *
     * @return EntityManager
     */
    protected abstract EntityManager getEntityManager();

    /**
     * Implemented the create method which is declared in DAO interface.
     * Create and insert new records in a table.
     *
     * @param entity entity
     * @throws DAO.DaoExn
     */
    @Override
    //@Transactional
    public void create(T entity) throws DaoExn {
        getEntityManager().persist(entity);
    }

    /**
     * Implemented the update method which is declared in DAO interface.
     * update data into the database.
     *
     * @param entity entity
     * @throws DAO.DaoExn
     */
    @Override
    //@Transactional
    public void update(T entity) throws DaoExn {
        getEntityManager().merge(entity);
    }

    /**
     * Implemented the remove method which is declared in DAO interface.
     * Delete records in a table.
     *
     * @param entity entity
     * @throws DAO.DaoExn
     */
    @Override
    //@Transactional
    public void remove(T entity) throws DaoExn {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Implemented the find method which is declared in DAO interface.
     * Retrieve data from a table by giving the id.
     *
     * @param id object id
     * @return object
     * @throws DAO.DaoExn
     */
    @Override
    public T find(Object id) throws DaoExn {
        return getEntityManager().find(entityClass, id);
    }

    /**
     * Implemented the findAll method which is declared in DAO interface.
     * Retrieve all records from a table.
     *
     * @return list of objects
     * @throws DAO.DaoExn
     */
    @Override
    public List<T> findAll() throws DaoExn {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Implemented the findRange method which is declared in DAO interface.
     * Retrieve all records from a table, based on given range.
     *
     * @param range range
     * @return list of objects
     * @throws DAO.DaoExn
     */
    @Override
    public List<T> findRange(int[] range) throws DaoExn {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * Implemented the count method which is declared in DAO interface.
     * Return the number of records.
     *
     * @return Integer
     * @throws DAO.DaoExn
     */
    @Override
    public int count() throws DaoExn {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}

