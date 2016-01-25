package stevens.edu.infotrail.representation;

import stevens.edu.infotrail.domain.entity.Item;

/**
 * 
 * @author Xingsheng
 */
public class
        ItemRep {
    private int itemId;
    private String itemName;
    private String itemInfo;
    private String roomName;
    private int roomId;
    
    public ItemRep(){
        this.itemId = 0;
        this.itemName = "";
        this.itemInfo = "";
        this.roomName = "";
        this.roomId = 0;
    }

    public ItemRep(Item item){
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.itemInfo = item.getItemInfo();
        this.roomName = item.getRoomId().getRoomName();
        this.roomId = item.getRoomId().getRoomId();

    }
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
    }

    public String getRoom() {
        return roomName;
    }

    public void setRoom(String roomName) {
        this.roomName = roomName;
    }
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    @Override
    public String toString(){
        return "Item";
    }
}
