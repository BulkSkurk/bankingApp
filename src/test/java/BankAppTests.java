import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class BankAppTests {

    //DONE - Add At least five tests

    @Test
    public void testCreatingTransaction(){
        LocalDateTime currentDate = LocalDateTime.now();
        Transaction testTransaction = new Transaction(currentDate,200.5);

        double actual = testTransaction.getTransactionAmount();
        double expected = 200.5;

        assertEquals(expected,actual,0.01);
    }
    @Test
    public void testCreatingSpendingTransaction(){
        LocalDateTime currentDate = LocalDateTime.now();

        Transaction testTransaction = new Transaction(currentDate,-200.1);

        double actual = testTransaction.getTransactionAmount();
        double expected = -200.1;

        assertEquals(expected,actual, 0.01);
    }
    @Test
    public void testGettingAccountHolder(){
        Account testAccount = new Account(0, "Iron Deficiency Man");

        String actual = testAccount.getAccountHolder();
        String expected = "Iron Deficiency Man";

        assertEquals(expected,actual);
    }
    @Test
    public void testMultiplePositiveTransactions(){
        LocalDateTime currentDate = LocalDateTime.now();

        Account testAccount = new Account(0, "The Incredible Bulk");

        testAccount.makeTransaction(currentDate, 500);
        testAccount.makeTransaction(currentDate, 125);
        testAccount.makeTransaction(currentDate, 5);
        testAccount.makeTransaction(currentDate, 20);

        double actual = testAccount.getAccountCurrentBalance();
        double expected = 650;

        assertEquals(expected,actual,0.01);
    }
    @Test
    public void testMultipleNegativeTransactions(){
        LocalDateTime currentDate = LocalDateTime.now();

        Account testAccount = new Account(0, "The Incredible Bulk");

        testAccount.makeTransaction(currentDate, -500);
        testAccount.makeTransaction(currentDate, -125);
        testAccount.makeTransaction(currentDate, -5);
        testAccount.makeTransaction(currentDate, -20);

        double actual = testAccount.getAccountCurrentBalance();
        double expected = -650;

        assertEquals(expected,actual,0.01);
    }
    
}
