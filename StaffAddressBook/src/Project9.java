import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class Project9 extends Application {
  // PreparedStatement for executing queries

  private TextField tfID = new TextField();
  private TextField tfLastName = new TextField();
  private TextField tfMI = new TextField();
  private TextField tfFirstName = new TextField();
  private TextField tfAddress = new TextField();
  private TextField tfCity = new TextField();
  private TextField tfState = new TextField();
  private TextField tfTelephone = new TextField();
  private TextField tfEmail = new TextField();
  private Button btView = new Button("View");
  private Button btInsert = new Button("Insert");
  private Button btUpdate = new Button("Update");
  private Button btClear = new Button("Clear");
  
  private Statement stmt;
  String url = "jdbc:mysql://localhost/javabook";
  String user = "scott";
  String password = "tiger";
  
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    // Initialize database connection and create a Statement object
    initializeDB();

 VBox vBox = new VBox(5);
    
 	HBox hBoxID = new HBox(5);
 	hBoxID.getChildren().addAll(new Label("ID"), tfID);
 	tfID.setPrefColumnCount(1);
 	
    HBox hBox2 = new HBox(5);
    hBox2.getChildren().addAll(new Label("Last Name"), tfLastName,
      new Label("First Name"), tfFirstName, new Label("MI"), tfMI);
    tfLastName.setPrefColumnCount(8);
    tfFirstName.setPrefColumnCount(8);
    tfMI.setPrefColumnCount(1);

    HBox hBox3 = new HBox(5);
    hBox3.getChildren().addAll(new Label("Street"), tfAddress);
    HBox hBox4 = new HBox(5);
    hBox4.getChildren().addAll(new Label("City"), tfCity,
      new Label("State"), tfState);   
    tfState.setPrefColumnCount(2);
   
    
    HBox hBox5 = new HBox(5);
    hBox5.getChildren().addAll(new Label("Telephone"), tfTelephone);
 
    HBox hBox1 = new HBox(5);
    hBox1.getChildren().addAll(new Label("Email"), tfEmail);
    vBox.getChildren().addAll(hBoxID, hBox2, hBox3, hBox4, hBox5, hBox1);
    
    HBox hBox = new HBox(5);
    hBox.getChildren().addAll(btView, btInsert, btClear, btUpdate);
    hBox.setAlignment(Pos.CENTER);
    
    BorderPane pane = new BorderPane();
    pane.setTop(hBox);
    pane.setCenter(vBox);
    
    btView.setOnAction(e -> view());
    btInsert.setOnAction(e -> insert());
    btUpdate.setOnAction(e -> update());
    btClear.setOnAction(e -> clear());
    
    // Create a scene and place it in the stage
    Scene scene = new Scene(pane, 450, 250);
    primaryStage.setTitle("Project 9"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage   
  }

  public void initializeDB() {
	    try {
	      // Load the JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");
	      System.out.println("Driver loaded");

	      // Establish a connection
	      Connection connection = DriverManager.getConnection
	        ("jdbc:mysql://localhost/javabook", "scott", "tiger");
//	    ("jdbc:oracle:thin:@liang.armstrong.edu:1521:ora9i",
//	     "scott", "tiger");
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
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }
  
  private void update() {
	  String id = tfID.getText();
	  String firstName = tfFirstName.getText();
	  String lastName = tfLastName.getText();
	  String mi = tfMI.getText();
	  String address = tfAddress.getText();
	  String city = tfCity.getText();
	  String state = tfState.getText();
	  String telephone = tfTelephone.getText();
	  String email = tfEmail.getText();
	  
	  try {
		  Connection myConn = DriverManager.getConnection(url, user, password);
		  
		  String updateString = "UPDATE Staff SET lastName = ?, firstName = ?, mi = ?, address = ?, city = ?, state = ?, telephone = ?, email = ?"
				  + "WHERE id = " + id;
		  
		  PreparedStatement stmt2 = myConn.prepareStatement(updateString);
		  stmt2.setString(1, lastName);
		  stmt2.setString(2, firstName);
		  stmt2.setString(3, mi);
		  stmt2.setString(4, address);
		  stmt2.setString(5, city);
		  stmt2.setString(6, state);
		  stmt2.setString(7, telephone);
		  stmt2.setString(8, email);
		  
		  
		  stmt2.executeUpdate();
		  
		    }
		    catch (SQLException ex) {
		      ex.printStackTrace();
		    }
	  }
 
  private void insert() {
	
	  String id = tfID.getText();
	  String firstName = tfFirstName.getText();
	  String lastName = tfLastName.getText();
	  String mi = tfMI.getText();
	  String address = tfAddress.getText();
	  String city = tfCity.getText();
	  String state = tfState.getText();
	  String telephone = tfTelephone.getText();
	  String email = tfEmail.getText();
	  
	  try {
		  
		  Connection myConn = DriverManager.getConnection(url, user, password);
		  
		  String updateString = "insert into Staff(id, lastName, firstName, mi,"
				  + "address, city, state, telephone, email)"
				  + "values(?,?,?,?,?,?,?,?,?)";
		  
		  PreparedStatement insetStmt = myConn.prepareStatement(updateString);
		  insetStmt.setString(1, id);
		  insetStmt.setString(2, lastName);
		  insetStmt.setString(3, firstName);
		  insetStmt.setString(4, mi);
		  insetStmt.setString(5, address);
		  insetStmt.setString(6, city);
		  insetStmt.setString(7, state);
		  insetStmt.setString(8, telephone);
		  insetStmt.setString(9, email);
		  
		  insetStmt.executeUpdate();
		  
		    }
		    catch (SQLException ex) {
		      ex.printStackTrace();
		    }
	  }
  private void clear() {
	    String id = tfID.getText();
	    try {
	        String queryString = "delete from Staff where Staff.id = " + id;

	        stmt.executeUpdate(queryString);
	        
	      }
	      catch (SQLException ex) {
	        ex.printStackTrace();
	      }
	    }
  
  private void view() {
    String id = tfID.getText();
    try {
    	 Connection myConn = DriverManager.getConnection(url, user, password);
    	 
    	String queryString = "select * from Staff where id = " + tfID.getText();

        ResultSet rset = stmt.executeQuery(queryString);

        while (rset.next()) {
          String lastName = rset.getString(2);
          String firstName = rset.getString(3);
          String mi = rset.getString(4);
          String address = rset.getString(5);
          String city = rset.getString(6);
          String state = rset.getString(7); 
          String telephone = rset.getString(8);
          String email = rset.getString(9);
          

          // Display result
          //tfID.setText(id);
          tfFirstName.setText(firstName);
          tfMI.setText(mi);
          tfLastName.setText(lastName);
          tfAddress.setText(address);
          tfCity.setText(city);
          tfState.setText(state);
          tfTelephone.setText(telephone);
          tfEmail.setText(email);

          
        }

        
      }
      catch (SQLException ex) {
        ex.printStackTrace();
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
