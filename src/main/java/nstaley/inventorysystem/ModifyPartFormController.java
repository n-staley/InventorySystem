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
 * This Class is the controller for the modify part screen. When the screen is initialized the text fields are populated
 * with the data for the part that was selected to be modified. The user is able to change any of this data except the
 * part id number. The part then can either be saved, or the user can cancel modifying the part and return to the main screen.
 * @author Nicholas Staley
 */
public class ModifyPartFormController implements Initializable {
    /**
     * Radio button to select the part as being an in house part.
     */
    public RadioButton modifyInHousePartButton;
    /**
     * Toggle group making the inHousePartButton and the outsourcedPartButton connected so only one may be selected.
     */
    public ToggleGroup modifyPartToggleGroup;
    /**
     * Radio button to select the part as being an outsourced part.
     */
    public RadioButton modifyOutsourcedPartButton;
    /**
     * Button to cancel modifying a part.
     */
    public Button cancelModifyPart;
    /**
     * Label that changes between Machine ID if in house radio button is selected, and Company Name if outsourced
     * radio button is selected.
     */
    public Label inOrOut;
    /**
     * In house part selected by the user to be modified.
     */
    private static InHouse inHousePartModify = null;
    /**
     * Outsourced part selected by the user to be modified.
     */
    private static Outsourced outsourcedPartModify = null;
    /**
     * Part list index for the part to be modified.
     */
    private static int modifyIndex = -1;
    /**
     * ID of part to be modified.
     */
    private int modifyId;
    /**
     * Text field for the part's Machine ID, or Company name.
     */
    public TextField inOrOutTxt;
    /**
     * Text field for the part's max inventory.
     */
    public TextField maxTxt;
    /**
     * Text field for the part's min inventory.
     */
    public TextField minTxt;
    /**
     * Text field for the part's price.
     */
    public TextField priceTxt;
    /**
     * Text field for the part's inventory.
     */
    public TextField invTxt;
    /**
     * Text field for the part's name.
     */
    public TextField nameTxt;
    /**
     * Text field for the part's id, this can not be edited.
     */
    public TextField idTextField;
    /**
     * Button to save the modified part.
     */
    public Button saveModify;


    /**
     * Method to initialize the modify part screen. This loads all the part data for the part the user wants to modify
     * into the text fields, and sets the radio button.
     * @param url File location
     * @param resourceBundle Resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (inHousePartModify != null) {
            modifyInHousePartButton.setSelected(true);
            inOrOut.setText("Machine ID");
            modifyId = inHousePartModify.getId();
            idTextField.setText(String.valueOf(inHousePartModify.getId()));
            nameTxt.setText(inHousePartModify.getName());
            invTxt.setText(String.valueOf(inHousePartModify.getStock()));
            maxTxt.setText(String.valueOf(inHousePartModify.getMax()));
            minTxt.setText(String.valueOf(inHousePartModify.getMin()));
            priceTxt.setText(String.valueOf(inHousePartModify.getPrice()));
            inOrOutTxt.setText(String.valueOf(inHousePartModify.getMachineID()));

        }
        else {
            modifyOutsourcedPartButton.setSelected(true);
            inOrOut.setText("Company Name");
            modifyId = outsourcedPartModify.getId();
            idTextField.setText(String.valueOf(outsourcedPartModify.getId()));
            nameTxt.setText(outsourcedPartModify.getName());
            invTxt.setText(String.valueOf(outsourcedPartModify.getStock()));
            maxTxt.setText(String.valueOf(outsourcedPartModify.getMax()));
            minTxt.setText(String.valueOf(outsourcedPartModify.getMin()));
            priceTxt.setText(String.valueOf(outsourcedPartModify.getPrice()));
            inOrOutTxt.setText(outsourcedPartModify.getCompanyName());
        }


    }

    /**
     * Method to change the inOrOut label to Company Name when the outsourced radio button is selected.
     * @param actionEvent Outsourced radio button
     */
    public void modifyOutsourcedSelected(ActionEvent actionEvent) {
        inOrOut.setText("Company Name");
    }

