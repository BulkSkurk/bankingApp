import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConfirmBox {

    static boolean answer;

 public static boolean display(String title, String message){
     Stage window = new Stage();

     window.initModality(Modality.APPLICATION_MODAL);
     window.setTitle(title);
     window.setMinWidth(384);
     window.setMinHeight(128);
     Label label = new Label(message);

     Button saveButton = new Button("Yes");
     Button exitButton = new Button("No");

     saveButton.setOnAction(e -> {
         answer = true;
         window.close();
     });
     exitButton.setOnAction(e -> {
         answer = false;
         window.close();
     });




     HBox layout = new HBox(10);
     layout.getChildren().addAll(label, saveButton, exitButton);
     layout.setAlignment(Pos.CENTER);
     Scene scene = new Scene(layout);
     window.setScene(scene);
     window.showAndWait();

     return answer;
 }
}
