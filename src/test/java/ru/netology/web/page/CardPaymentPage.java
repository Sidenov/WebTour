package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardPaymentPage {
    private SelenideElement fieldCardNumber1 = $("[placeholder=\"0000 0000 0000 0000\"]");
    private SelenideElement fieldMonth1 = $("[placeholder=\"08\"]");
    private SelenideElement fieldYear1 = $("[placeholder=\"22\"]");
    private SelenideElement fieldCardOwner1 = $$("[class='input__control']").get(3);
    private SelenideElement fieldCVV1 = $("[placeholder=\"999\"]");
    private SelenideElement continueButton1 = $(withText("Продолжить"));
    private SelenideElement success1 = $(withText("Успешно"));
    private SelenideElement operationApproved1 = $(withText("Операция одобрена Банком."));
    private SelenideElement error1 = $(withText("Ошибка"));
    private SelenideElement bankRefused1 = $(withText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement wrongFormat1 = $(withText("Неверный формат"));
    private SelenideElement requiredField1 = $(withText("Поле обязательно для заполнения"));
    private SelenideElement cardPeriod1 = $(withText("Неверно указан срок действия карты"));
    private SelenideElement cardExpired1 = $(withText("Истёк срок действия карты"));

    public void successOperationApproved() {
        success1.should(Condition.visible, Duration.ofSeconds(15));
        operationApproved1.should(Condition.visible, Duration.ofSeconds(15));
    }

    public void errorBankRefused() {
        error1.should(Condition.visible, Duration.ofSeconds(15));
        bankRefused1.should(Condition.visible, Duration.ofSeconds(15));
    }

    public void errorWrongFormat() {
        wrongFormat1.should(Condition.visible);
    }
    public void errorRequiredField() {
        requiredField1.should(Condition.visible);
    }

    public void errorCardPeriod() {
        cardPeriod1.should(Condition.visible);
    }

    public void errorCardExpired() {
        cardExpired1.should(Condition.visible);
    }

    public void fillingOutTheFormForCardPaymentTest(String cardNumber, String month, String year, String cardOwner, String CVV) {
        fieldCardNumber1.setValue(cardNumber);
        fieldMonth1.setValue(month);
        fieldYear1.setValue(year);
        fieldCardOwner1.setValue(cardOwner);
        fieldCVV1.setValue(CVV);
        continueButton1.click();
    }


}
