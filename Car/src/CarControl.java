import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.util.Duration;


public class CarControl extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    CarPane carPane = new CarPane(); // Create a car pane
    
    //create TransitionPath to make the car move across the screen
    PathTransition car = new PathTransition(Duration.millis(10000),
  		  new Line(100,65,400,65), carPane);
    car.setCycleCount(Timeline.INDEFINITE);
    car.play(); // Start animation
    
    // Pause and resume animation
    carPane.setOnMousePressed(e -> car.pause());
    carPane.setOnMouseReleased(e -> car.play());

    //increase and decrease speed
    carPane.setOnKeyPressed(e ->{
    	switch(e.getCode()) {
    	case UP: car.setRate(car.getRate() + 0.1); break;
    	case DOWN: car.setRate(
    		      car.getRate() > 0 ? car.getRate() - 0.1 : 0); break;
    	}
    });

    // Create a scene and place it in the stage
    Scene scene = new Scene(carPane, 250, 150);
    primaryStage.setTitle("RaceCarControl"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    // Must request focus after the primary stage is displayed
    carPane.requestFocus();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
