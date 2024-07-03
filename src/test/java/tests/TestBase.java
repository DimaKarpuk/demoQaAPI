package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;


public class TestBase {
    @BeforeAll
    public static void beforeAll(){
        Configuration.browserSize = System.getProperty("windowSize","1928x1080");
        Configuration.browser = System.getProperty("browser","chrome");
        Configuration.browserVersion = System.getProperty("version","120.0");
        Configuration.baseUrl = System.getProperty("baseUrl","");
        Configuration.pageLoadStrategy = "eager";
        Configuration.remote = System.getProperty("selenoidURL","");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
        Configuration.baseUrl = "https://demoqa.com";
        RestAssured.baseURI = "https://demoqa.com";
    }
    @AfterAll
    public static void afterAll(){
        Selenide.closeWebDriver();
    }
}
