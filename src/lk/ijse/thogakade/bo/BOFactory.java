package lk.ijse.thogakade.bo;

import lk.ijse.thogakade.bo.custom.impl.ItemBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {

    }

    public static BOFactory getBoFactory() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public enum BOTypes {
        CUSTOMER, ITEM, ORDER
    }

    public SuperBO getBO(BOTypes types) {
        switch (types) {
            case CUSTOMER:
                return null;
            case ITEM:
                return new ItemBOImpl();
            case ORDER:
                return  null;
            default:
                return null;
        }
    }
}

