package utlity;

import io.restassured.response.Response;
import loggerUtils.LogUtils;
import reportManager.ExtentManager;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SchemaValidator {

	private static final Logger LOGGER = Logger.getLogger(SchemaValidator.class.getName());

	public static void validateSchema(Response response, String schemaPath) {
		if (response == null) {
			throw new IllegalArgumentException("Response must not be null");
		}
		if (schemaPath == null || schemaPath.trim().isEmpty()) {
			throw new IllegalArgumentException("schemaPath must not be null or empty");
		}

		// Verify schema resource exists on the classpath to provide clearer errors
		if (SchemaValidator.class.getClassLoader().getResource(schemaPath) == null) {
			throw new IllegalArgumentException("Schema file not found on classpath: " + schemaPath);
		}

		try {
			response.then().assertThat()
					.body(matchesJsonSchemaInClasspath(schemaPath));

			LogUtils.info("Schema validation passed for: " + schemaPath);
			 ExtentManager.getExtentTest().pass("Schema validation passed: " + schemaPath);

		} catch (AssertionError e) {
			LogUtils.error("Schema validation failed for: " + schemaPath);
			 ExtentManager.getExtentTest().fail("Schema validation failed: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error during schema validation for: {0} - {1}", new Object[]{schemaPath, e.getMessage()});
			throw e;
		}
	}
}
