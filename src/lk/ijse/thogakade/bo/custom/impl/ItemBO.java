package lk.ijse.thogakade.bo.custom.impl;

import lk.ijse.thogakade.bo.SuperBO;
import lk.ijse.thogakade.dto.ItemDTO;

import java.util.ArrayList;

public interface ItemBO extends SuperBO {

    public boolean addItem(ItemDTO item)throws Exception;

    public boolean updateItem(ItemDTO item) throws  Exception;

    public boolean deletItem(String code) throws  Exception;

    ItemDTO searchItem(String code) throws Exception;

    ArrayList<ItemDTO> getAllItems() throws  Exception;

    boolean updateItemQtyOnHand(String code,int qtyOnHand)throws Exception;
}
