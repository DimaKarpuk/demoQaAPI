package drivers;

import com.codeborne.selenide.Configuration;
import config.LocalConfig;

public class LocalDriver {
    private final LocalConfig localConfig;

    public LocalDriver(LocalConfig localConfig) {
        this.localConfig = localConfig;
    }

    public void localConfig() {
        Configuration.baseUrl = localConfig.getBaseUrl();
        Configuration.browser = localConfig.getBrowser();
        Configuration.browserSize = localConfig.getBrowserSize();
        Configuration.browserVersion = localConfig.getBrowserVersion();
    }
}
