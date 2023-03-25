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
 * This class is the controller for the modify product screen. It contains two table views, one of the parts list, and the
 * other of the parts associated with the product being modified. There is a button to add parts from the parts list to the
 * associated parts list, and a button to remove parts from the associated parts list. There are text field to take all
 * the product's data, and also a cancel button and a save button.
 * @author Nicholas Staley
 */
public class ModifyProductFormController implements Initializable {
    /**
     * Product to be modified.
     */
    private static Product modifyProduct = null;
    /**
     * Product list index of the product to be modified.
     */
    private static int modifyIndex;
    /**
     * Product's associated parts list.
     */
    private ObservableList<Part> addPartsList = FXCollections.observableArrayList();
    /**
     * Button to cancel modifying a product.
     */
    public Button cancelModifyProduct;
    /**
     * Anchor pane that holds the table, labels, text fields, and buttons.
     */
    public AnchorPane modifyProductPane;
    /**
     * Table view of the parts table.
     */
    public TableView<Part> partsTable;
    /**
     * Table column of the part's id.
     */
    public TableColumn<Part, Integer> tablePartId;
    /**
     * Table column of the part's name.
     */
    public TableColumn<Part, String> tablePartName;
    /**
     * Table column of the part's inventory.
     */
    public TableColumn<Part, Integer> tablePartInventory;
    /**
     * Table column of the part's price.
     */
    public TableColumn<Part, Double> tablePartPrice;
    /**
     * Table view of the product's associated parts.
     */
    public TableView<Part> addedPart;
    /**
     * Table column of the part's id.
     */
    public TableColumn<Part, Integer> addedPartId;
    /**
     * Table column of the part's name.
     */
    public TableColumn<Part, String> addedPartName;
    /**
     * Table column of the part's inventory.
     */
    public TableColumn<Part, Integer> addedPartInventory;
    /**
     * Table column of the part's price.
     */
    public TableColumn<Part, Double> addedPartPrice;
    /**
     * Text field for the part search.
     */
    public TextField partSearch;
    /**
     * Button to add part to the product's associated parts list.
     */
    public Button addPartButton;
    /**
     * Button to remove a part from the product's associated parts list.
     */
    public Button removePartButton;
    /**
     * Button to save the modified product.
     */
    public Button saveProduct;
    /**
     * Text field for the product's id.
     */
    public TextField idTxt;
    /**
     * Text field for the product's inventory.
     */
    public TextField inventoryTxt;
    /**
     * Text field for the product's name.
     */
    public TextField nameTxt;
    /**
     * Text field for the product's price.
     */
    public TextField priceTxt;
    /**
     * Text field for the product's max inventory.
     */
    public TextField maxTxt;
    /**
     * Text field for the product's min inventory.
     */
    public TextField minTxt;

