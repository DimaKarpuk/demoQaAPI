package tests;

import models.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import steps.api.ApiSteps;
import pages.ProfilePage;

public class DeleteBookTest extends TestBase {
    ApiSteps apisteps = new ApiSteps();
    ProfilePage profilePage = new ProfilePage();
    LoginResponseModel loginResponseModel = apisteps.loginUser();

    @Tag("apiWithUi")
    @DisplayName("Тест на удаление книги из корзины пользователя")
    @Test
    void deleteBookTest() {
        apisteps.loginUser();
        apisteps.deleteBook(loginResponseModel);
        apisteps.addBook(loginResponseModel);
        profilePage.addCookie(loginResponseModel);
        profilePage.openProfilePage();
        profilePage.checkToAuthorized();
        profilePage.checkCartShouldHaveBook();
        profilePage.deleteBookUi();
        profilePage.confirmDelete();
        profilePage.closeDialogWindow();
        profilePage.checkCartIsEmpty();
    }
}
