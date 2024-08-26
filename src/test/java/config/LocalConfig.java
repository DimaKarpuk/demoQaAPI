package config;

import org.aeonbits.owner.Config;

public interface LocalConfig extends Config {
    @Key("baseUrl")
    @DefaultValue("https://demoqa.com")
    String getBaseUrl();

    @Key("browser")
    @DefaultValue("chrome")
    String getBrowser();

    @Key("browserSize")
    @DefaultValue("1920x1080")
    String getBrowserSize();

    @Key("browserVersion")
    @DefaultValue("122.0")
    String getBrowserVersion();
}
