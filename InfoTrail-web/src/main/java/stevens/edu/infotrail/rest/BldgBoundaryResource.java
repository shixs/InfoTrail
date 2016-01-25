package stevens.edu.infotrail.rest;

import stevens.edu.infotrail.dao.DAO;
import stevens.edu.infotrail.dao.daoFactory.DAOFactory;
import stevens.edu.infotrail.domain.entity.BldgBoundaries;
import stevens.edu.infotrail.domain.entity.Building;
import stevens.edu.infotrail.domain.entityFactory.BldgBoundariesFactory;

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
@RequestScoped
@Path("/bldgboundary")
public class BldgBoundaryResource {
    @Inject
    private DAOFactory daoFactory;

    @Context
    private UriInfo context;

    @GET
    @Produces("application/json")
    public Response getAll(){
        try{
            return Response.ok(daoFactory.getBldgBoundariesDAO().findAll()).build();
        }catch (DAO.DaoExn e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @GET
    @Path("{buildingId}")
    @Produces("application/json")
    public Response getBldgBoundary(@PathParam("buildingId") int buildingId){
        try {
            return Response.ok(daoFactory.getBldgBoundariesDAO().findByBuildingId(buildingId)).build();
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
        String latitude = object.getString("latitude");
        String longitude = object.getString("longitude");
        String buildingId = object.getString("buildingId");
        BldgBoundariesFactory boFactory = new BldgBoundariesFactory();
        try {
            Building building = daoFactory.getBuildingDAO().find(Integer.parseInt(buildingId));
            BldgBoundaries boundaries = boFactory.createBldgBoundaries(Integer.parseInt(boundariesId), Double.parseDouble(latitude), Double.parseDouble(longitude), building);
            daoFactory.getBldgBoundariesDAO().create(boundaries);
            UriBuilder ub = context.getAbsolutePathBuilder().path(boundariesId);
            URI url = ub.build(boundariesId);
            return Response.created(url).build();
        } catch (DAO.DaoExn ex) {
            return Response.accepted("Create boundary of Building failed. ID: " + boundariesId).build();
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
        String latitude = object.getString("latitude");
        String longitude = object.getString("longitude");
        String buildingId = object.getString("buildingId");
        BldgBoundariesFactory boFactory = new BldgBoundariesFactory();
        try {
            Building building = daoFactory.getBuildingDAO().find(Integer.parseInt(buildingId));
            BldgBoundaries boundaries = boFactory.createBldgBoundaries(Integer.parseInt(boundariesId), Double.parseDouble(latitude), Double.parseDouble(longitude), building);
            daoFactory.getBldgBoundariesDAO().update(boundaries);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.accepted("Update boundary of Building failed. ID: " + boundariesId).build();
        }
    }

    /**
     * deletes a boundary record
     *
     * @param buildingId Building ID
     * @return Response
     */
    @DELETE
    @Path("{buildingId}")
    @Consumes("application/json")
    @Transactional
    public Response deleteBoundaries(@PathParam("buildingId") int buildingId) {
        try {
            List<BldgBoundaries> boundaries = daoFactory.getBuildingDAO().find(buildingId).getBldgBoundariesList();
            for (BldgBoundaries b : boundaries) {
                daoFactory.getBldgBoundariesDAO().remove(b);
            }
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.accepted("Delete boundaries failed. building ID: " + buildingId).build();
        }
    }

    /**
     * deletes a boundary record
     *
     * @param boundaryId Building ID
     * @return Response
     */
    @DELETE
    @Path("/test/{boundaryId}")
    @Consumes("application/json")
    @Transactional
    public Response deleteABoundaries(@PathParam("boundaryId") int boundaryId) throws DAO.DaoExn {
        BldgBoundaries bldgBoundaries = null;
        try{
            bldgBoundaries = daoFactory.getBldgBoundariesDAO().find(boundaryId);
        }catch (DAO.DaoExn exn){
            return Response.accepted("boundaries not found. building ID: " + boundaryId).build();
        }
        try {

            daoFactory.getBldgBoundariesDAO().remove(bldgBoundaries);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.accepted("Delete boundaries failed. building ID: " + boundaryId).build();
        }
    }

}
