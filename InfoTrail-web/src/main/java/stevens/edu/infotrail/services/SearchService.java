package stevens.edu.infotrail.services;

import stevens.edu.infotrail.dao.DAO;
import stevens.edu.infotrail.dao.daoFactory.DAOFactory;
import stevens.edu.infotrail.domain.entity.*;
import stevens.edu.infotrail.helper.EntityToJSON;
import stevens.edu.infotrail.helper.QuerySearchHelper;
import stevens.edu.infotrail.helper.TreeHelper;
import stevens.edu.infotrail.helper.TreeNode;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author xshi90
 * @version %I%,%G%
 */
@RequestScoped
public class SearchService {
    private static Logger logger = Logger.getLogger(SearchService.class.getCanonicalName());

    @Inject
    private DAOFactory daoFactory;
    @Inject
    private EntityToJSON entityToJSON;
    private TreeNode root;
    private List<List<TreeNode>> searchRes;
    private List<TreeNode> resultTree;

    private JsonArrayBuilder jArrayBuilder;
    private int itemCount;
    private StringBuffer errInfo;

    public SearchService() {

        this.jArrayBuilder = Json.createArrayBuilder();
        this.itemCount = 0;
        this.errInfo = new StringBuffer("");
        this.searchRes = new ArrayList<>();
        this.resultTree = new ArrayList<>();

    }

    public void search(String query, String typeFilter, int parentId, double[] boundary) {
        logger.info("============================= Outdoor Searching Test ====================================");
        TreeNode rNode = this.root;
        List<TreeNode> tmpOrgNodes = rNode.getChildList();
        boolean flag = true;
        QuerySearchHelper querysSearchHelper = new QuerySearchHelper();
        //check if organization is in the given boundaries
        for (TreeNode n : tmpOrgNodes) {
            Organization o = (Organization) n.getObj();
            for (Boundaries bs : o.getBoundaries()) {
                if (bs.getLongitude() < boundary[0] || bs.getLongitude() > boundary[2]) {
                    flag = false;
                }
                if (bs.getLatitude() > boundary[1] || bs.getLatitude() < boundary[3]) {
                    flag = false;
                }
            }
            if (flag) {
                TreeNode tmpRoot;
                if (parentId != Integer.MIN_VALUE) {
                    tmpRoot = n.findTreeNodeById(parentId);
                } else {
                    tmpRoot = n;
                }
                //To Do:search matching the query
                querysSearchHelper.searchTree(query, tmpRoot);
                List<TreeNode> tmp = (querysSearchHelper.getQuerySearch() != null) ? querysSearchHelper.getQuerySearch() : null;
                //remove children of each TreeNode in case of duplicate
                for (TreeNode tn : tmp) {
                    tn.setChildList(new ArrayList<TreeNode>());
                }
                //generate new tree by using the TreeNode list
                TreeHelper helper = (resultTree.size() > 0) ? new TreeHelper(resultTree, resultTree.get(0).getSelfId()) : new TreeHelper();
                TreeNode pNode = helper.getRoot();
                List<String> tList = generateTypeList(typeFilter);
                if (tList != null) {
                    for (String tList1 : tList) {
                        generateResultByType(pNode, tList1);
                    }
                } else {
                    generateResult(pNode);
                }
            }
        }
    }

    /**
     * @param typeFilter type
     * @param parentId   parent ID
     */
    public void search(String typeFilter, int parentId) {
        logger.info("============================= Indoor Searching ====================================");
        logger.info("=============================TypeFilter: " + typeFilter + "==========================");
        TreeNode pNode;
        //if parentId is given, find the certain node
        if (parentId != Integer.MIN_VALUE) {
            TreeNode node = this.root;
            pNode = node.findTreeNodeById(parentId);
        } else {//otherwise use the root node
            pNode = this.root;
        }
        List<String> tList = generateTypeList(typeFilter);
        //if type of results is given, call generateResultByType method
        if (tList != null) {
            for (String tList1 : tList) {
                generateResultByType(pNode, tList1);
            }
        } else {//otherwise, call generateResult method
            generateResult(pNode);
        }
    }

