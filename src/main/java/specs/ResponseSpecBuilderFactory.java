   package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpecBuilderFactory {
   public static ResponseSpecification getResponseForGet() {
      return (new ResponseSpecBuilder()).expectStatusCode(200).expectContentType("application/json").build();
   }
}