package machine;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @param coins         set of coins that we use for our payment
 * @param insertedCoins arrayList of all inserted coins from client
 * @author Marcel Zacharias
 */
public class Coins implements Money {
    private final String[] coins = { "1p", "2p", "5p", "10p", "20p", "50p", "$1" };
    private ArrayList<Double> insertedCoins = new ArrayList<>();

    public Coins() {
    }

    /**
     * Check if inserted coin is in our set of coins value
     * 
     * @param coin
     * @return boolean
     */
    public Boolean isCoin(String coin) {
        return Arrays.stream(this.coins).anyMatch(coin::equals);
    }

    /**
     * Match current coin string and return real flaot number
     * 
     * @param coin
     * @return
     */
    @Override
    public Double getMoneyNumeration(String money) {
        switch (money) {
        case "1p":
            return 0.1;
        case "2p":
            return 0.2;
        case "5p":
            return 0.5;
        case "10p":
            return 0.10;
        case "20p":
            return 0.20;
        case "50p":
            return 0.50;
        case "$1":
            return 1.00;
        default:
            return 0.00;
        }
    }

    /**
     * Set real number of inserted coins to arrayList
     * 
     * @param coin
     */
    public void setInsertedCoins(Double coin) {
        this.insertedCoins.add(coin);
    }

    /**
     * 
     * @return arrayList of inserted coins in numeric representation
     */
    public ArrayList<Double> getInsertedCoins() {
        return this.insertedCoins;
    }

    /**
     * 
     * @return total amount of inserted coins
     */
    public Double sumOfInsertedCoins() {
        return this.insertedCoins.stream().mapToDouble(d -> d.doubleValue()).sum();
    }

    @Override
    public String toString() {

        StringBuffer paymentCoins = new StringBuffer();
        paymentCoins.append("Coins: ");
        for (String coin : this.coins) {
            paymentCoins.append(coin + ", ");
        }

        return paymentCoins.toString();
    }

}
