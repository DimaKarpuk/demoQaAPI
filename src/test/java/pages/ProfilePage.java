package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import models.LoginResponseModel;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ProfilePage {
    public final SelenideElement
    deleteBook =  $("#delete-record-undefined"),
    closeSmallModal = $("#closeSmallModal-ok"),
    emptyCart = $(".rt-noData");
    @Step("Добовляем cookie авторизации")
    public void addCookie(LoginResponseModel authData){
            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userID", authData.getUserId()));
            getWebDriver().manage().addCookie(new Cookie("expires", authData.getExpires()));
            getWebDriver().manage().addCookie(new Cookie("token", authData.getToken()));
    }
    @Step("Удаляем книгу из корзины через UI")
    public void delBookUi(){
        open("/profile");
        deleteBook.click();
        closeSmallModal.click();
        Selenide.confirm();
    }
    @Step("Проверяем что книга удалена из корзины через UI")
    public void checkDeleteBookWithUi(){
        open("/profile");
        emptyCart.shouldHave(text("No rows found"));
    }
}
