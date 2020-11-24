package ProductsAndParts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;
import model.Outsourced;

/** A compatible feature for future development would be to create a third class for assembly queues so that we know how many parts
 * are available and not currently being used to assemble products. */

/** This class is the main document for an Inventory Systems application*/
public class Main extends Application {

    /** This is the start method. This is the method that controls settings for the opening window.
     * @param primaryStage is the window. */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/ProductsAndPartsMain.fxml"));
        primaryStage.setTitle("Inventory Systems");
        primaryStage.setScene(new Scene(root, 950, 450));
        primaryStage.show();
    }

/** This is the main method. This is the frist method that gets called when the app is run. It also contains some sample data. */
    public static void main(String[] args) {
        Product pole = new Product(1,"pole",0.9,100,5,15);
        Product gear = new Product(2,"gear",9.99,100,5,15);
        Product cog = new Product(3,"cog",99.99,10000,5,15);
        Product android = new Product(4,"android",999999999.99,1,1,1);
        Product spaceBattleship = new Product(5,"Battleship",99999999999999999.99,1,1,2);




        Part cogWheel = new InHouse(6, "Cog Wheel", 234.22,1,2,3, 123 );
        Part bicycleHandle = new InHouse(7, "Bicycle Handle", 2133.23,34,2,3, 2435 );
        Part carPart = new InHouse(8, "Car Part", 423.23,1234,2,3, 5445 );

        android.addAssociatedPart(cogWheel);
        android.addAssociatedPart(bicycleHandle);
        android.addAssociatedPart(carPart);

        Inventory.addPart(cogWheel);
        Inventory.addProduct(pole);
        Inventory.addProduct(gear);
        Inventory.addProduct(cog);
        Inventory.addProduct(android);
        Inventory.addProduct(spaceBattleship);
        Inventory.addPart(bicycleHandle);
        Inventory.addPart(carPart);





        launch(args);

    }
}
