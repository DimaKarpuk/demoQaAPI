package tests;

import models.LoginResponseModel;
import org.junit.jupiter.api.Test;
import steps.api.ApiSteps;
import steps.ui.UiSteps;

public class DeleteBookTest extends TestBase{
    ApiSteps apisteps = new ApiSteps();
    UiSteps uiSteps = new UiSteps();
    LoginResponseModel loginResponseModel = apisteps.loginUser();
    @Test
    void deleteBookTest(){
        apisteps.loginUser();
        apisteps.deleteBook(loginResponseModel);
        apisteps.addBook(loginResponseModel);
        uiSteps.addCookie(loginResponseModel);
        uiSteps.delBookUi();
        uiSteps.checkDeleteBookWithUi();
        // apisteps.checkDeleteBookWithApi(loginResponseModel);
    }
}