package machine;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * @author Marcel Zacharias
 */
public interface PaymentMachine {
    public Boolean maxStay() throws ParseException;

    public Boolean AcceptPayment(String cash, Coins coin, Notes note);

    public ArrayList<String> refund(Double cash, ArrayList<String> outputChange);

    public Double getTotalInsertedMoney(Coins coin, Notes note);
}