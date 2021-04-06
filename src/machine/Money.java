package machine;

import java.text.DecimalFormat;

/**
 * @author Marcel Zacharias
 */
public interface Money {
    public Double getMoneyNumeration(String money);

    public static Double twoDecimal(Double num) {
        DecimalFormat format = new DecimalFormat("##.00");
        return Double.valueOf(format.format(num));
    }
}
