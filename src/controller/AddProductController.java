package controller;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Optional;

import static controller.ProductsAndPartsMainController.idCounter;

/** This class controls the Add Product form*/
public class AddProductController implements Initializable {
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
    private Label AddProdIdLbl;

    @FXML
    private Label AddProdNameLbl;

    @FXML
    private Label AddProdInvLbl;

    @FXML
    private Label AddProdPriceLbl;

    @FXML
    private Label AddProdMaxLbl;

    @FXML
    private Label AddProdMinLbl;

    @FXML
    private TextField addProdIdTxt;

    @FXML
    private TextField addProdNameTxt;

    @FXML
    private TextField addProdInvTxt;

    @FXML
    private TextField AddProdPriceTxt;

    @FXML
    private TextField AddProdMaxTxt;

    @FXML
    private TextField addProdMinTxt;

    @FXML
    private TextField addProdSearchTxt;

    @FXML
    private TableView<Part> addProdTblView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> invCol;

    @FXML
    private TableColumn<Part, Double> priceCostCol;

    @FXML
    private Button addPartBtn;

    @FXML
    private TableView<Part> addProdAssocTblView;

    @FXML
    private TableColumn<Part, Integer> assocPartIdCol;

    @FXML
    private TableColumn<Part, String> assocPartNameCol;

    @FXML
    private TableColumn<Part, Integer> assocInvCol;

    @FXML
    private TableColumn<Part, Double> assocPriceCostCol;

    @FXML
    private Button removeAssocPartBtn;

    @FXML
    private Button savePartBtn;

    @FXML
    private Button cancelPartBtn;

    @FXML
    private Label addProdLbl;

    /** This method is called when the window is first loaded. Within, it sets the id TextField with the current counter. A new object is created that can then be called upon
     * later.
     @param url is the location where this class is found.
     @param rb helps facilitate actions with objects.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int id = idCounter;
        Product newProduct = new Product(idCounter, "", 0.0, 0, 0, 0);
        Inventory.addProduct(newProduct);
        addProdIdTxt.setText(String.valueOf(id));
        addProdTblView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
//
        addProdAssocTblView.setItems(newProduct.getAllAssociatedParts());
//
        assocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPriceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    ;
/** This handles the Add Part button. It moves a part into the associated table.
 @param event this happens when the user clicks the "Add" button while selecting a part in the All Parts table. */
    @FXML
    void onActAddPart(ActionEvent event) throws IOException {


        Part selected = addProdTblView.getSelectionModel().getSelectedItem();
        int id = Integer.valueOf(addProdIdTxt.getText());
        for (Product product : Inventory.getAllProducts()) {
            if (Integer.valueOf(product.getId()) == id) {
                product.addAssociatedPart(selected);
            }
        }
    }

    /** This handles the cancel product event. An Alert is created.
     @param event this happens when the user clicks the cancel button. */
    @FXML
    void onActCancelProd(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this? All data will be lost!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.prodDelete(Integer.valueOf(addProdIdTxt.getText()));
            sceneManage("/view/ProductsAndPartsMain.fxml", event);
        }
    }
    /** This handles the remove assoc part button.
     * @param event when the user clicks the button, their current part is deleted fromt the associated parts list */
    @FXML
    void onActRemoveAssocPart(ActionEvent event) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setContentText("Are you sure you wish to remove this product? This action cannot be undone.");
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            Part selected = addProdAssocTblView.getSelectionModel().getSelectedItem();

            for (Part part : addProdAssocTblView.getItems()) {

                if (part.getId() == selected.getId()) {
                    addProdAssocTblView.getItems().remove(selected);


                }

            }

        } else return;

        }


    /** This handles the save button.
     * @param event when the user clicks the save button, a new product is created. */
    @FXML
    void onActSaveProd(ActionEvent event) throws IOException {
        try {

            if (Integer.valueOf(AddProdMaxTxt.getText()) < Integer.valueOf(addProdMinTxt.getText())
                    || Integer.valueOf(addProdInvTxt.getText()) < Integer.valueOf(addProdMinTxt.getText())
                    || Integer.valueOf(addProdInvTxt.getText()) > Integer.valueOf(AddProdMaxTxt.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Your inventory levels are incorrect.");
                alert.setContentText("Please ensure your inventory levels are appropriate between the max, min, and inventory fields.");
                alert.showAndWait();
            } else {
                int id = idCounter - 1;
                String name = addProdNameTxt.getText();
                int stock = Integer.parseInt(addProdInvTxt.getText());
                double price = Double.parseDouble(AddProdPriceTxt.getText());
                int min = Integer.parseInt(addProdMinTxt.getText());
                int max = Integer.parseInt(AddProdMaxTxt.getText());

                Product newProduct = new Product(id, name, price, stock, min, max);
                for (Product product : Inventory.getAllProducts()) {
                    if (Integer.valueOf(addProdIdTxt.getText()) == Integer.valueOf(product.getId())) {
                        for (Part part : product.getAllAssociatedParts()) {
                            newProduct.addAssociatedPart(part);
                        }
                        Inventory.prodUpdate(Integer.valueOf(addProdIdTxt.getText()), newProduct);


                    }
                }
                idCounter++;
                sceneManage("/view/ProductsAndPartsMain.fxml", event);
            }



        } catch (NumberFormatException ex) {
            System.out.println("Exception " + ex);
        }


    }



    /** This handles the search text field above the all parts table.
     @param event when a user types, their input is used to search the table. The table filters based on their input. If only one option remains,
      it will be highlighted. */
            @FXML
    void onActSearchTxt(KeyEvent event) {

        try

    {
        addProdTblView.setItems(partFilter(addProdSearchTxt.getText()));
        if (Inventory.getFilteredParts().size() == 1) {
            addProdTblView.getSelectionModel().select(0);
        }


    } catch(
    NullPointerException ex)

    {
        System.out.println("Exception: " + ex);
    }

}
    /** This method assists with the search method. It finds which part the user is searching for.
     @param name is the user's input.*/
    public static ObservableList<Part> partFilter(String name) {

        if (!(Inventory.getFilteredParts().isEmpty())) {
            Inventory.getFilteredParts().clear();
        }

        for (Part part : Inventory.getAllParts()) {
            if (part.getName().contains(name) || String.valueOf(part.getId()).contains(name)) {
                Inventory.getFilteredParts().add(part);
            }

        }
        if (Inventory.getFilteredParts().isEmpty()) {
            Alert noResult = new Alert(Alert.AlertType.ERROR);
            noResult.setContentText("No Results Found");
            noResult.setTitle("Error Message");
            noResult.showAndWait();


            return Inventory.getAllParts();
        } else {
            return Inventory.getFilteredParts();
        }
    }
}


