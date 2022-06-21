package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.PaymentByCardPage;
import ru.netology.web.page.SelectionPage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class BuyTourTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        Configuration.holdBrowserOpen = true;
    }

    @Test
    @DisplayName("Успешная оплата при вводе валидных значений по одобренной (Approved) карте")
    void shouldSuccessfulPaymentByApprovedCard() {
        var selectionPage = new SelectionPage();
        var select = selectionPage.paymentByCard();
        var approvedCard = DataHelper.getApprovedCardOfCardData();
        select.fillingOutTheForm(approvedCard);
        $(".notification__title")
                .shouldHave(Condition.text("Успешно"), Duration.ofSeconds(10));
        $(".notification__content")
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    @Test
    @DisplayName("Ошибка оплаты при вводе валидных значений по отклоненной (Declined) карте")
    void shouldUnsuccessfulPaymentByDeclinedCard() {
        var selectionPage = new SelectionPage();
        var select = selectionPage.paymentByCard();
        var declinedCard = DataHelper.getDeclinedCardOfCardData();
        select.fillingOutTheForm(declinedCard);
        $(".notification__title")
                .shouldHave(Condition.text("Ошибка"), Duration.ofSeconds(10));
        $(".notification__content")
                .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."));
    }

    @Test
    @DisplayName("Ошибка оплаты при вводе невалидного номера карты")
    void shouldUnsuccessfulPaymentByInvalidCard() {
        var selectionPage = new SelectionPage();
        var select = selectionPage.paymentByCard();
        var invalidCardData = DataHelper.getInvalidCardOfCardData();
        select.fillingOutTheForm(invalidCardData);
        $(".notification__title")
                .shouldHave(Condition.text("Ошибка"), Duration.ofSeconds(10));
        $(".notification__content")
                .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."));
    }
}
