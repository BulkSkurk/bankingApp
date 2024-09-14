import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class BankAppTests {

    //TODO - Add At least five tests

    @Test
    public void testCreatingTransaction(){
        LocalDateTime currentDate = LocalDateTime.now();
        Transaction testTransaction = new Transaction(currentDate,200.5);

        double actual = testTransaction.getTransactionAmount();
        double expected = 200.5;

        assertEquals(expected,actual,0.01);
    }
    @Test
    public void testAccountCurrentBalance(){
        LocalDateTime currentDate = LocalDateTime.now();
        Account testAccount = new Account(0, "Test McGee");
        testAccount.makeTransaction(currentDate, 500);
        testAccount.makeTransaction(currentDate, 100);

        double actual = testAccount.getAccountCurrentBalance();
        double expected = 600;

        assertEquals(expected,actual,0.01);
    }
    
}
