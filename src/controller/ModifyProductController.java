package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;


import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.ProductsAndPartsMainController.idCounter;
import static model.Inventory.lookupPart;

/** This class controls the Modify Product form*/
public class ModifyProductController implements Initializable {

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
    private Label ModifyProductIdLbl;

    @FXML
    private Label ModifyProductNameLbl;

    @FXML
    private Label ModifyProductInvLbl;

    @FXML
    private Label ModifyProductPriceLbl;

    @FXML
    private Label ModifyProductMaxLbl;

    @FXML
    private Label ModifyProductMinLbl;

    @FXML
    private TextField ModifyProductIdTxt;

    @FXML
    private TextField ModifyProductNameTxt;

    @FXML
    private TextField ModifyProductInvTxt;

    @FXML
    private TextField ModifyProductPriceTxt;

    @FXML
    private TextField ModifyProductMaxTxt;

    @FXML
    private TextField ModifyProductMinTxt;

    @FXML
    private TextField addProductSearchTxt;

    @FXML
    private TableView<Part> ModifyProductAllPartsTbl;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> invCol;

    @FXML
    private TableColumn<Part, Double> priceCostCol;

    @FXML
    private Button ModifyProductsAddPartBtn;

    @FXML
    private TableView<Part> ModifyProductsAssocPartsTbl;

    @FXML
    private TableColumn<Part, Integer> assocPartIdCol;

    @FXML
    private TableColumn<Part, String> assocPartNameCol;

    @FXML
    private TableColumn<Part, Integer> assocInvCol;

    @FXML
    private TableColumn<Part, Double> assocPriceCostCol;

    @FXML
    private Button ModifyPartsRemoveAssocPartBtn;

    @FXML
    private Button ModifyPartsSaveBtn;

    @FXML
    private Button ModifyPartsCancelBtn;

    /** This handles the Add Part button. It moves a part into the associated table.
     @param event this happens when the user clicks the "Add" button while selecting a part in the All Parts table. */
    @FXML
    void onActAddPart(ActionEvent event)  throws IOException {

        Part selected = ModifyProductAllPartsTbl.getSelectionModel().getSelectedItem();
        int id = Integer.valueOf(ModifyProductIdTxt.getText());
        for (Product product : Inventory.getAllProducts()) {
            if (Integer.valueOf(product.getId()) == id ) {
                product.addAssociatedPart(selected);
            }
        }
    }

    /** This handles the search text field above the all parts table.
     @param event when a user types, their input is used to search the table. The table filters based on their input. If only one option remains,
     it will be highlighted. */
    @FXML
    void onActSearchParts(KeyEvent event) {

        try

        {
            ModifyProductAllPartsTbl.setItems(lookupPart(addProductSearchTxt.getText()));
            if (Inventory.getFilteredParts().size() == 1) {
                ModifyProductAllPartsTbl.getSelectionModel().select(0);
            }


        } catch(
                NullPointerException ex)

        {
            System.out.println("Exception: " + ex);
        }

    }

    /** This handles the cancel product event. An Alert is created.
     @param event this happens when the user clicks the cancel button. The changes are canceled. */
    @FXML
    void onActCancelModify(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure you want to leave?");
        alert.setContentText("All your data for this product modification will be lost. Please click OK to continue");
        alert.showAndWait();
        sceneManage("/view/ProductsAndPartsMain.fxml", event);
    }

    /** This handles the remove assoc part button.
     * @param event when the user clicks the button, their current part is deleted fromt the associated parts list */
    @FXML
    void onActDelAssocPart(ActionEvent event) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setContentText("Are you sure you wish to remove this product? This action cannot be undone.");
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part selected = ModifyProductsAssocPartsTbl.getSelectionModel().getSelectedItem();
            int index = -1;

            for (Part part : ModifyProductsAssocPartsTbl.getItems()) {
                index++;
                if (part.getId() == selected.getId()) {
                    ModifyProductsAssocPartsTbl.getItems().remove(selected);


                }

            }

        }
    }
    /** This handles the save button.
     * @param event when the user clicks the save button, a new product is created. */
    @FXML
    void onActSaveProd(ActionEvent event)  throws IOException {

        try {

            if (Integer.valueOf(ModifyProductMaxTxt.getText()) < Integer.valueOf(ModifyProductMinTxt.getText())
                    || Integer.valueOf(ModifyProductInvTxt.getText()) < Integer.valueOf(ModifyProductMinTxt.getText())
                    || Integer.valueOf(ModifyProductInvTxt.getText()) > Integer.valueOf(ModifyProductMaxTxt.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Your inventory levels are incorrect.");
                alert.setContentText("Please ensure your inventory levels are appropriate between the max, min, and inventory fields.");
                alert.showAndWait();


            } else {


                int id = Integer.valueOf(ModifyProductIdTxt.getText());
                String name = ModifyProductNameTxt.getText();
                int stock = Integer.parseInt(ModifyProductInvTxt.getText());
                double price = Double.parseDouble(ModifyProductPriceTxt.getText());
                int min = Integer.parseInt(ModifyProductMinTxt.getText());
                int max = Integer.parseInt(ModifyProductMaxTxt.getText());

                Product newProduct = new Product(id, name, price, stock, min, max);
                for (Product product : Inventory.getAllProducts()) {
                    if (Integer.valueOf(ModifyProductIdTxt.getText()) == Integer.valueOf(product.getId())) {
                        for (Part part : product.getAllAssociatedParts()) {
                            newProduct.addAssociatedPart(part);
                        }
                        Inventory.prodUpdate(Integer.valueOf(ModifyProductIdTxt.getText()), newProduct);

                    }
                }

                sceneManage("/view/ProductsAndPartsMain.fxml", event);
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            }
        }


    /** This method is called from the main menu when a user modifies a product. It sends the data to the Modify Product page so that
     * the user is able to easily alter their data with pre-filled inputs.
     * @param product is the product which is sent over */
    public void sendProduct(Product product) {
        try    {
            ModifyProductIdTxt.setText(String.valueOf(product.getId()));
            ModifyProductNameTxt.setText(String.valueOf(product.getName()));
            ModifyProductInvTxt.setText(String.valueOf(product.getStock()));
            ModifyProductPriceTxt.setText(String.valueOf(product.getPrice()));
            ModifyProductMaxTxt.setText(String.valueOf(product.getMax()));
            ModifyProductMinTxt.setText(String.valueOf(product.getMin()));

            ModifyProductsAssocPartsTbl.setItems(product.getAllAssociatedParts());


        } catch (NullPointerException ex) {
            System.out.println("Exception " + ex);
        }
    }
    /** This method is called when the window is first loaded.
     @param url is the location where this class is found.
     @param rb helps facilitate actions with objects.
     */
    @Override
    public void initialize (URL url, ResourceBundle rb) {
        ModifyProductAllPartsTbl.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        assocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPriceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));



    };

}
