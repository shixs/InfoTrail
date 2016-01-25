package stevens.edu.infotrail.rest;

import stevens.edu.infotrail.dao.daoFactory.DAOFactory;
import stevens.edu.infotrail.dao.DAO.DaoExn;
import stevens.edu.infotrail.domain.entity.Item;
import stevens.edu.infotrail.domain.entityFactory.ItemFactory;
import stevens.edu.infotrail.domain.entity.Media;
import stevens.edu.infotrail.domain.entity.Room;
import stevens.edu.infotrail.representation.ItemRepForCMS;

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

/**
 * The ItemResource is a resources class that will be hosted at the URI path
 * "/item".
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
@Path("/item")
@RequestScoped
public class ItemResource {

    @Inject
    private DAOFactory daoFactory;

    @Context
    private UriInfo context;

    /**
     * returns a certain item record by given the id
     *
     * @param id Item ID
     * @return Response
     */
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Response findByItemId(@PathParam("id") int id) {
        Item item = null;
        try {
            item = daoFactory.getItemDAO().find(id);
            List<Media> medias = daoFactory.getMediaDAO().findByParentId(item.getItemId());
            return Response.ok(new ItemRepForCMS(item,medias)).build();
        } catch (DaoExn e) {
            if(e.getMessage().contains("Media")){
                return Response.ok(new ItemRepForCMS(item, new ArrayList<Media>())).build();
            }else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
    }

    /**
     * returns all item records
     *
     * @return Response
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public Response findAllItems() {
        try {
            List<ItemRepForCMS> res = new ArrayList<>();
            for (Item i : daoFactory.getItemDAO().findAll()) {
                ItemRepForCMS iRep = null;
                try{
                    List<Media> medias = (i != null) ? daoFactory.getMediaDAO().findByParentId(i.getItemId()):new ArrayList<Media>();
                    iRep = new ItemRepForCMS(i,medias);
                }catch (DaoExn daoExn){
                    if(daoExn.getMessage().contains("amazonS3")){
                        iRep = new ItemRepForCMS(i,new ArrayList<Media>());
                    }
                }
                res.add(iRep);
            }
            return Response.ok(res).build();
        } catch (DaoExn e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * inserts a item record into the table
     *
     * @param object a JSON object contains values of the Item entity
     * @return Response
     */
    @POST
    @Consumes("application/json")
    @Transactional
    public Response createItem(JsonObject object) {
        String itemId = object.getString("itemId");
        String itemName = object.getString("itemName");
        String itemInfo = object.getString("itemInfo");
        String roomId = object.getString("roomId");
        ItemFactory iFactory = new ItemFactory();
        try {
            Room room = daoFactory.getRoomDAO().find(Integer.parseInt(roomId));
            Item item = iFactory.createItem(Integer.parseInt(itemId), itemName, itemInfo, room);
            daoFactory.getItemDAO().create(item);

            UriBuilder ub = context.getAbsolutePathBuilder().path(itemId);
            URI url = ub.build(itemId);

            return Response.created(url).build();
        } catch (DaoExn e) {
            return Response.accepted("Create item failed. ID: " + itemId).build();
        }
    }

    /**
     * updates a item record to the table
     *
     * @param object a JSON object contains values of the Item entity
     * @return Response
     */
    @PUT
    @Consumes("application/json")
    @Transactional
    public Response updateItem(JsonObject object) {
        String itemId = object.getString("itemId");
        String itemName = object.getString("itemName");
        String itemInfo = object.getString("itemInfo");
        String roomId = object.getString("roomId");
        ItemFactory iFactory = new ItemFactory();
        try {
            Room room = daoFactory.getRoomDAO().find(Integer.parseInt(roomId));
            Item item = iFactory.createItem(Integer.parseInt(itemId), itemName, itemInfo, room);
            daoFactory.getItemDAO().update(item);
            return Response.ok().build();
        } catch (DaoExn e) {
            return Response.accepted("Update item failed. ID: " + itemId).build();
        }
    }

    /**
     * deletes a certain item record
     *
     * @param itemId Item ID
     * @return Response
     */
    @DELETE
    @Path("/{itemId}")
    @Consumes("application/json")
    @Transactional
    public Response deleteItem(@PathParam("itemId") int itemId) {
        try {
            Item item = daoFactory.getItemDAO().find(itemId);
            daoFactory.getItemDAO().remove(item);
            return Response.ok().build();
        } catch (DaoExn e) {
            return Response.accepted("Delete item failed. ID: " + itemId).build();
        }
    }
}
