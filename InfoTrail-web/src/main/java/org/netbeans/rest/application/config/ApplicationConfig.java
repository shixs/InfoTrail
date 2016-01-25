package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import stevens.edu.infotrail.rest.*;

/**
 *
 * @author Xingsheng
 */
@javax.ws.rs.ApplicationPath("resources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(MultiPartFeature.class);
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(stevens.edu.infotrail.rest.BeaconResource.class);
        resources.add(stevens.edu.infotrail.rest.BldgBoundaryResource.class);
        resources.add(stevens.edu.infotrail.rest.BoundaryResource.class);
        resources.add(stevens.edu.infotrail.rest.BuildingResource.class);
        resources.add(stevens.edu.infotrail.rest.EventsResource.class);
        resources.add(stevens.edu.infotrail.rest.FloorBoundaryResource.class);
        resources.add(stevens.edu.infotrail.rest.FloorResource.class);
        resources.add(stevens.edu.infotrail.rest.InfoResource.class);
        resources.add(stevens.edu.infotrail.rest.ItemResource.class);
        resources.add(stevens.edu.infotrail.rest.MapResource.class);
        resources.add(stevens.edu.infotrail.rest.MediaResource.class);
        resources.add(stevens.edu.infotrail.rest.OrgResource.class);
        resources.add(stevens.edu.infotrail.rest.RoomResource.class);
        resources.add(stevens.edu.infotrail.rest.SearchResource.class);
    }
    
}
