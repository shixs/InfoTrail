package stevens.edu.infotrail.rest;

import stevens.edu.infotrail.dao.daoFactory.DAOFactory;
import stevens.edu.infotrail.dao.DAO.DaoExn;
import stevens.edu.infotrail.domain.entity.Media;
import stevens.edu.infotrail.domain.entity.Organization;
import stevens.edu.infotrail.helper.ParamAnalysis;
import stevens.edu.infotrail.representation.OrgRepForCMS;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * The OrgResource is a resources class that will be hosted at the URI path
 * "/org".
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
@Path("/org")
@RequestScoped
public class OrgResource {

    @Inject
    private DAOFactory daoFactory;

    @Context
    private UriInfo context;

    /**
     * returns a certain organization record
     *
     * @param orgId Organization ID
     * @return Response
     */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getOrg(@PathParam("id") int orgId) {
        Organization organization = null;
        try {
            organization = daoFactory.getOrDAO().find(orgId);
            List<Media> medias = (organization != null) ? daoFactory.getMediaDAO().findByParentId(organization.getOrgId()):new ArrayList<Media>();
            return Response.ok(new OrgRepForCMS(organization,medias)).build();
        } catch (DaoExn e) {
            if(e.getMessage().contains("Media")){
                return Response.ok(new OrgRepForCMS(organization,new ArrayList<Media>())).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
    }

    @GET
    @Produces("application/json")
    public Response getAllOrg(){
        List<OrgRepForCMS> orgRepForCMSes = new ArrayList<>();
        try {
            for(Organization o : daoFactory.getOrDAO().findAll()){
                OrgRepForCMS orgRepForCMS = null;
                try{
                    List<Media> tmpMedias = (o != null) ? daoFactory.getMediaDAO().findByParentId(o.getOrgId()):new ArrayList<Media>();
                    orgRepForCMS = new OrgRepForCMS(o,tmpMedias);
                }catch(DaoExn daoExn1){
                    if(daoExn1.getMessage().contains("Media")){
                        orgRepForCMS = new OrgRepForCMS(o,new ArrayList<Media>());
                    }
                }
                orgRepForCMSes.add(orgRepForCMS);
            }
            return Response.ok(orgRepForCMSes).build();
        } catch (DaoExn daoExn) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * inserts an organization record into the table
     *
     * @param org Organization entity
     * @return Response
     */
    @POST
    @Consumes("application/json")
    @Transactional
    public Response createOrg(Organization org) {
        try {
            daoFactory.getOrDAO().create(org);
            UriBuilder ub = context.getAbsolutePathBuilder().path(String.valueOf(org.getOrgId()));
            URI url = ub.build(String.valueOf(org.getOrgId()));
            return Response.created(url).build();
        } catch (DaoExn e) {
            return Response.accepted("Create organization failed. ID: " + org.getOrgId()).build();
        }
    }

    /**
     * updates an organization record to the table
     *
     * @param org Organization entity
     * @return Response
     */
    @PUT
    @Consumes("application/json")
    @Transactional
    public Response updateOrg(Organization org) {
        try {
            daoFactory.getOrDAO().update(org);
            return Response.ok().build();
        } catch (DaoExn e) {
            return Response.accepted("Update organization failed. ID: " + org.getOrgId()).build();
        }
    }

    /**
     * deletes a certain organization record
     *
     * @param orgId Organization ID
     * @return Response
     */
    @DELETE
    @Path("/{orgId}")
    @Consumes("application/json")
    @Transactional
    public Response deleteOrg(@PathParam("orgId") int orgId) {
        try {
            daoFactory.getOrDAO().remove(daoFactory.getOrDAO().find(orgId));
            return Response.ok().build();
        } catch (DaoExn e) {
            return Response.accepted("Delete organization failed. ID: " + orgId).build();
        }
    }
}
