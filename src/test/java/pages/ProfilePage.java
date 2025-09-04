package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import models.LoginBodyModel;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

public class ProfilePage {
    public SelenideElement userName = $("#userName-value"),
            OkButton = $("#closeSmallModal-ok");
    public ElementsCollection bookNames = $$(".mr-2");
    public String deletedRow = ".rt-tr",
            binIcon = "#delete-record-undefined";

    @Step("Open Page")
    public ProfilePage openPage(LoginBodyModel userData) {

            open("/profile");
            userName.shouldHave(text(userData.getUserName()));

        return this;
    }
    @Step("Remove Ads")
    public ProfilePage removeAds() {
        executeJavaScript("$('footer').remove();");
        executeJavaScript("$('#fixedban').remove();");
        return this;
    }
    @Step("Click On Bin Icon")
    public ProfilePage clickOnBinIcon(String deletedBookTitle) {
            bookNames.findBy(text(deletedBookTitle)).closest(deletedRow).$(binIcon).click();
        return this;
    }
    @Step("Click On Ok Button")
    public ProfilePage clickOnOkButton() {
            OkButton.click();
        return this;
    }
    @Step("Close Confirmation Window(")
    public ProfilePage closeConfirmationWindow() {

            Selenide.confirm();

        return this;
    }
}
