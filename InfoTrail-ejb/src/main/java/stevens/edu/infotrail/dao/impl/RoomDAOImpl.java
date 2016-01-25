package stevens.edu.infotrail.dao.impl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import stevens.edu.infotrail.domain.entity.Room;

/**
 * RoomDAOImpl is implemented as a subclass of the DAOImpl class.
 * 
 * @author Xingsheng
 * @version %I%,%G%
 */
@Stateless
public class RoomDAOImpl extends DAOImpl<Room> {

    @PersistenceContext//(unitName = "infotrail_domain")
    private EntityManager em;
    
    public RoomDAOImpl() {
        super(Room.class);
    }

    public RoomDAOImpl(EntityManager em) {
        super(Room.class);
        this.em = em;
    }
    
    @Override
    public int count() throws DaoExn {
        return super.count(); 
    }

    @Override
    public List<Room> findAll() throws DaoExn {
        return super.findAll();
    }

    @Override
    public Room find(Object id) throws DaoExn {
        return super.find(id);
    }

    public Room findByName(String name) throws DaoExn{
        //Named query is defined in the Beacon entity class.
        TypedQuery<Room> query = this.em.createNamedQuery(
            "Room.findByRoomName", Room.class).setParameter("roomName", name);
        List<Room> rooms = query.getResultList();
        if(rooms.size() > 1){
            throw new DaoExn("Duplicate room records: room name = " + name);
        }else if(rooms.size() < 1){
            throw new DaoExn("Room not found: room name = " + name);
        }else{
            return rooms.get(0);
        }    
    }
    
    @Override
    public void remove(Room entity) throws DaoExn {
        super.remove(entity); 
    }

    @Override
    public void update(Room entity) throws DaoExn {
        super.update(entity); 
    }

    @Override
    public void create(Room entity) throws DaoExn {
        super.create(entity); 
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }    
}
