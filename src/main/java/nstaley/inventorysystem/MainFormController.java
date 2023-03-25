package nstaley.inventorysystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is the controller for the main form of the inventory management system. It has a table to view the parts
 * and products that are in the inventory system. It also has a search box for both the parts table and the products table.
 * There are buttons to add, modify, and delete both parts and products. There is also a button to exit the program.
 * @author Nicholas Staley
 */
public class MainFormController implements Initializable {

    /**
     * Button used to access the add part screen.
     */
    public Button addPart;
    /**
     * Button used to access the modify part screen.
     */
    public Button modifyPart;
    /**
     * Button used to access the add product screen.
     */
    public Button addProduct;
    /**
     * Button used to access the modify product screen.
     */
    public Button modifyProduct;
    /**
     * Button used to exit the program.
     */
    public Button exitMain;
    /**
     * Anchor pane used to hold all the tables, text fields, and buttons.
     */
    public AnchorPane mainFormPane;
    /**
     * Table view to hold the products list.
     */
    public TableView<Product> productsTable;
    /**
     * Table column to hold the product's id number.
     */
    public TableColumn<Product, Integer > tableProductId;
    /**
     * Table column to hold the product's name.
     */
    public TableColumn<Product, String> tableProductName;
    /**
     * Table column to hold the product's inventory.
     */
    public TableColumn<Product, Integer> tableProductInventory;
    /**
     * Table column to hold the product's price.
     */
    public TableColumn<Product, Double> tableProductPrice;
    /**
     * Table view to hold the parts list.
     */
    public TableView<Part> partsTable;
    /**
     * Table column to hold the part's id number.
     */
    public TableColumn<Part, Integer> tablePartId;
    /**
     * Table column to hold the part's name.
     */
    public TableColumn<Part, String> tablePartName;
    /**
     * Table column to hold the part's inventory.
     */
    public TableColumn<Part, Integer> tablePartInventory;
    /**
     * Table column to hold the part's price.
     */
    public TableColumn<Part, Double> tablePartPrice;
    /**
     * Text field for the part search, it is editable.
     */
    public TextField mainPartSearch;
    /**
     * Text field for the product search, it is editable.
     */
    public TextField mainProductSearch;
    /**
     * Button to delete a part.
     */
    public Button deletePartButton;
    /**
     * Button to delete a product.
     */
    public Button deleteProductButton;

