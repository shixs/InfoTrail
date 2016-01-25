package stevens.edu.infotrail.dao.impl;

import stevens.edu.infotrail.domain.entity.Beacon;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * BeaconDAOImpl is implemented as a subclass of the DAOImpl class.
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
@Stateless
public class BeaconDAOImpl extends DAOImpl<Beacon> {

    @PersistenceContext
    private EntityManager em;

    public BeaconDAOImpl() {
        super(Beacon.class);
    }

    public BeaconDAOImpl(EntityManager em) {
        super(Beacon.class);
        this.em = em;
    }

    @Override
    public int count() throws DaoExn {
        return super.count();
    }

    @Override
    public List<Beacon> findAll() throws DaoExn {
        return super.findAll();
    }

    @Override
    public Beacon find(Object id) throws DaoExn {
        return super.find(id);
    }

    public Beacon findByIds(String uuid, int major, int minor) throws DaoExn {
        //Named query is defined in the Beacon entity class.
        TypedQuery<Beacon> query = this.em.createNamedQuery(
                "Beacon.findABeacon", Beacon.class).setParameter("uuid", uuid).setParameter("major", major).setParameter("minor", minor);
        List<Beacon> beacons = query.getResultList();
        if (beacons.size() > 1) {
            throw new DaoExn("Duplicate beacon records: beacon number = " + uuid + major + minor);
        } else if (beacons.size() < 1) {
            throw new DaoExn("Beacon not found: beacon number = " + uuid + major + minor);
        } else {
            return beacons.get(0);
        }
    }

    public List<Beacon> findByUMa(String uuid, int major) throws DaoExn {
        //Named query is defined in the Beacon entity class.
        TypedQuery<Beacon> query = this.em.createNamedQuery(
                "Beacon.findBeaconUMa", Beacon.class).setParameter("uuid", uuid).setParameter("major", major);
        List<Beacon> beacons = query.getResultList();
        if (beacons.size() < 1) {
            throw new DaoExn("Beacon not found: beacon number = " + uuid + major);
        } else {
            return beacons;
        }
    }

    /**
     * Returns the beacon based on a given UUID.
     *
     * @param uuid given an uuid of beacons.
     * @return Beacon
     * @throws DaoExn
     */
    public List<Beacon> findByUuid(String uuid) throws DaoExn {
        //Named query is defined in the Beacon entity class.
        TypedQuery<Beacon> query = this.em.createNamedQuery(
                "Beacon.findByUuid", Beacon.class).setParameter("uuid", uuid);
        List<Beacon> beacons = query.getResultList();
        //check if there is no or duplicate records of reqeusted beacon.
        if (beacons.size() < 1) {
            throw new DaoExn("Beacon not found: beacon uuid = " + uuid);
        } else {
            return beacons;
        }
    }

    public Beacon findByMajor(String major) throws DaoExn {
        //Named query is defined in the Beacon entity class.
        TypedQuery<Beacon> query = this.em.createNamedQuery(
                "Beacon.findByMajor", Beacon.class).setParameter("major", major);
        List<Beacon> beacons = query.getResultList();
        //check if there is no or duplicate records of reqeusted beacon.
        if (beacons.size() > 1) {
            throw new DaoExn("Duplicate beacon records: beacon major = " + major);
        } else if (beacons.size() < 1) {
            throw new DaoExn("Beacon not found: beacon major = " + major);
        } else {
            Beacon b = beacons.get(0);
            return b;
        }
    }

    public Beacon findByMinor(String minor) throws DaoExn {
        //Named query is defined in the Beacon entity class.
        TypedQuery<Beacon> query = this.em.createNamedQuery(
                "Beacon.findByMinor", Beacon.class).setParameter("minor", minor);
        List<Beacon> beacons = query.getResultList();
        //check if there is no or duplicate records of reqeusted beacon.
        if (beacons.size() > 1) {
            throw new DaoExn("Duplicate beacon records: beacon minor = " + minor);
        } else if (beacons.size() < 1) {
            throw new DaoExn("Beacon not found: beacon minor = " + minor);
        } else {
            return beacons.get(0);
        }
    }

    @Override
    public void remove(Beacon entity) throws DaoExn {
        //delete the beacon
        super.remove(entity);
    }

    @Override
    public void update(Beacon entity) throws DaoExn {
        //update beacon records to the table.
        super.update(entity);
    }

    @Override
    public void create(Beacon entity) throws DaoExn {
        //insert a beacon record into the table.
        super.create(entity);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
