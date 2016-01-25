package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Item;
import stevens.edu.infotrail.domain.entity.Room;

/**
 * ItemFactory implements method that allow an application to create
 * a item object.
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
public class ItemFactory implements IItemFactory {

    @Override
    public Item createItem(int itemId, String itemName, String itemInfo, Room room) {
        Item item = new Item();
        item.setItemId(itemId);
        item.setItemName(itemName);
        item.setItemInfo(itemInfo);
        item.setRoomId(room);
        return item;
    }

    @Override
    public Item getItem() {
        return new Item();
    }

}
