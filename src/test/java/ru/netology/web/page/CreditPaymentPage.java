package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPaymentPage {
    private SelenideElement fieldCardNumber = $("[placeholder=\"0000 0000 0000 0000\"]");
    private SelenideElement fieldMonth = $("[placeholder=\"08\"]");
    private SelenideElement fieldYear = $("[placeholder=\"22\"]");
    private SelenideElement fieldCardOwner = $$("[class='input__control']").get(3);
    private SelenideElement fieldCVV = $("[placeholder=\"999\"]");
    private SelenideElement continueButton = $(withText("Продолжить"));
    private SelenideElement success = $(withText("Успешно"));
    private SelenideElement operationApproved = $(withText("Операция одобрена Банком."));
    private SelenideElement error = $(withText("Ошибка"));
    private SelenideElement bankRefused = $(withText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement wrongFormat = $(withText("Неверный формат"));
    private SelenideElement requiredField = $(withText("Поле обязательно для заполнения"));
    private SelenideElement cardPeriod = $(withText("Неверно указан срок действия карты"));
    private SelenideElement cardExpired = $(withText("Истёк срок действия карты"));

    public void successOperationApproved() {
        success.should(Condition.visible, Duration.ofSeconds(15));
        operationApproved.should(Condition.visible, Duration.ofSeconds(15));
    }

    public void errorBankRefused() {
        error.should(Condition.visible, Duration.ofSeconds(15));
        bankRefused.should(Condition.visible, Duration.ofSeconds(15));
    }

    public void errorWrongFormat() {
        wrongFormat.should(Condition.visible);
    }
    public void errorRequiredField() {
        requiredField.should(Condition.visible);
    }

    public void errorCardPeriod() {
        cardPeriod.should(Condition.visible);
    }

    public void errorCardExpired() {
        cardExpired.should(Condition.visible);
    }

    public void fillingOutTheFormForCreditPaymentTest(String cardNumber, String month, String year, String cardOwner, String CVV) {
        fieldCardNumber.setValue(cardNumber);
        fieldMonth.setValue(month);
        fieldYear.setValue(year);
        fieldCardOwner.setValue(cardOwner);
        fieldCVV.setValue(CVV);
        continueButton.click();
     }

}
