package steps.api;

import io.qameta.allure.Step;
import models.LoginRequestModel;
import models.LoginResponseModel;
import tests.TestData;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static specs.BookStoreSpecs.*;

public class ApiSteps {
    TestData data = new TestData();
    @Step("Авторизация пользователя")
    public LoginResponseModel loginUser() {
        LoginRequestModel loginModel = new LoginRequestModel();
        loginModel.setPassword(data.getPaswword());
        loginModel.setUserName(data.getUsername());
       return (given(requestSpecificationWithContentTypeApplicationJson)
                .body(loginModel)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpecificationWithStatusCode200)
                .extract().as(LoginResponseModel.class));
    }
    @Step("Удаляем книги из корзины пользователя API")
    public void deleteBook(LoginResponseModel authData){
        given(requestSpecificationWithContentTypeJson)
                .header("Authorization", "Bearer " + authData.getToken())
                .queryParams("UserId", authData.getUserId())
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(responseSpecificationWithStatusCode204)
                .extract().response();
    }

    @Step("Добавляем книгу в корзину пользователя")
    public void addBook(LoginResponseModel authData) {
        String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                authData.getUserId(), data.getIsbn());

        given(requestSpecificationWithContentTypeApplicationJson)
                .header("Authorization", "Bearer " + authData.getToken())
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpecificationWithStatusCode201)
                .extract().response();
    }
    @Step("Проверяем что в корзине пользователя нет книг")
    public void checkDeleteBookWithApi(LoginResponseModel authData){
        given(requestSpecificationWithContentTypeJson)
                .header("Authorization", "Bearer " + authData.getToken())
                .queryParams("UserId", authData.getUserId())
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .spec(responseSpecificationWithStatusCode204)
                .extract().response();
    }
}