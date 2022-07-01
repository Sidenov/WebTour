package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class CardData {
        private String cardNumber;
        private String month;
        private String year;
        private String cardOwner;
        private String CVV;
    }

    public static String getApprovedCard() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedCard() {
        return "4444 4444 4444 4442";
    }

    public static String getInvalidCard() {
        return "1111 1111 1111 1111";
    }

    public static String getInvalidCardIfFirstNumberZero() {
        return "0444 4444 4444 4441";
    }

    public static String getEmptyCard() {
        return " ";
    }

    public static String getValidCardOwner() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().fullName();
    }

    public static String getInvalidCardOwner() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String getInvalidCardOwnerEmpty() {
        return " ";
    }

    public static String getValidCVV() {
//        int code = (int) (Math.random() * 900) + 100;
//        return Integer.toString(code);
        var random = new Random();
//        return Integer.toString(random.nextInt(899) + 100);
        String a = Integer.toString(random.nextInt(10));
        String b = Integer.toString(random.nextInt(10));
        String c = Integer.toString(random.nextInt(10));
        return a+b+c;
    }

    public static String getInvalidCVVHaveOneNumber() {
        var random = new Random();
        return Integer.toString(random.nextInt(10));
    }

    public static String getInvalidCVVHaveTwoNumber() {
        var random = new Random();
        return Integer.toString(random.nextInt(90) + 10);
    }


    public static String getInvalidCVVEmpty() {
        return " ";
    }

    public static String generateDate(int months, String formatPattern) {
        return LocalDate.now().plusMonths(months).format(DateTimeFormatter.ofPattern(formatPattern));
    }

}
