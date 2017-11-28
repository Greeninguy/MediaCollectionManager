/**
 * Project 2 for CS1181
 * This program builds on the code for Project 1.
 * All functions of the program can now be executed through a GUI window.
 * Aside from added code, some methods from Project 1 have been edited.
 */
package project2_phelps;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.border.Border;



/**
 * @author Jimmie Phelps
 * CS1181-01
 * Instructor: Michelle Cheatham
 * Project 2
 */
public class Project2_Phelps extends Application{
    
    static Library collection = new Library();
    
    /**
     * Main method launches the GUI for the media collection manager
     * After the window is closed, the collection is saved with the sendData method.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        sendData(collection);
    }
    /**
     * GIU replaces the role of the menu from project 1.
     * All functions of the menu are available through buttons on the interface.
     * @param primaryStage stages the media collection manager
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        int loaded = 0;
        try {
            FileInputStream fileInput= new FileInputStream("library.tmp");
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            collection = (Library)objectInput.readObject();
            System.out.println("Loaded your collection.");
            loaded = 1;
            objectInput.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("No file found.");
        }
        catch(IOException | ClassNotFoundException e) {
            e.getMessage();
        }
        finally {
            if (loaded != 1) {
                System.out.println("Initializing new collection.");
            }
        }
        
        ObservableList<Item> items = FXCollections.observableArrayList(collection.getCollection());
        ListView<Item> pane = new ListView();
        pane.setMinWidth(400);
        pane.setItems(items);
        Collections.sort(items);
        
        HBox hBox = new HBox(10);
        hBox.setStyle("-fx-border-color: black");
        
        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(10, 15, 0, 0));
        
        GridPane pane1 = new GridPane();
        pane1.setStyle("-fx-border-color: black");
        pane1.setPadding(new Insets(10, 10, 10, 10));
        pane1.setVgap(10);
        pane1.setHgap(5);
        
        Text title = new Text("Title:");
        pane1.add(title, 1, 1);
        
        TextField titleText = new TextField();
        pane1.add(titleText, 2, 1);
        
        Text format = new Text("Format:");
        pane1.add(format, 1, 2);
        
        TextField formatText = new TextField();
        pane1.add(formatText, 2, 2);
        
        Button addButton = new Button("Add");
        pane1.add(addButton, 1, 3);
        
        vBox.getChildren().add(pane1);
        
        Button removeButton = new Button("Remove");
        vBox.getChildren().add(removeButton);
        
        Button returnButton = new Button("Return");
        vBox.getChildren().add(returnButton);
        
        GridPane pane2 = new GridPane();
        pane2.setStyle("-fx-border-color: black");
        pane2.setPadding(new Insets(10, 10, 10, 10));
        pane2.setVgap(10);
        pane2.setHgap(5);
        
        Text loanedTo = new Text("Loaned To:");
        pane2.add(loanedTo, 0, 0);
        
        TextField loanText = new TextField();
        pane2.add(loanText, 1, 0);
        
        Text loanedOn = new Text("Loaned On:");
        pane2.add(loanedOn, 0, 1);
        
        DatePicker datePicker = new DatePicker();
        pane2.add(datePicker, 1, 1);
        
        Button loanButton = new Button("Loan");
        pane2.add(loanButton, 0, 2);
        
        vBox.getChildren().add(pane2);
        
        Text sort = new Text("Sort");
        vBox.getChildren().add(sort);
        
        RadioButton radio1 = new RadioButton("By Title");
        vBox.getChildren().add(radio1);
        
        RadioButton radio2 = new RadioButton("By Loan Date");
        vBox.getChildren().add(radio2);
        
        hBox.getChildren().add(pane);
        hBox.getChildren().add(vBox);
        
        ToggleGroup group = new ToggleGroup();
        radio1.setToggleGroup(group);
        radio2.setToggleGroup(group);
        radio1.setSelected(true);
        
        radio1.setOnAction(e -> {
            sortByTitle(items);
        });
        
        radio2.setOnAction(e -> {
            sortByDate(items);
        });
        
        addButton.setOnAction(e -> {
            if (titleText.getText().isEmpty() || formatText.getText().isEmpty())
                System.out.println("Enter appropriate information in the fields");
            else {
                try {
                String newTitle = titleText.getText();
                String newFormat = formatText.getText();
                Item item = new Item(newTitle, newFormat);
                boolean itsthere = collection.checkCollection(newTitle);
                collection.addItem(newTitle, newFormat);
                if (itsthere == false)
                    items.add(item);
                if (radio2.isSelected() == true)
                    Collections.sort(items, Item.dateComparer);
                if (radio1.isSelected() == true)
                    Collections.sort(items);
                pane.refresh();
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        });
        
        removeButton.setOnAction(e -> {
            try {
                Item item = pane.getSelectionModel().getSelectedItem();
                items.remove(item);
                collection.removeItem(item);
                pane.refresh();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        });
        
        loanButton.setOnAction(e -> {
            Item item = pane.getSelectionModel().getSelectedItem();
            String whoLoaned = loanText.getText();
            LocalDate whenLoaned = datePicker.getValue();
            if (datePicker.getValue() == null)
                System.out.println("Select a loan date.");
            else {
                collection.loanItem(item, whoLoaned, whenLoaned);
                if (radio1.isSelected() == true)
                    Collections.sort(items);
                if (radio2.isSelected() == true)
                    Collections.sort(items, Item.dateComparer);
                pane.refresh();
            }
        });
        
        returnButton.setOnAction(e -> {
            Item item = pane.getSelectionModel().getSelectedItem();
            collection.returnItem(item);
            pane.refresh();
        });
        
        Scene scene = new Scene(hBox);
        primaryStage.setTitle("Media Collection");
        primaryStage.setScene(scene);
        primaryStage.setWidth(700);
        primaryStage.setHeight(500);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Method used to sort the items based on their titles.
     * Not a necessary method but was used in past attempts to find a solution.
     * @param items 
     */
    public static void sortByTitle(ObservableList<Item> items) {
        Collections.sort(items);
    }
    
