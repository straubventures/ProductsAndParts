package controller;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/** This class is the controller for Main file of an Inventory System app*/
public class ProductsAndPartsMainController implements Initializable {

    public static int idCounter = 10;

    Stage stage;
    Parent scene;

    /** This method changes windows when called.
     @param address is the location of the new window
     @param event is for the event handler that triggers the switch */
    public void sceneManage(String address, ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(address));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public TableView<Part> partTblView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvLvlCol;

    @FXML
    private TableColumn<Part, Double> partPriceCostCol;

    @FXML
    private TableView<Product> prodTblView;

    @FXML
    private TableColumn<Product, Integer> prodIdCol;

    @FXML
    private TableColumn<Product, String> prodNameCol;

    @FXML
    private TableColumn<Product, Integer> prodInvLvlCol;

    @FXML
    private TableColumn<Product, Double> prodPriceCostCol;

    @FXML
    private Button exitBtn;

    @FXML
    private Button addPartBtn;

    @FXML
    private Button modifyPartBtn;

    @FXML
    private Button delPartBtn;

    @FXML
    private TextField partSearchTxt;

    @FXML
    private Label partsTblLbl;

    @FXML
    private Label invManSysTitleLbl;

    @FXML
    private TextField prodSearchTxt;

    @FXML
    private Label prodTblLbl;

    @FXML
    private Button addProdBtn;

    @FXML
    private Button modifyProdBtn;

    @FXML
    private Button delProdBtn;


/** This handles the add part button.
 @param event navigates to the Add Part page. */
    @FXML
    void onActAddPart(ActionEvent event) throws IOException {
        sceneManage("/view/AddPart.fxml", event);
    }


