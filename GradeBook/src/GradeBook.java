import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class GradeBook extends Application {
	
	private Label firstNameLabel = new Label("First Name");
	private Label lastNameLabel = new Label("Last Name");
	private Label idLabel = new Label("Assignment ID");
	private Label gradeLabel = new Label("Grade");
	private Label assignmentNameLabel = new Label ("New Assignment Name");
	private TextField tfFirstName = new TextField();
	private TextField tfLastName = new TextField();
	private TextField tfID = new TextField();
	private TextField tfGrade = new TextField();
	private TextField tfAssignmentName = new TextField();
	private TextArea taDisplay = new TextArea();
	private Button btAdd = new Button("Add Grade");
	private Button btDisplay = new Button("Display Grades");
	private Button btModify = new Button("Modify Grade");
	private Button btStudents = new Button("Display Students");
	private Statement stmt;
	String url = "jdbc:mysql://localhost/javabook";
  	String user = "scott";
  	String password = "tiger";
  	
	@Override
	public void start(Stage primaryStage) throws SQLException {
		try {
			initializeDB();
		    Connection conn = DriverManager.getConnection(url, user, password);
		    Statement stmt = conn.createStatement();
		

		    // Visuals
			VBox vBox = new VBox(5);
			HBox box1 = new HBox(5);
			HBox box2 = new HBox(5);
			HBox box3 = new HBox(5);
			HBox box4 = new HBox(5);
			
			box1.getChildren().addAll(firstNameLabel, tfFirstName, lastNameLabel, tfLastName);
			box1.setPadding(new Insets(10, 10, 10, 10));
			box1.setSpacing(10);
			
			box2.getChildren().addAll(idLabel, tfID, gradeLabel, tfGrade);
			box2.setPadding(new Insets(10, 10, 10, 10));
			box2.setSpacing(10);
			
			box4.getChildren().addAll(btStudents, btAdd, btDisplay, btModify);
			box4.setPadding(new Insets(5, 5, 5, 5));
			box4.setSpacing(10);
			
			box3 .getChildren().addAll(assignmentNameLabel, tfAssignmentName);
			box3 .setPadding(new Insets(10, 10, 10, 10));
			box3 .setSpacing(10);
			
			vBox.getChildren().addAll(box1, box2, box3, box4, taDisplay);

			
			Scene scene = new Scene(vBox,500,400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			btAdd.setOnAction(e -> addGrade());
			btDisplay.setOnAction(e -> displayGrade());
			btModify.setOnAction(e -> modifyGrade());
			btStudents.setOnAction(e -> displayStudents());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void initializeDB() {
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
	      
	   // Create Tables
			 stmt.addBatch("drop table if exists Students;");
			 stmt.addBatch("drop table if exists Assignments;");
			 stmt.addBatch("drop table if exists Scores;");
			 stmt.addBatch("create table Students(studentID int not null auto_increment, firstName varchar(25), lastName varchar(25), constraint pkStudents primary key (studentID));");
			 stmt.addBatch("create table Assignments(assignmentID int not null auto_increment, assignmentName varchar(40), constraint pkAssignments primary key (assignmentID));");
			 stmt.addBatch("create table Scores(studentID int not null, assignmentID int not null, score varchar(5), primary key (studentID, assignmentID));");
			 stmt.addBatch("insert into Students (firstName, lastName) values ('Fanny', 'Price');");
			 stmt.addBatch("insert into Students (firstName, lastName) values ('Elizabeth', 'Bennett');");
			 stmt.addBatch("insert into Students (firstName, lastName) values ('Emma', 'Woodhouse');");
			 stmt.addBatch("insert into Students (firstName, lastName) values ('Elinor', 'Dashwood');");
			 stmt.addBatch("insert into Students (firstName, lastName) values ('Catherine', 'Morland');");
			 stmt.addBatch("insert into Assignments (assignmentName) values ('Homework 1');");
			 stmt.addBatch("insert into Assignments (assignmentName) values ('Midterm');");
			 stmt.addBatch("insert into Assignments (assignmentName) values ('Homework 2');");
			 stmt.addBatch("insert into Assignments (assignmentName) values ('Final');");
			 stmt.addBatch("insert into Assignments (assignmentName) values ('Term Paper');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (1, 1, '24');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (1, 2, '73');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (1, 3, '94');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (1, 4, '67');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (1, 5, '69');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (2, 1, '59');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (2, 2, '87');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (2, 3, '62');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (2, 4, '54');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (2, 5, '72');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (3, 1, '78');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (3, 2, '87');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (3, 3, '99');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (3, 4, '34');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (3, 5, '55');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (4, 1, '83');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (4, 2, '45');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (4, 3, '12');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (4, 4, '92');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (4, 5, '86');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (5, 1, '43');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (5, 2, '93');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (5, 3, '44');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (5, 4, '72');");
			 stmt.addBatch("insert into Scores (studentID, assignmentID, score) values (5, 5, '42');");

			    int count[] = stmt.executeBatch();
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }
	public void addGrade() {
		try{
	    Connection conn = DriverManager.getConnection(url, user, password);
	    
	    String findAssignments = "SELECT javabook.Assignments.assignmentName FROM javabook.Assignments";
	    PreparedStatement getName = conn.prepareStatement(findAssignments);
	    ResultSet rs = getName.executeQuery();
	    Boolean exists = false;
	    while(rs.next()){
	    	System.out.println(exists);
	    	if(tfAssignmentName.getText().trim().equals(rs.getString(1).trim())){
	    		exists = true;
	    		System.out.println("Assignment already exists");
	    	}else{
	    		exists = false;
	    		System.out.println(tfAssignmentName.getText().trim());
	    		System.out.println(rs.getString(1).trim());
	    		System.out.println("Assignment is new");
	    	}
	    }
	    if(exists == false){
	    	String newAssignment = "INSERT into javabook.Assignments (assignmentName) values(?)";
	    	PreparedStatement insertAssignment = conn.prepareStatement(newAssignment);
	    	insertAssignment.setString(1, tfAssignmentName.getText().trim());
	    	insertAssignment.executeUpdate();
	    }
	    
	    String id = null;
	    int intID = 0;
	    String searchForID = "SELECT javabook.Students.studentID FROM javabook.Students WHERE firstName = '" + tfFirstName.getText() + "' AND lastName = '" + tfLastName.getText() + "';";
        PreparedStatement findStmt = conn.prepareStatement(searchForID);   
	    ResultSet rsSearch = findStmt.executeQuery(searchForID);
	    while(rsSearch.next()){
	    	intID = rsSearch.getInt(1);
	    }
	    
	    id = Integer.toString(intID);
	    
	    String getAssignmentID = "SELECT javabook.Assignments.assignmentID "
	    		+ "FROM javabook.Assignments";
	    PreparedStatement findAssignmentID = conn.prepareStatement(getAssignmentID);
	    ResultSet rsAssignmentID = findAssignmentID.executeQuery();
	    int assignmentID = 0;
	    while(rsAssignmentID.next()){
	    	assignmentID = rsAssignmentID.getInt(1);
	    }
	    
	    String addScores = "insert into Scores "
	  					+ "(studentID, assignmentID, score)"
	  					+ "values(?,?,?)";
	    PreparedStatement scoreStmt = conn.prepareStatement(addScores);
	    scoreStmt.setInt(1, intID);
	    scoreStmt.setInt(2, assignmentID);
	    scoreStmt.setString(3, tfGrade.getText());
	    scoreStmt.executeUpdate();
	    
		} catch(Exception e) {
			
		}
	}
	public void displayStudents() {
		try{ 
			Connection conn = DriverManager.getConnection(url, user, password);
		    stmt = conn.createStatement();
		    
			String sqlGetStudents = "select * from Students";
		    ResultSet rs = stmt.executeQuery(sqlGetStudents);
		    taDisplay.setText("Students:\n\n");
		    while(rs.next()){
		    	taDisplay.appendText(rs.getString(2) + " ");
		    	taDisplay.appendText(rs.getString(3) + " \n");
		}
			
			
		    } catch(Exception e){
		
	}
		}
	
	public void displayGrade() {
		try{
	    Connection conn = DriverManager.getConnection(url, user, password);
	    
	    String grades ="SELECT Assignments.assignmentID, Assignments.assignmentName, Scores.score FROM Students JOIN Scores ON Students.studentID = Scores.studentID JOIN Assignments ON Assignments.assignmentID = Scores.assignmentID WHERE firstName = '" + tfFirstName.getText() +"' AND lastName = '"+ tfLastName.getText() +"';";          

        PreparedStatement gradeStmt = conn.prepareStatement(grades);   
        ResultSet rsGrade = gradeStmt.executeQuery(grades);
        taDisplay.clear();
        while(rsGrade.next()){
        	taDisplay.appendText(rsGrade.getString(1) + " ");
        	taDisplay.appendText(rsGrade.getString(2) + " ");
        	taDisplay.appendText(rsGrade.getString(3) + " ");
        	taDisplay.appendText("\n");
        }
		}catch(Exception e){
		}
	}
	
	public void modifyGrade() {
		try{

	    Connection conn = DriverManager.getConnection(url, user, password);
	    
	    String idString = null;
	    int idInt = 0;
	    String searchForID = "SELECT javabook.Students.studentID FROM javabook.Students WHERE firstName = '" + tfFirstName.getText() + "' AND lastName = '" + tfLastName.getText() + "';";
        PreparedStatement searchStmt = conn.prepareStatement(searchForID);   
	    ResultSet rs = searchStmt.executeQuery(searchForID);
	    while(rs.next()){
	    	idInt = rs.getInt(1);
	    }
	    
	    idString = Integer.toString(idInt);
	    System.out.println(idString);
	    
		String studentUpdate = "UPDATE javabook.Scores SET score = "+ tfGrade.getText() +" WHERE studentID = "+ idInt +" AND assignmentID = "+ tfID.getText() +"";
		PreparedStatement updateStmt = conn.prepareStatement(studentUpdate);
		updateStmt.executeUpdate();
		
	    System.out.println("Update Successful");
	    conn.close();
		} catch(Exception e) {
			
		}
	}
		
	public static void main(String[] args) {
		launch(args);
	}
}
