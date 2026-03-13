package sys_facturation.com.util;

import java.math.BigDecimal;

/**
 * Converts a monetary amount to Spanish words for SUNAT's Note field.
 * Example: 118.00 → "SON CIENTO DIECIOCHO CON 00/100 SOLES"
 */
public class NumberToWords {

    private static final String[] UNITS = {
        "", "UN", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE",
        "DIEZ", "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE", "DIECISEIS",
        "DIECISIETE", "DIECIOCHO", "DIECINUEVE", "VEINTE"
    };

    private static final String[] TENS = {
        "", "DIEZ", "VEINTE", "TREINTA", "CUARENTA", "CINCUENTA",
        "SESENTA", "SETENTA", "OCHENTA", "NOVENTA"
    };

    private static final String[] HUNDREDS = {
        "", "CIENTO", "DOSCIENTOS", "TRESCIENTOS", "CUATROCIENTOS", "QUINIENTOS",
        "SEISCIENTOS", "SETECIENTOS", "OCHOCIENTOS", "NOVECIENTOS"
    };

    public static String convert(BigDecimal amount, String currency) {
        long intPart = amount.longValue();
        int decPart = amount.remainder(BigDecimal.ONE).multiply(BigDecimal.valueOf(100)).intValue();
        String words = convertInteger(intPart);
        return String.format("SON %s CON %02d/100 %s", words, decPart, currency.toUpperCase());
    }

    private static String convertInteger(long n) {
        if (n == 0) return "CERO";
        if (n < 0) return "MENOS " + convertInteger(-n);

        StringBuilder sb = new StringBuilder();

        if (n >= 1_000_000) {
            long millions = n / 1_000_000;
            sb.append(millions == 1 ? "UN MILLON" : convertInteger(millions) + " MILLONES");
            n %= 1_000_000;
            if (n > 0) sb.append(" ");
        }

        if (n >= 1_000) {
            long thousands = n / 1_000;
            sb.append(thousands == 1 ? "MIL" : convertInteger(thousands) + " MIL");
            n %= 1_000;
            if (n > 0) sb.append(" ");
        }

        if (n >= 100) {
            int h = (int) (n / 100);
            sb.append(n == 100 ? "CIEN" : HUNDREDS[h]);
            n %= 100;
            if (n > 0) sb.append(" ");
        }

        if (n > 20) {
            int t = (int) (n / 10);
            int u = (int) (n % 10);
            sb.append(TENS[t]);
            if (u > 0) sb.append(" Y ").append(UNITS[u]);
        } else if (n > 0) {
            sb.append(UNITS[(int) n]);
        }

        return sb.toString().trim();
    }
}
