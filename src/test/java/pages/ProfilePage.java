package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import models.LoginResponseModel;
import org.openqa.selenium.Cookie;
import tests.TestData;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;

public class ProfilePage {
    TestData data = new TestData();
    public final SelenideElement
            deleteBook =  $("#delete-record-undefined"),
            closeSmallModal = $("#closeSmallModal-ok"),
            emptyCart = $(".rt-noData"),
            loginValue = $("#userName-value");

    @Step("Добовляем cookie авторизации")
    public void addCookie(LoginResponseModel authData){
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", authData.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", authData.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("token", authData.getToken()));
    }
    @Step("Удаляем книгу из корзины через UI")
    public void delBookUi(){
        step ("Открываем страницу профиль", () ->{
            open("/profile");
        });
        step("Проверяем что мы вошли на сайт авторизованные", () ->{
            loginValue.shouldHave(text(data.getUsername()));
        });
        step("Проверяем что в корзине присутствует книга", () ->{
            emptyCart.shouldNotBe(visible);
        });
        step("Удаляем книгу из корзины", () ->{
            emptyCart.shouldNotBe(visible);
            deleteBook.click();
            closeSmallModal.click();
            Selenide.confirm();
        });
        step("Проверяем что книга отсутствует в корзине", () ->{
            emptyCart.shouldHave(text("No rows found"));
        });
    }
}
