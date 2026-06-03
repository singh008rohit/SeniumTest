  package specs;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

import commonConstant.ApiCommonContant;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecBuilderFactory {
	
	
	
   public static RequestSpecification getRequestSpecForGet() {
	   
      return (new RequestSpecBuilder()).setContentType(ContentType.JSON).build();
   }
   
   
   public static RequestSpecification getPostRequestForgetProduct(String product) {
	   
	   return new RequestSpecBuilder().setContentType(ContentType.URLENC).addFormParam(ApiCommonContant.SEARCH_PRODUCT_KEY, product).build();
   }
   
   
   public static RequestSpecification getSecretToken() {
	   
	   return new RequestSpecBuilder().addHeader("was-secret", "3rXypRXEsRDSUe7i2hWs").setContentType("application/json; charset=utf-8").addQueryParam("languageCode", "en").build();
   }
   
public static RequestSpecification getSecretTokenEng() {
	   
	   return new RequestSpecBuilder().addHeader("was-secret", "3rXypRXEsRDSUe7i2hWs").setContentType("application/json; charset=utf-8").addQueryParam("languageCode", "en").build();
   }

public static RequestSpecification getSecretTokenAr() {
	   
	   return new RequestSpecBuilder().addHeader("was-secret", "3rXypRXEsRDSUe7i2hWs").setContentType("application/json; charset=utf-8").addQueryParam("languageCode", "ar").build();
}
public static RequestSpecification getSecretTokenHi() {
	   
	   return new RequestSpecBuilder().addHeader("was-secret", "3rXypRXEsRDSUe7i2hWs").setContentType("application/json; charset=utf-8").addQueryParam("languageCode", "hi").build();
}
}