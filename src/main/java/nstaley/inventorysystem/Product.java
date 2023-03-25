package nstaley.inventorysystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates the product objects and includes an associated parts list of the parts included in the product.
 * @author Nicholas Staley
 */
public class Product {
    /**
     * Holds an observable list of the parts associated with the product.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * Holds the product id.
     */
    private int id;
    /**
     * Holds the product name.
     */
    private String name;
    /**
     * Holds the product price.
     */
    private double price;
    /**
     * Holds the product inventory.
     */
    private int stock;
    /**
     * Holds the minimum number of the product inventory.
     */
    private int min;
    /**
     * Holds the maximum number of the product inventory.
     */
    private int max;

    /**
     * The constructor that initializes the Products.
     * @param id id of the product
     * @param name name of the product
     * @param price price of the product
     * @param stock inventory of the product
     * @param min minimum inventory
     * @param max maximum inventory
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Method that sets the product id.
     * @param id id of the product.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method that sets the name of the product.
     * @param name name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method that sets the price of the product.
     * @param price price of product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Method that sets the inventory of the product.
     * @param stock inventory of product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Method that sets the minimum inventory of the product.
     * @param min minimum inventory of product
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Method that sets the maximum inventory of the product.
     * @param max max inventory of product
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Method that gets the id of the product.
     * @return Returns the int value representing the product id.
     */
    public int getId() {
        return id;
    }

    /**
     * Method that gets the Name of the product.
     * @return Returns a String value representing the name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Method that gets the price of the product.
     * @return Returns a double value representing the price of the product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Method that gets the inventory of the product.
     * @return Returns an int value representing the inventory of the product.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Method that gets the minimum inventory of the product.
     * @return Returns an int value representing the minimum inventory of the product.
     */
    public int getMin() {
        return min;
    }

    /**
     * Method that gets the maximum inventory of the product.
     * @return Returns an int value representing the maximum inventory of the product.
     */
    public int getMax() {
        return max;
    }

    /**
     * Method to add an associated part to the associated parts list of the product.
     * @param part the part to be added to the associated parts list
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);

    }

    /**
     * Method to delete a part from the associated parts list and returns a true or false for if it was able to.
     * @param selectedAssociatedPart part to be removed from the associated parts list
     * @return Returns true if the part was removed from the products associated parts list or returns false if it wasn't removed.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        boolean wasDeleted = false;
        wasDeleted = associatedParts.remove(selectedAssociatedPart);

        return wasDeleted;
    }

    /**
     * Method to retrieve the products associated parts list.
     * @return Returns an ObservableList of parts that represents the products associated parts list.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }

    /**
     * Method to get the size of a product's associated parts list.
     * @return Returns an int value representing the size of the products associated parts list.
     */
    public int getAssociatedPartsListSize() {
        return associatedParts.size();
    }

    /**
     * Method to get a part from the products associated parts list at a certain index.
     * @param index index of the part to be retrieved from the list
     * @return Returns a Part object that was at the index of the associated parts list.
     */
    public Part getAssociatedPartAt(int index) {
        return associatedParts.get(index);
    }
}

