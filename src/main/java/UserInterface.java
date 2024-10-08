import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.Date;

public class UserInterface extends Application {

    Stage window;
    BorderPane layout;
    TextArea transactions;
    Date currentDate;
    Account userAccount;
    TextArea accountBalanceSum;

    Button clearFilterButton;
    ComboBox<Integer> yearDropDown;
    ComboBox<Integer> monthDropDown;
    ComboBox<Integer> weekDropDown;
    ComboBox<Integer> dayDropDown;
    ComboBox<String> incomeSpendingDropDown;



    public void startUserInterface(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        layout = new BorderPane();
        window = primaryStage;
        window.setTitle("Bank Application");
        window.setResizable(false);


        currentDate = new Date();
        userAccount = new Account(0, "Adam Thelin");
        userAccount.loadSavedTransactionsFromFile();

        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        //File menu
        Menu fileMenu = new Menu("_File");
        Menu accountMenu = new Menu("_Account");
        Menu sortMenu = new Menu("Sort _Transactions");
        //Menu items
        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction(e -> updateLabelsAndSave());
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> closeProgram());

        //Income Menu Items - Sorting by...
        MenuItem sortIncomeAsc = new MenuItem("By Date Asc");
        sortIncomeAsc.setOnAction(e -> setAccountText("asc",false));
        MenuItem sortIncomeDesc = new MenuItem("By Date Desc");
        sortIncomeDesc.setOnAction(e -> setAccountText("desc",false));

        //accountMenu Items
        MenuItem makeTransactionItem = new MenuItem("Make Deposit");
        makeTransactionItem.setOnAction(e -> performTransaction());
        MenuItem removeTransactionItem = new MenuItem("Remove Transaction");
        removeTransactionItem.setOnAction(e -> setRemoveTransaction());

        fileMenu.getItems().add(saveItem);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(exitItem);
        accountMenu.getItems().add(makeTransactionItem);
        accountMenu.getItems().add(removeTransactionItem);
        sortMenu.getItems().add(sortIncomeAsc);
        sortMenu.getItems().add(sortIncomeDesc);

        // Show by year, month, week, day
        Label showLabel = new Label("Show Only\nDates");
        showLabel.setTranslateX(-10);
        yearDropDown = new ComboBox<>();
        setComboBoxValues(yearDropDown,"year");

        monthDropDown = new ComboBox<>();
        setComboBoxValues(monthDropDown,"month");

        weekDropDown = new ComboBox<>();
        setComboBoxValues(weekDropDown,"week");

        dayDropDown = new ComboBox<>();
        setComboBoxValues(dayDropDown,"day");

        //Rensar alla combo boxes
        clearFilterButton = new Button("Clear Filters");
        clearFilterButton.setOnAction(e -> setAllFilterComboBoxValuesAndNull());
        clearFilterButton.setMinWidth(100);

        //Väljer income eller spending
        Label incomeSpendingLabel = new Label("Show Only\nIncome or Spending");
        incomeSpendingLabel.setTranslateX(-10);

        incomeSpendingDropDown = new ComboBox<>();
        incomeSpendingDropDown.getItems().addAll("Income","Spending");
        incomeSpendingDropDown.setMinWidth(100);
        incomeSpendingDropDown.setPromptText("Income/Spending");
        incomeSpendingDropDown.setOnAction(e -> updateTextArea());
        incomeSpendingDropDown.setDisable(true);

        Label accountBalanceLabel = new Label("Account Balance");
        accountBalanceLabel.setTranslateX(-10);
        accountBalanceLabel.setTranslateY(50);

        accountBalanceSum = new TextArea();
        accountBalanceSum.setEditable(false);
        accountBalanceSum.setWrapText(true);
        accountBalanceSum.setText("$" + userAccount.getAccountCurrentBalance());
        accountBalanceSum.setTranslateY(50);
        accountBalanceSum.setMaxSize(125,15);


