package tests;

import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.BookStoreSpecs.*;

public class ApiTests extends ApiTestBase {
    TestData data = new TestData();
    LoginRequestModel loginModel = new LoginRequestModel();
    AddBookModel bookData = new AddBookModel();

    @DisplayName("Тест на на неуспешную авторизацию без Password")
    @Test
    void loginWithOutUserNameTest() {
        loginModel.setPassword(data.getPassword());
        LoginErrorResponseModel response = step("Отправляем запрос", () ->
                given(requestSpecificationWithContentTypeApplicationJson)
                        .body(loginModel)
                        .when()
                        .post("/Account/v1/Authorized")
                        .then()
                        .spec(responseSpecificationWithStatusCode400)
                        .extract().as(LoginErrorResponseModel.class));

        step("Проверяем, что в ответе получен code : 1200", () ->
                assertThat(response.getCode()).isEqualTo("1200"));
        step("Проверяем, что в ответе получен сообщение об ошибке", () ->
                assertThat(response.getMessage()).isEqualTo("UserName and Password required."));
    }

    @DisplayName("Тест на на неуспешную авторизацию без UserName")
    @Test
    void loginWithOutPasswordTest() {
        loginModel.setPassword(data.getUsername());
        LoginErrorResponseModel response = step("Отправляем запрос", () ->
                given(requestSpecificationWithContentTypeApplicationJson)
                        .body(loginModel)
                        .when()
                        .post("/Account/v1/Authorized")
                        .then()
                        .spec(responseSpecificationWithStatusCode400)
                        .extract().as(LoginErrorResponseModel.class));

        step("Проверяем, что в ответе получен code : 1200", () ->
                assertThat(response.getCode()).isEqualTo("1200"));
        step("Проверяем, что в ответе получен сообщение об ошибке", () ->
                assertThat(response.getMessage()).isEqualTo("UserName and Password required."));
    }

    @DisplayName("Тест на получение книги")
    @Test
    void getListBookTest() {
        TestData testData = new TestData();
        GetBookModel getBookModel = step("Отправляем запрос", () ->
                given()
                        .queryParam("ISBN", testData.isbn())
                        .when()
                        .get("/BookStore/v1/Book")
                        .then()
                        .spec(responseSpecificationWithStatusCode200)
                        .extract().as(GetBookModel.class));
        step("Проверяем, что в ответе получен isbn " + testData.isbn(), () ->
                assertThat(getBookModel.getIsbn()).isEqualTo(getBookModel.getIsbn()));
    }

    @DisplayName("Тест на получение Token авторизации")
    @Test
    void getTokenTest() {
        loginModel.setPassword(data.getPassword());
        loginModel.setUserName(data.getUsername());
        GetTokenModel response = step("Отправляем запрос", () ->
                given(requestSpecificationWithContentTypeApplicationJson)
                        .body(loginModel)
                        .when()
                        .post("/Account/v1/GenerateToken")
                        .then()
                        .spec(responseSpecificationWithStatusCode200)
                        .extract().as(GetTokenModel.class));

        step("Проверяем, что в ответе token не пустой", () ->
                assertThat(response.getToken()).isNotEmpty());
        step("Проверяем, сообщение о успешной авторизации", () ->
                assertThat(response.getResult()).isEqualTo("User authorized successfully."));
    }
}

