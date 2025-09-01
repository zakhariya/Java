package ua.od.zakhariya.other;

import java.math.BigDecimal;

public class DoubleExample {
    public static void main(String[] args) {
        BigDecimal bigDecimalValue = new BigDecimal("10.50"); // Initial BigDecimal
        double doubleValue = 5.25; // Double value to add

        // Convert the double to a BigDecimal
        BigDecimal doubleAsBigDecimal = BigDecimal.valueOf(doubleValue);

        // Add the two BigDecimal values !important to create new link
        BigDecimal result = bigDecimalValue.add(doubleAsBigDecimal);

        System.out.println("Original BigDecimal: " + bigDecimalValue);
        System.out.println("Double value: " + doubleValue);
        System.out.println("Result of addition: " + result);
/*
        If you need to round a double to a specific number of decimal places,
        you would typically use BigDecimal for precision or a combination of
        Math.round(), multiplication, and division by powers of 10. For displaying
        a rounded value, DecimalFormat or String.format() can be used.
*/
        BigDecimal progress = new BigDecimal(0.0);
        double p = 0.0;

        while (progress.doubleValue() < 1) {
            p += 0.1;

            //important to update link
            progress = progress.add(BigDecimal.valueOf(0.025));

            System.out.println(p + " " + (double) Math.round(p * 10)/10 + " " + progress.doubleValue());

            try {
                Thread.sleep(125);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
