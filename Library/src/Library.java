	

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


public class Library extends Application {
	private TextField tfAuthor = new TextField();
	private TextField tfTitle = new TextField();
	private TextField tfFirstName = new TextField();
	private TextField tfLastName = new TextField();
	
	private TextArea taDisplay = new TextArea();
	
	private TextField tfISBN = new TextField();
	
	private Button btCheckOut = new Button("Check Out");
	private Button btReturn = new Button("Return");
	private Button btBooks = new Button("Display Books");
	private Button btcheckedOutBooks = new Button("Checked Out Books");
	private Button btPatrons = new Button("List Patrons");
	private Button btwhoHasBook = new Button("Find Borrower");
	
	private Label isbnLabel = new Label("ISBN: ");
	private Label authorLabel = new Label("Author: ");
	private Label titleLabel = new Label("Title: ");
	private Label firstNameLabel = new Label("First Name: ");
	private Label lastNameLabel = new Label("Last Name: ");
	
	private Statement stmt;
	String url = "jdbc:mysql://localhost/javabook";
  	String user = "scott";
  	String password = "tiger";

  	
    
	@Override
	public void start(Stage primaryStage) throws SQLException{
		try {
			initializeDB();
			Connection conn = DriverManager.getConnection(url, user, password);
		    stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
	                   ResultSet.CONCUR_UPDATABLE);
			
		    		
			HBox box1 = new HBox(5);
			box1.getChildren().addAll(titleLabel, tfTitle, authorLabel, tfAuthor);
			box1.setPadding(new Insets(10, 10, 10, 10));
			box1.setSpacing(10);
			
			HBox box2 = new HBox(5);
			box2.getChildren().addAll(isbnLabel, tfISBN);
			box2.setPadding(new Insets(10, 10, 10, 10));
			box2.setSpacing(10);
		    
			HBox box3 = new HBox(5);
			box3.getChildren().addAll(firstNameLabel, tfFirstName, lastNameLabel, tfLastName);
			box3.setPadding(new Insets(10, 10, 10, 10));
			box3.setSpacing(10);
			
			HBox box4 = new HBox(5);
			box4.getChildren().add(taDisplay);
			box4.setPadding(new Insets(10, 10, 10, 10));
			box4.setSpacing(10);
			
			HBox box6 = new HBox(5);
			box6.getChildren().addAll(btCheckOut, btReturn);
			box6.setPadding(new Insets(5, 5, 5, 5));
			box6.setSpacing(10);
			
			HBox box7 = new HBox(5);
			box7.getChildren().addAll(btBooks, btPatrons, btwhoHasBook, btcheckedOutBooks);
			box7.setPadding(new Insets(5, 5, 5, 5));
			box7.setSpacing(10);
			
			VBox mainBox = new VBox(1);
			mainBox.getChildren().addAll(box1, box2, box3, box4, box6, box7);
			
			Scene scene = new Scene(mainBox,580,380);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			btBooks.setOnAction(e -> displayBooks());
			btCheckOut.setOnAction(e -> checkOutBook());
			btcheckedOutBooks.setOnAction(e -> checkedOutBooks());
			btPatrons.setOnAction(e -> listPatrons());
			btwhoHasBook.setOnAction(e -> bookBorrowers());
			btReturn.setOnAction(e -> returnBook());

		} catch(Exception e) {
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
	      stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                  ResultSet.CONCUR_UPDATABLE);
	      
	      // Book Table 
		    String dropBookTable = "drop table if exists Book;";
		    stmt.executeUpdate(dropBookTable);
			String createBookTable = 
					  "create table Book (" +  
					  "id int not null auto_increment," +
					  "ISBN varchar(50)," +
					  "title varchar(35)," +
					  "author varchar(45)," +
					  "copies int," +
					  "primary key (id));";
			stmt.executeUpdate(createBookTable);
			