    /**
     * Method to initialize the screen, populates the parts table and the associated parts table. Also populates the
     * text fields with the modified product's data.
     * @param url file location
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts());
        tablePartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tablePartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tablePartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        if (modifyProduct.getAssociatedPartsListSize() != 0) {
            for (int i = 0; i < modifyProduct.getAssociatedPartsListSize(); ++i) {
                addPartsList.add(modifyProduct.getAssociatedPartAt(i));
            }
        }

        addedPart.setItems(addPartsList);
        addedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        idTxt.setText(String.valueOf(modifyProduct.getId()));
        inventoryTxt.setText(String.valueOf(modifyProduct.getStock()));
        nameTxt.setText(modifyProduct.getName());
        priceTxt.setText(String.valueOf(modifyProduct.getPrice()));
        maxTxt.setText(String.valueOf(modifyProduct.getMax()));
        minTxt.setText(String.valueOf(modifyProduct.getMin()));


    }

    /**
     * Method to cancel modify product and return to the main screen.
     * @param actionEvent Cancel button clicked.
     * @throws IOException throws IOException if file does not load.
     */
    public void returnToMain(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear the text fields and not update \nthe product information. Would you like to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            modifyProduct = null;
            addPartsList.clear();

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/nstaley/inventorysystem/MainForm.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1123, 542);
                stage.setTitle("Inventory Management System");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Method to search the parts list for a part by id number or a partial name search.
     * @param actionEvent text entered into the search text field and enter hit
     */
    public void searchParts(ActionEvent actionEvent) {
        ObservableList<Part> searchedParts = FXCollections.observableArrayList();
        searchedParts = Inventory.lookupPart(partSearch.getText());
        partsTable.setItems(searchedParts);
        Part searchPart = null;

        if (searchedParts.size() == 0) {
            try {
                searchPart = Inventory.lookupPart(Integer.parseInt(partSearch.getText()));
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
     * Method to add a part to the product's associated parts list.
     * @param actionEvent Add button clicked
     */
    public void addPartToProduct(ActionEvent actionEvent) {
        if (partsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Selected Part");
            alert.setContentText("Must select a part fom the table to add it to product.");
            alert.showAndWait();
            return;
        }

        addPartsList.add(partsTable.getSelectionModel().getSelectedItem());
    }

    /**
     * Method to remove an associated part from a product.
     * @param actionEvent Remove Associated Part button clicked
     */
    public void removePartFromProduct(ActionEvent actionEvent) {
        if (addedPart.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Selected Part");
            alert.setContentText("Must select a part fom the table to remove it from product.");
            alert.showAndWait();
            return;
        }
        boolean wasRemoved = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove the part from the Product?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            wasRemoved = addPartsList.remove(addedPart.getSelectionModel().getSelectedItem());

            if (!wasRemoved) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setHeaderText("Part Not Removed");
                alert2.setContentText("The part was not removed from product.");
                alert2.showAndWait();
            }
        }
    }

    /**
     * Method to update a product and return to the main screen.
     * @param actionEvent Save button clicked
     * @throws IOException throws IOException if file does not load
     */
    public void saveProduct(ActionEvent actionEvent) throws IOException {
        boolean wasError = false;
        String nameErrorMessage = "Must enter a product name into the Name text field.\n";
        String nameNumberErrorMessage = "Name must not begin with a number.\n";
        String invErrorMessage = "Must enter an integer into the Inventory text field.\n";
        String priceErrorMessage = "Must enter a double into the Price/Cost text Field.\n";
        String maxErrorMessage = "Must enter an integer into the Max text field.\n";
        String minErrorMessage = "Must enter an integer into the Min text field.\n";
        String maxLessThanMinMessage = "Max must be greater than the Min.\n";
        String inventoryMaxMin = "Inventory must be between the Min and Max values.\n";

        String alertMessage = "";

        if (nameTxt.getText().isEmpty()) {
            wasError = true;
            alertMessage = alertMessage.concat(nameErrorMessage);
        }
        else if (Character.isDigit(nameTxt.getText().charAt(0))) {
            wasError = true;
            alertMessage = alertMessage.concat(nameNumberErrorMessage);

        }
        try {
            Integer.parseInt(inventoryTxt.getText());
        }
        catch (NumberFormatException e) {
            wasError = true;
            alertMessage = alertMessage.concat(invErrorMessage);
        }
        try {
            Double.parseDouble(priceTxt.getText());
        }
        catch (NumberFormatException e) {
            wasError = true;
            alertMessage = alertMessage.concat(priceErrorMessage);
        }
        try {
            Integer.parseInt(maxTxt.getText());
        }
        catch (NumberFormatException e) {
            wasError = true;
            alertMessage = alertMessage.concat(maxErrorMessage);
        }
        try {
            Integer.parseInt(minTxt.getText());
        }
        catch (NumberFormatException e) {
            wasError = true;
            alertMessage = alertMessage.concat(minErrorMessage);
        }

        if (!wasError) {
            if (Integer.parseInt(maxTxt.getText()) < Integer.parseInt(minTxt.getText())) {
                wasError = true;
                alertMessage = alertMessage.concat(maxLessThanMinMessage);
            }

        }
        if (!wasError) {
            if ((Integer.parseInt(inventoryTxt.getText()) > Integer.parseInt(maxTxt.getText())) || (Integer.parseInt(inventoryTxt.getText()) < Integer.parseInt(minTxt.getText()))) {
                wasError = true;
                alertMessage = alertMessage.concat(inventoryMaxMin);
            }
        }

        if (wasError) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(alertMessage);
            alert.showAndWait();
            wasError = false;
            alertMessage = "";
            return;
        }
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "This will save your updated product, do you want to continue?");

        Optional<ButtonType> result = alertConfirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int id = modifyProduct.getId();
            String name = nameTxt.getText();
            int stock = Integer.parseInt(inventoryTxt.getText());
            double price = Double.parseDouble(priceTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());

            Product updateProduct = new Product(id, name, price, stock, min, max);
            if (addPartsList.size() != 0) {
                for (int i = 0; i < addPartsList.size(); ++i) {
                    updateProduct.addAssociatedPart(addPartsList.get(i));
                }
            }

            Inventory.updateProduct(modifyIndex, updateProduct);

            addPartsList.clear();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/nstaley/inventorysystem/MainForm.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1123, 542);
                stage.setTitle("Inventory Management System");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Method to set the product that will be modified.
     * @param product product to be modified
     */
    public static void setModifyProduct(Product product) {
        modifyProduct = product;
    }

    /**
     * Method to set the index from the products list of the part to be modified.
     * @param index index to be set
     */
    public static void setModifyIndex(int index) {
        modifyIndex = index;
    }
}
