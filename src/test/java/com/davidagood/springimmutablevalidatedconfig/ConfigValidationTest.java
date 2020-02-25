package com.davidagood.springimmutablevalidatedconfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {ValidationAutoConfiguration.class})
class ConfigValidationTest {

    @Autowired
    private LocalValidatorFactoryBean localValidatorFactoryBean;

    private AppConfig expectedAppConfig = new AppConfig("dev-user", 2, new AppConfig.UserApi("http://example.com"));

    private ConfigurationPropertiesBinderValidator<AppConfig> binderValidator;

    private String delimiter = ".";
    private String app = "app";
    private String userPath = Path.from(delimiter, app, "user").toString();
    private String threadsPath = Path.from(delimiter, app, "threads").toString();
    private String userApiUrlPath = Path.from(delimiter, app, "user-api", "url").toString();

    @BeforeEach
    void setUp() {
        binderValidator = ConfigurationPropertiesBinderValidator.forClass(AppConfig.class)
                .propertiesFile("application.yml")
                .prefix(app)
                .validator(localValidatorFactoryBean);
    }

    @Test
    void validationSuccess() throws BindException {
        AppConfig appConfig = binderValidator.build();
        assertThat(appConfig).isEqualTo(expectedAppConfig);
    }

    @Test
    void userMissing() {
        assertThrows(BindException.class, () -> binderValidator.omitProperty(userPath).build());
    }

    @Test
    void userEmpty() {
        assertThrows(BindException.class, () -> binderValidator.propertyOverride(userPath, "").build());
    }

    @Test
    void threadsMissing() {
        assertThrows(BindException.class, () -> binderValidator.omitProperty(threadsPath).build());
    }

    @Test
    void threadsEmpty() {
        assertThrows(BindException.class, () -> binderValidator.propertyOverride(threadsPath, "").build());
    }

    @Test
    void threadsInvalid() {
        assertThrows(BindException.class, () -> binderValidator.propertyOverride(threadsPath, "-1").build());
    }

    @Test
    void urlMissing() {
        assertThrows(BindException.class, () -> binderValidator.omitProperty(userApiUrlPath).build());
    }

    @Test
    void urlEmpty() {
        assertThrows(BindException.class, () -> binderValidator.propertyOverride(userApiUrlPath, "").build());
    }

    @Test
    void urlInvalid() {
        assertThrows(BindException.class,
                () -> binderValidator.propertyOverride(userApiUrlPath, "htp://typo.com").build());
    }

}
