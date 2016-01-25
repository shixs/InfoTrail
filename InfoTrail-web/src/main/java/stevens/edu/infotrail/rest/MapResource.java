package stevens.edu.infotrail.rest;

import com.amazonaws.services.s3.model.S3Object;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import stevens.edu.infotrail.amazonS3.AmazonS3Helper;
import stevens.edu.infotrail.dao.DAO;
import stevens.edu.infotrail.dao.daoFactory.DAOFactory;
import stevens.edu.infotrail.domain.entity.Beacon;
import stevens.edu.infotrail.domain.entity.Floor;
import stevens.edu.infotrail.domain.entity.Media;
import stevens.edu.infotrail.domain.entityFactory.MediaFactory;

import javax.enterprise.context.RequestScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.awt.*;
import java.io.*;
import java.util.logging.Logger;

/**
 * The MapResource is a resources class that will be hosted at the URI path
 * "/map".
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
@Path("/map")
@RequestScoped
public class MapResource {

    private static final Logger logger = Logger.getLogger(MapResource.class.getCanonicalName());

    @Inject
    private DAOFactory daoFactory;
    @Inject
    private AmazonS3Helper amazonS3Helper;

    /**
     * @param info UriInfo
     * @return Response
     */
    @GET
    @Produces("image/jpeg")
    public Response getIndoorMap(@Context UriInfo info) {
        String requestId = info.getQueryParameters().getFirst("requestID");
        String floorId = info.getQueryParameters().getFirst("floorID");
        String key = floorId + ".jpg";
        try {
            S3Object object = amazonS3Helper.getObject(key);
            Image image = (object != null) ? ImageIO.read(object.getObjectContent()) : null;
            return Response.ok(image).header("requestID", requestId).build();
        } catch (Exception e) {
            return Response.noContent().header("requestID", requestId).header("Error", "Map Not Found").build();
        }
    }

    /**
     * @param key Object key name used in amazon s3
     * @return Response
     */
    @GET
    @Path("/{key}")
    @Produces("image/jpeg")
    public Response getIndoorMap(@PathParam("key") String key) {
        try {
            S3Object object = amazonS3Helper.getObject(key);
            Image image = (object != null) ? ImageIO.read(object.getObjectContent()) : null;
            return Response.ok(image).build();
        } catch (IOException e) {
            return Response.noContent().build();
        }
    }

    /**
     * returns a certain floor by given the beacon's uuid
     *
     * @param info UriInfo
     * @return Response
     */
    @GET
    @Path("/location")
    @Produces("application/json")
    public Response findMe(@Context UriInfo info) {
        try {
            String ids = info.getQueryParameters().getFirst("ids");
            if (ids.length() < 1) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            String[] b_ids = ids.split("\\.");
            if (b_ids.length < 3) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            Beacon beacon = daoFactory.getBeaconDAO().findByIds(b_ids[0], Integer.parseInt(b_ids[1]), Integer.parseInt(b_ids[2]));
            String location = beacon.getLocation();
            String[] strs = location.split("-");
            Floor floor = daoFactory.getFloorDAO().find(Integer.parseInt(strs[0]));

            return Response.ok(floor).build();
        } catch (DAO.DaoExn ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
