package sample;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

/**
 * This was built in IntellJ
 * JavaFX sdk 13 was added to the compiler and to the VM.
 */

public class Main extends Application {

    public static final double Width = 500;
    public static final double Height = 500;

    /**
     * group is the background.
     * group2 has the mouse events and the node commands
     * and sits on top of group.
     * The width and height are an arbitrary reference.
     *
     */

    public static Group group = new Group();
    public static Group group2 = new Group();
    Camera camera = new PerspectiveCamera();
    Double anchorX, anchorY;
    Double anchorAngleX = 0.0;
    Double anchorAngleY = 0.0;
    final DoubleProperty angleX = new SimpleDoubleProperty(0);
    final DoubleProperty angleY = new SimpleDoubleProperty(0);

    public void start(Stage stage){
        stage.setTitle("JavaFX Commands");
        group.getChildren().add(group2);
        group2.setLayoutX(700);
        group2.setLayoutY(300);

        Scene scene = new Scene(group, Width, Height);

        GroupGestures groupGestures = new GroupGestures(group2);
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, groupGestures.getOnMousePressedEventHandler());
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, groupGestures.getOnMouseDraggedEventHandler());

        /**
         * GenTextFieldA builds the 2 text field nodes on the background group
         * and sets the default event with the return key.
         * GenRect and GenText build nodes on the background group.
         */

        GenTextFieldA(500,10);
        Funct.GenRect(800,220,15,50);
        Funct.GenText("Object List",Funct.objectx+35,Funct.objecty);

        /**
         * line_0 and line_1 will be the x and y axes.
         */

        Funct.lineM(0,-400,0,400);
        Funct.lineM(-400,0,400,0);

        /**
         * These translate Property calls puts the nodes in
         * reference to (0, 0, 0).
         * The initMouseControl puts the left click roll and
         * the scroll on group 2. There are no mouse events on
         * the background group.
         * The Drag and GroupGestures classes handle the right click
         * panning for group 2.
         */

        group2.translateXProperty().set(Width/2);
        group2.translateYProperty().set(Height/2);
        group2.translateZProperty().set(0);
        initMouseControl(group2, scene, stage);

        Funct.fxmlLoader("test.fxml");

        scene.setFill(Color.WHITE);
        scene.setCamera(camera);
        stage.setScene(scene);
        stage.show();

    }//start

    static TextField textfieldA = new TextField();
    static TextField textfieldB = new TextField();

    public void GenTextFieldA(double x, double y){
        group.getChildren().addAll(textfieldA,textfieldB);
        textfieldA.setLayoutX(x);
        textfieldA.setLayoutY(y);
        textfieldB.setLayoutX(x);
        textfieldB.setLayoutY(y+30);
        textfieldA.setText("Enter Command");
        textfieldA.setPrefColumnCount(50);
        textfieldB.setText("Return Method Call");
        textfieldB.setPrefColumnCount(50);

        EventHandler<ActionEvent> eventA = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event)
            {
                Funct.decision();
            }
        };
        textfieldA.setOnAction(eventA);
    }//GenTextFieldA

    private void initMouseControl (Group group, Scene scene, Stage stage){
        Rotate xRotate;
        Rotate yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);
        scene.setOnMousePressed(event ->{
            if(event.getButton() == MouseButton.PRIMARY) {
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
                anchorAngleX = angleX.get();
                anchorAngleY = angleY.get();
            }
        });
        scene.setOnMouseDragged((MouseEvent event) -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
                angleY.set(anchorAngleY - (anchorX - event.getSceneX()));
            }
        });
        stage.addEventHandler(ScrollEvent.SCROLL, event -> {double delta = event.getDeltaY();
            group.translateZProperty().set(group.getTranslateZ() + delta);
        });
    }//mousecontrol

    /**
     * In the main method launch args has to be last.
     * Anything after launch args will not run.
     * makeRunAll builds the node command library.
     */

    public static void main(String[] args) {
        Runnable r1 = () -> {
            Funct.makeRunAll();
            System.out.println("Total memory available to java in mb");
            System.out.println(Runtime.getRuntime().totalMemory() / Math.pow(10, 6));
            System.out.println("Program memory usage in mb");
            System.out.println((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / Math.pow(10, 6));
            System.out.println("Max memory available to java in gb");
            System.out.println(Runtime.getRuntime().maxMemory()/ Math.pow(10, 9));
        };
        Runnable r2 = () -> launch(args);

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();

    }//main
}//class