		 // Patron Table 
		    String dropPatronTable = "drop table if exists Patron;";
		    stmt.executeUpdate(dropPatronTable);
		    String createPatronTable = 
		    		  "create table Patron (" +  
					  "id int not null auto_increment," +
					  "firstname varchar(30)," +
					  "lastname varchar(30)," +
					  "street varchar(20)," +
					  "city char(20)," +
					  "state char(2)," +
					  "primary key (id));";
		    stmt.executeUpdate(createPatronTable);
		    
		    // Checked Out Book Table 
		    String sqlDropCheckedOutTable = "drop table if exists CheckedOut;";
		    stmt.executeUpdate(sqlDropCheckedOutTable);
		    String sqlCreateCheckedOutTable = 
		    		"create table CheckedOut( " +
		            "bookID int not null, " + 
				    "patronID int not null, " +
				    "primary key (bookID, patronID));";
		    stmt.executeUpdate(sqlCreateCheckedOutTable);

		    
		    // Populate Database 
		    stmt.addBatch("insert into Book (ISBN, title, author, copies) values "
		    		+ "('1947','On the Road', 'Jack Kerouac', '2');");
		    stmt.addBatch("insert into Book (ISBN, title, author, copies) values "
		    		+ "('3421','Ham on Rye', 'Charles Bukowski', '2');");
		    stmt.addBatch("insert into Book (ISBN, title, author, copies) values "
		    		+ "('5958','A Season in Hell', 'Arthur Rimbaud', '3');");
		    stmt.addBatch("insert into Book (ISBN, title, author, copies) values "
		    		+ "('8584','Lolita', 'Vladimir Nabokov', '1');");
		    stmt.addBatch("insert into Book (ISBN, title, author, copies) values "
		    		+ "('8749','Ghost Wars', 'Steve Coll', '4');");
			
		    stmt.addBatch("insert into Patron (firstname, lastname, street, city, state) values "
		    		+ "('Debbie', 'Logan', 'Harbor Street', 'Seattle', 'WA');");
		    stmt.addBatch("insert into Patron (firstname, lastname, street, city, state) values "
		    		+ "('Kelly', 'Boito', 'Maple Street', 'Blacksburg', 'VA');");
		    stmt.addBatch("insert into Patron (firstname, lastname, street, city, state) values "
		    		+ "('Alex', 'Singletary', 'Parker Avenue', 'Richmond', 'VA');");
		    stmt.addBatch("insert into Patron (firstname, lastname, street, city, state) values "
		    		+ "('Jamie', 'Silverthorne', 'Main Blvd', 'Hunkertown', 'PA');");
		    stmt.addBatch("insert into Patron (firstname, lastname, street, city, state) values "
		    		+ "('Elizabeth', 'Choi', 'Chance Place', 'Harrison', 'DE');");

