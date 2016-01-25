package stevens.edu.infotrail.rest;

import stevens.edu.infotrail.services.InfoService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Xingsheng
 * @version %I%,%G%
 */
@Path("/info")
@RequestScoped
public class InfoResource {

    //dependency injection
    //@Inject
    //private DAOFactory daoFactory;
    @Inject
    private InfoService infoService;

    public InfoResource() {
    }

    /**
     * @param info UriInfo
     * @return Response
     */
    @GET
    @Produces("application/json")
    public Response getItemById(@Context UriInfo info) {
        String requestId = info.getQueryParameters().getFirst("requestID");
        String itemIds = info.getQueryParameters().getFirst("itemID");

        if(itemIds == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        //check if itemID is invalid
        if (itemIds.length() < 1) {
            JsonObject resErr = infoService.generateResponseJSON(requestId, 400, "Bad Request: invalid id");
            return Response.ok(resErr).build();
        }
        //process multiple itemIDs
        String[] ids = itemIds.split(",");
        for (String id : ids) {
            infoService.findById(id);
        }

        //return the response
        if (infoService.getjArrayBuilder() != null) {
            JsonObject res = infoService.generateResponseJSON(requestId, infoService.getItemCount(), infoService.getErrInfo().toString());
            return Response.ok(res).build();
        } else {
            JsonObject resErr = infoService.generateResponseJSON(requestId, 404, "invalid id");
            return Response.ok(resErr).build();
        }
    }
}
