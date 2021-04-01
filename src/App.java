import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import machine.Coins;
import machine.Notes;
import machine.PaymentMachine;

public class App {
    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "utf-8"));
        System.out.println("Please enter Time In for car park (format: yy/MM/dd HH:mm)");
        String timeIn = reader.readLine();
        System.out.println("Please enter Time Out for car park (format: yy/MM/dd HH:mm)");
        String timeOut = reader.readLine();
        PaymentMachine machine = new PaymentMachine(timeIn, timeOut);
        Coins coin = new Coins();
        Notes note = new Notes();
        boolean flag = true;
        while (flag) {
            System.out.println("Please enter Payment for car park (format: notes $5, $10 or coins $1, 1p, 2p)");
            String cash = reader.readLine();
            if (!machine.AcceptPayment(cash, coin, note)) {
                System.out.println("Please enter correct cash coin or note");
            } else {
                machine.setInsertedMoneyIn(cash, coin, note);
                if (machine.outpustCost(machine.timeDiffInMinutes()) < machine.getTotalInsertedMoney(coin, note)) {
                    flag = false;
                } else {
                    System.out.println(
                            "The money inserted is not suffecient to cover your car park stay. Charge of your stay is $"
                                    + machine.outpustCost(machine.timeDiffInMinutes()));
                }
            }

        }

        reader.close();

        if (machine.maxStay()) {
            System.out.println(
                    "It’s a max stay 24-hour car park, so cars cannot stay longer than this duration or they get towed!");
            System.exit(1);
        }

        System.out.println();
        System.out.println("/**************************************************/");
        System.out.println();

        System.out.println("Time In: " + timeIn.split(" ")[1]);
        System.out.println("Time Out: " + timeOut.split(" ")[1]);
        Integer sizeOfmoneyIn = machine.getInsertedMoney().size();
        int count = 0;
        System.out.print("Payment In: ");
        for (String cashMoney : machine.getInsertedMoney()) {
            count += 1;
            if (sizeOfmoneyIn == 1 || sizeOfmoneyIn == count) {
                System.out.println(cashMoney);
                break;
            }
            System.out.printf("%s, ", cashMoney);
        }
        System.out.println("Output Cost: $" + machine.outpustCost(machine.timeDiffInMinutes()));
        Double diffOfMoneyInAndCost = machine.getTotalInsertedMoney(coin, note)
                - machine.outpustCost(machine.timeDiffInMinutes());
        DecimalFormat format = new DecimalFormat("##.00");
        System.out.print("Output Change: ");
        String difference = format.format(diffOfMoneyInAndCost);
        machine.refund(Double.valueOf(difference), machine.getMoneyChangeList());

        int size = machine.getMoneyChangeList().size();
        int countSize = 0;
        for (String change : machine.getMoneyChangeList()) {
            countSize += 1;
            if (size == 1 || size == countSize) {
                System.out.printf("%s", change);
                break;
            }
            System.out.printf("%s, ", change);
        }
    }
}
