package nstaley.inventorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is the controller for the add part screen in the inventory management system. It contains an automatically
 * generated id for the part, and text fields that the user enters values into for the name, price, inventory, min,
 * and max values. It also has a cancel button which clears the text fields and returns the user to the main screen.
 * Finally, it contains a save button which will add the part that the user created to the parts list.
 * @author Nicholas Staley
 */
public class AddPartFormController implements Initializable {
    /**
     * Radio button to select the part as being an in house part.
     */
    public RadioButton inHousePartButton;
    /**
     * Toggle group making the inHousePartButton and the outsourcedPartButton connected so only one may be selected.
     */
    public ToggleGroup partToggleGroup;
    /**
     * Radio button to select the part as being an outsourced part.
     */
    public RadioButton outsourcedPartButton;
    /**
     * Button used to cancel adding a new part.
     */
    public Button partCancel;
    /**
     * Label that changes between Machine ID if in house radio button is selected, and Company Name if outsourced
     * radio button is selected.
     */
    public Label inOrOut;
    /**
     * Text field to take the user input of either Machine ID, or Company Name.
     */
    public TextField partInOrOut;
    /**
     * Text field to take the user input of max part inventory.
     */
    public TextField partMax;
    /**
     * Text field to take the user input of min part inventory.
     */
    public TextField partMin;
    /**
     * Text field to take the user input of part price.
     */
    public TextField partPrice;
    /**
     * Text field to take the user input of part inventory.
     */
    public TextField partInventory;
    /**
     * Text field to take the user input of part name.
     */
    public TextField partName;
    /**
     * Button to save the new part.
     */
    public Button save;

    /**
     * Method to initialize the screen.
     * @param url file location
     * @param resourceBundle Resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    /**
     * Method to change the form to in house part when the in house radio button is selected.
     * @param actionEvent in house radio button selected
     */
    public void inHouseSelected(ActionEvent actionEvent) {
        inOrOut.setText("Machine ID");
    }

    /**
     * Method to change the form to outsourced when the outsourced radio button is selected
     * @param actionEvent outsourced radio button selected
     */
    public void outsourcedSelected(ActionEvent actionEvent) {
        inOrOut.setText("Company Name");
    }

    /**
     * Method to return to the main screen without saving a part.
     * @param actionEvent Cancel button is clicked
     * @throws IOException can throw an IOException if the file location is not found.
     */
    public void partReturnToMain(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear the text fields, do you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK){

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
     * Method to add a new part to the parts list and return to the main screen. Contains text field error handling to
     * prevent the program from crashing if inappropriate data is entered. Fixed min getting saved to max and max
     * getting saved to min. The parameters  were in the wrong order for the creation of the new InHouse and Outsourced
     * Parts.
     * @param actionEvent Save button is clicked
     * @throws IOException can throw an IOException if the file location is not found.
     */
    public void addNewPart(ActionEvent actionEvent) throws IOException {
        boolean wasError = false;
        String nameErrorMessage = "Must enter a part name into the Name text field.\n";
        String nameNumberErrorMessage = "Name must not begin with a number.\n";
        String invErrorMessage = "Must enter an integer into the Inventory text field.\n";
        String priceErrorMessage = "Must enter a double into the Price/Cost text Field.\n";
        String maxErrorMessage = "Must enter an integer into the Max text field.\n";
        String minErrorMessage = "Must enter an integer into the Min text field.\n";
        String inHouseMachineMessage = "Must enter an integer into the Machine ID text field.\n";
        String outCompanyNameMessage = "Must enter a name into the Company Name text field. \n";
        String maxLessThanMinMessage = "Max must be greater than the Min.\n";
        String inventoryMaxMin = "Inventory must be between the Min and Max values.\n";

        String alertMessage = "";

        if (partName.getText().isEmpty()) {
            wasError = true;
            alertMessage = alertMessage.concat(nameErrorMessage);
        }
        else if (Character.isDigit(partName.getText().charAt(0))) {
            wasError = true;
            alertMessage = alertMessage.concat(nameNumberErrorMessage);

        }
        try {
            Integer.parseInt(partInventory.getText());
        }
        catch (NumberFormatException e) {
            wasError = true;
            alertMessage = alertMessage.concat(invErrorMessage);
        }
        try {
            Double.parseDouble(partPrice.getText());
        }
        catch (NumberFormatException e) {
            wasError = true;
            alertMessage = alertMessage.concat(priceErrorMessage);
        }
        try {
            Integer.parseInt(partMax.getText());
        }
        catch (NumberFormatException e) {
            wasError = true;
            alertMessage = alertMessage.concat(maxErrorMessage);
        }
        try {
            Integer.parseInt(partMin.getText());
        }
        catch (NumberFormatException e) {
            wasError = true;
            alertMessage = alertMessage.concat(minErrorMessage);
        }
        if (inHousePartButton.isSelected()) {
            try {
                Integer.parseInt(partInOrOut.getText());
            }
            catch (NumberFormatException e) {
                wasError = true;
                alertMessage = alertMessage.concat(inHouseMachineMessage);
            }
        }
        if (outsourcedPartButton.isSelected()) {
            if (partInOrOut.getText().isEmpty()) {
                wasError = true;
                alertMessage = alertMessage.concat(outCompanyNameMessage);
            }
        }
        if (!wasError) {
            if (Integer.parseInt(partMax.getText()) < Integer.parseInt(partMin.getText())) {
                wasError = true;
                alertMessage = alertMessage.concat(maxLessThanMinMessage);
            }

        }
        if (!wasError) {
            if ((Integer.parseInt(partInventory.getText()) > Integer.parseInt(partMax.getText())) || (Integer.parseInt(partInventory.getText()) < Integer.parseInt(partMin.getText()))) {
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
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "This will save your part, do you want to continue?");

        Optional<ButtonType> result = alertConfirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            int id = Inventory.getPartIdGen();
            String name = partName.getText();
            int stock = Integer.parseInt(partInventory.getText());
            double price = Double.parseDouble(partPrice.getText());
            int max = Integer.parseInt(partMax.getText());
            int min = Integer.parseInt(partMin.getText());

            if (inHousePartButton.isSelected()) {
                int machineId = Integer.parseInt(partInOrOut.getText());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                Inventory.increasePartIdGen();
            } else {
                String companyName = partInOrOut.getText();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                Inventory.increasePartIdGen();
            }
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
