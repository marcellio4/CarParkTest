package FactoryParkMachine;

import machine.Money;
import machine.PaymentMachine;

/**
 * @author Marcel Zacharias
 */
public interface FactoryPark {

    public PaymentMachine build(String type, String timeIn, String timeOut);

    public Money create(String type);
};