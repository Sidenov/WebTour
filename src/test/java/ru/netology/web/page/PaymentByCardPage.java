package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentByCardPage {
    private SelenideElement cardNumber = $("[placeholder=\"0000 0000 0000 0000\"]");
    private SelenideElement month = $("[placeholder=\"08\"]");
    private SelenideElement year = $("[placeholder=\"22\"]");
    private SelenideElement cardOwner = $$("[class='input__control']").get(3);
    private SelenideElement CVV = $("[placeholder=\"999\"]");
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
