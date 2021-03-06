import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;

public class DatabaseConnection extends Application {
	 Stage thestage;
	 Scene scene;
	 Scene scene2;
	    
	private Button btConnectDatabase = new Button("Connect to Database");
	  private Button btBatchUpdate = new Button("Batch Update");
	  private Button btNonBatchUpdate = new Button("Non-Batch Update");
	  private Label lblStatus = new Label();
	  private TextArea display = new TextArea();
	
	//second pane
	  private Connection connection;
	  private Label lblConnectionStatus = new Label("No connection");

	  private Button btDBConnect = new Button("Connect to DB");
	  private ComboBox<String> cboDriver = new ComboBox<>(
	    FXCollections.observableArrayList(
	      "com.mysql.jdbc.Driver", "sun.jdbc.odbc.JdbcOdbcDriver",
	      "oracle.jdbc.driver.OracleDriver"));
	  private ComboBox<String> cboURL = new ComboBox<>(
	    FXCollections.observableArrayList(
	      "jdbc:mysql://localhost/javabook",
	      "jdbc:odbc:exampleMDBDataSource",
	      "jdbc:oracle:thin:@liang.armstrong.edu:1521:ora9i"));

	  private TextField tfUsername = new TextField();
	  private PasswordField pfPassword = new PasswordField();
	  
	  private Statement stmt;
	  String url = "jdbc:mysql://localhost/javabook";
	  String user = "scott";
	  String password = "tiger";
	  
@Override // Override the start method in the Application class
public void start(Stage primaryStage) {  
	 thestage=primaryStage;
	 
		//first pane
	    HBox hbox = new HBox(5);
	    HBox hbox2 = new HBox(5);
	    hbox.getChildren().addAll(lblStatus, btConnectDatabase);
	    hbox2.getChildren().addAll(btBatchUpdate, btNonBatchUpdate);
	    hbox.setPadding(new Insets(5, 5, 5, 5));
	    hbox.setSpacing(10);
	    hbox2.setPadding(new Insets(5, 5, 5, 5));
	    hbox2.setSpacing(10);
	    BorderPane pane = new BorderPane();
	    pane.setTop(hbox);
	    pane.setCenter(display);
	    pane.setBottom(hbox2);
	    
	  //second pane
		    cboDriver.setEditable(true);
		    cboURL.setEditable(true);

		    GridPane gridPane = new GridPane();
		    gridPane.add(new Label("JDBC Drive"), 0, 0);
		    gridPane.add(new Label("Database URL"), 0, 1);
		    gridPane.add(new Label("Username"), 0, 2);
		    gridPane.add(new Label("Password"), 0, 3);
		    gridPane.add(cboDriver, 1, 0);
		    gridPane.add(cboURL, 1, 1);
		    gridPane.add(tfUsername, 1, 2);
		    gridPane.add(pfPassword, 1, 3);
		    gridPane.add(btDBConnect, 1, 4);
		    gridPane.add(lblConnectionStatus, 0, 4);
		    GridPane.setHalignment(btDBConnect, HPos.RIGHT);
		    
	    
	 // Create a scene and place it in the stage
	    scene = new Scene(pane, 420, 180);
	    scene2 = new Scene(gridPane, 420, 180);
	    primaryStage.setTitle("DB Connection"); // Set the stage title
	    primaryStage.setScene(scene); // Place the scene in the stage
	    primaryStage.show(); // Display the stage   
		    
	    btConnectDatabase.setOnAction(e -> connectDatabase());
	    btNonBatchUpdate.setOnAction(e -> insertNonBatchUpdate());
	    btBatchUpdate.setOnAction(e -> insertBatchUpdate());
	    btDBConnect.setOnAction(e -> connectDB());
}

public void connectDatabase() {
    try {
    	//create a scene and set it in the stage
    			
    			Stage dialogStage = new Stage();
    			dialogStage.initModality(Modality.WINDOW_MODAL);
    			dialogStage.setScene(scene2);
    			dialogStage.show();
/*
    	// Load the JDBC driver
      Class.forName("com.mysql.jdbc.Driver");
      System.out.println("Driver loaded");

      // Establish a connection
      Connection connection = DriverManager.getConnection
        ("jdbc:mysql://localhost/javabook", "scott", "tiger");

      System.out.println("Database connected");*/

      // Create a statement
      stmt = connection.createStatement();
      
      //create a temp table
	    String dropTempTable = "drop table if exists Temp;";
	    stmt.executeUpdate(dropTempTable);
		String createTempTable = 
				  "create table Temp (" +  
				  "num1 double," +
				  "num2 double," +
				  "num3 double);";
		stmt.executeUpdate(createTempTable);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

private void insertNonBatchUpdate() {
	long start = System.nanoTime();
	  try {
		  
		  Connection myConn = DriverManager.getConnection(url, user, password);
		  
		  int i=0;
		  
		  while (i < 1000){
		  String updateString = "insert into Temp(num1, num2, num3)"
				  + "values(?,?,?)";
		  
		  PreparedStatement insetStmt = myConn.prepareStatement(updateString);
		  insetStmt.setLong(1, (long) Math.random());
		  insetStmt.setLong(2, (long) Math.random());
		  insetStmt.setLong(3, (long) Math.random());
		  
		  insetStmt.executeUpdate();
		  
		  i++;
		  }
		  
		  long end = System.nanoTime();   
	      long elapsedTime = end-start;
	      display.setText("Non-batch update successful \nThe elapsed time is " + elapsedTime + "\n");
		    }
		    catch (SQLException ex) {
		      ex.printStackTrace();
		    }
}

private void insertBatchUpdate() {
	long start = System.nanoTime();
	
		Connection connection = null;
	    PreparedStatement statement = null;
	    try {
	        connection = DriverManager.getConnection(url, user, password);
	        statement = connection.prepareStatement("insert into Temp(num1, num2, num3)"
					  + "values(?,?,?)");
	        for (int i = 0; i < 1000; i++) {
	            statement.setLong(1, (long) Math.random());
	            statement.setLong(2, (long) Math.random());
	            statement.setLong(3, (long) Math.random());
	            statement.addBatch();
	            if ((i + 1) % 1000 == 0) {
	                statement.executeBatch(); // Execute every 1000 items.
	                
	         long end = System.nanoTime();   
	         long elapsedTime = end-start;
	         display.setText("Batch update successful \nThe elapsed time is " + elapsedTime + "\n");
	            }
	        }
	        statement.executeBatch();
	    } catch(SQLException ex) {
	        if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
	        if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
	    }
}

private void connectDB() {
    // Get database information from the user input
    String driver = cboDriver.getValue();
    String url = cboURL.getValue();
    String username = tfUsername.getText().trim();
    String password = new String(pfPassword.getText());

    // Connection to the database
    try {
      Class.forName(driver);
      connection = DriverManager.getConnection(
        url, username, password);
      lblConnectionStatus.setText("Connected to " + url);
    }
    catch (java.lang.Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Return connection */
  public Connection getConnection() {
    return connection;
  }

/**
 * The main method is only needed for the IDE with limited
 * JavaFX support. Not needed for running from the command line.
 */
public static void main(String[] args) {
  launch(args);
}
}
