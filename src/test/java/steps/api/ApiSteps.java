package steps.api;

import io.qameta.allure.Step;
import models.LoginRequestModel;
import models.LoginResponseModel;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static specs.BookStoreSpecs.*;

public class ApiSteps {
    final String userName = "DimKa";
    final String password = "Karpuk20!";
    @Step("Авторизация пользователя")
    public LoginResponseModel loginUser() {
        LoginRequestModel loginModel = new LoginRequestModel();
        loginModel.setPassword(password);
        loginModel.setUserName(userName);
       return (given(requestSpecificationWithContentTypeApplicationJson)
                .body(loginModel)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpecificationWithStatusCode200)
                .extract().as(LoginResponseModel.class));
    }
    @Step("Удаление книги из корзины пользователя")
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

    @Step("Добавление книги в корзину пользователя")
    public void addBook(LoginResponseModel authData) {
        String isbn = "9781449325862";
        String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                authData.getUserId(), isbn);

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