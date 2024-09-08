package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.google.common.reflect.Reflection;
import config.LocalConfig;
import config.RemoteConfig;
import drivers.LocalDriver;
import drivers.RemoteDriver;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.remote.DesiredCapabilities;
import helpers.Attach;

import java.util.Map;


public class TestBase {
    private static final LocalConfig localConfig = ConfigFactory.create(LocalConfig.class, System.getProperties());
    private static final RemoteConfig remoteConfig = ConfigFactory.create(RemoteConfig.class, System.getProperties());

    private static String defHost = "local";
    @BeforeAll
    public static void beforeAll(){
            RestAssured.baseURI = "https://demoqa.com";
            Configuration.pageLoadStrategy = "eager";
            if (System.getProperty("browserHost", defHost).equals("local")) {
                SelenideLogger.addListener("allure", new AllureSelenide());
                LocalDriver localDriver = new LocalDriver(localConfig);
                localDriver.localConfig();
            } else if (System.getProperty("browserHost", defHost).equals("remote")) {
                SelenideLogger.addListener("allure", new AllureSelenide());
                RemoteDriver remoteDriver = new RemoteDriver(remoteConfig);
                remoteDriver.remoteConfig();

                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                        "enableVNC", true,
                        "enableVideo", true
                ));
                Configuration.browserCapabilities = capabilities;
        }
    }
    @AfterEach
    void addAttachments(TestInfo testInfo) {
        if (testInfo.getTags().contains("api")) {
            return;
        }
            Attach.screenshotAs("Last screenshot");
            Attach.pageSource();
            Attach.browserConsoleLogs();
            if (System.getProperty("browserHost", defHost).equals("remote")) {
                Attach.addVideo();
            }
            Selenide.closeWebDriver();
    }
}
