package stevens.edu.infotrail.rest;

import stevens.edu.infotrail.dao.DAO.DaoExn;
import stevens.edu.infotrail.dao.daoFactory.DAOFactory;
import stevens.edu.infotrail.domain.entity.Beacon;
import stevens.edu.infotrail.helper.ParamAnalysis;
import stevens.edu.infotrail.services.*;

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
@Path("/search")
@RequestScoped
public class SearchResource {

    @Inject
    private SearchService searchService;
    @Inject
    private DAOFactory daoFactory;
    private ParamAnalysis param;

    private StringBuffer err;

    public SearchResource() {
        this.param = new ParamAnalysis();
    }

    /**
     * @param info UriInfo
     * @return Response
     */
    @GET
    @Produces("application/json")
    public Response search(@Context UriInfo info) {
        param.doProcess(info);
        String requestId = info.getQueryParameters().getFirst("requestID");
        String typeFilter = param.getTypeFilter();
        String query = info.getQueryParameters().getFirst("query");
        double qX = param.getqX();
        double qY = param.getqY();
        String[] bIds = param.getbIds();
        int sRH = param.getsRH();
        int sRW = param.getsRW();
        int parId = param.getParId();
        int inX = param.getInX();
        int inY = param.getInY();
        int limit = param.getLimit();
        int[] offset = param.getOffset();
        searchService.initTree();
        int[] boundary = new int[4];
        double[] boundaries = new double[4];
        //check if query coordinates are given, do outdoor search
        if (qX != Double.MIN_VALUE && qY != Double.MIN_VALUE) {
            if (offset != null && offset.length == 2) {
                qX += offset[0];
                qY += offset[1];
            }
            //left boundary
            boundaries[0] = (qX - sRW / 2);
            //top boundary
            boundaries[1] = (qY + sRH / 2);
            //right boundary
            boundaries[2] = (qX + sRW / 2);
            //bottom boundary
            boundaries[3] = (qY - sRH / 2);
            if (query != null && query.length() > 0) {
                searchService.search(query, typeFilter, parId, boundaries);

            } else {
                searchService.search(typeFilter, parId, boundaries);
            }
        } else if (bIds != null) { //if query coordinates are not given, check if nearby beacons are given
            if (inX != Integer.MIN_VALUE && inY != Integer.MIN_VALUE) {
                //setup boundary
                if (offset != null && offset.length == 2) {
                    inX += offset[0];
                    inY += offset[1];
                }
                boundary = searchService.setBoundary(inX, inY, sRH, sRW);
            } else {
                //used beacon's location
                try {
                    String[] bids = bIds[0].split("\\.");
                    if (bids.length == 3) {
                        Beacon beacon = daoFactory.getBeaconDAO().findByIds(bids[0], searchService.toInteger(bids[1]), searchService.toInteger(bids[2]));
                        if (beacon != null) {
                            int newX = (beacon.getLocation() != null) ? searchService.toInteger(beacon.getLocation().split("-")[0]) : 0;
                            int newY = (beacon.getLocation() != null) ? searchService.toInteger(beacon.getLocation().split("-")[1]) : 0;
                            if (offset != null && offset.length == 2) {
                                newX += offset[0];
                                newY += offset[1];
                            }
                            if (newX != Integer.MIN_VALUE && newY != Integer.MIN_VALUE) {
                                boundary = searchService.setBoundary(newX, newY, sRH, sRW);
                            }
                        }
                    } else {
                        err.append("=======================Wrong locating information. ID: ").append(bIds[0]).append("  | ");
                        JsonObject res = searchService.generateResponseJSON(requestId, searchService.getItemCount(), err.toString());
                        return Response.accepted(res).build();
                    }
                } catch (DaoExn e) {
                    err.append("=======================Not Specific ID: ").append(bIds[0]).append("  | ");
                }
            }
            searchService.search(query, typeFilter, parId, bIds, boundary);
        } else {//otherwise, search all items
            searchService.search(typeFilter, parId);
        }
        JsonObject res = searchService.generateResponseJSON(requestId, searchService.getItemCount(), searchService.getErrInfo().toString());
        return Response.ok(res).build();
    }
}