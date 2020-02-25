package com.davidagood.springimmutablevalidatedconfig;

import lombok.Value;
import lombok.experimental.NonFinal;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "app")
@ConstructorBinding
@Value
@NonFinal
@Validated
public class AppConfig {

    @NotBlank
    private String user;

    @Min(1)
    private int threads;

    @NotNull
    private UserApi userApi;

    @Value
    public static class UserApi {

        @NotBlank
        @URL
        private String url;
    }
}
