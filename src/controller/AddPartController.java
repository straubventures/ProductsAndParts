package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.ProductsAndPartsMainController.idCounter;

/** This class controls the Add Part form*/
public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;
    /** This method changes windows when called.
     @param address is the location of the new window
     @param event is for the event handler that triggers the switch */
    public void sceneManage(String address, ActionEvent event) throws IOException{

        stage = (Stage)((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(address));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    @FXML
    private Label IdLbl;

    @FXML
    private Label nameLbl;

    @FXML
    private Label invLbl;

    @FXML
    private Label priceCostLbl;

    @FXML
    private Label maxLbl;

    @FXML
    public Label machineIdLbl;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField invTxt;

    @FXML
    private TextField priceCostTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField machineIdTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private Label minLbl;

    @FXML
    private Label addPartLbl;

    @FXML
    private RadioButton inHouseRBtn;

    @FXML
    private RadioButton outsourcedRBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;




    /** This method is called when the window is first loaded. Within, it sets the id TextField with the current counter.
     @param url is the location where this class is found.
     @param rb helps facilitate actions with objects.
     */
    @Override
    public void initialize (URL url, ResourceBundle rb) {
        int id = idCounter;
        idTxt.setText(String.valueOf(id));
        inHouseRBtn.setSelected(true);


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
/** This handles the cancel button event. An alert will pop up to confirm the user decision to cancel.
 @param event this is from the cancel button. */
    @FXML
    void onActCancelPart(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this? All data will be lost!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            sceneManage("/view/ProductsAndPartsMain.fxml", event);
        }




    }
    /** This handles the save button event.
     @param event when the user saves the part, it saves the object and adds it to the Parts list. */
    @FXML
    void onActSavePart(ActionEvent event) throws IOException {

     try {

         if (Integer.valueOf(maxTxt.getText()) < Integer.valueOf(minTxt.getText())
            || Integer.valueOf(invTxt.getText()) < Integer.valueOf(minTxt.getText())
            || Integer.valueOf(invTxt.getText()) > Integer.valueOf(maxTxt.getText())) {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Your inventory levels are incorrect.");
             alert.setContentText("Please ensure your inventory levels are appropriate between the max, min, and inventory fields.");
             alert.showAndWait();



         } else {

             int id = idCounter;
             idCounter++;
             String name = nameTxt.getText();
             int stock = Integer.parseInt(invTxt.getText());
             double price = Double.parseDouble(priceCostTxt.getText());
             int min = Integer.parseInt(minTxt.getText());
             int max = Integer.parseInt(maxTxt.getText());

             if (inHouseRBtn.isSelected()) {
                 int machineId = Integer.parseInt(machineIdTxt.getText());
                 Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
             } else if (outsourcedRBtn.isSelected()) {
                 String companyName = machineIdTxt.getText();
                 Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
             }

             sceneManage("/view/ProductsAndPartsMain.fxml", event);
         }
     }catch (NumberFormatException ex){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR: You messed up.");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }}
    }


