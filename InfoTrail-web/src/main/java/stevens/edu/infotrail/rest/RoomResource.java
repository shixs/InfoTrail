package stevens.edu.infotrail.rest;

import stevens.edu.infotrail.dao.daoFactory.DAOFactory;
import stevens.edu.infotrail.dao.DAO.DaoExn;
import stevens.edu.infotrail.domain.entity.Floor;
import stevens.edu.infotrail.domain.entity.Media;
import stevens.edu.infotrail.domain.entity.Room;
import stevens.edu.infotrail.domain.entityFactory.RoomFactory;
import stevens.edu.infotrail.representation.RoomRepForCMS;

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
 * The RoomResource is a resources class that will be hosted at the URI path
 * "/room".
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
@Path("/room")
@RequestScoped
public class RoomResource {

    @Inject
    private DAOFactory daoFactory;

    @Context
    private UriInfo context;

    /**
     * @return all room records
     */
    @GET
    @Produces("application/json")
    public Response findRooms() {
        try {
            List<RoomRepForCMS> res = new ArrayList<>();
            for (Room r : daoFactory.getRoomDAO().findAll()) {
                RoomRepForCMS rRep = null;
                try{
                    List<Media> medias = (r != null) ? daoFactory.getMediaDAO().findByParentId(r.getRoomId()):new ArrayList<Media>();
                    rRep = new RoomRepForCMS(r,medias);
                }catch(DaoExn daoExn){
                    if(daoExn.getMessage().contains("Media")){
                        rRep = new RoomRepForCMS(r,new ArrayList<Media>());
                    }
                }
                res.add(rRep);
            }
            return Response.ok(res).build();
        } catch (DaoExn e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * @param roomId Room ID
     * @return a certain room record by given room id
     */
    @GET
    @Path("{room_id}")
    @Produces("application/json")
    public Response findRoom(@PathParam("room_id") int roomId) {
        Room room = null;
        try {
            room = daoFactory.getRoomDAO().find(roomId);
            List<Media> medias = (room != null)?daoFactory.getMediaDAO().findByParentId(room.getRoomId()):new ArrayList<Media>();
            return (room != null)?Response.ok(new RoomRepForCMS(room,medias)).build():Response.status(Response.Status.NOT_FOUND).build();
        } catch (DaoExn e) {
            if(e.getMessage().contains("Media")){
                return Response.ok(new RoomRepForCMS(room,new ArrayList<Media>())).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * inserts a room record into the table
     *
     * @param object a JSON object contains values of room entity
     * @return Response
     */
    @POST
    @Consumes("application/json")
    @Transactional
    public Response createRoom(JsonObject object) {
        String roomId = object.getString("roomId");
        String roomName = object.getString("roomName");
        String roomInfo = object.getString("roomInfo");
        String roomType = object.getString("roomType");
        String roomX1 = object.getString("roomX1");
        String roomY1 = object.getString("roomY1");
        String roomX2 = object.getString("roomX2");
        String roomY2 = object.getString("roomY2");
        String floorName = object.getString("floorName");
        RoomFactory rFactory = new RoomFactory();
        try {
            Floor floor = daoFactory.getFloorDAO().findByName(floorName);
            Room room = rFactory.createRoom(Integer.parseInt(roomId), roomName, roomInfo, roomType, Integer.parseInt(roomX1), Integer.parseInt(roomY1),Integer.parseInt(roomX2), Integer.parseInt(roomY2), floor);
            daoFactory.getRoomDAO().create(room);
            UriBuilder ub = context.getAbsolutePathBuilder().path(roomId);
            URI url = ub.build(roomId);
            return Response.created(url).build();
        } catch (DaoExn e) {
            return Response.accepted("Create room failed. ID: " + roomId).build();
        }
    }

    /**
     * updates a room record to the table
     *
     * @param object a JSON object contains values of room entity
     * @return Response
     */
    @PUT
    @Consumes("application/json")
    @Transactional
    public Response updateRoom(JsonObject object) {
        String roomId = object.getString("roomId");
        String roomName = object.getString("roomName");
        String roomInfo = object.getString("roomInfo");
        String roomType = object.getString("roomType");
        String roomX1 = object.getString("roomX1");
        String roomY1= object.getString("roomY1");
        String roomX2 = object.getString("roomX2");
        String roomY2 = object.getString("roomY2");
        String floorId = object.getString("floorId");
        RoomFactory rFactory = new RoomFactory();
        try {
            Floor floor = daoFactory.getFloorDAO().find(Integer.parseInt(floorId));
            Room room = rFactory.createRoom(Integer.parseInt(roomId), roomName, roomInfo, roomType, Integer.parseInt(roomX1), Integer.parseInt(roomY1),Integer.parseInt(roomX2), Integer.parseInt(roomY2), floor);
            daoFactory.getRoomDAO().update(room);
            return Response.ok().build();
        } catch (DaoExn e) {
            return Response.accepted("Update room failed. ID: " + roomId).build();
        }
    }

    /**
     * deletes a certain room record
     *
     * @param roomId Room ID
     * @return Response
     */
    @DELETE
    @Path("/{roomId}")
    @Consumes("application/json")
    @Transactional
    public Response deleteRoom(@PathParam("roomId") int roomId) {
        try {
            Room room = daoFactory.getRoomDAO().find(roomId);
            daoFactory.getRoomDAO().remove(room);
            return Response.ok().build();
        } catch (DaoExn e) {
            return Response.accepted("Delete room failed. ID: " + roomId).build();
        }
    }
}
