package ui;

import models.AddListOfBooksResponseModel;
import models.LoginBodyModel;
import models.LoginResponseModel;
import org.openqa.selenium.Cookie;
import pages.ProfilePage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;

public class DeleteUI {
    ProfilePage profilePage = new ProfilePage();

    public void deleteBookWithUI (LoginBodyModel userData, AddListOfBooksResponseModel bookResponse) {

        profilePage.openPage(userData)
                .removeAds()
                .clickOnBinIcon("Learning JavaScript Design Patterns")
                .clickOnOkButton()
                .closeConfirmationWindow();
    }
    public void authByCookie (
            LoginResponseModel authResponse
    ) {
        step("Authorization with api", () -> {
            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userName", authResponse.getUsername()));
            getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
            getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));
            getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
        });
    }
}
