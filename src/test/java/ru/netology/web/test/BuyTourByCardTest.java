package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
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

public class BuyTourByCardTest {


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
    @DisplayName("1. Успешная оплата при вводе валидных значений по одобренной (Approved) карте")
    void shouldSuccessfulPaymentByApprovedCard() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCard();
        fill.fillingOutTheFormForCardPaymentTest(getApprovedCard(), generateDate(1, "MM"), generateDate(1, "yy"), getValidCardOwner(), getValidCVV());
        fill.successOperationApproved();
        var expected = "APPROVED";
        var actual = DbInteraction.getStatusBuyCard();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("2. Ошибка оплаты при вводе валидных значений по отклоненной (Declined) карте")
    void shouldUnsuccessfulPaymentByDeclinedCard() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCard();
        fill.fillingOutTheFormForCardPaymentTest(getDeclinedCard(), generateDate(1, "MM"), generateDate(1, "yy"), getValidCardOwner(), getValidCVV());
        fill.errorBankRefused();
        var expected = "DECLINED";
        var actual = DbInteraction.getStatusBuyCard();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("3. Ошибка оплаты при вводе невалидного номера карты")
    void shouldUnsuccessfulPaymentByInvalidCard() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCard();
        fill.fillingOutTheFormForCardPaymentTest(getInvalidCard(), generateDate(1, "MM"), generateDate(1, "yy"), getValidCardOwner(), getValidCVV());
        fill.errorBankRefused();
    }

    @Test
    @DisplayName("4. Номер карты начинается с 0")
    void shouldFieldNumberOfCardIfFirstNumberZero() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCard();
        fill.fillingOutTheFormForCardPaymentTest(getInvalidCardIfFirstNumberZero(), generateDate(1, "MM"), generateDate(1, "yy"), getValidCardOwner(), getValidCVV());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("5. Оставить пустое поле Номер карты")
    void shouldFieldNumberOfCardIsEmpty() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCard();
        fill.fillingOutTheFormForCardPaymentTest(getEmptyCard(), generateDate(1, "MM"), generateDate(1, "yy"), getValidCardOwner(), getValidCVV());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("6. Ввод в поле Месяц значение прошедшего месяца текущего года")
    void shouldFieldMonthIfCardHasExpired() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCard();
        fill.fillingOutTheFormForCardPaymentTest(getApprovedCard(), generateDate(-1, "MM"), generateDate(0, "yy"), getValidCardOwner(), getValidCVV());
        fill.errorCardPeriod();
    }

    @Test
    @DisplayName("7. Ввод в поле Месяц граничное значение 00")
    void shouldFieldMonthLessThenOne() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCard();
        fill.fillingOutTheFormForCardPaymentTest(getApprovedCard(), "00", generateDate(1, "yy"), getValidCardOwner(), getValidCVV());
        fill.errorCardPeriod();
    }

    @Test
    @DisplayName("8. Ввод в поле Месяц граничное значение 13")
    void shouldFieldMonthMoreThenTwelve() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCard();
        fill.fillingOutTheFormForCardPaymentTest(getApprovedCard(), "13", generateDate(1, "yy"), getValidCardOwner(), getValidCVV());
        fill.errorCardPeriod();
    }

    @Test
    @DisplayName("9. Оставить пустым поле Месяц")
    void shouldFieldMonthIsEmpty() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCard();
        fill.fillingOutTheFormForCardPaymentTest(getApprovedCard(), " ", generateDate(1, "yy"), getValidCardOwner(), getValidCVV());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("10. Ввод карты с истекшим сроком действия")
    void shouldFieldYearLessThenOne() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCard();
        fill.fillingOutTheFormForCardPaymentTest(getApprovedCard(), generateDate(1, "MM"), generateDate(-12, "yy"), getValidCardOwner(), getValidCVV());
        fill.errorCardExpired();
    }

    @Test
    @DisplayName("11. Ввод карты со сроком действия больше 5 лет")
    void shouldFieldYearMoreThenTwelve() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCard();
        fill.fillingOutTheFormForCardPaymentTest(getApprovedCard(), generateDate(1, "MM"), generateDate(72, "yy"), getValidCardOwner(), getValidCVV());
        fill.errorCardPeriod();
    }

    @Test
    @DisplayName("12. Оставить пустым поле Год")
    void shouldFieldYearIsEmpty() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCard();
        fill.fillingOutTheFormForCardPaymentTest(getApprovedCard(), generateDate(1, "MM"), " ", getValidCardOwner(), getValidCVV());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("13. Ввод кириллицы в поле Владелец")
    void shouldFieldOwnerSymbols() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCard();
        fill.fillingOutTheFormForCardPaymentTest(getApprovedCard(), generateDate(1, "MM"), generateDate(1, "yy"), getInvalidCardOwner(), getValidCVV());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("14. Ввод цифр в поле Владелец")
    void shouldFieldOwnerNumber() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCard();
        fill.fillingOutTheFormForCardPaymentTest(getApprovedCard(), generateDate(1, "MM"), generateDate(1, "yy"), "111", getValidCVV());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("15. Ввод символов в поле Владелец")
    void shouldFieldOwnerNumbersAndSymbols() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCard();
        fill.fillingOutTheFormForCardPaymentTest(getApprovedCard(), generateDate(1, "MM"), generateDate(1, "yy"), "<>!№%:?*", getValidCVV());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("16, Оставить пустым поле Владелец")
    void shouldFieldOwnerIsEmpty() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCard();
        fill.fillingOutTheFormForCardPaymentTest(getApprovedCard(), generateDate(1, "MM"), generateDate(1, "yy"), getInvalidCardOwnerEmpty(), getValidCVV());
        fill.errorRequiredField();
    }

    @Test
    @DisplayName("17, Ввод в поле CVC/CVV одну цифру")
    void shouldFieldCVVHaveOneNumber() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCard();
        fill.fillingOutTheFormForCardPaymentTest(getApprovedCard(), generateDate(1, "MM"), generateDate(1, "yy"), getValidCardOwner(), getInvalidCVVHaveOneNumber());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("18. Ввод в поле CVC/CVV две цифры")
    void shouldFieldCVVHaveTwoNumber() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCard();
        fill.fillingOutTheFormForCardPaymentTest(getApprovedCard(), generateDate(1, "MM"), generateDate(1, "yy"), getValidCardOwner(), getInvalidCVVHaveTwoNumber());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("19. Оставить пустым поле CVC/CVV")
    void shouldFieldCVVIsEmpty() {
        var mainPage = new MainPage();
        var fill = mainPage.paymentByCard();
        fill.fillingOutTheFormForCardPaymentTest(getApprovedCard(), generateDate(1, "MM"), generateDate(1, "yy"), getValidCardOwner(), getInvalidCVVEmpty());
        fill.errorWrongFormat();
    }
}
