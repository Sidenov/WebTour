package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.DbInteraction;
import ru.netology.web.page.MainPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

public class BuyTourOnCreditTest {

    private String generateDate(int months, String formatPattern) {
        return LocalDate.now().plusMonths(months).format(DateTimeFormatter.ofPattern(formatPattern));
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
//        Configuration.holdBrowserOpen = true;
    }

    @Test
    @DisplayName("Успешная оплата при вводе валидных значений по одобренной (Approved) карте")
    void shouldSuccessfulPaymentByApprovedCard() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCreditCard();
        fill.fillingOutTheFormForCreditPaymentTest(getApprovedCard(), generateDate(1, "MM"), generateDate(1, "yy"), getValidCardOwner(), getValidCVV());
        fill.successOperationApproved();
        var expected = "APPROVED";
        var actual = DbInteraction.getStatusBuyCredit();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Ошибка оплаты при вводе валидных значений по отклоненной (Declined) карте")
    void shouldUnsuccessfulPaymentByDeclinedCard() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCreditCard();
        fill.fillingOutTheFormForCreditPaymentTest(getDeclinedCard(), generateDate(1, "MM"), generateDate(1, "yy"), getValidCardOwner(), getValidCVV());
        fill.errorBankRefused();
        var expected = "DECLINED";
        var actual = DbInteraction.getStatusBuyCredit();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Ошибка оплаты при вводе невалидного номера карты")
    void shouldUnsuccessfulPaymentByInvalidCard() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCreditCard();
        fill.fillingOutTheFormForCreditPaymentTest(getInvalidCard(), generateDate(1, "MM"), generateDate(1, "yy"), getValidCardOwner(), getValidCVV());
        fill.errorBankRefused();
    }

    @Test
    @DisplayName("Оставить пустое поле Номер карты")
    void shouldFieldNumberOfCardIsEmpty() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCreditCard();
        fill.fillingOutTheFormForCreditPaymentTest(getEmptyCard(), generateDate(1, "MM"), generateDate(1, "yy"), getValidCardOwner(), getValidCVV());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("Ввод в поле Месяц граничное значение 0")
    void shouldFieldMonthLessThenOne() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCreditCard();
        fill.fillingOutTheFormForCreditPaymentTest(getApprovedCard(), "00", generateDate(1, "yy"), getValidCardOwner(), getValidCVV());
        fill.errorCardPeriod();
    }

    @Test
    @DisplayName("Ввод в поле Месяц граничное значение 13")
    void shouldFieldMonthMoreThenTwelve() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCreditCard();
        fill.fillingOutTheFormForCreditPaymentTest(getApprovedCard(), "13", generateDate(1, "yy"), getValidCardOwner(), getValidCVV());
        fill.errorCardPeriod();
    }

    @Test
    @DisplayName("Оставить пустым поле Месяц")
    void shouldFieldMonthIsEmpty() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCreditCard();
        fill.fillingOutTheFormForCreditPaymentTest(getApprovedCard(), " ", generateDate(1, "yy"), getValidCardOwner(), getValidCVV());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("Ввод карты с истекшим сроком действия")
    void shouldFieldYearLessThenOne() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCreditCard();
        fill.fillingOutTheFormForCreditPaymentTest(getApprovedCard(), generateDate(1, "MM"), generateDate(-12, "yy"), getValidCardOwner(), getValidCVV());
        fill.errorCardExpired();
    }

    @Test
    @DisplayName("Ввод карты со сроком действия больше 5 лет")
    void shouldFieldYearMoreThenTwelve() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCreditCard();
        fill.fillingOutTheFormForCreditPaymentTest(getApprovedCard(), generateDate(1, "MM"), generateDate(72, "yy"), getValidCardOwner(), getValidCVV());
        fill.errorCardPeriod();
    }

    @Test
    @DisplayName("Оставить пустым поле Год")
    void shouldFieldYearIsEmpty() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCreditCard();
        fill.fillingOutTheFormForCreditPaymentTest(getApprovedCard(), generateDate(1, "MM"), " ", getValidCardOwner(), getValidCVV());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("Ввод кирилицы в поле Владелец")
    void shouldFieldOwnerRussianLetters() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCreditCard();
        fill.fillingOutTheFormForCreditPaymentTest(getApprovedCard(), generateDate(1, "MM"), generateDate(1, "yy"), getInvalidCardOwner(), getValidCVV());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("Ввод цифр и символов в поле Владелец")
    void shouldFieldOwnerNumbersAndSymbols() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCreditCard();
        fill.fillingOutTheFormForCreditPaymentTest(getApprovedCard(), generateDate(1, "MM"), generateDate(1, "yy"), "111<>!№%:?*", getValidCVV());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("Оставить пустым поле Владелец")
    void shouldFieldOwnerIsEmpty() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCreditCard();
        fill.fillingOutTheFormForCreditPaymentTest(getApprovedCard(), generateDate(1, "MM"), generateDate(1, "yy"), getInvalidCardOwnerEmpty(), getValidCVV());
        fill.errorRequiredField();
    }

    @Test
    @DisplayName("Ввод в поле CVC/CVV одну цифру")
    void shouldFieldCVVHaveOneNumber() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCreditCard();
        fill.fillingOutTheFormForCreditPaymentTest(getApprovedCard(), generateDate(1, "MM"), generateDate(1, "yy"), getValidCardOwner(), getInvalidCVVHaveNumber());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("Ввод в поле CVC/CVV две цифры")
    void shouldFieldCVVHaveTwoNumber() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCreditCard();
        fill.fillingOutTheFormForCreditPaymentTest(getApprovedCard(), generateDate(1, "MM"), generateDate(1, "yy"), getValidCardOwner(), getInvalidCVVHaveTwoNumber());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("Оставить пустым поле CVC/CVV")
    void shouldFieldCVVIsEmpty() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCreditCard();
        fill.fillingOutTheFormForCreditPaymentTest(getApprovedCard(), generateDate(1, "MM"), generateDate(1, "yy"), getValidCardOwner(), getInvalidCVVEmpty());
        fill.errorWrongFormat();
    }
}
