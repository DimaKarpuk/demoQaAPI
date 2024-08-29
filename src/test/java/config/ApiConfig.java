package config;

import org.aeonbits.owner.Config;

public interface ApiConfig extends Config {
    @Config.DefaultValue("https://demoqa.com")
    String baseURI();

}
