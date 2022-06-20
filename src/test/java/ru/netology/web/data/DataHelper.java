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

    public static String getValidMonth() {
        return "01";
    }

    public static String getInvalidMonthLessThenOne() {
        return "00";
    }

    public static String getInvalidMonthMoreThenTwelve() {
        return "13";
    }

    public static String getInvalidMonthEmpty() {
        return " ";
    }

    public static String getValidYear() {
        return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getInvalidYear() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getInvalidYearEmpty() {
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

    public static String getInvalidCVVLessThenThreeNumber() {
        return "1";
    }

    public static String getInvalidCVVEmpty() {
        return " ";
    }

    public static CardData getApprovedCardData() {
        return new CardData(getApprovedCard(), getValidMonth(), getValidYear(), getValidCardOwner(), getValidCVV());
    }

    public static CardData getDeclinedCardData() {
        return new CardData(getDeclinedCard(), getValidMonth(), getValidYear(), getValidCardOwner(), getValidCVV());
    }
}
