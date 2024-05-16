package br.com.jonathan.integrationtests;

import br.com.jonathan.configs.TestConfigs;
import br.com.jonathan.integrationtests.testcontainer.AbstractIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {


	@Test
	void shouldDisplaySwaggerUiPage() {
		var content = given()
									.basePath("/controller-ui/index.html")
									.port(TestConfigs.SERVER_PORT)
									.when()
										.get()
									.then()
										.statusCode(200)
									.extract()
										.body()
											.asString();

		Assertions.assertTrue(content.contains("Swagger UI"));
	}

}
