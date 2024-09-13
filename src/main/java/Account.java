import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.*;

public class Account {

    private double currentBalance;
    private String accountHolder;
    private ArrayList<Transaction> transactions;
    private DateTimeFormatter dateFormat;

    Account(int currentBalance, String accountHolder) {
        this.currentBalance = currentBalance;
        this.accountHolder = accountHolder;
        this.transactions = new ArrayList<>();
        dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    public void makeTransaction(LocalDateTime dateOfTransaction, double balanceToChange) {
        //Skapar ett nytt transaktionsobjekt och lägger till det i ArrayListen.
        Transaction transaction = new Transaction(dateOfTransaction, balanceToChange);
        transactions.add(transaction);
    }

    public void printAccountBalance() {
        //Testfunktion - Ta bort sedan
        System.out.println("Account Holder:" + accountHolder + " Current Balance:" + getAccountCurrentBalance());
    }

    public double getAccountCurrentBalance() {
        //Loopar igenom samtliga transactions i Arraylist och uppdaterar currentBalance.
        currentBalance = 0; //Återställer värdet till 0, om vi inte gör det så kommer Sum beräkningen i UI att lägga på numret varje gång.
        for (Transaction transaction : transactions) {
            currentBalance += transaction.getTransactionAmount();
        }
        return currentBalance;
    }
    public String getAccountHolder(){
        return this.accountHolder;
    }

    public StringBuilder getTransactions(String sorting) {
        dateSorter(sorting);

        StringBuilder output = new StringBuilder();
        for (Transaction transaction : transactions) {
            output.append("Amount: ").append(transaction.getTransactionAmount()).append(" - Date Made: ").append(transaction.getDateOfTransaction().format(dateFormat)).append("\n");
        }
        return output;
    }
    public void dateSorter(String sorting){
        switch (sorting) {
            case "desc" ->
                    transactions.sort((o1, o2) -> o2.getDateOfTransaction().compareTo(o1.getDateOfTransaction()));
            case "asc" -> transactions.sort((o1, o2) -> o1.getDateOfTransaction().compareTo(o2.getDateOfTransaction()));
        }
    }

    public StringBuilder dateStringSorter(String input, String type){
        StringBuilder output = new StringBuilder();
        for (Transaction transaction : transactions) {
            if (transaction.getDateOfTransaction().getYear() == Integer.parseInt(input) && type.equalsIgnoreCase("year")) {
                output.append("Amount: ").append(transaction.getTransactionAmount()).append(" - Date Made: ").append(transaction.getDateOfTransaction().format(dateFormat)).append("\n");
            }
            if (transaction.getDateOfTransaction().getMonthValue() == Integer.parseInt(input) && type.equalsIgnoreCase("month")) {
                output.append("Amount: ").append(transaction.getTransactionAmount()).append(" - Date Made: ").append(transaction.getDateOfTransaction().format(dateFormat)).append("\n");
            }
            if (transaction.getDateOfTransaction().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == Integer.parseInt(input) && type.equalsIgnoreCase("week")) {
                output.append("Amount: ").append(transaction.getTransactionAmount()).append(" - Date Made: ").append(transaction.getDateOfTransaction().format(dateFormat)).append("\n");
            }
            if (transaction.getDateOfTransaction().getDayOfMonth() == Integer.parseInt(input) && type.equalsIgnoreCase("day")) {
                output.append("Amount: ").append(transaction.getTransactionAmount()).append(" - Date Made: ").append(transaction.getDateOfTransaction().format(dateFormat)).append("\n");
            }
        }
        return output;
    }


    public void saveTransactionsToFile() {
        try {
            //Sparar info ifrån enstaka Transaktioner med ett "-" för att hjälpa parsing när vi laddar ifrån filen.
            PrintWriter writer = new PrintWriter("accountInfo.dat");
            for (Transaction transaction : transactions) {
                writer.println(transaction.getDateOfTransaction().format(dateFormat) + "|" + transaction.getTransactionAmount());
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }
    public Set<Integer> getTransactionDateValues(String searchTerm){
        Set<Integer> searchFilterTerm = new TreeSet<>();

        for (Transaction transaction : transactions){
            switch (searchTerm) {
                case "year" -> searchFilterTerm.add(transaction.getDateOfTransaction().getYear());
                case "month" -> searchFilterTerm.add(transaction.getDateOfTransaction().getMonthValue());
                case "week" ->
                        searchFilterTerm.add(transaction.getDateOfTransaction().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
                case "day" -> searchFilterTerm.add(transaction.getDateOfTransaction().getDayOfMonth());
            }
        }
        return searchFilterTerm;
    }

    public void loadSavedTransactionsFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("accountInfo.dat"));
            String line = br.readLine();

            //Används för att avgöra att importerad info ifrån .txt filen följer rätt format så att vi kan skapa ett
            //date object ifrån informationen i Strängen.

            while (line != null) {
                String[] accountInfoSplit = line.split("\\|");

                LocalDateTime date = LocalDateTime.parse(accountInfoSplit[0].trim(), dateFormat);
                double balance = Double.parseDouble(accountInfoSplit[1]);

                makeTransaction(date, balance);
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
