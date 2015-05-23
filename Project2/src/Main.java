/**
 * Project #2
 * Name: Allison Smith
 * Class: CMSC 214
 * Date: February 16, 2015
 * Compiled on Eclipse
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Main extends Application {
	//create text fields and button
	private TextField tfIntBox = new TextField();
	private TextField tfDisplayBox = new TextField();
	private Button showElement = new Button("Show Element");
	
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		// Create the UI
		GridPane pane = new GridPane();
		pane.setHgap(5);
		pane.setVgap(5);
		pane.add(new Label("Enter Array Index: "), 0, 0);
		pane.add(tfIntBox, 0, 5);
		pane.add(new Label("Array Element: "), 0,10);
		pane.add(tfDisplayBox, 0, 15);
		pane.add(showElement, 0, 25);  
		pane.setAlignment(Pos.CENTER);
		  
		// Create event handler
		showElement.setOnAction(e -> randomInt());
		    
	    //Create a new scene and place it in the stage
		Scene scene = new Scene(pane, 250, 250);
	    primaryStage.setTitle("Display Random Integer"); // Set the stage title
	    primaryStage.setScene(scene); // Place the scene in the stage
	    primaryStage.show(); // Display the stage
	    
	  }

//Create a method for the array of random integers.	
public void randomInt (){
	try{
		//take user's input from the tfIntBof text field
		int index = Integer.parseInt(tfIntBox.getText());
		
		// Create and populate array list
		 List<Integer> list = new ArrayList<Integer>();
		 for (int i = 1; i < 101; i++) {
		     list.add(i);
		 }

		 // Shuffle so the integers are random
		 Collections.shuffle(list);

		 // Get an int[] array
		 int[] randArray = new int[list.size()];
		 for (int i = 0; i < list.size(); i++) {
		     randArray[i] = list.get(i);
		 
		 //Convert to a string    
		 Arrays.toString(randArray);
		 
		 //Display in the displayInt text field
		 String displayInt = Integer.toString(randArray[index]);
		 tfDisplayBox.setText(displayInt);   
	}
}
	//Exception handling
	catch (NumberFormatException ex) {
		//create UI for error message
		GridPane pane = new GridPane();
		pane.setHgap(5);
		pane.setVgap(5);
		pane.add(new Label("Out of Bounds"), 0, 0);
		Button btOK = new Button("OK");
		pane.add(btOK, 0, 10);
		pane.setAlignment(Pos.CENTER);
		
		//create a scene and set it in the stage
		Scene scene = new Scene(pane, 250, 250);
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.setScene(scene);
		dialogStage.show();
	
		//event handling so error message will disappear
		btOK.setOnAction(e -> dialogStage.hide());
	}
}

public static void main(String[] args) {
			    launch(args);
		  }
}
