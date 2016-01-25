package stevens.edu.infotrail.helper;

import stevens.edu.infotrail.domain.entity.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xshi90
 * @version %I%,%G%
 */
public class QuerySearchHelper {
    private List<TreeNode> querySearch;
    public QuerySearchHelper(){
        this.querySearch = new ArrayList<>();
    }

    public List<TreeNode> getQuerySearch() {
        return querySearch;
    }

    public void searchTree(String query,TreeNode root){
        if(root == null){
            return;
        }
        if(root.getNodeName().contains(query)){
            querySearch.add(root);
        }else{
            switch (root.getNodeName()){
                case "Organization" :
                    Organization organization = (Organization) root.getObj();
                    StringBuilder sbd1 = new StringBuilder();
                    sbd1.append(organization.getName())
                            .append(organization.getAddress1())
                            .append(organization.getAddress2())
                            .append(organization.getCity())
                            .append(organization.getInfo())
                            .append(organization.getState())
                            .append(organization.getZip());
                    if(sbd1.toString().contains(query)){
                        querySearch.add(root);
                    }
                    break;
                case "Building" :
                    Building building = (Building) root.getObj();
                    StringBuilder sbd2 = new StringBuilder();
                    sbd2.append(building.getName())
                            .append(building.getInfo())
                            .append(building.getAddress1())
                            .append(building.getAddress2())
                            .append(building.getState())
                            .append(building.getCity())
                            .append(building.getCountry())
                            .append(building.getZipcode());
                    if(sbd2.toString().contains(query)){
                        querySearch.add(root);
                    }
                    break;
                case "Floor" :
                    Floor floor = (Floor) root.getObj();
                    StringBuilder sbd3 = new StringBuilder();
                    sbd3.append(floor.getName())
                            .append(floor.getInfo());
                    if(sbd3.toString().contains(query)){
                        querySearch.add(root);
                    }
                    break;
                case "Room" :
                    Room room = (Room) root.getObj();
                    StringBuilder sbd4 = new StringBuilder();
                    sbd4.append(room.getRoomName())
                            .append(room.getRoomType())
                            .append(room.getRoomInfo());
                    if(sbd4.toString().contains(query)){
                        querySearch.add(root);
                    }
                    break;
                case "Item" :
                    Item item = (Item) root.getObj();
                    StringBuilder sbd5 = new StringBuilder();
                    sbd5.append(item.getItemName())
                            .append(item.getItemInfo());
                    if(sbd5.toString().contains(query)){
                        querySearch.add(root);
                    }
                    break;
                case "Event" :
                    Events event = (Events) root.getObj();
                    StringBuilder sbd6 = new StringBuilder();
                    sbd6.append(event.getEventName())
                            .append(event.getEventInfo())
                            .append(event.getEventOrganizer());
                    if(sbd6.toString().contains(query)){
                        querySearch.add(root);
                    }
                    break;
                case "Spot" :
                    Spot spot = (Spot) root.getObj();
                    StringBuilder sbd7 = new StringBuilder();
                    sbd7.append(spot.getName())
                            .append(spot.getType())
                            .append(spot.getDescription());
                    if(sbd7.toString().contains(query)){
                        querySearch.add(root);
                    }
                    break;
                case "amazonS3" :
                    Media media = (Media) root.getObj();
                    StringBuilder sbd8 = new StringBuilder();
                    sbd8.append(media.getName())
                            .append(media.getType())
                            .append(media.getDescription());
                    if(sbd8.toString().contains(query)){
                        querySearch.add(root);
                    }
                    break;
                default:
                    break;
            }
        }

        for (TreeNode child : root.getChildList()) {
            searchTree(query,child);
        }

    }
}
