package stevens.edu.infotrail.rest;

import stevens.edu.infotrail.dao.daoFactory.DAOFactory;
import stevens.edu.infotrail.dao.DAO.DaoExn;
import stevens.edu.infotrail.domain.entity.Boundaries;
import stevens.edu.infotrail.domain.entityFactory.BoundariesFactory;
import stevens.edu.infotrail.domain.entity.Organization;

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
 * The BoundaryResource is a resources class that will be hosted at the URI path
 * "/boundary".
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
@Path("/boundary")
@RequestScoped
public class BoundaryResource {

    @Inject
    private DAOFactory daoFactory;

    @Context
    private UriInfo context;

    /**
     * @param id This id is a boundary id in the table
     * @return a certain boundary record matching the given id
     */
    @GET
    @Path("{orgId}")
    @Produces("application/json")
    public Response getB(@PathParam("orgId") int id) {
        try {
            return Response.ok(daoFactory.getBoundariesDAO().findByOrgId(id)).build();
        } catch (DaoExn e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * inserts a boundary record into the table
     *
     * @param object accept a JSON object that contains values of a boundary
     * @return Response 201 created
     */
    @POST
    @Consumes("application/json")
    @Transactional
    public Response createBoundaries(JsonObject object) {
        String boundariesId = object.getString("boundariesId");
        String latitude = object.getString("latitude");
        String longitude = object.getString("longitude");
        String orgId = object.getString("orgId");
        BoundariesFactory boFactory = new BoundariesFactory();
        try {
            Organization org = daoFactory.getOrDAO().find(Integer.parseInt(orgId));
            Boundaries boundaries = boFactory.createBoundaries(Integer.parseInt(boundariesId), Double.parseDouble(latitude), Double.parseDouble(longitude), org);
            daoFactory.getBoundariesDAO().create(boundaries);

            UriBuilder ub = context.getAbsolutePathBuilder().path(boundariesId);
            URI url = ub.build(boundariesId);
            return Response.created(url).build();
        } catch (DaoExn ex) {
            return Response.accepted("Create boundary failed. ID: " + boundariesId).build();
        }
    }

    /**
     * updates a boundary record to the table
     *
     * @param object accept a JSON object that contains values of a boundary
     * @return Response
     */
    @PUT
    @Consumes("application/json")
    @Transactional
    public Response updateBoundaries(JsonObject object) {
        String boundariesId = object.getString("boundariesId");
        String latitude = object.getString("latitude");
        String longitude = object.getString("longitude");
        String orgId = object.getString("orgId");
        BoundariesFactory boFactory = new BoundariesFactory();
        try {
            Organization org = daoFactory.getOrDAO().find(Integer.parseInt(orgId));
            Boundaries boundaries = boFactory.createBoundaries(Integer.parseInt(boundariesId), Double.parseDouble(latitude), Double.parseDouble(longitude), org);
            daoFactory.getBoundariesDAO().update(boundaries);
            return Response.ok().build();
        } catch (DaoExn ex) {
            return Response.accepted("Update boundary failed. ID: " + boundariesId).build();
        }
    }

    /**
     * deletes a boundary record
     *
     * @param orgId organization ID
     * @return Response
     */
    @DELETE
    @Path("{orgId}")
    @Consumes("application/json")
    @Transactional
    public Response deleteBoundaries(@PathParam("orgId") int orgId) {
        try {
            List<Boundaries> boundaries = daoFactory.getOrDAO().find(orgId).getBoundaries();
            for (Boundaries b : boundaries) {
                daoFactory.getBoundariesDAO().remove(b);
            }
            return Response.ok().build();
        } catch (DaoExn ex) {
            return Response.accepted("Delete boundaries failed. Organization ID: " + orgId).build();
        }
    }
}
