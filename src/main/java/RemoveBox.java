import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class RemoveBox {
    public static void display(Account userAccount){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Remove Transaction");
        window.setMinWidth(425);
        window.setMinHeight(150);
        Label label = new Label();

        ComboBox<StringBuilder> transactions = new ComboBox<>();
        transactions.setPromptText("Choose transaction to remove");
        String[] transactionList = userAccount.getTransactions("asc").toString().split("\n");
        for (String transaction : transactionList) {
            transactions.getItems().add(new StringBuilder(transaction));
        }
        //TODO - This works, It adds the text to different instances in the combo box. Cool.
        //TODO - Now we need to map the transaction to the text to remove it.
        //TODO - Just Remove the String, append to string and save to the userAccount class.

        transactions.setMinWidth(256);

        Button removeButton = new Button("Remove");
        Button exitButton = new Button("Exit");

        removeButton.setOnAction(e -> {
            try {
                System.out.println(transactions.getValue());
                window.close();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "How did you manage this?");
                alert.showAndWait();
            }
        });
        exitButton.setOnAction(e -> window.close());

        HBox top = new HBox(10);
        top.getChildren().addAll(label);
        top.setAlignment(Pos.TOP_CENTER);

        HBox middle = new HBox(10);
        middle.getChildren().addAll(transactions);
        middle.setAlignment(Pos.CENTER);

        HBox bottom = new HBox(10);
        bottom.getChildren().addAll(removeButton, exitButton);
        bottom.setAlignment(Pos.BOTTOM_CENTER);

        VBox layoutColumn = new VBox(10);
        layoutColumn.getChildren().addAll(top,middle,bottom);

        Scene scene = new Scene(layoutColumn);
        window.setScene(scene);
        window.showAndWait();
    }
}
