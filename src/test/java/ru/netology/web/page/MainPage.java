package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {
    private SelenideElement dayTrip = $(withText("Путешествие дня"));
    private SelenideElement buttonBuy = $(withText("Купить"));
    private SelenideElement buttonBuyOnCredit = $(withText("Купить в кредит"));
    private SelenideElement paymentByCard = $(withText("Оплата по карте"));
    private SelenideElement paymentByCreditCard = $(withText("Кредит по данным карты"));

    public MainPage() {
        dayTrip.should(Condition.visible);
    }

    public CardPaymentPage paymentByCard() {
        buttonBuy.click();
        paymentByCard.should(Condition.visible);
        return new CardPaymentPage();
    }

    public CreditPaymentPage paymentByCreditCard() {
        buttonBuyOnCredit.click();
        paymentByCreditCard.should(Condition.visible);
        return new CreditPaymentPage();
    }
}