    /**
     * Method called when main form is accessed, it sets up the parts and products table views.
     * @param url File location
     * @param resourceBundle Resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productsTable.setItems(Inventory.getAllProducts());
        tableProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableProductInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tableProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.setItems(Inventory.getAllParts());
        tablePartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tablePartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tablePartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));



    }

    /**
     * Method to take user to the add part screen, when the add button in the part pane is clicked.
     * @param actionEvent add button clicked in the part pane
     * @throws IOException can throw an IOException if the file location is not found.
     */
    public void toAddPart(ActionEvent actionEvent) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/nstaley/inventorysystem/AddPartForm.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 700, 700);
            stage.setTitle("Add Part");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Method to take the user to the modify part screen, when the modify part button is clicked and a part is selected
     * in the table view.
     * @param actionEvent modify button is clicked in part pane
     * @throws IOException can throw an IOException if the file location is not found.
     */
    public void toModifyPart(ActionEvent actionEvent) throws IOException {
        if (partsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Selected Part");
            alert.setContentText("Must select a part fom the table to modify it.");
            alert.showAndWait();
            return;
        }


        if (partsTable.getSelectionModel().getSelectedItem() instanceof InHouse) {
            InHouse partToModify = (InHouse) partsTable.getSelectionModel().getSelectedItem();
            ModifyPartFormController.setInHousePartModify(partToModify);
            partToModify = null;
        }
        else {
            Outsourced partToModify = (Outsourced) partsTable.getSelectionModel().getSelectedItem();
            ModifyPartFormController.setOutsourcedPartModify(partToModify);
            partToModify = null;
        }
        int indexToModify = partsTable.getSelectionModel().getSelectedIndex();
        ModifyPartFormController.setModifyIndex(indexToModify);



        try {
            Parent root = FXMLLoader.load(getClass().getResource("/nstaley/inventorysystem/ModifyPartForm.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 700, 700);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Method to take the user to the add product screen, when the add button is clicked in the product pane.
     * @param actionEvent add button is clicked in the product pane
     * @throws IOException can throw an IOException if the file location is not found.
     */
    public void toAddProduct(ActionEvent actionEvent) throws IOException {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/nstaley/inventorysystem/AddProductForm.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1200, 775);
            stage.setTitle("Add Product");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to take the user to the modify product screen, when the modify product button is clicked and a product is
     * selected in the table view.
     * @param actionEvent modify button clicked in the product pane
     * @throws IOException can throw an IOException if the file location is not found.
     */
    public void toModifyProduct(ActionEvent actionEvent) throws IOException {
        if (productsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Selected Product");
            alert.setContentText("Must select a product from the table to modify it.");
            alert.showAndWait();
            return;
        }

        Product productToModify = productsTable.getSelectionModel().getSelectedItem();
        int indexToModify = productsTable.getSelectionModel().getSelectedIndex();
        ModifyProductFormController.setModifyProduct(productToModify);
        ModifyProductFormController.setModifyIndex(indexToModify);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/nstaley/inventorysystem/ModifyProductForm.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1200, 775);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to exit the program when the exit button is clicked.
     * @param actionEvent exit button clicked
     */
    public void exitProgram(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will exit the program, and you will lose all data. \nDo you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) mainFormPane.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Method to search the parts list when an id number or a name has been entered into the search field and the enter
     * button has been hit. Will also return the whole list if the search field is cleared out and entered.
     * @param actionEvent search text box in part pane and enter key
     */
    public void searchParts(ActionEvent actionEvent) {
        ObservableList<Part> searchedParts = FXCollections.observableArrayList();
        searchedParts = Inventory.lookupPart(mainPartSearch.getText());
        partsTable.setItems(searchedParts);
        Part searchPart = null;

        if (searchedParts.size() == 0) {
            try {
                searchPart = Inventory.lookupPart(Integer.parseInt(mainPartSearch.getText()));
                partsTable.setItems(Inventory.getAllParts());
                partsTable.getSelectionModel().select(searchPart);
            }
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Part Not Found!");
                alert.setContentText("The part name you entered was not found.");
                alert.showAndWait();
            }
        }
        if (searchedParts.size() == 0 && searchPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Part Not Found!");
            alert.setContentText("The part ID you entered was not found.");
            alert.showAndWait();
        }
    }

    /**
     * Method to search the products list when an id number or a name has been entered into the search field and the
     * enter button has been hit. Will also return the whole list if the search field is cleared out and entered.
     * @param actionEvent search text box in product pane and enter key
     */
    public void searchProducts(ActionEvent actionEvent) {
        ObservableList<Product> searchedProducts = FXCollections.observableArrayList();
        searchedProducts = Inventory.lookupProduct(mainProductSearch.getText());
        productsTable.setItems(searchedProducts);
        Product searchProduct = null;

        if (searchedProducts.size() == 0) {
            try {
                searchProduct = Inventory.lookupProduct(Integer.parseInt(mainProductSearch.getText()));
                productsTable.setItems(Inventory.getAllProducts());
                productsTable.getSelectionModel().select(searchProduct);
            }
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Product Not Found!");
                alert.setContentText("The product name you entered was not found.");
                alert.showAndWait();
            }
        }
        if (searchedProducts.size() == 0 && searchProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Product Not Found!");
            alert.setContentText("The product ID you entered was not found.");
            alert.showAndWait();
        }
    }

    /**
     * Method to delete a part from the part table view. A part must be selected to be able to delete it.
     * @param actionEvent delete button clicked in parts pane
     */
    public void deletePart(ActionEvent actionEvent) {
        if (partsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Selected Part");
            alert.setContentText("Must select a part fom the table to delete it.");
            alert.showAndWait();
            return;
        }
        boolean wasDeleted = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            wasDeleted = Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());

            if (!wasDeleted) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setHeaderText("Part was not Deleted");
                alert2.setContentText("The part you selected was not deleted.");
                alert2.showAndWait();
            }
        }

    }

    /**
     * Method to delete a product from the product table view. A product must be selected and have no associated parts
     * to be able to delete the product.
     * @param actionEvent delete button clicked in product pane
     */
    public void deleteProduct(ActionEvent actionEvent) {
        if (productsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Selected Product");
            alert.setContentText("Must select a product fom the table to delete it.");
            alert.showAndWait();
            return;
        }

        boolean wasDeleted = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the product?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            wasDeleted = Inventory.deleteProduct(productsTable.getSelectionModel().getSelectedItem());

            if (!wasDeleted) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setHeaderText("Product Not Deleted");
                alert2.setContentText(productsTable.getSelectionModel().getSelectedItem().getName() + " has parts associated with it that must be removed before it can be deleted. This can be done in the modify products page.");
                alert2.showAndWait();
            }
        }
    }
}