    /**
     * Method used to sort the items based on their loan date.
     * Not a necessary method but was used in past attempts to find a solution.
     * @param items 
     */
    public static void sortByDate(ObservableList<Item> items) {
        Collections.sort(items, Item.dateComparer);
    }
    
    /**
     * Menu is no longer used for Project 2.  GUI replaces its function
     * @param collection array list of the user's media items
     */
    /**
    public static void menu(Library collection) {
        int choice = 0;
        Scanner input = new Scanner(System.in);
        String menu = "1) Add an item." + 
                "\n2) Loan an item." + 
                "\n3) Return an item." + 
                "\n4) List items." + 
                "\n5) Remove an item." +
                "\n6) Quit.";
        
        System.out.println("What would you like to do? Pick 1-6 ");
        System.out.println(menu);
        try {
            choice = input.nextInt();
        }
        catch (InputMismatchException e) {
            System.out.println("Please enter a number between 1-6.");
        }
        System.out.println();
        while (choice != 6) {
            if (choice > 5 || choice < 1) {
                System.out.println("Please choose one of the options 1-6");
                menu(collection);
            }
            if (choice == 1) {
                collection.addItem();
                menu(collection);
            }
            else if (choice == 2) {
                collection.loanItem();
                menu(collection);
            }
            else if (choice == 3) {
                collection.returnItem();
                menu(collection);
            }
            else if (choice == 4) {
                collection.listItems();
                menu(collection);
            }
            else {
                collection.removeItem();
                menu(collection);
            }
        }
        sendData(collection);
    }
    **/
    
    /**
     * If the user quits the program, their media collection is saved into a file.
     * @param collection array list of the user's media items
     */
    public static void sendData(Library collection) {
        try {
            FileOutputStream fileOutput = new FileOutputStream("library.tmp");
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
            objectOutput.writeObject(collection);
            System.out.println("Collection saved to \"libray.tmp\".");
            System.exit(0);
        }
        catch(FileNotFoundException e) {
            System.out.println("Could not find the file");
        }
        catch(IOException e) {
            System.out.println("There was a problem saving your collection");
        }
    }
}
