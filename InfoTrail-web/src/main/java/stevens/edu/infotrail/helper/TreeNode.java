package stevens.edu.infotrail.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author xshi90
 */
public class TreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(TreeNode.class.getCanonicalName());
    protected String nodeName;
    protected Object obj;
    protected TreeNode parentNode;
    protected List<TreeNode> childList;
    private int parentId;
    private int selfId;

    /**
     * default constructor
     */
    public TreeNode() {
        initChildList();
    }

    /**
     * @param parentNode TreeNode
     */
    public TreeNode(TreeNode parentNode) {
        this.getParentNode();
        initChildList();
    }

    /**
     * @return true if the current node is leaf, otherwise return false
     */
    public boolean isLeaf() {
        return childList == null || childList.isEmpty();
    }

    /**
     * add a child to the current node
     * @param treeNode TreeNode
     */
    /* insert a child node into current node*/
    public void addChildNode(TreeNode treeNode) {
        initChildList();
        childList.add(treeNode);
    }

    /**
     * initialize the children list
     */
    private void initChildList() {
        if (childList == null) {
            childList = new ArrayList<>();
        }
    }

    public boolean isValidTree() {
        return true;
    }

    /**
     * @return Elders list
     */
    /* return a list of current nodes' parent node */
    public List<TreeNode> getElders() {
        List<TreeNode> elderList = new ArrayList<>();
        TreeNode pNode = this.getParentNode();
        if (pNode == null) {
            return elderList;
        } else {
            elderList.add(pNode);
            elderList.addAll(pNode.getElders());
            return elderList;
        }
    }

    /**
     * @return Juniors list
     */
    /* return a list of children nodes */
    public List<TreeNode> getJuniors() {
        List<TreeNode> juniorList = new ArrayList<>();
        List<TreeNode> cList = this.getChildList();
        if (cList == null) {
            return juniorList;
        } else {
            for (TreeNode junior : cList) {
                juniorList.add(junior);
                juniorList.addAll(junior.getJuniors());
            }
            return juniorList;
        }
    }

    /**
     * @return children list
     */
    /* return children list */
    public List<TreeNode> getChildList() {
        return childList;
    }

    /**
     * @param childList list of nodes
     */
    public void setChildList(List<TreeNode> childList) {
        this.childList = childList;
    }

    /**
     * delete the current node
     */
    public void deleteNode() {
        TreeNode pNode = this.getParentNode();
        int id = this.getSelfId();

        if (pNode != null) {
            pNode.deleteChildNode(id);
        }
    }

    /**
     * delete a certain child of current node
     * @param childId node self ID
     */
    public void deleteChildNode(int childId) {
        List<TreeNode> cList = this.getChildList();
        int childNumber = cList.size();
        for (int i = 0; i < childNumber; i++) {
            TreeNode child = cList.get(i);
            if (child.getSelfId() == childId) {
                cList.remove(i);
                return;
            }
        }
    }

    /**
     * insert a node in this tree
     * @param treeNode TreeNode
     * @return true if added, otherwise return false
     */
    public boolean insertJuniorNode(TreeNode treeNode) {
        int juniorParentId = treeNode.getParentId();
        if (this.parentId == juniorParentId) {
            addChildNode(treeNode);
            return true;
        } else {
            List<TreeNode> cList = this.getChildList();
            boolean insertFlag;

            for (TreeNode childNode : cList) {
                insertFlag = childNode.insertJuniorNode(treeNode);
                if (insertFlag) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * find a certain node
     * @param id TreeNode self ID
     * @return TreeNode
     */
    public TreeNode findTreeNodeById(int id) {
        if (this.selfId == id) {
            return this;
        }
        if (childList.isEmpty()) {//check
            return null;
        } else {
            for (TreeNode child : childList) {
                TreeNode resultNode = child.findTreeNodeById(id);
                if (resultNode != null) {
                    return resultNode;
                }
            }
            return null;
        }
    }

    /**
     * pre-order traversal
     */
    public void traverse() {
        if (selfId < 0) {
            return;
        }
        print(this.selfId);
        if (childList == null || childList.isEmpty()) {
            return;
        }
        for (TreeNode child : childList) {
            child.traverse();
        }
    }

    public void print(String content) {
        logger.info(content);
    }

    /**
     * @param content content
     */
    public void print(int content) {
        logger.info(String.valueOf(content));
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getSelfId() {
        return selfId;
    }

    public void setSelfId(int selfId) {
        this.selfId = selfId;
    }

    private TreeNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(TreeNode parentNode) {
        this.parentNode = parentNode;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

}
