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
 * This class is the controller for the add product screen. It contains two table views, one of the parts list, and the
 * other of the parts associated with the product being added. There is a button to add parts from the parts list to the
 * associated parts list, and a button to remove parts from the associated parts list. There are text field to take all
 * the product's data, and also a cancel button and a save button.
 * @author Nicholas Staley
 */
public class AddProductFormController implements Initializable {
    /**
     * Observable list of the products associated parts.
     */
    private ObservableList<Part> addPartsList = FXCollections.observableArrayList();
    /**
     * Button to cancel the add product.
     */
    public Button cancelAddProduct;
    /**
     * Anchor pane that holds the table, labels, text fields, and buttons.
     */
    public AnchorPane addProductPane;
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
     * Table view of the product's associated parts list.
     */
    public TableView<Part> addedPart;
    /**
     * Table column of the associated part's id.
     */
    public TableColumn<Part, Integer> addedPartId;
    /**
     * Table column of the associated part's name.
     */
    public TableColumn<Part, String> addedPartName;
    /**
     * Table column of the associated part's inventory.
     */
    public TableColumn<Part, Integer> addedPartInventory;
    /**
     * Table column of the associated part's price.
     */
    public TableColumn<Part, Double> addedPartPrice;
    /**
     * Text field for the part search.
     */
    public TextField partSearch;
    /**
     * Button to add parts to the associated parts list.
     */
    public Button addPartButton;
    /**
     * Button to remove parts from the associated parts list.
     */
    public Button removePartButton;
    /**
     * Button to save the product.
     */
    public Button saveProduct;
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
     * Method to initialize the screen, populates the parts table and the associated parts table.
     * @param url File location
     * @param resourceBundle Resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts());
        tablePartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tablePartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tablePartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        addedPart.setItems(addPartsList);
        addedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Method to cancel adding the product and return to the main screen.
     * @param actionEvent Cancel button clicked
     * @throws IOException throws IOException if the file is not loaded.
     */
    public void returnToMain(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear the text fields, do you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

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
     * Method to save a new product and add it to the products list.
     * @param actionEvent Save button clicked
     * @throws IOException Throws IOException if file does not load.
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
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "This will save your product, do you want to continue?");

        Optional<ButtonType> result = alertConfirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int id = Inventory.getProductIdGen();
            String name = nameTxt.getText();
            int stock = Integer.parseInt(inventoryTxt.getText());
            double price = Double.parseDouble(priceTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());

            Product newProduct = new Product(id, name, price, stock, min, max);
            if (addPartsList.size() != 0) {
                for (int i = 0; i < addPartsList.size(); ++i) {
                    newProduct.addAssociatedPart(addPartsList.get(i));
                }
            }

            Inventory.addProduct(newProduct);
            Inventory.increaseProductIdGen();

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
}
