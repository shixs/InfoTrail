package stevens.edu.infotrail.domain.entityFactory;

import stevens.edu.infotrail.domain.entity.Item;
import stevens.edu.infotrail.domain.entity.Room;

/**
 * The IItemFactory interface provides methods for creating item
 * object.
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
public interface IItemFactory {
    /**
     * Returns a new item created with the given info.
     *
     * @param itemId   Item ID
     * @param itemName Item name
     * @param itemInfo Item information
     * @param room     Room
     * @return Item
     */
    Item createItem(int itemId, String itemName, String itemInfo, Room room);

    Item getItem();
}
