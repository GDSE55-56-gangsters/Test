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
import lk.ijse.thogakade.bo.custom.CustomerBO;
import lk.ijse.thogakade.dto.CustomerDTO;
import lk.ijse.thogakade.view.tblmodel.CustomerTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageCustomerFormController implements Initializable {
    boolean addnew = true;
    public AnchorPane root;
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public TableView <CustomerTM>tblCustomers;

    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblCustomers.getColumns().get(0).setStyle("-fx-alignment:center");
        tblCustomers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        tblCustomers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerTM>() {
            @Override
            public void changed(ObservableValue<? extends CustomerTM> observable, CustomerTM oldValue, CustomerTM newValue) {

                if(newValue == null){
                    clearTextFields();
                    addnew = false;
                }
            }
        });
        loadAllCustomers();
    }

    private void loadAllCustomers(){

        try {
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers();
            ArrayList<CustomerTM> all = new ArrayList<>();

            for(CustomerDTO cus:allCustomers){
                all.add(new CustomerTM(cus.getId(),cus.getName(),cus.getAddress()));
            }
            ObservableList<CustomerTM> olCustomers = FXCollections.observableArrayList(all);
            tblCustomers.setItems(olCustomers);
        } catch (Exception ex) {
            Logger.getLogger(ManageCustomerFormController.class.getName()).log(Level.SEVERE,null,ex);
        }

    }

    @FXML
    public void navigateToHome(MouseEvent mouseEvent) {
        AppInitializer.navigateToHome(root, (Stage) this.root.getScene().getWindow());
    }

    @FXML
    public void btnAddNewCustomer_OnAction(ActionEvent actionEvent) {
        txtCustomerId.requestFocus();
        tblCustomers.getSelectionModel().clearSelection();

        addnew = true;
    }

    @FXML
    public void btnSave_OnAction(ActionEvent actionEvent) {

        if (addnew) {

            try {

                boolean b = customerBO.addCustomer ( new CustomerDTO ( txtCustomerId.getText ( ) , txtCustomerName.getText ( ) , txtCustomerAddress.getText ( ) ) );

                if (b) {
                    loadAllCustomers();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Unable to add new customer", ButtonType.OK).show();
                }
            } catch (Exception ex) {
                Logger.getLogger(ManageCustomerFormController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            try {
                //Update

                boolean b = customerBO.updateCustomer ( new CustomerDTO ( txtCustomerId.getText ( ) , txtCustomerName.getText ( ) , txtCustomerAddress.getText ( ) ) );

                if (b) {
                    loadAllCustomers();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Unable to update the customer", ButtonType.OK).show();
                }


            } catch (Exception ex) {
                Logger.getLogger(ManageCustomerFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }



    @FXML
    public void btnDelete_OnAction(ActionEvent actionEvent) {
        Alert confirmAlert = new Alert(Alert.AlertType.WARNING, "Are you sure whether you want to delete the customer?", ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.get() == ButtonType.YES) {

            String customerID = tblCustomers.getSelectionModel().getSelectedItem().getId();

            try {

                boolean b =customerBO.deleteCustomer(txtCustomerId.getText());

                if (b) {
                    loadAllCustomers();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Failed to delete the customer", ButtonType.OK);
                    a.show();
                }
            } catch (Exception ex) {
                Logger.getLogger(ManageCustomerFormController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

        private void clearTextFields(){
        txtCustomerId.setText("");
        txtCustomerName.setText("");
        txtCustomerAddress.setText("");
    }
}
