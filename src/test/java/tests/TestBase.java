package tests;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;


public class TestBase {
    @BeforeAll
    public static void beforeAll(){
        Configuration.baseUrl = "https://demoqa.com";
        RestAssured.baseURI = "https://demoqa.com";
        Configuration.holdBrowserOpen = true;
    }
}
