package com.iteratia.titanicquest.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "iteratia.titanicquest")
@Getter
@Setter
public class AppConfigurationProperties {
    private Integer paginationMax=10;
    private Integer searchGuessMax=10;
    private Boolean redisEnable=false;
    private String loadFileUrl;
}
