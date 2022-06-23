package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.PaymentByCardPage;
import ru.netology.web.page.SelectionPage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.DataHelper.*;

public class BuyTourTest {

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
        var selectionPage = new SelectionPage();
        var fill = selectionPage.paymentByCard();
        var approvedCard = DataHelper.getApprovedCardOfCardData();
        fill.fillingOutTheForm(approvedCard);
        fill.successOperationApproved();
    }

    @Test
    @DisplayName("Ошибка оплаты при вводе валидных значений по отклоненной (Declined) карте")
    void shouldUnsuccessfulPaymentByDeclinedCard() {
        var selectionPage = new SelectionPage();
        var fill = selectionPage.paymentByCard();
        var declinedCard = DataHelper.getDeclinedCardOfCardData();
        fill.fillingOutTheForm(declinedCard);
        fill.errorBankRefused();
    }

    @Test
    @DisplayName("Ошибка оплаты при вводе невалидного номера карты")
    void shouldUnsuccessfulPaymentByInvalidCard() {
        var selectionPage = new SelectionPage();
        var fill = selectionPage.paymentByCard();
        var invalidCard = DataHelper.getInvalidCardOfCardData();
        fill.fillingOutTheForm(invalidCard);
        fill.errorBankRefused();
    }

    @Test
    @DisplayName("Оставить пустое поле Номер карты")
    void shouldFieldNumberOfCardIsEmpty() {
        var selectionPage = new SelectionPage();
        var fill = selectionPage.paymentByCard();
        fill.fillingOutTheForm1(getApprovedCard(), getValidMonth(), getValidYear(), getValidCardOwner(), getValidCVV());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("Ввод в поле Месяц граничное значение 0")
    void shouldFieldMonthLessThenOne() {
        var selectionPage = new SelectionPage();
        var fill = selectionPage.paymentByCard();
        fill.fillingOutTheForm1(getApprovedCard(), getInvalidMonthLessThenOne(), getValidYear(), getValidCardOwner(), getValidCVV());
        fill.errorCardPeriod();
    }

    @Test
    @DisplayName("Ввод в поле Месяц граничное значение 13")
    void shouldFieldMonthMoreThenTwelve() {
        var selectionPage = new SelectionPage();
        var fill = selectionPage.paymentByCard();
        fill.fillingOutTheForm1(getApprovedCard(), getInvalidMonthMoreThenTwelve(), getValidYear(), getValidCardOwner(), getValidCVV());
        fill.errorCardPeriod();
    }

    @Test
    @DisplayName("Оставить пустым поле Месяц")
    void shouldFieldMonthIsEmpty() {
        var selectionPage = new SelectionPage();
        var fill = selectionPage.paymentByCard();
        fill.fillingOutTheForm1(getApprovedCard(), getInvalidMonthEmpty(), getValidYear(), getValidCardOwner(), getValidCVV());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("Ввод карты с истекшим сроком действия")
    void shouldFieldYearLessThenOne() {
        var selectionPage = new SelectionPage();
        var fill = selectionPage.paymentByCard();
        fill.fillingOutTheForm1(getApprovedCard(), getValidMonth(), getInvalidYearLessThenNow(), getValidCardOwner(), getValidCVV());
        fill.errorCardExpired();
    }

    @Test
    @DisplayName("Ввод карты со сроком действия больше 5 лет")
    void shouldFieldYearMoreThenTwelve() {
        var selectionPage = new SelectionPage();
        var fill = selectionPage.paymentByCard();
        fill.fillingOutTheForm1(getApprovedCard(), getValidMonth(), getInvalidYearMoreThenFive(), getValidCardOwner(), getValidCVV());
        fill.errorCardPeriod();
    }

    @Test
    @DisplayName("Оставить пустым поле Год")
    void shouldFieldYearIsEmpty() {
        var selectionPage = new SelectionPage();
        var fill = selectionPage.paymentByCard();
        fill.fillingOutTheForm1(getApprovedCard(), getValidMonth(), getInvalidYearEmpty(), getValidCardOwner(), getValidCVV());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("Ввод кирилицы в поле Владелец")
    void shouldFieldOwnerRussianLetters() {
        var selectionPage = new SelectionPage();
        var fill = selectionPage.paymentByCard();
        fill.fillingOutTheForm1(getApprovedCard(), getValidMonth(), getValidYear(), getInvalidCardOwner(), getValidCVV());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("Ввод цифр и символов в поле Владелец")
    void shouldFieldOwnerNumbersAndSymbols() {
        var selectionPage = new SelectionPage();
        var fill = selectionPage.paymentByCard();
        fill.fillingOutTheForm1(getApprovedCard(), getValidMonth(), getValidYear(), "111<>!№%:?*", getValidCVV());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("Оставить пустым поле Владелец")
    void shouldFieldOwnerIsEmpty() {
        var selectionPage = new SelectionPage();
        var fill = selectionPage.paymentByCard();
        fill.fillingOutTheForm1(getApprovedCard(), getValidMonth(), getValidYear(), getInvalidCardOwnerEmpty(), getValidCVV());
        fill.errorRequiredField();
    }

    @Test
    @DisplayName("Ввод в поле CVC/CVV одну цифру")
    void shouldFieldCVVHaveOneNumber() {
        var selectionPage = new SelectionPage();
        var fill = selectionPage.paymentByCard();
        fill.fillingOutTheForm1(getApprovedCard(), getValidMonth(), getValidYear(), getValidCardOwner(), getInvalidCVVHaveNumber());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("Ввод в поле CVC/CVV две цифры")
    void shouldFieldCVVHaveTwoNumber() {
        var selectionPage = new SelectionPage();
        var fill = selectionPage.paymentByCard();
        fill.fillingOutTheForm1(getApprovedCard(), getValidMonth(), getValidYear(), getValidCardOwner(), getInvalidCVVHaveTwoNumber());
        fill.errorWrongFormat();
    }

    @Test
    @DisplayName("Оставить пустым поле CVC/CVV")
    void shouldFieldCVVIsEmpty() {
        var selectionPage = new SelectionPage();
        var fill = selectionPage.paymentByCard();
        fill.fillingOutTheForm1(getApprovedCard(), getValidMonth(), getValidYear(), getValidCardOwner(), getInvalidCVVEmpty());
        fill.errorWrongFormat();
    }
}