    /** This handles the add product button.
     @param event navigates to the Add Product page. */
    @FXML
    void onActAddProd(ActionEvent event) throws IOException {
        sceneManage("/view/AddProduct.fxml", event);
    }
    /** This handles the delete part button.
     @param event deletes the selected part. */
    @FXML
    void onActDelPart(ActionEvent event) throws IOException {
        try {

            if ((partTblView.getSelectionModel().getSelectedItem() == null)){
                Alert alert = new Alert(Alert.AlertType.ERROR, "You need to select a part first");
                alert.showAndWait();
                return;
            } else {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setContentText("Are you sure you wish to remove this product? This action cannot be undone.");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Part selected = partTblView.getSelectionModel().getSelectedItem();
                int index = -1;

                for (Part part : Inventory.getAllParts()) {
                    index++;
                    if (part.getId() == selected.getId()) {
                        Inventory.getAllParts().remove(part);


                    }

                }}
            }
        } catch (NullPointerException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Exeption: " + ex);
            alert.showAndWait();
        }

    }

    /** This handles the delete product button.
     @param event navigates to the delete Product action. */
    @FXML
    void onActDelProd(ActionEvent event) throws IOException {
        try {
            if ((prodTblView.getSelectionModel().getSelectedItem() == null)){
                Alert alert = new Alert(Alert.AlertType.ERROR, "You need to select a product first");
                alert.showAndWait();
                return;
            } else {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setContentText("Are you sure you wish to remove this product? This action cannot be undone.");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {


                Product selected = prodTblView.getSelectionModel().getSelectedItem();

                if (selected.getAllAssociatedParts().size() > 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Cannot delete a product with associated parts.");
                    alert.setContentText("Please remove associated parts from this product to continue.");
                    alert.showAndWait();


                } else {

                    int index = -1;

                    for (Product part : Inventory.getAllProducts()) {
                        index++;
                        if (part.getId() == selected.getId()) {
                            Inventory.getAllProducts().remove(part);
                        }
                        }


                }
                } else return;
            } }catch(NullPointerException ex){

            Alert alert = new Alert(Alert.AlertType.ERROR, "Exeption: " + ex);
            alert.showAndWait();
            }

        }

    /** This handles the exit button.
     @param event navigates to exit the entire application.  */
    @FXML
    void onActExit(ActionEvent event) throws IOException {
        System.exit(0);
    }
    /** This handles the modify part button.
     @param event navigates to the modify part page. It also calls the sendPart() method in the Modify Part Controller. */
    @FXML
    void onActModifyPart(ActionEvent event) throws IOException {

        if ((partTblView.getSelectionModel().getSelectedItem() == null)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "You need to select a part first");
            alert.showAndWait();
            return;
        } else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                Logger.getLogger(ProductsAndPartsMainController.class.getName()).log(Level.SEVERE, null, ex);
            }

            ModifyPartController MPController = loader.getController();
            MPController.sendPart(partTblView.getSelectionModel().getSelectedItem());

            Parent scene = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(scene));
            stage.showAndWait();
        }
    }
    /** This handles the modify product button.
     @param event navigates to the modify Product page. This uses the sendProduct method, which is sourced from the ModifyProduct
      Controller. */
    @FXML
    void onActModifyProd(ActionEvent event) throws IOException {

        if ((prodTblView.getSelectionModel().getSelectedItem() == null)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "You need to select a product first");
            alert.showAndWait();
            return;
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            try {
                loader.load();
                System.out.println(loader);
            } catch (IOException ex) {
                Logger.getLogger(ProductsAndPartsMainController.class.getName()).log(Level.SEVERE, null, ex);
            }

            ModifyProductController MPController = loader.getController();
            MPController.sendProduct(prodTblView.getSelectionModel().getSelectedItem());

            Parent scene = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(scene));
            stage.showAndWait();

        }
    }
    /** This handles the search parts textfield.
     @param event handles the filtering of the results */
    @FXML
    void onActSearchParts(KeyEvent event) throws IOException {
        try {
            partTblView.setItems(partFilter(partSearchTxt.getText()));
            if (Inventory.getFilteredParts().size() == 1) {
                partTblView.getSelectionModel().select(0);
            }

        } catch (NullPointerException ex) {
            System.out.println("Exception: " + ex);
        }
    }
    /** This handles the search products textfield.
     @param event handles the filtering of the results */
    @FXML
    void onActSearchProducts(KeyEvent event) throws IOException {
        try {
            prodTblView.setItems(prodFilter(prodSearchTxt.getText()));
            if (Inventory.getFilteredProducts().size() == 1) {
                prodTblView.getSelectionModel().select(0);
            }

        }
        catch(NullPointerException ex)
        {
            System.out.println("Exception " + ex);
        }
    }


/** This feature gave me numerous logical errors. Eventually, I was able to figure out that '!' needs to be in front of parentheses,
 * I needed to develop my Inventory class methods, and I needed to learn the for loop format for Java within class lists. */
    /** This method is used to filter through the products while searching them.
     @param name is the user input. */
    public ObservableList<Product> prodFilter(String name) {

        if (!(Inventory.getFilteredProducts().isEmpty())) {
            Inventory.getFilteredProducts().clear();
        }

        for (Product part : Inventory.getAllProducts()) {
            if (part.getName().contains(name) || String.valueOf(part.getId()).contains(name)) {
                Inventory.getFilteredProducts().add(part);
            }

        }
        if (Inventory.getFilteredProducts().isEmpty()) {
            Alert noResult = new Alert(Alert.AlertType.ERROR);
            noResult.setContentText("No Results Found");
            noResult.setTitle("Error Message");
            noResult.showAndWait();
            return Inventory.getAllProducts();
        } {
                return Inventory.getFilteredProducts();
            }

    }

    /** This method is used to filter through the parts while searching them.
     @param name is the user input. */
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



    /** This method is called when the window is first loaded. Within, it sets the id TextField with the current counter. A new object is created that can then be called upon
     * later.
     @param url is the location where this class is found.
     @param rb helps facilitate actions with objects.
     */
    @Override
    public void initialize (URL url, ResourceBundle rb) {
        partTblView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodTblView.setItems(Inventory.getAllProducts());

        prodIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

}


