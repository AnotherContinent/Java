import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

public class StaffDatabase extends Application {
  private Button btView = new Button("View");
  private Button btInsert = new Button("Insert");
  private Button btDelete = new Button("Delete");
  private Button btUpdate = new Button("Update");
  private TextField tfID = new TextField();
  private TextField tfLastName = new TextField();
  private TextField tfFirstName = new TextField();
  private TextField tfMi = new TextField();
  private TextField tfAddress = new TextField();
  private TextField tfCity = new TextField();
  private TextField tfState = new TextField();
  private TextField tfTelephone = new TextField();
  private TextField tfEmail = new TextField();
  private Label lblStatus = new Label();
  
  // Result set
  private ResultSet resultSet;

  // Current row number
  private int currentRowNumber;

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) throws SQLException {
    VBox vBox = new VBox(5);
    
    HBox hBox2 = new HBox(5);
    hBox2.getChildren().addAll(new Label("Last Name"), tfLastName,
      new Label("First Name"), tfFirstName, new Label("MI"), tfMi);
    tfLastName.setPrefColumnCount(8);
    tfFirstName.setPrefColumnCount(8);
    tfMi.setPrefColumnCount(1);

    HBox hBox3 = new HBox(5);
    hBox3.getChildren().addAll(new Label("Street"), tfAddress);
    HBox hBox4 = new HBox(5);
    hBox4.getChildren().addAll(new Label("City"), tfCity,
      new Label("State"), tfState, new Label("ID"), tfID);   
    tfState.setPrefColumnCount(2);
    tfID.setPrefColumnCount(5);
    
    HBox hBox5 = new HBox(5);
    hBox5.getChildren().addAll(new Label("Telephone"), tfTelephone);
 
    HBox hBox1 = new HBox(5);
    hBox1.getChildren().addAll(new Label("Email"), tfEmail);
    vBox.getChildren().addAll(hBox2, hBox3, hBox4, hBox5, hBox1);
    
    HBox hBox = new HBox(5);
    hBox.getChildren().addAll(btView, 
      btInsert, btDelete, btUpdate);
    hBox.setAlignment(Pos.CENTER);
    
    BorderPane pane = new BorderPane();
    pane.setTop(hBox);
    pane.setCenter(vBox);
    pane.setBottom(lblStatus);
    
    // Create a scene and place it in the stage
    Scene scene = new Scene(pane, 450, 200);
    primaryStage.setTitle("Allison Smith Project 9"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage

    initializeDB();
    
    btView.setOnAction(e -> view(resultSet));
    btInsert.setOnAction(e -> insert());
    btUpdate.setOnAction(e -> updateRecord());
    btDelete.setOnAction(e -> clear());    
  }
  
    
  /**Initialize the database connection, create statement, and result set */
  private void initializeDB() {
    try {
      // Load the driver
      Class.forName("com.mysql.jdbc.Driver");
      System.out.println("Driver loaded");
      
      // Connect to the local InterBase database
      Connection connection = DriverManager.getConnection
//        ("dbc:odbc:exampleMDBDataSource", "", "" );
        ("jdbc:mysql://localhost/javabook", "scott", "tiger");
      System.out.println("Database connected");

      // Create a statement
      Statement statement = connection.createStatement
        (ResultSet.TYPE_SCROLL_SENSITIVE,
         ResultSet.CONCUR_UPDATABLE);

      // Get result set
      resultSet = statement.executeQuery("select * from Staff");

      // Show the first record in the result set
      resultSet.first();
      view(resultSet);
    }
    catch (Exception ex) {
      lblStatus.setText(ex.toString());
    }
  }

  /**Load the record into text fields*/
  private void view(ResultSet rs) {
    try{
	  if (rs.next()) {
      tfLastName.setText(rs.getString(2));
      tfFirstName.setText(rs.getString(3));
      tfMi.setText(rs.getString(4));
      tfAddress.setText(rs.getString(5));
      tfCity.setText(rs.getString(6));
      tfState.setText(rs.getString(7));
      tfTelephone.setText(rs.getString(8));
      lblStatus.setText("Record found");
    }
    else
      lblStatus.setText("Record not found");
  }
    catch (SQLException ex) {
        ex.printStackTrace();
      }
  }
  

  /**Delete text fields*/
  private void clear() {
    tfEmail.setText(null);
    tfLastName.setText(null);
    tfFirstName.setText(null);
    tfMi.setText(null);
    tfAddress.setText(null);
    tfCity.setText(null);
    tfState.setText(null);
    tfTelephone.setText(null);
  }

  /** private void view() {
   try{
    tfFirstName.setText(resultSet.getString("firstName"));
    tfLastName.setText(resultSet.getString("lastName"));
    tfMi.setText(resultSet.getString("mi"));
    tfAddress.setText(resultSet.getString("Street"));
    tfCity.setText(resultSet.getString("City"));
    tfState.setText(resultSet.getString("State"));
    tfTelephone.setText(resultSet.getString("Telephone"));
    tfID.setText(resultSet.getString("ID"));
    tfEmail.setText(resultSet.getString("email"));

    currentRowNumber = resultSet.getRow();
    lblStatus.setText("Current row number: " + currentRowNumber);
   }
   catch (SQLException ex) {
	      ex.printStackTrace();
	    }
  }
  */
  
  /**Insert a new record to the database*/
  private void insert() {
    try {
      // Update the fields
      updateRecord();

      // Insert the row
      resultSet.insertRow();

      // Move the cursor back to the position before the insertion
      resultSet.moveToCurrentRow();
      
      lblStatus.setText("Insertion succeeded");
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**Update fields in the record*/
  protected void updateRecord() {
    try {
      // Gather data from the UI and update the database fields
      resultSet.updateString("firstName",
        tfFirstName.getText().trim());
      resultSet.updateString("mi", tfMi.getText().trim());
      resultSet.updateString("lastName", tfLastName.getText().trim());
      resultSet.updateString("address", tfAddress.getText().trim());
      resultSet.updateString("city", tfCity.getText().trim());
      resultSet.updateString("id", tfID.getText().trim());
      resultSet.updateString("telephone", tfTelephone.getText().trim());
      resultSet.updateString("email", tfEmail.getText().trim());
      
      lblStatus.setText("Update succeeded");
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
  
  private void delete() {
    try {
      resultSet.deleteRow();
      lblStatus.setText("Deletion succeeded");
    }
    catch (Exception ex) {
      lblStatus.setText(ex.toString());
    }
  }
  
  /**
   * The main method is only needed for the EmailE with limited
   * avaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
