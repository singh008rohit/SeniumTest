package stepDefination;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import test.data.Colour;

public class PassDifferentData {
	
	
	@Given("Login with valid username {string} and password {string}")
	public void login_with_valid_username_and_password(String string, String string1) {
		
		System.out.println("Login with user name "+ string);
		System.out.println("Login with password "+ string1);
	   
	}
	
	@ParameterType("red|green|blue")
	
	public Colour colour(String str) {
		
		return Colour.valueOf(str.toUpperCase());
		
	}
	
	@When("we pass colour {colour}")
	public void we_pass_colour(Colour colour) {
	    // Write code here that turns the phrase above into concrete actions
		
		System.out.println("Passing colour from enum class " +colour);
	    
	}

}
