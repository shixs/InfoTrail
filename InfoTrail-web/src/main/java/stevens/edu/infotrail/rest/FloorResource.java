package stevens.edu.infotrail.rest;

import stevens.edu.infotrail.dao.daoFactory.DAOFactory;
import stevens.edu.infotrail.dao.DAO.DaoExn;
import stevens.edu.infotrail.domain.entity.Building;
import stevens.edu.infotrail.domain.entity.Floor;
import stevens.edu.infotrail.domain.entityFactory.FloorFactory;
import stevens.edu.infotrail.domain.entity.Media;
import stevens.edu.infotrail.representation.FloorRepForCMS;

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
import java.util.logging.Logger;

/**
 * The FloorResource is a resources class that will be hosted at the URI path
 * "/floor".
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
@Path("/floor")
@RequestScoped
public class FloorResource {

    private static Logger logger = Logger.getLogger(FloorResource.class.getCanonicalName());
    @Inject
    private DAOFactory daoFactory;

    @Context
    private UriInfo context;

    /**
     * returns all floor records
     *
     * @return Response
     */
    @GET
    @Produces("application/json")
    public Response findFloors() {
        try {
            List<FloorRepForCMS> floors = new ArrayList<>();
            for (Floor f : daoFactory.getFloorDAO().findAll()) {
                FloorRepForCMS fRep = null;
                try{
                    List<Media> medias = (f != null) ? daoFactory.getMediaDAO().findByParentId(f.getFloorId()):new ArrayList<Media>();
                    fRep = new FloorRepForCMS(f,medias);
                }catch(DaoExn daoExn1){
                    if(daoExn1.getMessage().contains("Media")){
                        fRep = new FloorRepForCMS(f,new ArrayList<Media>());
                    }
                }
                floors.add(fRep);
            }
            return Response.ok(floors).build();
        } catch (DaoExn e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * returns the certain floor record
     *
     * @param floorId Floor ID
     * @return Response
     */
    @GET
    @Path("{floor_id}")
    @Produces("application/json")
    public Response findFloor(@PathParam("floor_id") int floorId) {
        Floor floor = null;
        try {
            floor = daoFactory.getFloorDAO().find(floorId);
            List<Media> medias = daoFactory.getMediaDAO().findByParentId(floor.getFloorId());
            return Response.ok(new FloorRepForCMS(floor,medias)).build();
        } catch (DaoExn e) {
            if(e.getMessage().contains("Media")){
                return Response.ok(new FloorRepForCMS(floor, new ArrayList<Media>())).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
    }

    /**
     * returns all floor records of the certain building
     *
     * @param buildingId Building ID
     * @return Response
     */
    @GET
    @Path("/building/{buildingId}")
    @Produces("application/json")
    public Response findFloorsByBuilding(@PathParam("buildingId") int buildingId) {
        try {
            List<Floor> floors = daoFactory.getFloorDAO().findByBuildingId(buildingId);
            List<FloorRepForCMS> res = new ArrayList<>();
            for (Floor f : floors) {
                FloorRepForCMS fRep = null;
                logger.info("=================floor id:" + f.getFloorId());
                try{
                    List<Media> medias = daoFactory.getMediaDAO().findByParentId(f.getFloorId());
                    fRep = new FloorRepForCMS(f,medias);
                }catch(DaoExn daoExn1){
                    if(daoExn1.getMessage().contains("Media")){
                        fRep = new FloorRepForCMS(f,new ArrayList<Media>());
                    }
                }
                res.add(fRep);
            }
            if(res.size() > 0){
                return Response.ok(res).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (DaoExn e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * inserts a floor record into the table
     *
     * @param object a JSON object contains values of floor entity
     * @return Response
     */
    @POST
    @Consumes("application/json")
    @Transactional
    public Response createFloor(JsonObject object) {
        String floorId = object.getString("floorId");
        String name = object.getString("name");
        String info = object.getString("info");
        String b_id = object.getString("buildingId");
        FloorFactory ffactory = new FloorFactory();
        try {
            Building building = daoFactory.getBuildingDAO().find(Integer.parseInt(b_id));
            Floor floor = ffactory.createFloor(Integer.parseInt(floorId), name, info, building);
            daoFactory.getFloorDAO().create(floor);

            UriBuilder ub = context.getAbsolutePathBuilder().path(floorId);
            URI url = ub.build(floorId);
            return Response.created(url).build();
        } catch (DaoExn e) {
            return Response.accepted("Create floor failed. ID" + floorId).build();
        }
    }

    /**
     * updates a floor record to the table
     *
     * @param object a JSON object contains values of floor entity
     * @return Response
     */
    @PUT
    @Consumes("application/json")
    @Transactional
    public Response updateFloor(JsonObject object) {
        String floorId = object.getString("floorId");
        String name = object.getString("name");
        String info = object.getString("info");
        String b_id = object.getString("buildingId");
        FloorFactory factory = new FloorFactory();
        try {
            Building building = daoFactory.getBuildingDAO().find(Integer.parseInt(b_id));
            Floor floor = factory.createFloor(Integer.parseInt(floorId), name, info, building);
            daoFactory.getFloorDAO().update(floor);
            return Response.ok().build();
        } catch (DaoExn e) {
            return Response.accepted("Update floor failed. ID" + floorId).build();
        }
    }

    /**
     * deletes a floor record
     *
     * @param floorId Floor ID
     * @return Response
     */
    @DELETE
    @Path("{floorId}")
    @Consumes("application/json")
    @Transactional
    public Response deleteFloor(@PathParam("floorId") int floorId) {
        try {
            daoFactory.getFloorDAO().remove(daoFactory.getFloorDAO().find(floorId));
            return Response.ok().build();
        } catch (DaoExn e) {
            return Response.accepted("Delete floor failed. ID" + floorId).build();
        }
    }
}
