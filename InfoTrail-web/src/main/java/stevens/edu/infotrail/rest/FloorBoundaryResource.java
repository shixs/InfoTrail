package stevens.edu.infotrail.rest;

import stevens.edu.infotrail.dao.DAO;
import stevens.edu.infotrail.dao.daoFactory.DAOFactory;
import stevens.edu.infotrail.domain.entity.Floor;
import stevens.edu.infotrail.domain.entity.FloorBoundary;
import stevens.edu.infotrail.domain.entityFactory.FloorBoundaryFactory;

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
import java.util.List;

/**
 * @author xshi90
 * @version %I%,%G%
 */
@Path("/floorboundary")
@RequestScoped
public class FloorBoundaryResource {
    @Inject
    private DAOFactory daoFactory;

    @Context
    private UriInfo context;

    @GET
    @Produces("application/json")
    public Response getAll(){
        try{
            return Response.ok(daoFactory.getFloorBoundariesDAO().findAll()).build();
        }catch (DAO.DaoExn e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @GET
    @Path("{floorId}")
    @Produces("application/json")
    public Response getFloorBoundary(@PathParam("floorId") int floorId){
        try {
            return Response.ok(daoFactory.getFloorBoundariesDAO().findByFloorId(floorId)).build();
        } catch (DAO.DaoExn e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * inserts a building boundary record into the table
     *
     * @param object accept a JSON object that contains values of a boundary of building
     * @return Response 201 created
     */
    @POST
    @Consumes("application/json")
    @Transactional
    public Response createBoundaries(JsonObject object) {
        String boundariesId = object.getString("boundariesId");
        String floorX = object.getString("floorX");
        String floorY = object.getString("floorY");
        String floorId = object.getString("floorId");
        FloorBoundaryFactory floorBoundaryFactory = new FloorBoundaryFactory();
        try {
            Floor floor = daoFactory.getFloorDAO().find(Integer.parseInt(floorId));
            FloorBoundary boundaries = floorBoundaryFactory.createFloorBoundary(Integer.parseInt(boundariesId), Integer.parseInt(floorX), Integer.parseInt(floorY), floor);
            daoFactory.getFloorBoundariesDAO().create(boundaries);
            UriBuilder ub = context.getAbsolutePathBuilder().path(boundariesId);
            URI url = ub.build(boundariesId);
            return Response.created(url).build();
        } catch (DAO.DaoExn ex) {
            return Response.accepted("Create boundary of Floor failed. ID: " + floorId).build();
        }
    }

    /**
     * update building boundary record into the table
     *
     * @param object accept a JSON object that contains values of a boundary of building
     * @return Response
     */
    @PUT
    @Consumes("application/json")
    @Transactional
    public Response updateBoundaries(JsonObject object) {
        String boundariesId = object.getString("boundariesId");
        String floorX = object.getString("floorX");
        String floorY = object.getString("floorY");
        String floorId = object.getString("floorId");
        FloorBoundaryFactory floorBoundaryFactory = new FloorBoundaryFactory();
        try {
            Floor floor = daoFactory.getFloorDAO().find(Integer.parseInt(floorId));
            FloorBoundary boundaries = floorBoundaryFactory.createFloorBoundary(Integer.parseInt(boundariesId), Integer.parseInt(floorX), Integer.parseInt(floorY), floor);
            daoFactory.getFloorBoundariesDAO().update(boundaries);
            return Response.ok().build();
        } catch (DAO.DaoExn ex) {
            return Response.accepted("Update boundary of Building failed. ID: " + boundariesId).build();
        }
    }

    /**
     * deletes a boundary record
     *
     * @param floorId Building ID
     * @return Response
     */
    @DELETE
    @Path("{floorId}")
    @Consumes("application/json")
    @Transactional
    public Response deleteBoundaries(@PathParam("floorId") int floorId) {
        try {
            List<FloorBoundary> boundaries = daoFactory.getFloorDAO().find(floorId).getFloorBoundaryList();
            for (FloorBoundary b : boundaries) {
                daoFactory.getFloorBoundariesDAO().remove(b);
            }
            return Response.ok().build();
        } catch (DAO.DaoExn ex) {
            return Response.accepted("Delete boundaries failed. floor ID: " + floorId).build();
        }
    }
}
