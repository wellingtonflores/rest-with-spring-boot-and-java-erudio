package com.erudio.rest_with_spring_boot_and_java_erudio.integrationtests.swagger;

import com.erudio.rest_with_spring_boot_and_java_erudio.configs.TestConfigs;
import com.erudio.rest_with_spring_boot_and_java_erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class SwaggerIntegrationTest extends AbstractIntegrationTest {



    @Test
    public void shouldDisplaySwaggerUiPage() {

        var content =
                given()
                        .baseUri("http://localhost")
                        .basePath("/swagger-ui/index.html/")
                        .port(TestConfigs.SERVER_PORT)

                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString();
        assertTrue(content.contains("Swagger UI"));

    }

}
