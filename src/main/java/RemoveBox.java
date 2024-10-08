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

        transactions.setMinWidth(256);

        Button removeButton = new Button("Remove");
        Button exitButton = new Button("Exit");

        removeButton.setOnAction(e -> {
            try {

                //Build string into correct format
                StringBuilder trimmedString = transactions.getValue().delete(0,8);
                int dIndex = trimmedString.indexOf("D");
                int colonIndex = trimmedString.indexOf(":");
                trimmedString.delete(dIndex-3,colonIndex);

                String[] testArray = trimmedString.toString().split(":",2);
                String modulatedString = testArray[1] + "|" + testArray[0];

                //Remove transaction from list
                userAccount.removeTransaction(modulatedString.trim());
                window.close();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please choose an option!");
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
