package FactoryParkMachine;

import machine.CarPaymentMachine;
import machine.Coins;
import machine.Money;
import machine.Notes;
import machine.PaymentMachine;

/**
 * Creates and build for us classes type for Park machine software
 */
public class FactoryParkMachine implements FactoryPark {

    @Override
    public PaymentMachine build(String type, String timeIn, String timeOut) {
        if (type == "CarPaymentMachine") {
            return new CarPaymentMachine(timeIn, timeOut);
        } else {
            throw new IllegalArgumentException("Such class " + type + " does not exist!");
        }
    }

    @Override
    public Money create(String type) {
        switch (type) {
        case "Coins":
            return new Coins();
        case "Notes":
            return new Notes();
        default:
            throw new IllegalArgumentException("Such class " + type + " does not exist!");
        }
    }

}
