package br.com.concretesolutions.cantina.utils;

public class PriceHelper {

    /**
     *  Format value price for visualization
     *
     * @param price string value for formatting
     * @return string value formatted with two decimals
     */
    public static String formatPrice(String price) {
        if (price.contains(".")) {
            String[] split = price.split("\\.");
            if (split[1].length() == 2) {
                return convertDotToComma(price);
            } else if (split[1].length() == 1) {
                return convertDotToComma(price.concat("0"));
            } else {
                return convertDotToComma(split[0] + "." + split[1].substring(0, 2));
            }
        }
        return convertDotToComma(price.concat(".00"));
    }

    /**
     *  Format value price for save in database
     *
     * @param price double value for formatting
     * @return string double formatted with maximum two decimals
     */
    public static double formatPrice(double price) {
        if (Math.round(price) != price) {
            return Double.parseDouble(String.format("%.2f",price));
        }
        return price;
    }

    public static String convertDotToComma(String value){
        return value.replace(".", ",");
    }
}
