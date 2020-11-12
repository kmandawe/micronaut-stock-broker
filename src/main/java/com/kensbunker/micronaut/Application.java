package com.kensbunker.micronaut;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition(
    info = @Info(
        title = "micronaut-stock-broker",
        version = "0.1",
        description = "Micronaut Practice Application",
        license = @License(name = "MIT")
    )
)
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
        LOG.info("Hello");
    }
}
