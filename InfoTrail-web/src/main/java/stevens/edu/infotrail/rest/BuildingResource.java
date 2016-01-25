package stevens.edu.infotrail.rest;

import stevens.edu.infotrail.dao.DAO.DaoExn;
import stevens.edu.infotrail.dao.daoFactory.DAOFactory;
import stevens.edu.infotrail.domain.entity.Building;
import stevens.edu.infotrail.domain.entity.Media;
import stevens.edu.infotrail.domain.entity.Organization;
import stevens.edu.infotrail.domain.entityFactory.BuildingFactory;
import stevens.edu.infotrail.helper.ParamAnalysis;
import stevens.edu.infotrail.representation.BuildingRepForCMS;

import javax.ejb.TransactionAttribute;
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
 * The BuildingResource is a resources class that will be hosted at the URI path
 * "/building".
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
@Path("/building")
@RequestScoped
public class BuildingResource {

    private static final Logger logger = Logger.getLogger(BuildingResource.class.getCanonicalName());

    @Inject
    private DAOFactory daoFactory;

    private ParamAnalysis paramAnalysis;

    @Context
    private UriInfo context;

    public BuildingResource(){
        this.paramAnalysis = new ParamAnalysis();
    }
    /**
     * @param name Building name
     * @return Response contains a building object according to the specified name
     */
    @GET
    @Path("{name}")
    @Produces("application/json")
    public Response findBuilding(@PathParam("name") String name) {
        logger.fine("finding the building...");
        Building building = null;
        try {
            building = daoFactory.getBuildingDAO().findByName(name);
            List<Media> medias = (building != null) ? daoFactory.getMediaDAO().findByParentId(building.getBuildingId()) : new ArrayList<Media>();
            return Response.ok(new BuildingRepForCMS(building, medias)).build();
        } catch (DaoExn e) {
            if (e.getMessage().contains("Media")) {
                return Response.ok(new BuildingRepForCMS(building, new ArrayList<Media>())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
    }

    /**
     * @return List of building objects
     */
    @GET
    @Produces("application/json")
    public Response findBuildings() {
        try {
            List<BuildingRepForCMS> res = new ArrayList<>();
            for (Building b : daoFactory.getBuildingDAO().findAll()) {
                BuildingRepForCMS rep = null;
                try {
                    List<Media> medias = (b != null) ? daoFactory.getMediaDAO().findByParentId(b.getBuildingId()) : new ArrayList<Media>();
                    rep = new BuildingRepForCMS(b, medias);
                } catch (DaoExn daoExn1) {
                    if (daoExn1.getMessage().contains("Media")) {
                        rep = new BuildingRepForCMS(b, new ArrayList<Media>());
                    }
                }
                res.add(rep);
            }
            return Response.ok(res).build();
        } catch (DaoExn e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * inserts a building record into the table
     *
     * @param building Building entity
     * @return 201 Created
     */
    @POST
    @Path("/old")
    @Consumes("application/json")
    @Transactional
    public Response createBuilding(Building building) {
        try {
            daoFactory.getBuildingDAO().create(building);
            int id = building.getBuildingId();
            UriBuilder ub = context.getAbsolutePathBuilder().path(String.valueOf(id));
            URI url = ub.build(String.valueOf(id));
            return Response.created(url).build();
        } catch (DaoExn e) {
            return Response.accepted("Create building failed. ID" + building.getBuildingId()).build();
        }
    }

    @POST
    @Consumes("application/json")
    @Transactional
    public Response createBuilding(JsonObject object) {
        String buildingId = object.getString("buildingId");
        String name= object.getString("name");
        String address1= object.getString("address1");
        String address2= (object.containsKey("address2"))?object.getString("address2"):"";
        String city= object.getString("city");
        String state= object.getString("state");
        String country= object.getString("country");
        String info= object.getString("info");
        String zipcode= object.getString("zipcode");
        String latitude= object.getString("latitude");
        String longitude= object.getString("longitude");
        String orgId= object.getString("orgId");
        BuildingFactory buildingFactory = new BuildingFactory();
        try{
            Organization organization = daoFactory.getOrDAO().find(paramAnalysis.toInteger(orgId));
            logger.info("=====@@@======"+organization.getOrgId());
            Building building = buildingFactory.createBuilding(paramAnalysis.toInteger(buildingId),name,address1,address2,city,state,country,info,zipcode,paramAnalysis.toDouble(latitude),paramAnalysis.toDouble(longitude),organization);
            daoFactory.getBuildingDAO().create(building);
            int id = building.getBuildingId();
            UriBuilder ub = context.getAbsolutePathBuilder().path(String.valueOf(id));
            URI url = ub.build(String.valueOf(id));
            return Response.created(url).build();
        }catch(DaoExn e){
            return Response.accepted("Create building failed. ID" + buildingId).build();
        }

    }

    /**
     * updates a building records to the table
     *
     * @param building Building entity
     * @return Response 200 OK
     */
    @PUT
    @Consumes("application/json")
    @Transactional()
    public Response updateBuilding(Building building) {
        try {
            daoFactory.getBuildingDAO().update(building);
            return Response.ok().build();
        } catch (DaoExn e) {
            return Response.accepted("Update building failed. ID" + building.getBuildingId()).build();
        }
    }

    /**
     * deletes a building record
     *
     * @param name Building name
     * @return Response 200 OK
     */
    @DELETE
    @Path("{name}")
    @Consumes("application/json")
    @Transactional
    public Response deleteBuilding(@PathParam("name") String name) {
        try {
            daoFactory.getBuildingDAO().remove(daoFactory.getBuildingDAO().findByName(name));
            return Response.ok().build();
        } catch (DaoExn e) {
            return Response.accepted("Delete building failed. ID" + name).build();
        }
    }
}
