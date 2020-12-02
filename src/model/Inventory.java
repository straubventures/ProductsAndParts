package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.Alert;
import model.InHouse;
/** This class creates list objects for Part and Product objects. */
public class Inventory {
    private static ObservableList <Part> allParts = FXCollections.observableArrayList();
    private static ObservableList <Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList <Product> filteredProducts = FXCollections.observableArrayList();
    private static ObservableList <Part> filteredParts = FXCollections.observableArrayList();

    /** This method adds a part to the allParts list.
     * @param part is the part which will be added to the allParts list. */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /** This method adds a product to the allProducts list.
     * @param product is the product which will be added to the allProducts list. */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /** This method finds a part from the allParts list.
     @param partID is the id of the part which the user wishes to lookup. */
    public static Part lookUpPart(int partID) {
        Part desiredPart = null;
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == partID) {
                desiredPart = part;
            }
        } return desiredPart;
    }
    /** This method finds a product from the allProducts list.
     @param prodID is the id of the product which the user wishes to lookup. */
    public static Product lookUpProduct(int prodID) {
        Product desiredProd = null;
        for (Product prod : Inventory.getAllProducts()) {
            if (prod.getId() == prodID) {
                desiredProd = prod;
            }
        } return desiredProd;
    }


    /** This feature gave me numerous logical errors. Eventually, I was able to figure out that '!' needs to be in front of parentheses,
     * I needed to develop my Inventory class methods, and I needed to learn the for loop format for Java within class lists. */
    /** This method is used to filter through the products while searching them.
     @param name is the user input. */
    public static ObservableList<Product> lookupProduct(String name) {

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
    public static ObservableList<Part> lookupPart(String name) {

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

    /** This method updates a product on the allProducts list.
     @param id is the id of the product which the user would like the update.
     @param product is the product the user will use to replace the old object.
     @return the boolean value of if the product was found, and therefore updated. */
    public static void prodUpdate(int id, Product product) {
        int index = -1;

        for (Product product1 : Inventory.getAllProducts()) {
            index++;

            if (product1.getId() == id) {
                Inventory.getAllProducts().set(index, product);


            }
        }

    }


    /** This method deletes a product on the allProducts list.
     * @param id is the id of the product which the user wishes to delete.
     * @return the removal of the designated part. */
    public static boolean prodDelete(int id) {


        for (Product part : Inventory.getAllProducts()) {


            if (part.getId() == id) {
                Inventory.getAllProducts().remove(part);
                return true;

            }

        } return false;

    }

    /** This method updates a part to the allParts list.
     @param id is the id of the part which the user would like to update.
     @param part is the part which will replace the old part.
     @return the boolean value of whether the part was found and therefore updated. */
    public static void partUpdate(int id, Part part) {
        int index = -1;

        for (Part cog: Inventory.getAllParts()) {
            index++;

            if (cog.getId() == id) {
                Inventory.getAllParts().set(index, part);

            }
        }
    }
    /** This method deletes a part to the allParts list.
     * @param id is the is of the part which will be deleted.
     * @return the boolean value of updated or not. */
    public static boolean partDelete(int id) {


        for (Part part : Inventory.getAllParts()) {


            if (part.getId() == id) {
                Inventory.getAllParts().remove(part);
                return true;


            }

        }
        return false;
    }

    /** This method lists all products on the allProducts list.
     * @return all products. */

    public static ObservableList <Product> getAllProducts() {
        return allProducts;
    }


    /** This method lists all the parts on the allParts list.
     * @return all Parts. */

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /** This method lists all the products on the filtered product list.
     * @return all filtered products. */
    public static ObservableList <Product> getFilteredProducts() {
        return filteredProducts;
    }
    /** This method lists all the parts on the filtered part list.
     * @return all filtered parts. */
    public static ObservableList <Part> getFilteredParts() { return filteredParts;}


    }



