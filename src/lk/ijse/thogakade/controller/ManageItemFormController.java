package lk.ijse.thogakade.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.thogakade.AppInitializer;
import lk.ijse.thogakade.bo.BOFactory;
import lk.ijse.thogakade.bo.custom.ItemBO;
import lk.ijse.thogakade.dao.custom.impl.ItemDAOImpl;
import lk.ijse.thogakade.dto.ItemDTO;
import lk.ijse.thogakade.view.tblmodel.ItemTM;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageItemFormController implements Initializable {

    @FXML
    private JFXTextField txtItemCode;
    @FXML
    private JFXTextField txtDescription;
    @FXML
    private JFXTextField txtUnitPrice;
    @FXML
    private JFXTextField txtQty;
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<ItemTM> tblItems;

    private boolean addNew = true;

    private final  ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    private void loadAllItems(){
        try {
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            ArrayList<ItemTM> allItemsForTable = new ArrayList<>();
            for(ItemDTO i : allItems){
                allItemsForTable.add(new ItemTM(i.getCode(),i.getDescription(),i.getUnitPrice(),i.getQtyOnHand()));
            }

            ObservableList<ItemTM> olItems = FXCollections.observableArrayList();
            tblItems.setItems(olItems);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblItems.getColumns().get(0).setStyle("-fx-alignment: center");
        tblItems.getColumns().get(2).setStyle("-fx-alignment: center-right");
        tblItems.getColumns().get(3).setStyle("-fx-alignment: center-right");

        tblItems.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblItems.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblItems.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblItems.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        loadAllItems();

        tblItems.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ItemTM>() {
            @Override
            public void changed(ObservableValue<? extends ItemTM> observable, ItemTM oldValue, ItemTM newValue) {
                if(newValue ==  null){
                    addNew = true;
                    clearTextFields();
                    return;
                }

                txtItemCode.setText(newValue.getCode());
                txtDescription.setText(newValue.getDescription());
                txtUnitPrice.setText(newValue.getUnitPrice().toPlainString());
                txtQty.setText(newValue.getQtyOnHand() + "");

                addNew = false;
            }
        });
    }

    private void clearTextFields(){
        txtItemCode.setText("");
        txtDescription.setText("");
        txtUnitPrice.setText("");
        txtQty.setText("");
    }

    @FXML
    public void navigateToHome(MouseEvent mouseEvent) {
        AppInitializer.navigateToHome(root,(Stage) this.root.getScene().getWindow());
    }
    @FXML
    public void btnAddNewItem_OnAction(ActionEvent actionEvent) {
        tblItems.getSelectionModel().clearSelection();
        txtItemCode.requestFocus();
        addNew = true;

    }
    @FXML
    public void btnSave_OnAction(ActionEvent actionEvent) {
        if(addNew){

            try {
                ItemDTO itemDTO = new ItemDTO(txtItemCode.getText(), txtDescription.getText(), new BigDecimal(txtUnitPrice.getText()), Integer.parseInt(txtQty.getText()));
                boolean b = itemBO.addItem(itemDTO);
                if (b) {
                    loadAllItems();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Failed to add the item", ButtonType.OK).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{


            try {
                ItemDTO itemDTO = new ItemDTO(txtItemCode.getText(), txtDescription.getText(), new BigDecimal(txtUnitPrice.getText()), Integer.parseInt(txtQty.getText()));
                boolean b = itemBO.updateItem(itemDTO);
                if(b){
                    loadAllItems();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Failed to update the Item",ButtonType.OK).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        
    }
    @FXML
    public void btnDelete_OnAction(ActionEvent actionEvent) {
        if(tblItems.getSelectionModel().getSelectedIndex() == -1) return;
        String code = tblItems.getSelectionModel().getSelectedItem().getCode();


        try {
            ItemDAOImpl itemDAO = new ItemDAOImpl();
            boolean delete = itemDAO.delete(code);
            if (delete) {
                loadAllItems();
            }else {
                new Alert(Alert.AlertType.ERROR,"Unable to delete the customer",ButtonType.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
