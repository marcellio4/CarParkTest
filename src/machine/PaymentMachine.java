package machine;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Payment machine for car park
 * 
 * @param money   all inserted money from the client
 * @param timeIn  date with time format "yy/MM/dd HH:mm"
 * @param timeOut date with time format "yy/MM/dd HH:mm"
 * @author Marcel Zacharias
 */

public class PaymentMachine implements PaymentMachineInterface {

    private ArrayList<String> moneyIn = new ArrayList<>();
    private String timeIn;
    private String timeOut;
    private ArrayList<String> change = new ArrayList<>();

    public PaymentMachine(String timeIn, String timeOut) {
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    /**
     * Set money to our arrayList of inserted money
     * 
     * @param money
     */
    public void setMoneyIn(String money) {
        this.moneyIn.add(money);
    }

    /**
     * 
     * @return arrayList of all inserted money
     */
    public ArrayList<String> getInsertedMoney() {
        return this.moneyIn;
    }

    /**
     * Separate coins and notes and put them to correct object
     * 
     * @param coin
     * @param note
     */
    public void setInsertedMoneyIn(Coins coin, Notes note) {
        for (String money : moneyIn) {
            if (coin.isCoin(money)) {
                double coinMoney = coin.getPenceNumeration(money);
                coin.setInsertedCoins(coinMoney);
            }
            if (note.isNotes(money)) {
                double noteMoney = note.getNotesNumeration(money);
                note.setInsertedNotes(noteMoney);
            }
        }
    }

    /**
     * @return boolean if maximum stay is longer then 24h
     */
    @Override
    public Boolean maxStay() throws ParseException {
        int diffInDays = Math.toIntExact(this.getDifference()) / (1000 * 60 * 60 * 24);
        return (diffInDays > 0) ? true : false;
    }

    /**
     * Get time in minutes between start and end time
     * 
     * @param start date with time format "yy/MM/dd HH:mm"
     * @param end   date with time format "yy/MM/dd HH:mm"
     * @return Long number of minutes
     * @throws ParseException
     */
    public long timeDiffInMinutes() throws ParseException {
        return TimeUnit.MILLISECONDS.toMinutes(this.getDifference());
    }

    /**
     * 
     * @return long number if diference between the time in and time out
     * @throws ParseException
     */
    public Long getDifference() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm");
        Date dt2 = format.parse(this.timeOut);
        Date dt1 = format.parse(this.timeIn);

        return dt2.getTime() - dt1.getTime();
    }

    /**
     * Calculate total cost of car
     * 
     * @param number calculated time in minutes of stay
     * @return total charge for staying in the car park
     */
    public Double outpustCost(Long number) {
        double total = number - 60;
        return total / 100 + 3.00;
    }

    /**
     * @return Boolean if the inserted cash exist. either coin or note
     */
    @Override
    public Boolean AcceptPayment(String cash, Coins coin, Notes note) {
        return (coin.isCoin(cash) || note.isNotes(cash)) ? true : false;
    }

    /**
     * Recusive method Calculates all change coins and notes for refund back to
     * client
     * 
     * @param cash         total cash for refund
     * @param outputChange arrayList add all notes and coins for refund
     */
    @Override
    public ArrayList<String> refund(Double cash, ArrayList<String> outputChange) {
        DecimalFormat format = new DecimalFormat("##.00");
        cash = Double.valueOf(format.format(cash));
        if (cash >= 20.00) {
            outputChange.add("$20");
            refund((cash - 20.00), outputChange);
        }
        if (cash >= 10.00 && cash < 20.00) {
            outputChange.add("$10");
            refund((cash - 10.00), outputChange);
        }
        if (cash >= 5.00 && cash < 10.00) {
            outputChange.add("$5");
            refund((cash - 5.00), outputChange);
        }
        if (cash >= 1.00 && cash < 5.00) {
            outputChange.add("$1");
            refund((cash - 1.00), outputChange);
        }
        if (cash >= 0.50 && cash < 1.00) {
            outputChange.add("50p");
            refund((cash - 0.50), outputChange);
        }
        if (cash >= 0.20 && cash < 0.50) {
            outputChange.add("20p");
            refund((cash - 0.20), outputChange);
        }
        if (cash >= 0.10 && cash < 0.20) {
            outputChange.add("10p");
            refund((cash - 0.10), outputChange);
        }
        if (cash >= 0.05 && cash < 0.10) {
            outputChange.add("5p");
            refund((cash - 0.05), outputChange);
        }
        if (cash >= 0.02 && cash < 0.05) {
            outputChange.add("2p");
            refund((cash - 0.02), outputChange);
        }
        if (cash >= 0.01 && cash < 0.02) {
            outputChange.add("1p");
            refund((cash - 0.01), outputChange);
        }
        return outputChange;
    }

    /**
     * 
     * @return arrayList
     */
    public ArrayList<String> getMoneyChangeList() {
        return this.change;
    }

    /**
     * @return double number of all coins and notes that has been inserted by client
     */
    @Override
    public Double getTotalInsertedMoney(Coins coin, Notes note) {
        return coin.sumOfInsertedCoins() + note.sumOfInsertedNotes();
    }

}
