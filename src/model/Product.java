package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class creates Product objects. */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * constructor for Product.
     *
     * @param id creates product with just an id.
     */
    public Product(int id) {
        this.id = id;
    }

    ;

    /**
     * Constructor for product.
     *
     * @param id    is the id of the new object.
     * @param name  is the name.
     * @param price is the price.
     * @param stock is the inventory level.
     * @param min   is the min amount of inventory allowed.
     * @param max   is the max amount of inventory allowed.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    ;

    /**
     * @return the id.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id Set the id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name.
     *
     * @param name is set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @returns the price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price is the price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock is the set stock.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min.
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min is the set min.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return max.
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max is the set max.
     */
    public void setMax(int max) {
        this.max = max;
    }


    /**
     * @param part is the associated part that will be deleted.
     */
    public void delAssocPart(Part part) {
        associatedParts.remove(part);
    }

    /**
     * @param part is the associated part that will be added.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }


    /**
     * @return all associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}
