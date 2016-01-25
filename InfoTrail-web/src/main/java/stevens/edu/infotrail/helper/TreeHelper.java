package stevens.edu.infotrail.helper;

import stevens.edu.infotrail.domain.entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author xshi90
 */
public class TreeHelper {

    private TreeNode root;
    private int id;
    private List<TreeNode> tempNodeList;
    private boolean isValidTree = true;

    public TreeHelper() {
        this.id = Integer.MIN_VALUE;
        this.tempNodeList = new ArrayList<>();
    }

    public TreeHelper(List<TreeNode> treeNodeList, int id) {
        tempNodeList = treeNodeList;
        this.id = id;
        generateTree();
    }

    /**
     * @param tree TreeNode
     * @param id   Target ID
     * @return TreeNode
     */
    public static TreeNode getTreeNodeById(TreeNode tree, int id) {
        if (tree == null) {
            return null;
        }
        return tree.findTreeNodeById(id);
    }

    /**
     * generate a tree from the given treeNode or entity list
     */
    private void generateTree() {
        HashMap nodeMap = putNodesIntoMap();
        putChildIntoParent(nodeMap);
    }

    /**
     * put all the treeNodes into a hash table by its id as the key
     *
     * @return hashmap that contains the treenodes
     */
    protected HashMap putNodesIntoMap() {
        HashMap<String, TreeNode> nodeMap = new HashMap<>();
        for (TreeNode treeNode : tempNodeList) {
            int sid = treeNode.getSelfId();
            if (sid == this.id) {
                this.root = treeNode;
            }
            String keyId = String.valueOf(sid);
            nodeMap.put(keyId, treeNode);
        }
        return nodeMap;
    }

    /**
     * set the parent nodes point to the child nodes
     *
     * @param nodeMap a hashmap that contains all the treenodes by its id as the
     *                key
     */
    protected void putChildIntoParent(HashMap nodeMap) {
        for (Object o : nodeMap.values()) {
            TreeNode treeNode = (TreeNode) o;
            int parentId = treeNode.getParentId();
            String parentKeyId = String.valueOf(parentId);
            if (nodeMap.containsKey(parentKeyId)) {
                TreeNode parentNode = (TreeNode) nodeMap.get(parentKeyId);
                if (parentNode == null) {
                    this.isValidTree = false;
                    return;
                } else {
                    parentNode.addChildNode(treeNode);
                }
            }
        }
    }

    /**
     * initialize the tempNodeList property
     */
    protected void initTempNodeList() {
        if (this.tempNodeList == null) {
            this.tempNodeList = new ArrayList<>();
        }
    }

    /**
     * add a tree node to the tempNodeList
     *
     * @param treeNode TreeNode
     */
    public void addTreeNode(TreeNode treeNode) {
        initTempNodeList();
        this.tempNodeList.add(treeNode);
    }

    /**
     * insert a tree node to the tree generated already
     *
     * @param treeNode TreeNode
     * @return show the insert operation is ok or not
     */
    public boolean insertTreeNode(TreeNode treeNode) {
        return root.insertJuniorNode(treeNode);
    }

    public boolean isValidTree() {
        return this.isValidTree;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public List<TreeNode> getTempNodeList() {
        return tempNodeList;
    }

    public void setTempNodeList(List<TreeNode> tempNodeList) {
        this.tempNodeList = tempNodeList;
    }

    /**
     * convert organization entity to TreeNode
     *
     * @param org Organization entity
     * @return TreeNode
     */
    public TreeNode orgToTreeNode(Organization org) {
        TreeNode node = new TreeNode();
        node.setObj(org);
        node.setNodeName("Organization");
        node.setParentId(Integer.MAX_VALUE);
        node.setSelfId(org.getOrgId());
        return node;
    }

    /**
     * convert building entity to TreeNode
     *
     * @param building Building entity
     * @return TreeNode
     */
    public TreeNode buildingToTreeNode(Building building) {
        TreeNode node = new TreeNode();
        node.setObj(building);
        node.setNodeName("Building");
        Organization otmp = building.getOrgId();
        int tmpId = (otmp != null) ? otmp.getOrgId() : Integer.MIN_VALUE;
        node.setParentId(tmpId);
        node.setSelfId(building.getBuildingId());
        return node;
    }

    /**
     * convert floor entity to TreeNode
     *
     * @param floor Floor entity
     * @return TreeNode
     */
    public TreeNode floorToTreeNode(Floor floor) {
        TreeNode node = new TreeNode();
        node.setObj(floor);
        node.setNodeName("Floor");
        node.setParentId(floor.getBuildingId().getBuildingId());
        node.setSelfId(floor.getFloorId());
        return node;
    }

    /**
     * convert room entity to TreeNode
     *
     * @param room Room entity
     * @return TreeNode
     */
    public TreeNode roomToTreeNode(Room room) {
        TreeNode node = new TreeNode();
        node.setObj(room);
        node.setNodeName("Room");
        node.setParentId(room.getFloorId().getFloorId());
        node.setSelfId(room.getRoomId());
        return node;
    }

    /**
     * covert item entity to TreeNode
     *
     * @param item Item entity
     * @return TreeNode
     */
    public TreeNode itemToTreeNode(Item item) {
        TreeNode node = new TreeNode();
        node.setObj(item);
        node.setNodeName("Item");
        node.setParentId(item.getRoomId().getRoomId());
        node.setSelfId(item.getItemId());
        return node;
    }

    /**
     * convert event entity to TreeNode
     *
     * @param event Event entity
     * @return TreeNode
     */
    public TreeNode eventToTreeNode(Events event) {
        TreeNode node = new TreeNode();
        node.setObj(event);
        node.setNodeName("Event");
        Room room = event.getRoomId();
        int tmpId = (room != null) ? room.getRoomId() : Integer.MIN_VALUE;
        node.setParentId(tmpId);
        node.setSelfId(event.getEventId());
        return node;
    }

    /**
     * convert beacon entity to TreeNode
     *
     * @param beacon Beacon entity
     * @return TreeNode
     */
    public TreeNode beaconToTreeNode(Beacon beacon) {
        TreeNode node = new TreeNode();
        node.setObj(beacon);
        node.setNodeName("Beacon");
        node.setParentId(beacon.getItemId().getItemId());
        node.setSelfId(beacon.getBeaconId());
        return node;
    }

    /**
     * convert media entity to TreeNode
     *
     * @param media amazonS3 entity
     * @return TreeNode
     */
    public TreeNode mediaToTreeNode(Media media) {
        TreeNode node = new TreeNode();
        node.setObj(media);
        node.setNodeName("amazonS3");
        node.setParentId(media.getParentId());
        node.setSelfId(media.getMediaId());
        return node;
    }

    /**
     * convert spot entity to TreeNode
     *
     * @param spot Spot entity
     * @return TreeNode
     */
    public TreeNode spotToTreeNode(Spot spot) {
        TreeNode node = new TreeNode();
        node.setObj(spot);
        node.setNodeName("Spot");
        node.setParentId(spot.getBeaconId().getBeaconId());
        node.setSelfId(spot.getSpotId());
        return node;
    }
}
