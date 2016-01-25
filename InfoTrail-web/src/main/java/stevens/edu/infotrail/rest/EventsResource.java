package stevens.edu.infotrail.rest;

import stevens.edu.infotrail.dao.daoFactory.DAOFactory;
import stevens.edu.infotrail.dao.DAO.DaoExn;
import stevens.edu.infotrail.domain.entity.*;
import stevens.edu.infotrail.domain.entityFactory.EventFactory;
import stevens.edu.infotrail.representation.EventRepForCMS;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

/**
 * The EventsResource is a resources class that will be hosted at the URI path
 * "/event".
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
@Path("/event")
@RequestScoped
public class EventsResource {

    private static final Logger logger = Logger.getLogger(BeaconResource.class.getCanonicalName());

    @Inject
    private DAOFactory daoFactory;

    @Context
    private UriInfo context;
    private ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();

    /**
     * @param id Event id
     * @return an event matching the given id
     */
    @GET
    @Path("{id}")
    public Response findEventById(@PathParam("id") int id) {
        logger.fine("finding an Event...");
        Events event = null;
        try {
            event = daoFactory.getEventDAO().find(id);
            List<Media> medias = (event != null)?daoFactory.getMediaDAO().findByParentId(event.getEventId()):new ArrayList<Media>();

            return (event != null)?Response.ok(new EventRepForCMS(event,medias)).build(): Response.status(Response.Status.NOT_FOUND).build();
        } catch (DaoExn e) {
            if(e.getMessage().contains("Media")){
                return Response.ok(new EventRepForCMS(event,new ArrayList<Media>())).build();
            }else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
    }

    /**
     * inserts an event record into the table
     *
     * @param object a JSON object that contains values of an event
     * @return Response 201 Created
     */
    @POST
    @Consumes("application/json")
    @Transactional
    public Response createEvent(JsonObject object) {
        String eventId = object.getString("eventId");
        String eventName = object.getString("eventName");
        String eventOrganizer = object.getString("eventOrganizer");
        String eventInfo = object.getString("eventInfo");
        String roomName = object.getString("room");
        String floorName = object.getString("floor");
        String buildingName = object.getString("building");
        EventFactory eFactory = new EventFactory();
        try {
            Room room = daoFactory.getRoomDAO().findByName(roomName);
            Floor floor = daoFactory.getFloorDAO().findByName(floorName);
            Building building = daoFactory.getBuildingDAO().findByName(buildingName);
            Events event = eFactory.createEvent(Integer.parseInt(eventId), eventName, eventOrganizer, eventInfo, room, floor, building);
            daoFactory.getEventDAO().create(event);

            UriBuilder ub = context.getAbsolutePathBuilder().path(eventId);
            URI url = ub.build(eventId);
            return Response.created(url).build();
        } catch (DaoExn e) {
            return Response.accepted("Create event failed. ID" + eventId).build();
        }
    }

    /**
     * updates an event record to the table
     *
     * @param object a JSON object that contains values of an event
     * @return Response 200 OK
     */
    @PUT
    @Consumes("application/json")
    @Transactional
    public Response updateEvent(JsonObject object) {
        String eventId = object.getString("eventId");
        String eventName = object.getString("eventName");
        String eventOrganizer = object.getString("eventOrganizer");
        String eventInfo = object.getString("eventInfo");
        String roomName = object.getString("room");
        String floorName = object.getString("floor");
        String buildingName = object.getString("building");
        EventFactory eFactory = new EventFactory();
        try {
            Room room = daoFactory.getRoomDAO().findByName(roomName);
            Floor floor = daoFactory.getFloorDAO().findByName(floorName);
            Building building = daoFactory.getBuildingDAO().findByName(buildingName);
            Events event = eFactory.createEvent(Integer.parseInt(eventId), eventName, eventOrganizer, eventInfo, room, floor, building);
            daoFactory.getEventDAO().update(event);

            UriBuilder ub = context.getAbsolutePathBuilder().path(eventId);
            URI url = ub.build(eventId);
            return Response.created(url).build();
        } catch (DaoExn e) {
            return Response.accepted("Update event failed. ID" + eventId).build();
        }

    }

    /**
     * deletes an event record
     *
     * @param eventId Event id
     * @return Response
     */
    @DELETE
    @Path("/{id}")
    @Consumes("application/json")
    @Transactional
    public Response deleteEvent(@PathParam("id") int eventId) {
        try {
            daoFactory.getEventDAO().remove(daoFactory.getEventDAO().find(eventId));
            return Response.ok().build();
        } catch (DaoExn e) {
            return Response.accepted("Delete event failed. ID" + eventId).build();
        }
    }
    //try to do this method asynchronously

    /**
     * returns all event records
     *
     * @param asyncResponse AsyncResponse
     */
    @GET
    @Produces(value = "application/json")
    public void findEvent(@Suspended final AsyncResponse asyncResponse) {
        executorService.submit(new Runnable() {
            //overrides the run method
            @Override
            public void run() {
                asyncResponse.resume(doFindEvent());// call to doFindEvent method
            }
        });
    }

    private Response doFindEvent() {
        try {
            List<EventRepForCMS> res = new ArrayList<>();
            for (Events e : daoFactory.getEventDAO().findAll()) {
                EventRepForCMS eventRepForCMS = null;
                try{
                    List<Media> medias = (e != null) ? daoFactory.getMediaDAO().findByParentId(e.getEventId()):new ArrayList<Media>();
                    eventRepForCMS = new EventRepForCMS(e,medias);
                }catch (DaoExn daoExn){
                    eventRepForCMS = new EventRepForCMS(e,new ArrayList<Media>());
                }
                res.add(eventRepForCMS);
            }
            return Response.ok(res).build();
        } catch (DaoExn e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
