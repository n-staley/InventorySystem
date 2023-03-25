module nstaley.inventorysystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens nstaley.inventorysystem to javafx.fxml;
    exports nstaley.inventorysystem;
}