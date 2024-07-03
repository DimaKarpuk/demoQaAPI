package steps.ui;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import models.LoginResponseModel;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class UiSteps {
    @Step("Добавление cookie авторизации")
    public void addCookie(LoginResponseModel authData){
            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userID", authData.getUserId()));
            getWebDriver().manage().addCookie(new Cookie("expires", authData.getExpires()));
            getWebDriver().manage().addCookie(new Cookie("token", authData.getToken()));
    }
    @Step("Удаление книги через UI")
    public void delBookUi(){
        open("/profile");
        $("#delete-record-undefined").click();
        $("#closeSmallModal-ok").click();
        Selenide.refresh();
    }
    @Step("Проверяем что книга удалена из корзины через UI")
    public void checkDeleteBookWithUi(){
        open("/profile");
        $(".action-buttons").shouldNotBe(visible);
    }
}
