import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class TransactionBox {

    static double amount;

 public static double display(String title, String message){
     Stage window = new Stage();

     window.initModality(Modality.APPLICATION_MODAL);
     window.setTitle(title);
     window.setMinWidth(425);
     window.setMinHeight(150);
     Label label = new Label(message);

     TextField amountField = new TextField();
     amountField.setPromptText("Enter Amount");

     Button saveButton = new Button("Save");
     Button discardButton = new Button("Discard");

     saveButton.setOnAction(e -> {
         try {
             amount = Double.parseDouble(amountField.getText());
             window.close();
         } catch (Exception ex) {
             Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid number!");
             alert.showAndWait();
         }
     });
     discardButton.setOnAction(e -> {
         amount = 0;
         window.close();
     });

     HBox top = new HBox(10);
     top.getChildren().addAll(label);
     top.setAlignment(Pos.TOP_CENTER);

     HBox middle = new HBox(10);
     middle.getChildren().addAll(amountField);
     middle.setAlignment(Pos.CENTER);

     HBox bottom = new HBox(10);
     bottom.getChildren().addAll(saveButton, discardButton);
     bottom.setAlignment(Pos.BOTTOM_CENTER);

     VBox layoutColumn = new VBox(10);
     layoutColumn.getChildren().addAll(top,middle,bottom);

     Scene scene = new Scene(layoutColumn);
     window.setScene(scene);
     window.showAndWait();



     return amount;
 }
}