    /**
     * @param typeFilter type
     * @param parentId   parent ID
     * @param beaconIds  beacons' ID
     * @param boundary   search region
     */
    public void search(String query, String typeFilter, int parentId, String[] beaconIds, int[] boundary) {
        logger.info("============================= Indoor Searching Test ====================================");
        TreeNode pNode;
        if (parentId != Integer.MIN_VALUE) {
            TreeNode node = this.root;
            pNode = node.findTreeNodeById(parentId);
        } else {
            pNode = this.root;
        }
        //get all nodes matching the given beacon ids
        for (String str : beaconIds) {
            getPath(pNode, str, boundary);
        }
        //generate a new list of TreeNode
        if (searchRes != null && searchRes.size() > 0) {
            for (List<TreeNode> pathList : searchRes) {
                for (TreeNode testRe1 : pathList) {
                    if (!isDuplicate(testRe1)) {
                        resultTree.add(testRe1);
                    }
                }
            }
        }
        //remove children of each TreeNode in case of duplicate
        for (TreeNode tn : resultTree) {
            tn.setChildList(new ArrayList<TreeNode>());
        }
        //generate new tree by using the TreeNode list
        TreeHelper helper = (resultTree.size() > 0) ? new TreeHelper(resultTree, resultTree.get(0).getSelfId()) : new TreeHelper();
        TreeNode tmpR = helper.getRoot();
        if (query != null && query.length() > 0) {
            QuerySearchHelper querysSearchHelper = new QuerySearchHelper();
            querysSearchHelper.searchTree(query, tmpR);
            List<TreeNode> tmp = (querysSearchHelper.getQuerySearch() != null) ? querysSearchHelper.getQuerySearch() : null;
            //remove children of each TreeNode in case of duplicate
            for (TreeNode tn : tmp) {
                tn.setChildList(new ArrayList<TreeNode>());
            }
            helper = (tmp.size() > 0) ? new TreeHelper(tmp, tmp.get(0).getSelfId()) : new TreeHelper();
            tmpR = helper.getRoot();
        }

        //call appropriate search method
        List<String> tList = generateTypeList(typeFilter);
        if (tmpR != null) {
            if (tList != null) {
                for (String tList1 : tList) {
                    generateResultByType(tmpR, tList1);
                }
            } else {
                generateResult(tmpR);
            }
        }
    }

