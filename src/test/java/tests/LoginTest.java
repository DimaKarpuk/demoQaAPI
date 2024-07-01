package tests;

import io.restassured.response.Response;
import models.LoginRequestModel;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;
public class LoginTest extends TestBase {
    String userName = "DimKa";
    String password = "Karpuk20!";
    @Test
    void loginTest() {
        LoginRequestModel loginModel = new LoginRequestModel();
        loginModel.setPassword(password);
        loginModel.setUserName(userName);
        Response authData = given()
                .log().all()
                .contentType("application/json")
                .body(loginModel)
                .when()
                .post("/Account/v1/Login")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        Response deleteBookData = given()
                .log().all()
                .contentType(JSON)
                .header("Authorization", "Bearer " + authData.path("token"))
                .queryParams("UserId", authData.path("userId"))
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .log().all()
                .statusCode(204)
                .extract().response();




        String isbn = "9781449325862";
        String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                authData.path("userId"), isbn);

        Response addBookData = given()
                .log().all()
                .contentType("application/json")
                .header("Authorization", "Bearer " + authData.path("token"))
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .log().all()
                .statusCode(201)
                .extract().response();


        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", authData.path("userId")));
        getWebDriver().manage().addCookie(new Cookie("expires", authData.path("expires")));
        getWebDriver().manage().addCookie(new Cookie("token", authData.path("token")));

        open("/profile");
        $(".ReactTable").shouldHave(text("Git Pocket Guide"));
    }
}