        //Main menu bar
        VBox topContainer = new VBox();
        topContainer.setMinWidth(1024);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu,accountMenu,sortMenu);
        menuBar.setMinWidth(1024);

        HBox topCssBar = new HBox();
        topCssBar.setMinHeight(40);
        topCssBar.setStyle("-fx-background-color: darkgreen;");

        Label topBarText = new Label("Adam Bank - Pengar, vilka pengar?");
        topBarText.setStyle("-fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 20px; -fx-font-weight: bold; -fx-font-style: italic;");
        topBarText.setPadding(new Insets(10,0,0,10));

        topCssBar.getChildren().add(topBarText);

        topContainer.getChildren().addAll(menuBar,topCssBar);

        layout.setTop(topContainer);


        //layout.setTop(menuBar);

        //Skapar mittendelen av konsolen
        transactions = new TextArea();
        transactions.setEditable(false);
        transactions.setWrapText(true);

        //Sätter den högra delen
        VBox rightSidePane = new VBox(10);
        rightSidePane.getChildren().addAll(clearFilterButton,showLabel,yearDropDown,monthDropDown,weekDropDown,dayDropDown);
        rightSidePane.setPadding(new Insets(20));
        rightSidePane.setAlignment(Pos.CENTER_RIGHT);

        VBox centerRightPane = new VBox(10);
        Label yearLabel = new Label(" Year");
        yearLabel.setTranslateY(15);
        Label monthLabel = new Label("Month");
        monthLabel.setTranslateY(30);
        Label weekLabel = new Label(" Week");
        weekLabel.setTranslateY(40);
        Label dayLabel = new Label("  Day");
        dayLabel.setTranslateY(50);

        centerRightPane.getChildren().addAll(yearLabel,monthLabel,weekLabel,dayLabel);
        centerRightPane.setPadding(new Insets(20));
        centerRightPane.setAlignment(Pos.CENTER);
        centerRightPane.setTranslateY(15);
        centerRightPane.setTranslateX(40);

        VBox leftSidePane = new VBox(10);
        leftSidePane.getChildren().addAll(incomeSpendingLabel,incomeSpendingDropDown,accountBalanceLabel,accountBalanceSum);
        leftSidePane.setPadding(new Insets(20));
        leftSidePane.setAlignment(Pos.CENTER_LEFT);
        leftSidePane.setTranslateY(-15);

        HBox rightContainer = new HBox(10);
        rightContainer.getChildren().addAll(leftSidePane,centerRightPane,rightSidePane);
        rightContainer.setPadding(new Insets(20));
        layout.setRight(rightContainer);

        //Padding runt TextArea och StackPane
        StackPane centerPane = new StackPane(transactions);
        centerPane.setPadding(new Insets(20));
        centerPane.setMaxSize(650,384);
        centerPane.setTranslateX(0);
        centerPane.setTranslateY(50);
        layout.setCenter(centerPane);

        //Första sorteringen av data
        setAccountText("asc",false);

        Scene scene = new Scene(layout, 1024, 512);
        window.setScene(scene);
        window.show();
    }
    private void closeProgram(){
        boolean answer = ConfirmBox.display("Exit", "Do you wish to exit?");
        if (answer){
            window.close();
        }
    }
    private void performTransaction(){
        double amountToTransfer = TransactionBox.display("Make a Transaction","Please enter how much to deposit");
        if (amountToTransfer != 0) {
            userAccount.makeTransaction(LocalDateTime.now(), amountToTransfer);
            userAccount.saveTransactionsToFile();
            setAccountBalanceSum();
            setAccountText("asc", false);
            setAllFilterComboBoxValues();
        }
    }
    private void setAccountText(String sorting, boolean save){
        if (save) {
            transactions.setText("---SAVED---" + "\n -  Transaction History for: " + userAccount.getAccountHolder() + "\n\n" + userAccount.getTransactions(sorting));
        } else {
            transactions.setText("Transaction History for: " + userAccount.getAccountHolder() + "\n\n" + userAccount.getTransactions(sorting));
        }
    }
    private void setFiltering(Integer input, String type){
        if (input != null){
            updateTextArea(input,type);
        }
    }
    private void updateTextArea(int input, String type){
        transactions.setText("Transaction History for: " + userAccount.getAccountHolder() + " Showing only Transactions in " + type + " " + input + "\n\n" + userAccount.dateStringSorter(String.valueOf(input),type));
        incomeSpendingDropDown.setDisable(false);
        updateButtonLabels(type);
    }
    private void updateTextArea(){
        String transactionText = transactions.getText();
        String[] lines = transactionText.split("\n");
        StringBuilder filteredText = new StringBuilder();

        if (incomeSpendingDropDown.getValue() != null) {

            if (incomeSpendingDropDown.getValue().equalsIgnoreCase("income")) {
                for (String line : lines) {
                    if (!line.contains("Amount: -")) {
                        filteredText.append(line).append("\n");
                    }
                }
                transactions.setText(filteredText.toString());
            }
            if (incomeSpendingDropDown.getValue().equalsIgnoreCase("spending")) {
                for (String line : lines) {
                    if (line.contains("Amount: -")) {
                        filteredText.append(line).append("\n");
                    }
                }
                transactions.setText(filteredText.toString());
            }
        } else {
            transactions.setText("No transactions found!");
        }
        incomeSpendingDropDown.setDisable(true);
    }
    private void updateButtonLabels(String type){

        if (!type.equalsIgnoreCase("year")){
            yearDropDown.getSelectionModel().clearSelection();
        }
        if (!type.equalsIgnoreCase("month")){
            monthDropDown.getSelectionModel().clearSelection();
        }
        if (!type.equalsIgnoreCase("week")){
            weekDropDown.getSelectionModel().clearSelection();
        }
        if (!type.equalsIgnoreCase("day")){
            dayDropDown.getSelectionModel().clearSelection();
        }
    }
    private void updateLabelsAndSave(){
        userAccount.saveTransactionsToFile();
        setAccountText("asc",true);
        setAccountBalanceSum();
        setAllFilterComboBoxValues();
    }
    private void setAccountBalanceSum(){
        accountBalanceSum.setText("$" + userAccount.getAccountCurrentBalance());
    }
    private void setRemoveTransaction(){
        RemoveBox.display(userAccount);
        setAccountText("asc",false);
        setAccountBalanceSum();
        setAllFilterComboBoxValues();
    }
    private void setComboBoxValues(ComboBox<Integer> boxName, String dateType){
        boxName.getItems().addAll(userAccount.getTransactionDateValues(dateType));
        boxName.setMinWidth(100);
        boxName.setPromptText(String.valueOf(dateType.charAt(0)).toUpperCase() + dateType.substring(1));
        boxName.setOnAction(e -> setFiltering(boxName.getValue(),dateType));
    }
    private void setAllFilterComboBoxValues(){
        yearDropDown.getItems().clear();
        monthDropDown.getItems().clear();
        weekDropDown.getItems().clear();
        dayDropDown.getItems().clear();
        setComboBoxValues(yearDropDown, "year");
        setComboBoxValues(monthDropDown, "month");
        setComboBoxValues(weekDropDown, "week");
        setComboBoxValues(dayDropDown, "day");
    }
    private void setAllFilterComboBoxValuesAndNull(){
        yearDropDown.setValue(null);
        monthDropDown.setValue(null);
        weekDropDown.setValue(null);
        dayDropDown.setValue(null);
        incomeSpendingDropDown.setValue(null);
        incomeSpendingDropDown.setDisable(true);
    }

}