    /**
     * @param other TreeNode
     * @return return true if the given TreeNode has exist in search result list, otherwise return false
     */
    private boolean isDuplicate(TreeNode other) {
        for (TreeNode t : resultTree) {
            if (other.getSelfId() == t.getSelfId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param typeFilter type
     * @param parentId   parent ID
     * @param boundary   boundaries
     */
    public void search(String typeFilter, int parentId, double[] boundary) {
        logger.info("============================= Outdoor Searching Test ====================================");
        TreeNode rNode = this.root;
        List<TreeNode> tmpOrgNodes = rNode.getChildList();
        boolean flag = true;
        //check if organization is in the given boundaries
        for (TreeNode n : tmpOrgNodes) {
            Organization o = (Organization) n.getObj();
            for (Boundaries bs : o.getBoundaries()) {
                if (bs.getLongitude() < boundary[0] || bs.getLongitude() > boundary[2]) {
                    flag = false;
                }
                if (bs.getLatitude() > boundary[1] || bs.getLatitude() < boundary[3]) {
                    flag = false;
                }
            }
            if (flag) {
                TreeNode tmpRoot;
                if (parentId != Integer.MIN_VALUE) {
                    tmpRoot = n.findTreeNodeById(parentId);
                } else {
                    tmpRoot = n;
                }
                List<String> tList = generateTypeList(typeFilter);
                if (tList != null) {
                    for (String tList1 : tList) {
                        generateResultByType(tmpRoot, tList1);
                    }
                } else {
                    generateResult(tmpRoot);
                }
            }
        }
    }

    /**
     * @param type type
     * @return type list
     */
    private List<String> generateTypeList(String type) {
        List<String> typeL = new ArrayList<>();
        for (String str : type.split(",")) {
            switch (str) {
                case "All":
                    return null;
                case "Organization":
                    typeL.add(str);
                    break;
                case "Building":
                    typeL.add(str);
                    break;
                case "Floor":
                    typeL.add(str);
                    break;
                case "Room":
                    typeL.add(str);
                    break;
                case "Item":
                    typeL.add(str);
                    break;
                case "Event":
                    typeL.add(str);
                    break;
                case "Beacon":
                    typeL.add(str);
                    break;
                case "Spot":
                    typeL.add(str);
                    break;
                case "amazonS3":
                    typeL.add(str);
                    break;
                default:
                    return null;
            }
        }
        return typeL;
    }

    /**
     * @param node     TreeNode
     * @param ids      beacon ID
     * @param boundary boundaries
     */
    private void getPath(TreeNode node, String ids, int[] boundary) {
        if (node == null) {
            return;
        }
        List<TreeNode> tree = new ArrayList<>();
        try {
            Beacon tmpBeacon = daoFactory.getBeaconDAO().findByIds(ids.split("\\.")[0], Integer.parseInt(ids.split("\\.")[1]), Integer.parseInt(ids.split("\\.")[2]));
            int tmpFloorId = (tmpBeacon != null) ? tmpBeacon.getItemId().getRoomId().getFloorId().getFloorId() : Integer.MIN_VALUE;
            //generate all possible TreeNodes
            getPathHelper(node, ids, tree, tmpFloorId, boundary);
        } catch (DAO.DaoExn daoExn) {
            return;
        }

    }

    /**
     * @param node     TreeNode
     * @param ids      Beacon ID
     * @param tree     List of TreeNode
     * @param boundary Boundaries
     */
    private void getPathHelper(TreeNode node, String ids, List<TreeNode> tree, int tmpFloorId, int[] boundary) {
        if (node == null) {
            return;
        }
        //Add current TreeNode
        tree.add(node);
        StringBuilder sb = new StringBuilder();
        //check if current node is a beacon node
        if (node.getNodeName().equals("Beacon")) {
            Beacon beacon = (Beacon) node.getObj();
            int floorId = beacon.getItemId().getRoomId().getFloorId().getFloorId();
            int tmpX = toInteger(beacon.getLocation().split("-")[0]);
            int tmpY = toInteger(beacon.getLocation().split("-")[1]);
            //generate beacon ID
            sb.append(beacon.getUuid()).append(".").append(beacon.getMajor()).append(".").append(beacon.getMinor());
            //check if current node's id equals to the given id
            if (sb.toString().equals(ids)) {
                List<TreeNode> childList1 = node.getChildList();

                //add children if any
                if (childList1 != null) {
                    for (TreeNode child : childList1) {
                        tree.add(child);
                    }
                }
                searchRes.add(new ArrayList<>(tree));
                return;
            } else if (floorId == tmpFloorId && tmpX > boundary[0] && tmpY < boundary[1] && tmpX < boundary[2] && tmpY > boundary[3]) {//otherwise the current beacon node is in the given search region
                List<TreeNode> childList1 = node.getChildList();
                if (childList1 != null) {
                    for (TreeNode child : childList1) {
                        tree.add(child);
                    }
                }
                searchRes.add(new ArrayList<>(tree));
                return;
            }
        }
        List<TreeNode> childList = node.getChildList();
        if (childList != null) {
            for (TreeNode child : childList) {
                if (!child.getNodeName().equals("Spot")) {
                    getPathHelper(child, ids, tree, tmpFloorId, boundary);//call method recursively
                }
            }
        }
        //remove the last TreeNode list if it doesn't match that condition
        tree.remove(tree.size() - 1);
    }

    /**
     * @param node TreeNode
     */
    private void generateResult(TreeNode node) {
        //do a pre-order traversal, DFS
        if (node == null) {
            return;
        }
        //convert TreeNode to JSON
        switch (node.getNodeName()) {
            case "Organization":
                Organization o = (Organization) node.getObj();
                jArrayBuilder.add(entityToJSON.organizationToJSON(o));
                itemCount++;
                break;
            case "Building":
                Building bu = (Building) node.getObj();
                jArrayBuilder.add(entityToJSON.buildingToJSON(bu));
                itemCount++;
                break;
            case "Floor":
                Floor f = (Floor) node.getObj();
                jArrayBuilder.add(entityToJSON.floorToJSON(f));
                itemCount++;
                break;
            case "Room":
                Room r = (Room) node.getObj();
                jArrayBuilder.add(entityToJSON.roomToJSON(r));
                itemCount++;
                break;
            case "Item":
                Item it = (Item) node.getObj();
                jArrayBuilder.add(entityToJSON.itemToJSON(it));
                itemCount++;
                break;
            case "Event":
                Events e = (Events) node.getObj();
                jArrayBuilder.add(entityToJSON.eventToJSON(e));
                itemCount++;
                break;
            case "Beacon":
                Beacon b = (Beacon) node.getObj();
                jArrayBuilder.add(entityToJSON.beaconToJSON(b));
                itemCount++;
                break;
            case "amazonS3":
                Media m = (Media) node.getObj();
                jArrayBuilder.add(entityToJSON.MediaToJSON(m));
                itemCount++;
                break;
            case "Spot":
                Spot spot = (Spot) node.getObj();
                jArrayBuilder.add(entityToJSON.SpotToJSON(spot));
                itemCount++;
                break;
        }
        List<TreeNode> childList = node.getChildList();
        if (childList != null) {
            for (TreeNode child : childList) {
                generateResult(child);
            }
        }
    }

    /**
     * @param node TreeNode
     * @param type type
     */
    private void generateResultByType(TreeNode node, String type) {
        if (node == null) {
            return;
        }
        if (node.getNodeName().equals(type)) {
            switch (type) {
                case "Organization":
                    Organization o = (Organization) node.getObj();
                    jArrayBuilder.add(entityToJSON.organizationToJSON(o));
                    itemCount++;
                    break;
                case "Building":
                    Building bu = (Building) node.getObj();
                    jArrayBuilder.add(entityToJSON.buildingToJSON(bu));
                    itemCount++;
                    break;
                case "Floor":
                    Floor f = (Floor) node.getObj();
                    jArrayBuilder.add(entityToJSON.floorToJSON(f));
                    itemCount++;
                    break;
                case "Room":
                    Room r = (Room) node.getObj();
                    jArrayBuilder.add(entityToJSON.roomToJSON(r));
                    itemCount++;
                    break;
                case "Item":
                    Item it = (Item) node.getObj();
                    jArrayBuilder.add(entityToJSON.itemToJSON(it));
                    itemCount++;
                    break;
                case "Event":
                    Events e = (Events) node.getObj();
                    jArrayBuilder.add(entityToJSON.eventToJSON(e));
                    itemCount++;
                    break;
                case "Beacon":
                    Beacon b = (Beacon) node.getObj();
                    jArrayBuilder.add(entityToJSON.beaconToJSON(b));
                    itemCount++;
                    break;
                case "amazonS3":
                    Media m = (Media) node.getObj();
                    jArrayBuilder.add(entityToJSON.MediaToJSON(m));
                    itemCount++;
                    break;
                case "Spot":
                    Spot spot = (Spot) node.getObj();
                    jArrayBuilder.add(entityToJSON.SpotToJSON(spot));
                    itemCount++;
                    break;
            }
        }
        List<TreeNode> childList = node.getChildList();
        if (childList != null) {
            for (TreeNode child : childList) {
                generateResultByType(child, type);
            }
        }
    }

    /**
     * @param str String
     * @return Integer
     */
    public int toInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return Integer.MIN_VALUE;
        }
    }

    /**
     * @param x       X
     * @param y       Y
     * @param searchH search region height
     * @param searchW search region width
     * @return Integer array
     */
    public int[] setBoundary(int x, int y, int searchH, int searchW) {
        int[] res = new int[4];
        //left boundary
        res[0] = x - searchW / 2;
        //top boundary
        res[1] = y + searchH / 2;
        //right boundary
        res[2] = x + searchW / 2;
        //bottom boundary
        res[3] = y - searchH / 2;
        return res;
    }

    /**
     * Initialize the search tree
     */
    public void initTree() {
        List<TreeNode> tmpList = new ArrayList<>();
        TreeHelper tHelper = new TreeHelper();
        //add root node
        root = new TreeNode();
        root.setNodeName("Info");
        root.setSelfId(Integer.MAX_VALUE);
        tmpList.add(root);
        //convert entity to TreeNode
        List<Organization> tmpO = getAllOrg();
        if (tmpO != null) {
            for (Organization o : tmpO) {
                tmpList.add(tHelper.orgToTreeNode(o));
            }
        }
        List<Building> tmpBu = getAllBuilding();
        if (tmpBu != null) {
            for (Building bu : tmpBu) {
                tmpList.add(tHelper.buildingToTreeNode(bu));
            }
        }
        List<Floor> tmpF = getAllFloor();
        if (tmpF != null) {
            for (Floor f : tmpF) {
                tmpList.add(tHelper.floorToTreeNode(f));
            }
        }
        List<Room> tmpR = getAllRoom();
        if (tmpR != null) {
            for (Room r : tmpR) {
                tmpList.add(tHelper.roomToTreeNode(r));
            }
        }
        List<Item> tmpI = getAllItem();
        if (tmpI != null) {
            for (Item it : tmpI) {
                tmpList.add(tHelper.itemToTreeNode(it));
            }
        }
        List<Events> tmpE = getAllEvent();
        if (tmpE != null) {
            for (Events e : tmpE) {
                tmpList.add(tHelper.eventToTreeNode(e));
            }
        }
        List<Beacon> tmpB = getAllBeacon();
        if (tmpB != null) {
            for (Beacon b : tmpB) {
                tmpList.add(tHelper.beaconToTreeNode(b));
            }
        }
        List<Media> tmpM = getAllMedia();
        if (tmpM != null) {
            for (Media m : tmpM) {
                tmpList.add(tHelper.mediaToTreeNode(m));
            }
        }
        List<Spot> tmpS = getAllSpot();
        if (tmpS != null) {
            for (Spot s : tmpS) {
                tmpList.add(tHelper.spotToTreeNode(s));
            }
        }
        //generate a tree
        if (tmpList.size() > 0) {
            tHelper = new TreeHelper(tmpList, Integer.MAX_VALUE);
        }
        this.root = tHelper.getRoot();
    }

    /**
     * @return all organization entity
     */
    private List<Organization> getAllOrg() {
        try {
            return (daoFactory.getOrDAO().findAll() != null) ? daoFactory.getOrDAO().findAll() : new ArrayList<Organization>();
        } catch (DAO.DaoExn ex) {
            return new ArrayList<>();
        }
    }

    /**
     * @return all building entity
     */
    private List<Building> getAllBuilding() {
        try {
            return (daoFactory.getBuildingDAO().findAll() != null) ? daoFactory.getBuildingDAO().findAll() : new ArrayList<Building>();
        } catch (DAO.DaoExn ex) {
            return new ArrayList<>();
        }
    }

    /**
     * @return all floor entity
     */
    private List<Floor> getAllFloor() {
        try {
            return (daoFactory.getFloorDAO().findAll() != null) ? daoFactory.getFloorDAO().findAll() : new ArrayList<Floor>();
        } catch (DAO.DaoExn ex) {
            return new ArrayList<>();
        }
    }

    /**
     * @return all room entity
     */
    private List<Room> getAllRoom() {
        try {
            return (daoFactory.getRoomDAO().findAll() != null) ? daoFactory.getRoomDAO().findAll() : new ArrayList<Room>();
        } catch (DAO.DaoExn ex) {
            return new ArrayList<>();
        }
    }

    /**
     * @return all item entity
     */
    private List<Item> getAllItem() {
        try {
            return (daoFactory.getItemDAO().findAll() != null) ? daoFactory.getItemDAO().findAll() : new ArrayList<Item>();
        } catch (DAO.DaoExn ex) {
            return new ArrayList<>();
        }
    }

    /**
     * @return all event entity
     */
    private List<Events> getAllEvent() {
        try {
            return (daoFactory.getEventDAO().findAll() != null) ? daoFactory.getEventDAO().findAll() : new ArrayList<Events>();
        } catch (DAO.DaoExn ex) {
            return new ArrayList<>();
        }
    }

    /**
     * @return all beacon entty
     */
    private List<Beacon> getAllBeacon() {
        try {
            return (daoFactory.getBeaconDAO().findAll() != null) ? daoFactory.getBeaconDAO().findAll() : new ArrayList<Beacon>();
        } catch (DAO.DaoExn ex) {
            return new ArrayList<>();
        }
    }

    /**
     * @return all media entity
     */
    private List<Media> getAllMedia() {
        try {
            return (daoFactory.getMediaDAO().findAll() != null) ? daoFactory.getMediaDAO().findAll() : new ArrayList<Media>();
        } catch (DAO.DaoExn ex) {
            return new ArrayList<>();
        }
    }

    /**
     * @return all spot entity
     */
    private List<Spot> getAllSpot() {
        try {
            return (daoFactory.getSpotDAO().findAll() != null) ? daoFactory.getSpotDAO().findAll() : new ArrayList<Spot>();
        } catch (DAO.DaoExn ex) {
            return new ArrayList<>();
        }
    }

    /**
     * @param requestID  Request ID
     * @param itemsCount Number of items
     * @param error      Error information
     * @return JSON object
     */
    public JsonObject generateResponseJSON(String requestID, int itemsCount, String error) {
        JsonObject error_info = Json.createObjectBuilder().add("code", 0)
                .add("type", "")
                .add("description", error).build();

        return Json.createObjectBuilder().add("requestID", requestID)
                .add("itemsCount", itemsCount)
                .add("items", jArrayBuilder.build())
                .add("error", error_info).build();
    }

    public JsonArrayBuilder getjArrayBuilder() {
        return jArrayBuilder;
    }

    public void setjArrayBuilder(JsonArrayBuilder jArrayBuilder) {
        this.jArrayBuilder = jArrayBuilder;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public StringBuffer getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(StringBuffer errInfo) {
        this.errInfo = errInfo;
    }
}

