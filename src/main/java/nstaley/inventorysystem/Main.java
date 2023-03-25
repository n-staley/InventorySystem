package nstaley.inventorysystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * This class is the main class which loads the test data used, and launches the application.
 * FUTURE ENHANCEMENT - In the future I would like to add database storage for the parts and products. This would not only
 * allow the data to be saved between runs of the program, but would allow much more advanced searches and use for the
 * data that is stored.
 * RUNTIME/LOGIC ERROR - located in the ModifyPartFormController backToMain method.
 * @author Nicholas Staley
 */
public class Main extends Application {

    /**
     * Method to start the graphical application and load the main screen.
     * @param stage stage used to run the program
     * @throws IOException can throw an IOException if the file is not found.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1123, 542);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method main method that starts the application.
     * JavaDocs located at InventorySystem\src\main\JavaDocs\index.html
     * @param args arguments
     */
    public static void main(String[] args) {

        Outsourced part1 = new Outsourced(1, "Frame", 25.00, 20, 1, 100, "Bike Frame Superstore");
        InHouse part2 = new InHouse(2, "Large Tires", 12.99, 56, 1, 999, 1223);
        InHouse part3 = new InHouse(3, "Small Tires", 4.99, 33, 1, 270, 1224);

        Product product1 = new Product(1, "Adult Bike", 300.00, 20, 1, 1000);
        product1.addAssociatedPart(part2);
        product1.addAssociatedPart(part1);
        Product product2 = new Product(2,"Kids Bike", 200.00, 12, 1, 200);




        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);


        launch(args);
    }
}