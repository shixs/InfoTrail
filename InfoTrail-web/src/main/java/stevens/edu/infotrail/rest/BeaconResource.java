package stevens.edu.infotrail.rest;

import stevens.edu.infotrail.dao.daoFactory.DAOFactory;
import stevens.edu.infotrail.dao.DAO.DaoExn;
import stevens.edu.infotrail.domain.entity.Beacon;
import stevens.edu.infotrail.domain.entityFactory.BeaconFactory;
import stevens.edu.infotrail.domain.entity.Item;
import stevens.edu.infotrail.domain.entityFactory.ItemFactory;
import stevens.edu.infotrail.representation.BeaconRep;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The BeaconResource is a resources class that will be hosted at the URI path
 * "/beacon".
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
@Path("/beacon")
@RequestScoped
public class BeaconResource {

    private static final Logger logger = Logger.getLogger(BeaconResource.class.getCanonicalName());
    //Dependency injection
    @Inject
    private DAOFactory daoFactory;
    //inject information into a class field,UriInfo
    @Context
    private UriInfo context;

    /**
     * @param ids The ids contains both beacons' uuid, major and minor.
     * @return A certain beacon matching the given id.
     */
    @GET
    @Path("{ids}")
    @Produces("application/json")
    public Response findByBeaconId(@PathParam("ids") String ids) {//path parameter
        logger.fine("Finding a beacon...");
        //check if ids is null or empty, return the response with bad request status
        if (ids.length() < 1) {
            logger.log(Level.SEVERE,"Invalid ID");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        //split the id string
        String[] b_ids = ids.split("\\.");
        //check if ids does not contain all uuid, major and minor, return response with bad request status
        if (b_ids.length < 3) {
            logger.log(Level.WARNING,"Invalid ID");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        try {
            //return beacon representation with HTTP code OK(200)
            return Response.ok(new BeaconRep(daoFactory.getBeaconDAO().findByIds(b_ids[0], Integer.parseInt(b_ids[1]), Integer.parseInt(b_ids[2])))).build();
        } catch (DaoExn e) {
            //return the response with not found status
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * @return all beacon records
     */
    @GET
    @Produces("application/json")
    public Response findAllBeacons() {
        try {//get all beacons from database
            List<Beacon> beacons = daoFactory.getBeaconDAO().findAll();
            //convert beacon entity to beacon representation
            List<BeaconRep> beaconReps = new ArrayList<>();
            for (Beacon b : beacons) {
                BeaconRep bRep = new BeaconRep(b);
                beaconReps.add(bRep);
            }
            return Response.ok(beaconReps).build();
        } catch (DaoExn e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * inserts a beacon record to the table
     *
     * @param object accept a JSON object that contains values of a beacon
     * @return Response 201 created
     */
    @POST
    @Consumes("application/json")
    @Transactional
    public Response createBeacon(JsonObject object) {
        //obtain values
        String beaconId = object.getString("beaconId");
        String uuid = object.getString("uuid");
        String major = object.getString("major");
        String minor = object.getString("minor");
        String location = object.getString("location");
        String itemId = object.getString("itemId");
        BeaconFactory bFactory = new BeaconFactory();
        ItemFactory iFactory = new ItemFactory();
        try {

            //retrieve data from table if it is existed, otherwise create a new object
            Item item = (itemId != null) ? daoFactory.getItemDAO().find(Integer.parseInt(itemId)) : iFactory.getItem();
            Beacon beacon = bFactory.createBeacon(Integer.parseInt(beaconId), uuid, Integer.parseInt(major), Integer.parseInt(minor), location, item);
            //persist beacon entity
            daoFactory.getBeaconDAO().create(beacon);
            //build a path of the new beacon 
            UriBuilder ub = context.getAbsolutePathBuilder().path("beaconId");
            URI url = ub.build("beaconId");
            //return the url of the new beacon
            return Response.created(url).build();
        } catch (DaoExn e) {
            return Response.accepted("Create beacon failed. ID: " + beaconId).build();
        }
    }

    /**
     * updates a beacon records to the table
     *
     * @param object accept a JSON object that contains values of a beacon
     * @return Response
     */
    @PUT
    @Consumes("application/json")
    @Transactional
    public Response updateBeacon(JsonObject object) {
        String beaconId = object.getString("beaconId");
        String uuid = object.getString("uuid");
        String major = object.getString("major");
        String minor = object.getString("minor");
        String location = object.getString("location");
        String itemId = object.getString("itemId");
        BeaconFactory bFactory = new BeaconFactory();
        ItemFactory iFactory = new ItemFactory();
        try {
            Item item = (itemId != null) ? daoFactory.getItemDAO().find(Integer.parseInt(itemId)) : iFactory.getItem();
            Beacon beacon = bFactory.createBeacon(Integer.parseInt(beaconId), uuid, Integer.parseInt(major), Integer.parseInt(minor), location, item);
            daoFactory.getBeaconDAO().update(beacon);
            return Response.ok().build();
        } catch (DaoExn e) {
            return Response.accepted("Update beacon failed. ID: " + beaconId).build();
        }
    }

    /**
     * deletes the certain beacon record
     *
     * @param ids The ids contains both beacons' uuid, major and minor.
     * @return Response 200 OK
     */
    @DELETE
    @Path("/{ids}")
    @Consumes("application/json")
    @Transactional
    public Response deleteBeacon(@PathParam("ids") String ids) {
        if (ids.length() < 1) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        String[] b_ids = ids.split("\\.");
        if (b_ids.length < 3) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            daoFactory.getBeaconDAO().remove(daoFactory.getBeaconDAO().findByIds(b_ids[0], Integer.parseInt(b_ids[1]), Integer.parseInt(b_ids[2])));
            return Response.ok().build();
        } catch (DaoExn ex) {
            return Response.accepted("Delete beacon failed. ID: " + ids).build();
        }
    }
}
