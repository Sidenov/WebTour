package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
        return "111";
    }

    public static String getInvalidCVVHaveNumber() {
        return "1";
    }

    public static String getInvalidCVVHaveTwoNumber() {
        return "11";
    }

    public static String getInvalidCVVEmpty() {
        return " ";
    }

}
