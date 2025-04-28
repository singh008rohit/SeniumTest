  package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpecBuilderFactory {
   public static RequestSpecification getRequestSpecForGet() {
      return (new RequestSpecBuilder()).setContentType("application/json").build();
   }
}