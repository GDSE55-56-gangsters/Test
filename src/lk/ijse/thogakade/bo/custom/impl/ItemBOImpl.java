package lk.ijse.thogakade.bo.custom.impl;

import lk.ijse.thogakade.dao.DAOFactory;
import lk.ijse.thogakade.dao.custom.ItemDAO;
import lk.ijse.thogakade.dto.ItemDTO;
import lk.ijse.thogakade.model.Item;

import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean addItem(ItemDTO item) throws Exception {
     return itemDAO.add(new Item(item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));
    }

    @Override
    public boolean updateItem(ItemDTO item) throws Exception {
        return itemDAO.update(new Item(item.getCode(),item.getDescription(),item.getUnitPrice(),
                item.getQtyOnHand()));
    }

    @Override
    public boolean deletItem(String code) throws Exception {
        return itemDAO.delete(code);
    }

    @Override
    public ItemDTO searchItem(String code) throws Exception {
        Item search = itemDAO.search(code);
        return new ItemDTO(search.getCode(),search.getDescription(),search.getUnitPrice(),search.getQtyOnHand());
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws Exception {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        for(Item item : all){
            allItems.add(new ItemDTO(item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand()));
        }
        return allItems;
    }

    @Override
    public boolean updateItemQtyOnHand(String code, int qtyOnHand) throws Exception {
        return itemDAO.updateItemQtyOnHand(code,qtyOnHand);
    }
}
