package com.tictactoebot.api;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({"classpath:config.properties"})
public interface ProjectConfig extends Config {
    String baseUrl();

    boolean logging();
}
