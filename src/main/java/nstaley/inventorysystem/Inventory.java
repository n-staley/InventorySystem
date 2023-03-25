package nstaley.inventorysystem;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class holds the list of parts and products and the methods to manipulate those lists.
 * @author Nicholas Staley
 */
public class Inventory {
    /**
     * Holds an observable list of the parts that have been created.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * Holds an observable list of all the products that have been created.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /**
     * The id for the next part to be added.
     */
    private static int partIdGen = 4;
    /**
     * The id for the next product to be added.
     */
    private static int productIdGen = 3;

    /**
     * Method to add a new part to the observable parts list.
     * @param newPart part to be added to the list
     */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    /**
     * Method to add a new product to observable products list.
     * @param newProduct product to be added to the list
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Method to look up a part from the observable parts list by the partID.
     * @param partId part id to be used in the search
     * @return Returns the part if it is found in the observable parts list or returns null if it wasn't in the list.
     */
    public static Part lookupPart(int partId) {
        for (Part part : getAllParts()) {
            if (partId == part.getId()) {
                return part;
            }
        }

        return null;
    }

    /**
     * Method to look up a product from the observable products list by the productID.
     * @param productId product id to be used in the search
     * @return Returns the product if it is found in the observable products list or returns null if it wasn't in the list.
     */
    public static Product lookupProduct(int productId) {
        for (Product product : getAllProducts()) {
            if (productId == product.getId()) {
                return product;
            }
        }
        return null;
    }

    /**
     * Method to get an observable list of parts returned with the parts that match the partial name search.
     * @param name name to be used in the partial name search
     * @return Returns an observable list of parts that can either contain parts if they were found during the search,
     * or can be empty if no matching parts were found in the search.
     */
    public static ObservableList<Part> lookupPart(String name) {
        ObservableList<Part> searchedParts = FXCollections.observableArrayList();
        for (Part part : getAllParts()) {
            String listName = part.getName().toLowerCase();
            String searchName = name.toLowerCase();
            if (listName.contains(searchName)) {
                searchedParts.add(part);
            }
        }
        return searchedParts;
    }

    /**
     * Method to get an observable list of products from a search that utilizes a partial name match.
     * @param name name to be used in the search
     * @return Returns an observable list of products that can either contain products if they were found during the search,
     * or can be empty if no matching parts were found in the search.
     */
    public static ObservableList<Product> lookupProduct(String name) {
        ObservableList<Product> searchedProducts = FXCollections.observableArrayList();
        for (Product product : getAllProducts()) {
            String listName = product.getName().toLowerCase();
            String searchName = name.toLowerCase();
            if (listName.contains(searchName)) {
                searchedProducts.add(product);
            }
        }
        return searchedProducts;
    }

    /**
     * Method to replace the part stored in the observable parts list at the index provided with the part provided.
     * @param index index in the observable parts list of the part to be replaced
     * @param selectedPart part to be used as the replacement part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);

    }

    /**
     * Method to replace the product stored in the observable products list at the index provided with the product provided.
     * @param index index in the observable products list of the product to be replaced
     * @param newProduct product to be used as the replacement product
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);

    }

    /**
     * Method to delete a selected part from the observable parts list.
     * @param selectedPart part to be deleted
     * @return Returns true if the part was deleted from the list, and false if it has not been deleted.
     */
    public static boolean deletePart(Part selectedPart) {
        boolean partRemoved = false;
        partRemoved = allParts.remove(selectedPart);
        return partRemoved;
    }

    /**
     * Method to delete a selected product from the observable products list, but it can only be deleted if there are no
     * associated parts.
     * @param selectedProduct product to be deleted
     * @return Returns true if the product was deleted, and returns false if the product had an associated part and could
     * not be deleted.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        boolean productRemoved = false;
        if (selectedProduct.getAssociatedPartsListSize() == 0) {
            productRemoved = allProducts.remove(selectedProduct);
        }

        return productRemoved;
    }

    /**
     * Method to get the observable parts list.
     * @return Returns the observable parts list.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Method to get the observable products list.
     * @return Returns the observable products list.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Method to get the id number for the next part to be added.
     * @return Returns an int value representing the next part's id number.
     */
    public static int getPartIdGen() {
        return partIdGen;
    }

    /**
     * Method to increase the part id generator by one after a new part has been added.
     */
    public static void increasePartIdGen() {
        partIdGen +=1;
    }

    /**
     * Method to get the id number for the next product to be added.
     * @return Returns an int value representing the id number for the next part to be added.
     */
    public static int getProductIdGen() {
        return productIdGen;
    }

    /**
     * Method to increase the product id generator by one after a new product has been added.
     */
    public static void increaseProductIdGen() {
        productIdGen += 1;
    }



}
