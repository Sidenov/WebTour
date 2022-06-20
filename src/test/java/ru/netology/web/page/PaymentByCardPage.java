package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class PaymentByCardPage {
    private SelenideElement cardNumber = $(withText("Номер карты"));
    private SelenideElement month = $(withText("Месяц"));
    private SelenideElement year = $(withText("Год"));
    private SelenideElement cardOwner = $(withText("Владелец"));
    private SelenideElement CVV = $(withText("CVC/CVV"));
    private SelenideElement continueButton = $(withText("Продолжить"));

    public void fillingOutTheForm(DataHelper.CardData cardData) {
        cardNumber.setValue(cardData.getCardNumber());
        month.setValue(cardData.getMonth());
        year.setValue(cardData.getYear());
        cardOwner.setValue(cardData.getCardOwner());
        CVV.setValue(cardData.getCVV());
        continueButton.click();
    }
}
