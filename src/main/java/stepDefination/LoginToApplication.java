package stepDefination;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import test.data.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utlity.JsonUtils;




public class LoginToApplication {
	
	
	@Given("valid user and valid password")
	public void valid_user_and_valid_password() {
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("valid user and valid password");
	}
	@When("enter user name {string} and password {string}")
	public void enter_user_name_user_and_password_password(String user,String pass) {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Login with user name -: "+user + " And valid password"+ pass);
	    
	}
	@Then("login to application successfully")
	public void login_to_application_successfully() {
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("login to application successfully");
	}
	@Given("try with user details")
	public void try_with_user_details(DataTable data) {
		
		Map<String,String> dt= data.asMap(String.class,String.class);
		System.out.println(dt.get("name"));
		System.out.println(dt.get("age"));
		System.out.println(dt.get("gender"));
		
	}
	@Given("I load test data from {string}")
	public void loadTestData(String fileName) throws IOException {
		
		JsonUtils.getMapFromJson(fileName, fileName);
		
	}
	
	@When("Open my laptop {string}")
	public void open_my_Laptop(String st) {
		
		System.out.println(st);
		
	}
	@ParameterType("Electronics|Books|Clothing")
	public Category category(String category) {
	    return Category.valueOf(category.toUpperCase());
	}
	@When("I filter products by category {category}")
	public void filterByCategory(Category category) {
	   System.out.println(category);
	}
	
	@Given("user navigates to login page")
	public void user_navigates_to_login_page() {
		System.out.println("user navigates to login page");
	}
	}


