import java.time.LocalDateTime;

public class Transaction {

    private double transactionAmount;
    private LocalDateTime dateOfTransaction;

    public Transaction(LocalDateTime dateOfTransaction ,double transactionAmount) {
        this.transactionAmount = transactionAmount;
        this.dateOfTransaction = dateOfTransaction;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public LocalDateTime getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(LocalDateTime dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }
}