    /**
     * Method to change the inOrOut label to Machine ID when the In House radio button is selected.
     * @param actionEvent In House radio button
     */
    public void modifyInHouseSelected(ActionEvent actionEvent) {
        inOrOut.setText("Machine ID");
    }
    /**
     *Method to cancel modifying a part and return to the main screen.
     * RUNTIME/LOGIC ERROR - After modifying an in house part, the program would always show the last in house part that
     * had been either modified or had been selected to be modified. I found that this was being caused by the initializer
     * first checking to see if there was an in house part to be modified, and once one had been modified it was staying
     * because it is static and would have to be manually removed when you left the screen. This was done by setting the
     * inHousePartModify and outsourcedPartModify both to null when either the cancel button or save button is clicked,
     * so there would be no remaining data to cause the error.
     */
    public void backToMain(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear the text fields and not update \nthe part information. Would you like to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            inHousePartModify = null;
            outsourcedPartModify = null;

            Parent root = FXMLLoader.load(getClass().getResource("/nstaley/inventorysystem/MainForm.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1123, 542);
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Method to set the part to be modified when it is an in house part.
     * @param partToModify part to be modified
     */
    public static void setInHousePartModify(InHouse partToModify) {
        inHousePartModify = partToModify;
    }

    /**
     * Method to set the part to be modified when it is an outsourced part.
     * @param partToModify part to be modified
     */
    public static void setOutsourcedPartModify(Outsourced partToModify) {
        outsourcedPartModify = partToModify;
    }

    /**
     * Method to save the Modified part, has exception handling to prevent the program from crashing if invalid
     * data is entered into the text fields.
     * @param actionEvent Save button clicked
     * @throws IOException Throws IOException if the file can not be loaded.
     */
    public void saveModify(ActionEvent actionEvent) throws IOException {
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

        if (nameTxt.getText().isEmpty()) {
            wasError = true;
            alertMessage = alertMessage.concat(nameErrorMessage);
        }
        else if (Character.isDigit(nameTxt.getText().charAt(0))) {
            wasError = true;
            alertMessage = alertMessage.concat(nameNumberErrorMessage);

        }
        try {
            Integer.parseInt(invTxt.getText());
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
        if (modifyInHousePartButton.isSelected()) {
            try {
                Integer.parseInt(inOrOutTxt.getText());
            }
            catch (NumberFormatException e) {
                wasError = true;
                alertMessage = alertMessage.concat(inHouseMachineMessage);
            }
        }
        if (modifyOutsourcedPartButton.isSelected()) {
            if (inOrOutTxt.getText().isEmpty()) {
                wasError = true;
                alertMessage = alertMessage.concat(outCompanyNameMessage);
            }
        }
        if (!wasError) {
            if (Integer.parseInt(maxTxt.getText()) < Integer.parseInt(minTxt.getText())) {
                wasError = true;
                alertMessage = alertMessage.concat(maxLessThanMinMessage);
            }

        }
        if (!wasError) {
            if ((Integer.parseInt(invTxt.getText()) > Integer.parseInt(maxTxt.getText())) || (Integer.parseInt(invTxt.getText()) < Integer.parseInt(minTxt.getText()))) {
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
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "This will save your updated part, do you want to continue?");

        Optional<ButtonType> result = alertConfirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            String name = nameTxt.getText();
            int stock = Integer.parseInt(invTxt.getText());
            double price = Double.parseDouble(priceTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());

            if (modifyInHousePartButton.isSelected()) {
                int machineId = Integer.parseInt(inOrOutTxt.getText());
                InHouse updatedPart = new InHouse(modifyId, name, price, stock, min, max, machineId);
                Inventory.updatePart(modifyIndex, updatedPart);
                inHousePartModify = null;
            } else {
                String companyName = inOrOutTxt.getText();
                Outsourced updatedPart = new Outsourced(modifyId, name, price, stock, min, max, companyName);
                Inventory.updatePart(modifyIndex, updatedPart);
                outsourcedPartModify = null;

            }


            Parent root = FXMLLoader.load(getClass().getResource("/nstaley/inventorysystem/MainForm.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1123, 542);
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Method to set the modify index field.
     * @param index index to be set
     */
    public static void setModifyIndex(int index) {
        modifyIndex = index;
    }
}