		    stmt.executeBatch();
		 	    		
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }
	
	public void bookBorrowers() {
		try{
	    Connection conn = DriverManager.getConnection(url, user, password);
		String getBorrower = "SELECT javabook.Patron.firstname, javabook.Patron.lastname, javabook.Book.title "
				+ "FROM javabook.Patron "
				+ "JOIN javabook.CheckedOut "
				+ "ON javabook.Patron.id = javabook.CheckedOut.patronID "
				+ "JOIN javabook.Book ON javabook.Book.id = javabook.CheckedOut.bookID "
				+ "WHERE javabook.Book.ISBN = '"+ tfISBN.getText() +"';";
		PreparedStatement borrowerStmt = conn.prepareStatement(getBorrower);   
        ResultSet rs = borrowerStmt.executeQuery(getBorrower);
        taDisplay.clear();
        while(rs.next()){
        	//Retrieve by column name
        	taDisplay.appendText(rs.getString(1) + " ");
        	taDisplay.appendText(rs.getString(2) + " ");
        	taDisplay.appendText(rs.getString(3) + " ");
        	taDisplay.appendText("\n");
        }
		
		}catch(Exception e) {
			
		}
	}
	
	public void listPatrons() {
		try{
	    Connection conn = DriverManager.getConnection(url, user, password);
	    Statement patronStmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
		String getPatrons = "select * from Patron";
	    ResultSet rs = patronStmt.executeQuery(getPatrons);
	    taDisplay.clear();
	    while(rs.next()){
	    	taDisplay.appendText(rs.getString(2) + " ");
	    	taDisplay.appendText(rs.getString(3) + " \n");
	    }
		}catch(Exception e) {
			
		}
	   		 
	}
	
	public void displayBooks() {
		try{
		    Connection conn = DriverManager.getConnection(url, user, password);
			String getBooks = "select * from Book;";
			PreparedStatement bookStmt = conn.prepareStatement(getBooks);   
	        ResultSet rs = bookStmt.executeQuery(getBooks);
	        taDisplay.clear();
	        while(rs.next()){
	        	//Retrieve by column name
	        	taDisplay.appendText("ISBN: ");
	        	taDisplay.appendText(rs.getString(2) + " ");
	        	taDisplay.appendText(rs.getString(3) + " ");
	        	taDisplay.appendText(" by ");
	        	taDisplay.appendText(rs.getString(4) + " ");
	        	taDisplay.appendText(" Number of Copies: ");
	        	taDisplay.appendText(rs.getString(5) + " ");
	        	taDisplay.appendText("\n");
	        }
	        
		}catch(Exception e){
			
		}
	}
	
	public void returnBook() {
		try{
		    Connection conn = DriverManager.getConnection(url, user, password);
			
	        // Get the book
	        String checkOutBook = "SELECT * FROM Book WHERE ISBN='"+ tfISBN.getText() +"';";
	        PreparedStatement getCopies = conn.prepareStatement(checkOutBook);
		    ResultSet rsfind = getCopies.executeQuery();
		    int copies = 0;
	        int bookID = 0;
	        while(rsfind.next()) {
	        	bookID = rsfind.getInt(1);
	        	copies = rsfind.getInt(5);
	        }
	        
	        // Subtract a copy of the book
	        copies++;
	        taDisplay.setText("Returned " + tfISBN.getText());
	        
	        // Update copies in the database
	        String updateCopies = "UPDATE Book SET copies = "+copies+" WHERE ISBN = '"+ tfISBN.getText() +"';";
	        PreparedStatement updateStmt = conn.prepareStatement(updateCopies);
	        updateStmt.executeUpdate();
	        
	        // Delete record to CheckedOut Table
	        String bookIDQuery = "SELECT javabook.CheckedOut.bookID "
	        		+ "FROM javabook.CheckedOut JOIN javabook.Patron "
	        		+ "ON javabook.Patron.id = javabook.CheckedOut.patronID "
	        		+ "JOIN javabook.Book ON javabook.Book.id = javabook.Patron.id "
	        		+ "WHERE javabook.Patron.firstname = '"+ tfFirstName.getText() +"' "
	        				+ "AND javabook.Patron.lastname = '"+ tfLastName.getText() 
	        				+"' AND javabook.CheckedOut.bookID = '"+ bookID +"';";       
	        PreparedStatement getBookID = conn.prepareStatement(bookIDQuery);

	        ResultSet rsPatron = getBookID.executeQuery();
	        while(rsPatron.next()) {
	        	bookID = rsPatron.getInt(1);
	        }

	        String returnBookQuery = "delete from javabook.CheckedOut where javabook.CheckedOut.bookID = '"+ bookID +"'";

	        PreparedStatement myStmt = conn.prepareStatement(returnBookQuery);
	      	myStmt.executeUpdate();
	      	

		}catch(Exception e){
			
		}
	}
	
	public void checkOutBook() {
		try{
		    Connection conn = DriverManager.getConnection(url, user, password);
			String getBooks = "select * from Book;";
			PreparedStatement findStmt = conn.prepareStatement(getBooks);   
	        ResultSet rs = findStmt.executeQuery(getBooks);
	        taDisplay.clear();
	        while(rs.next()){
	        	//Retrieve by column name
	        	taDisplay.appendText("ISBN#: ");
	        	taDisplay.appendText(rs.getString(2) + " ");
	        	taDisplay.appendText(rs.getString(3) + " ");
	        	taDisplay.appendText(" by ");
	        	taDisplay.appendText(rs.getString(4) + " ");
	        	taDisplay.appendText(" Number of Copies: ");
	        	taDisplay.appendText(rs.getString(5) + " ");
	        	taDisplay.appendText("\n");
	        }
	        // Get the book
	        String checkOut = "SELECT * FROM Book WHERE ISBN='"+ tfISBN.getText() +"';";
	        PreparedStatement getCopies = conn.prepareStatement(checkOut);
		    ResultSet rsfind = getCopies.executeQuery();
		    int copies = 0;
	        int bookID = 0;
	        while(rsfind.next()) {
	        	bookID = rsfind.getInt(1);
	        	copies = rsfind.getInt(5);
	        }
	        
	        if (copies > 0){
	        // Subtract a copy of the book
	        copies--;
	        taDisplay.setText("Checked Out " + tfISBN.getText());
	        
	        // Update copies in the database
	        String updateCopies = "UPDATE Book SET copies="+copies+" WHERE ISBN='"+ tfISBN.getText() +"'";
	        PreparedStatement updateStmt = conn.prepareStatement(updateCopies);
	        updateStmt.executeUpdate();
	        
	        // Add record to CheckedOut Table
	        int patronID = 0;
	        String getPatronIDQuery = "select * from Patron WHERE firstname = '"+ tfFirstName.getText() +"' AND lastname = '"+ tfLastName.getText() +"'";
	        PreparedStatement getPatronID = conn.prepareStatement(getPatronIDQuery);

	        ResultSet rsPatron = getPatronID.executeQuery();
	        while(rsPatron.next()) {
	        	patronID = rsPatron.getInt(1);
	        }

	        String addCheckedOutBook = "insert into CheckedOut (bookID, patronID) values (?,?)";
	        PreparedStatement myStmt = conn.prepareStatement(addCheckedOutBook);
	        myStmt.setInt(1, bookID);
	        myStmt.setInt(2, patronID);
	      	myStmt.executeUpdate();
	        } else {
	        	taDisplay.setText("Book is unavailable.");
	        }

		}catch(Exception e){
			
		}
	}
	
	public void checkedOutBooks() {
		try{
		    Connection conn = DriverManager.getConnection(url, user, password);
			String getCheckedOut = "SELECT javabook.Patron.firstname, javabook.Patron.lastname, javabook.Book.title"
					+ " FROM javabook.Patron "
					+ "JOIN javabook.CheckedOut "
					+ "ON javabook.Patron.id = javabook.CheckedOut.patronID "
					+ "JOIN javabook.Book "
					+ "ON javabook.Book.id = javabook.CheckedOut.bookID ";
					//+ "WHERE javabook.Patron.firstname = '"+ tfFirstName.getText() +"' "
					//+ "AND javabook.Patron.lastname = '"+ tfLastName.getText() +"';";                              
			PreparedStatement checkedOutStmt = conn.prepareStatement(getCheckedOut);   
	        ResultSet rs = checkedOutStmt.executeQuery(getCheckedOut);
	        taDisplay.clear();
	        while(rs.next()){
	        	//Retrieve by column name
	        	taDisplay.appendText(rs.getString(1) + " ");
	        	taDisplay.appendText(rs.getString(2) + " ");
	        	taDisplay.appendText(rs.getString(3) + " ");
	        	taDisplay.appendText("\n");
	        }
			
		}catch(Exception e) {
			
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
