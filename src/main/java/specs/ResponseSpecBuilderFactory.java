   package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.Matchers.*;



public class ResponseSpecBuilderFactory {
	
	
	
   public static ResponseSpecification getResponseForGet() {
	   
      return new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
   }
   
   public static ResponseSpecification getSearchProductResponse() {
	    return new ResponseSpecBuilder()
	            .expectStatusCode(200)                // Expect a 200 status code
	            .expectContentType(ContentType.HTML).expectBody("product",not(empty())) // Expect a JSON response content type
	            .build();                             // Build the ResponseSpecification
	}
}