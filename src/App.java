import java.io.BufferedReader;
import java.io.InputStreamReader;

import FactoryParkMachine.FactoryParkMachine;
import machine.Coins;
import machine.Money;
import machine.Notes;
import machine.CarPaymentMachine;

public class App {
    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "utf-8"));
        System.out.println("Please enter Time In for car park (format: yy/MM/dd HH:mm)");
        String timeIn = reader.readLine();
        System.out.println("Please enter Time Out for car park (format: yy/MM/dd HH:mm)");
        String timeOut = reader.readLine();
        FactoryParkMachine factory = new FactoryParkMachine();
        CarPaymentMachine carPaymentMachine = (CarPaymentMachine) factory.build("CarPaymentMachine", timeIn, timeOut);

        if (carPaymentMachine.maxStay()) {
            System.out.println(
                    "It’s a max stay 24-hour car park, so cars cannot stay longer than this duration or they get towed!");
            System.exit(1);
        }

        Coins coin = (Coins) factory.create("Coins");
        Notes note = (Notes) factory.create("Notes");
        boolean flag = true;
        while (flag) {
            System.out.println("Please enter Payment for car park (format: notes $5, $10 or coins $1, 1p, 2p)");
            String cash = reader.readLine();
            if (!carPaymentMachine.AcceptPayment(cash, coin, note)) {
                System.out.println("Please enter correct cash coin or note");
            } else {
                carPaymentMachine.setInsertedMoneyIn(cash, coin, note);
                if (carPaymentMachine.outpustCost(carPaymentMachine.timeDiffInMinutes()) < carPaymentMachine
                        .getTotalInsertedMoney(coin, note)) {
                    flag = false;
                } else {
                    System.out.println(
                            "The money inserted is not suffecient to cover your car park stay. Charge of your stay is $"
                                    + carPaymentMachine.outpustCost(carPaymentMachine.timeDiffInMinutes()));
                }
            }

        }

        reader.close();

        System.out.println();
        System.out.println("/**************************************************/");
        System.out.println();

        System.out.println("Time In: " + timeIn.split(" ")[1]);
        System.out.println("Time Out: " + timeOut.split(" ")[1]);
        Integer sizeOfmoneyIn = carPaymentMachine.getInsertedMoney().size();
        int count = 0;
        System.out.print("Payment In: ");
        for (String cashMoney : carPaymentMachine.getInsertedMoney()) {
            count += 1;
            if (sizeOfmoneyIn == 1 || sizeOfmoneyIn == count) {
                System.out.println(cashMoney);
                break;
            }
            System.out.printf("%s, ", cashMoney);
        }
        System.out.println("Output Cost: $" + carPaymentMachine.outpustCost(carPaymentMachine.timeDiffInMinutes()));
        Double diffOfMoneyInAndCost = carPaymentMachine.getTotalInsertedMoney(coin, note)
                - carPaymentMachine.outpustCost(carPaymentMachine.timeDiffInMinutes());
        System.out.print("Output Change: ");
        carPaymentMachine.refund(Money.twoDecimal(diffOfMoneyInAndCost), carPaymentMachine.getMoneyChangeList());

        int size = carPaymentMachine.getMoneyChangeList().size();
        int countSize = 0;
        for (String change : carPaymentMachine.getMoneyChangeList()) {
            countSize += 1;
            if (size == 1 || size == countSize) {
                System.out.printf("%s", change);
                break;
            }
            System.out.printf("%s, ", change);
        }
    }
}
