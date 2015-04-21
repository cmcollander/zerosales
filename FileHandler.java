// Chris Collander, Costco 1173, 4/20/2015
package zerosales;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Chris Collander
 */
public class FileHandler {

    public static ArrayList<Integer> loadZeroSales(Stage stage) {
        ArrayList<Integer> list = new ArrayList<>();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open ZeroSales File");
        File file = fileChooser.showOpenDialog(stage);

        if (file == null) {
            return list;
        }

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                if (str.length() < 3) {
                    continue; // Prepare for blank lines
                }
                Integer i = new Integer(str);
                list.add(i);
            }
        } catch (Exception e) {
            return list;
        }

        return list;
    }

    public static ArrayList<Integer> loadInventory(Stage stage) {
        ArrayList<Integer> list = new ArrayList<>();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Inventory File");
        File file = fileChooser.showOpenDialog(stage);

        if (file == null) {
            return list;
        }

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                if (str.length() < 3) {
                    continue; // Prepare for blank lines
                }
                Integer i = new Integer(str);
                list.add(i);
            }
        } catch (Exception e) {
            return list;
        }

        return list;
    }
}
