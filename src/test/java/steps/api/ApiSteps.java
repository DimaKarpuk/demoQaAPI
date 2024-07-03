package steps.api;

import io.qameta.allure.Step;
import models.LoginRequestModel;
import models.LoginResponseModel;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;

public class ApiSteps {
    String userName = "DimKa";
    String password = "Karpuk20!";
    @Step("Авторизация пользователя")
    public LoginResponseModel loginUser() {
        LoginRequestModel loginModel = new LoginRequestModel();
        loginModel.setPassword(password);
        loginModel.setUserName(userName);
       return (given()
                .log().all()
                .contentType("application/json")
                .body(loginModel)
                .when()
                .post("/Account/v1/Login")
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(LoginResponseModel.class));
    }
    @Step("Удаление книги из корзины пользователя")
    public void deleteBook(LoginResponseModel authData){
        given()
                .log().all()
                .contentType(JSON)
                .header("Authorization", "Bearer " + authData.getToken())
                .queryParams("UserId", authData.getUserId())
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .log().all()
                .statusCode(204)
                .extract().response();
    }

    @Step("Добавление книги в корзину пользователя")
    public void addBook(LoginResponseModel authData) {
        String isbn = "9781449325862";
        String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                authData.getUserId(), isbn);

        given()
                .log().all()
                .contentType("application/json")
                .header("Authorization", "Bearer " + authData.getToken())
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .log().all()
                .statusCode(201)
                .extract().response();
    }
    @Step("Проверяем что в корзине пользователя нет книг")
    public void checkDeleteBookWithApi(LoginResponseModel authData){
        given()
                .log().all()
                .contentType(JSON)
                .header("Authorization", "Bearer " + authData.getToken())
                .queryParams("UserId", authData.getUserId())
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .log().all()
                .statusCode(204)
                .extract().response();
    }
}