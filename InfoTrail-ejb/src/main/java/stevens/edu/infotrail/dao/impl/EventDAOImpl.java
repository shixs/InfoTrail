package stevens.edu.infotrail.dao.impl;

import stevens.edu.infotrail.domain.entity.Events;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * EventDAOImpl is implemented as a subclass of the DAOImpl class.
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
@Stateless
public class EventDAOImpl extends DAOImpl<Events> {

    @PersistenceContext
    private EntityManager em;

    public EventDAOImpl() {
        super(Events.class);
    }

    public EventDAOImpl(EntityManager em) {
        super(Events.class);
        this.em = em;
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    @Override
    public int count() throws DaoExn {
        return super.count();
    }

    @Override
    public List<Events> findAll() throws DaoExn {
        return super.findAll();
    }

    @Override
    public Events find(Object id) throws DaoExn {
        return super.find(id);
    }

    /**
     * Returns all events according to the specified room ID.
     *
     * @param roomId Room ID
     * @return list of events
     * @throws DaoExn
     */
    public List<Events> findByRoomId(int roomId) throws DaoExn {
        TypedQuery<Events> query = this.em.createNamedQuery(
                "Events.findByRoomId", Events.class).setParameter("roomId", roomId);
        List<Events> events = query.getResultList();
        if (events.size() < 1) {
            throw new DaoExn("Event not found: roomId = " + roomId);
        } else {
            return events;
        }
    }

    @Override
    public void remove(Events entity) throws DaoExn {
        super.remove(entity);
    }

    @Override
    public void update(Events entity) throws DaoExn {
        super.update(entity);
    }

    @Override
    public void create(Events entity) throws DaoExn {
        super.create(entity);
    }
}
