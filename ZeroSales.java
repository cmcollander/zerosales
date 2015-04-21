// Chris Collander, Costco 1173, 4/20/2015
package zerosales;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Chris Collander
 */
public class ZeroSales extends Application {

    private static Stage primaryStage;

    private static ArrayList<Integer> zeroSalesList;
    private static ArrayList<Integer> inventoryList;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        zeroSalesList = new ArrayList<>();
        inventoryList = new ArrayList<>();

        // Prepare Menus
        final Menu menu1 = new Menu("File");
        MenuItem menu11 = new MenuItem("Open Zero Sales");
        MenuItem menu12 = new MenuItem("Open Inventory");
        MenuItem menu13 = new MenuItem("View Report");
        MenuItem menu14 = new MenuItem("Exit");
        menu1.getItems().addAll(menu11, menu12, menu13, menu14);
        // About Menu
        Label aboutLabel = new Label("About");
        // MouseHandler for About button
        aboutLabel.setOnMouseClicked(e -> {
            aboutDialog();
        });
        Menu menu2 = new Menu();
        menu2.setGraphic(aboutLabel);
        /// MenuBar
        MenuBar menubar = new MenuBar();
        menubar.getMenus().addAll(menu1, menu2);
        // MenuItem Listener
        EventHandler<ActionEvent> menuAction = menuItemSelected();
        menu11.setOnAction(menuAction);
        menu12.setOnAction(menuAction);
        menu13.setOnAction(menuAction);
        menu14.setOnAction(menuAction);

        // MainView
        TextArea mainView = new TextArea();
        String text = new String();
        text += "INSTRUCTIONS\n";
        text += "\tType ZeroSales into a text file, with a different item number on every line\n";
        text += "\tType 35 Inventory into a text file, with a different item number on every line\n";
        text += "\tClick 'View Report' to see what numbers on the Zero Sales are not on the Inventory";
        mainView.setText(text);

        // ButtonList
        GridPane buttonList = new GridPane();
        Button zeroSalesButton = new Button("Load Zero Sales");
        zeroSalesButton.setOnMouseClicked(e -> {
            zeroSalesList = FileHandler.loadZeroSales(primaryStage);
        });
        Button inventoryButton = new Button("Load Inventory");
        inventoryButton.setOnMouseClicked(e -> {
            inventoryList = FileHandler.loadInventory(primaryStage);
        });
        Button reportButton = new Button("View Report");
        reportButton.setOnMouseClicked(e -> {
            if (!zeroSalesList.isEmpty() && !inventoryList.isEmpty()) {
                viewReport();
            } else {
                loadReportError();
            }
        });
        buttonList.add(zeroSalesButton, 0, 0);
        buttonList.add(inventoryButton, 1, 0);
        buttonList.add(reportButton, 2, 0);

        // Create Scene/Stage
        BorderPane root = new BorderPane();
        root.setTop(menubar);
        root.setCenter(mainView);
        root.setBottom(buttonList);
        Scene scene = new Scene(root, 500, 150);
        primaryStage.setTitle("Zero Sales 35 Cross Referencer");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void aboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About ZeroSales");
        alert.setHeaderText(null);
        alert.setContentText("Chris Collander\n4/20/2014\nCreated for Costco 1173 for Zero Sales cross referencing");
        alert.showAndWait();
    }

    public static void loadReportError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Loading Report");
        alert.setHeaderText(null);
        alert.setContentText("Please load ZeroSales and Inventory files. If loaded, review included numbers");
        alert.showAndWait();
    }

    public static void viewReport() {
        String results = new String();

        for (Integer i : zeroSalesList) {
            boolean found = false;
            for (Integer j : inventoryList) {
                if (i.equals(j)) {
                    found = true;
                }
            }
            if (!found) {
                results += i.toString() + "\n";
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Zero Sales Results");
        alert.setHeaderText(null);
        if (results.length() < 3) {
            results = "No items from the Zero Sales were missing from Inventory";
        }
        alert.setContentText(results);
        alert.showAndWait();
    }

    private EventHandler<ActionEvent> menuItemSelected() {
        return (ActionEvent event) -> {
            MenuItem mItem = (MenuItem) event.getSource();
            String text = mItem.getText();

            // File Menu
            // Open MenuItem
            if ("Open Zero Sales".equalsIgnoreCase(text)) {
                FileHandler.loadZeroSales(primaryStage);
            }
            if ("Open Inventory".equalsIgnoreCase(text)) {
                FileHandler.loadInventory(primaryStage);
            }
            if ("View Report".equalsIgnoreCase(text)) {
                if (!zeroSalesList.isEmpty() && !inventoryList.isEmpty()) {
                    viewReport();
                } else {
                    loadReportError();
                }
            }
            if ("Exit".equalsIgnoreCase(text)) {
                System.exit(0);
            }
        };
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
