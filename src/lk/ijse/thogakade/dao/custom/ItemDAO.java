package lk.ijse.thogakade.dao.custom;

import lk.ijse.thogakade.dao.CrudDAO;
import lk.ijse.thogakade.model.Item;

public interface ItemDAO extends CrudDAO<Item,String> {
    boolean updateItemQtyOnHand(String code, int qtyOnHand) throws Exception;
}
