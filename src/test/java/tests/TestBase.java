package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import helpers.Attach;

import java.util.Map;


public class TestBase {
    @BeforeAll
    public static void beforeAll(){
        Configuration.browserSize = System.getProperty("windowSize","1928x1080");
        Configuration.browser = System.getProperty("browser","chrome");
        Configuration.browserVersion = System.getProperty("version","120.0");
        Configuration.baseUrl = System.getProperty("baseUrl","https://demoqa.com");
        Configuration.pageLoadStrategy = "eager";
        Configuration.remote = System.getProperty("selenoidURL");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
        RestAssured.baseURI = "https://demoqa.com";
    }
    @AfterEach
    void addAttach(){
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        Selenide.closeWebDriver();
    }
}
