
import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class CarPane extends Pane {
  //create shapes for car	
  private Polygon carTop = new Polygon();
  private Rectangle rectangle = new Rectangle(5,40,40,10);
  private Circle wheel1= new Circle (15,55,5);
  private Circle wheel2 = new Circle (35,55,5);

  //create PathTransition
  PathTransition car = new PathTransition();
  
  //assemble the car
  public CarPane() {
	  getChildren().add(rectangle);
	  rectangle.setFill(Color.GREEN);
	    
	  getChildren().add(carTop);
	  carTop.getPoints().addAll(new Double[]{
	            40.0, 40.0,
	            30.0, 30.0,
	            20.0, 30.0,
	            10.0, 40.0});
	    carTop.setFill(Color.RED);
	    
	    getChildren().add(wheel1);
	    getChildren().add(wheel2);
	    wheel1.setFill(Color.BLACK);
	    wheel2.setFill(Color.BLACK);    
	    
  }
  
}