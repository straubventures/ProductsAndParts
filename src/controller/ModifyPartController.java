package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;


import java.beans.ExceptionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.ProductsAndPartsMainController.idCounter;
/** This class controls the Modify Part form*/
public class ModifyPartController implements Initializable {

    Stage stage;
    Parent scene;

    /** This method changes windows when called.
     @param address is the location of the new window
     @param event is for the event handler that triggers the switch */
    public void sceneManage(String address, ActionEvent event) throws IOException {
        stage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(address));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private Label idLbl;

    @FXML
    private Label nameLbl;

    @FXML
    private Label invLbl;

    @FXML
    private Label priceCostLbl;

    @FXML
    private Label maxLbl;

    @FXML
    private Label machineIdLbl;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField invTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField machineIdTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private Label modifyPartTitleLbl;

    @FXML
    private RadioButton inHouseRBtn;

    @FXML
    private RadioButton outsourcedRBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    /** This method is called when the window is first loaded. Within, it sets the id TextField with the current counter. A new object is created that can then be called upon
     * later.
     @param url is the location where this class is found.
     @param rb helps facilitate actions with objects.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (outsourcedRBtn.isSelected()) {
            outsourcedRBtn.setSelected(true);
        }
    }

    /** This method handles the event of switching between InHouse and OutSourced objects.
     @param event is when the user clicks the corresponding radio button. */
    @FXML
    void onActInHouse(ActionEvent event) {
        machineIdLbl.setText("Machine ID");

    }

    /** This method handles the event of switching between InHouse and OutSourced objects.
     @param event is when the user clicks the corresponding radio button. */
    @FXML
    void onActOutSourced(ActionEvent event) {
        machineIdLbl.setText("Company Name");
    }

    ;
    /** This method is called from the main menu when a user modifies a part. It sends the data to the Modify Part page so that
     * the user is able to easily alter their data with pre-filled inputs.
     * @param part is the part which is sent over */
    public void sendPart(Part part) {
        try {
            idTxt.setText(String.valueOf(part.getId()));
            nameTxt.setText(String.valueOf(part.getName()));
            invTxt.setText(String.valueOf(part.getStock()));
            priceTxt.setText(String.valueOf(part.getPrice()));
            maxTxt.setText(String.valueOf(part.getMax()));
            minTxt.setText(String.valueOf(part.getMin()));
            machineIdTxt.setText(String.valueOf(part.getMin()));
            if (part.getClass().toString().contains("InHouse")) {
                machineIdLbl.setText("Machine ID");
                inHouseRBtn.setSelected(true);
            } else {
                machineIdLbl.setText("Company Name");
                outsourcedRBtn.setSelected(true);
            }


        } catch (NullPointerException ex) {
            System.out.println("Exception " + ex);
        }
    }
    /** This handles the cancel button.
     * @param event when the user clicks the cancel button the part is deleted and user goes back to the main menu. */
    @FXML
    void onActCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this? All data will be lost!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            sceneManage("/view/ProductsAndPartsMain.fxml", event);
        }
    }
    /** This handles the save button.
     * @param event when the user clicks the save button the part is formally created and added to the list. The user navigates back to the main
     * menu. */
    @FXML
    void onActSave(ActionEvent event) throws IOException {

        try {

            if (Integer.valueOf(maxTxt.getText()) < Integer.valueOf(minTxt.getText())
                    || Integer.valueOf(invTxt.getText()) < Integer.valueOf(minTxt.getText())
                    || Integer.valueOf(invTxt.getText()) > Integer.valueOf(maxTxt.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Your inventory levels are incorrect.");
                alert.setContentText("Please ensure your inventory levels are appropriate between the max, min, and inventory fields.");
                alert.showAndWait();


        } else {

            int id = Integer.valueOf(idTxt.getText());
            String name = nameTxt.getText();
            int stock = Integer.parseInt(invTxt.getText());
            double price = Double.parseDouble(priceTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int machineId = Integer.parseInt(machineIdTxt.getText());


            if (inHouseRBtn.isSelected()) {
                Part updatedPart = new InHouse(id, name, price, stock, min, max, machineId);
                Inventory.partUpdate(Integer.valueOf(idTxt.getText()), updatedPart);
            } else if (outsourcedRBtn.isSelected()) {
                String companyName = machineIdTxt.getText();
                Part updatedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.partUpdate(Integer.valueOf(idTxt.getText()), updatedPart);
            }

            sceneManage("/view/ProductsAndPartsMain.fxml", event);

        } } catch(NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR: You messed up.");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }
}