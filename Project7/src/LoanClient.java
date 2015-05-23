

import java.io.*;
import java.net.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LoanClient extends Application {
  private TextField tfInterestRate = new TextField();
  private TextField tfYears = new TextField();
  private TextField tfAmount = new TextField();
  private TextArea tfDisplayBox = new TextArea();

  // Button for sending a student to the server
  private Button btSubmit = new Button("Submit");

  // Host name or ip
  String host = "localhost";

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    GridPane pane = new GridPane();
    pane.add(new Label("Annual Interest Rate"), 0, 0);
    pane.add(tfInterestRate, 1, 0);  
    tfInterestRate.maxWidth(50);
    pane.add(new Label("Number of Years"), 0, 1);
    pane.add(tfYears, 1, 1);
    pane.add(new Label("Loan Amount"), 0, 2);
    pane.add(tfAmount, 1, 2);
    pane.add(tfDisplayBox, 1, 3);
    tfDisplayBox.setMinWidth(200);
    tfDisplayBox.setMinHeight(150);
    pane.add(btSubmit, 2, 2);
    GridPane.setHalignment(btSubmit, HPos.RIGHT);
    
    pane.setAlignment(Pos.CENTER);   
    tfInterestRate.setPrefColumnCount(15);
    tfYears.setPrefColumnCount(15);
    tfAmount.setPrefColumnCount(10);


    btSubmit.setOnAction(new ButtonListener());
    
    // Create a scene and place it in the stage
    Scene scene = new Scene(pane, 450, 200);
    primaryStage.setTitle("LoanClient"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }

  /** Handle button action */
  private class ButtonListener implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e) {
      try {
        // Establish connection with the server
        Socket socket = new Socket(host, 8001);

        // Create an output stream to the server
        ObjectOutputStream toServer =
          new ObjectOutputStream(socket.getOutputStream());

        // Get text field
        Double interestRate = Double.parseDouble(tfInterestRate.getText());
        Double years = Double.parseDouble(tfYears.getText());
        Double amount = Double.parseDouble(tfAmount.getText());
      

        // Create a Student object and send to the server
        LoanCalculator s =
          new LoanCalculator(interestRate, years, amount);
        toServer.writeObject(s);
        
        tfDisplayBox.setText("Annual Interest Rate: "+s.getRate()+
        		"\nNumber of Years: "+s.getYears()+"\nLoan Amount: "+
        		s.getAmount()+"\nMonthly Payment: "+String.valueOf(s.getMonthly())+ 
        		"\nTotal Amount: " + s.getTotal());
      }
      catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }
  
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
