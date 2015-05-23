import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class Project10 extends Application { 
	  private Button btConnectDatabase = new Button("Connect to Database");
	  private Button btBatchUpdate = new Button("Batch Update");
	  private Button btNonBatchUpdate = new Button("Non-Batch Update");
	  private Label lblStatus = new Label();
	  private TextField display = new TextField();
	  
	  private Statement stmt;
	  String url = "jdbc:mysql://localhost/javabook";
	  String user = "scott";
	  String password = "tiger";
	  
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {   

	    HBox hbox = new HBox(5);
	    HBox hbox2 = new HBox(5);
	    hbox.getChildren().addAll(btConnectDatabase, lblStatus);
	    hbox2.getChildren().addAll(btBatchUpdate, btNonBatchUpdate);
	    
	    BorderPane pane = new BorderPane();
	    pane.setTop(hbox);
	    pane.setCenter(display);
	    pane.setBottom(hbox2);
	    
	    
	 // Create a scene and place it in the stage
	    Scene scene = new Scene(pane, 420, 80);
	    primaryStage.setTitle("DB Connection"); // Set the stage title
	    primaryStage.setScene(scene); // Place the scene in the stage
	    primaryStage.show(); // Display the stage  
	    
	    btConnectDatabase.setOnAction(e -> connectDatabase());
  }
}

  

  private Connection connection;
  private Label lblConnectionStatus = new Label("No connection");

  private Button btConnect = new Button("Connect to DB");
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

  /** Creates new form DBConnectionPanel */
  public DBConnectionPane() {
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
    gridPane.add(btConnect, 1, 4);
    GridPane.setHalignment(btConnect, HPos.RIGHT);
    
    this.setTop(lblConnectionStatus);
    this.setCenter(gridPane);

    btConnect.setOnAction(e -> connectDB());
  }

  public void connectDB() {
	    try {
	      // Load the JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");
	      System.out.println("Driver loaded");

	      // Establish a connection
	      Connection connection = DriverManager.getConnection
	        ("jdbc:mysql://localhost/javabook", "scott", "tiger");

	      System.out.println("Database connected");
	    
	      // Create a statement
	      stmt = connection.createStatement();
	      
	      //create a Staff table
	      String CreateTable = 
				  "create table Staff (" +
				  "id char(9) not null," +
				  "lastName varchar(15)," +
				  "firstName varchar(15)," +
				  "mi char(1)," +
				  "address varchar(20)," +
				  "city varchar(20)," +
				  "state char(2)," +
				  "telephone char(10)," +
				  "email varchar(40)," +
				  "primary key (id)" +
				  ");";
	      stmt.executeUpdate(CreateTable);
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }
  
  /** Return connection */
  public Connection getConnection() {
    return connection;
  }

  private void insertNonBatchUpdate() {
	  
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
		  }
		  
		    }
		    catch (SQLException ex) {
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
}
