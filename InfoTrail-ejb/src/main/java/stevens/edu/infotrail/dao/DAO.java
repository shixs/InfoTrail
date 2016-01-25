package stevens.edu.infotrail.dao;

import java.util.List;

/**
 * The DAO interface provides seven methods for accessing to a persistence storage
 * and handling the database operation.
 *
 * @param <T>
 * @author Xingsheng
 * @version %I%,%G%
 */
public interface DAO<T> {
    void create(T entity) throws DaoExn;

    void update(T entity) throws DaoExn;

    void remove(T entity) throws DaoExn;

    T find(Object id) throws DaoExn;

    List<T> findAll() throws DaoExn;

    List<T> findRange(int[] range) throws DaoExn;

    int count() throws DaoExn;

    class DaoExn extends Exception {
        private static final long serialVersionUID = 1L;

        public DaoExn(String msg) {
            super(msg);
        }
    }
}

