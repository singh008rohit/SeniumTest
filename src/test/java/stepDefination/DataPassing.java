package stepDefination;

import java.util.List;
import java.util.Map;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class DataPassing {
	
	
	
	@Given("Login to application with {string} and {string}")
	public void login_to_application_with_and_password(String username,String password) {
	  System.out.println("Login with username"+ username +" and password "+ password);
	}
	@Then("Login witha welcome message {string}")
	public void login_witha_welcome_message(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("wlecone message -: "+string);
	}
	
	@Given("Login user has access of different department")
	public void login_user_has_access_of_different_department(DataTable dataTable) {
		
		List<String> roles=dataTable.asList();
		for(String role:roles) {
			System.out.println("Get all the role :-"+role);
			
		}
	}
		@Given("User username and there department")
		public void user_username_and_there_department(DataTable dataTable) {
			
		Map<String,String> dep=	dataTable.asMap();
		
		System.out.println("Get department with username "+dep.get("rohit"));
		System.out.println("Get department with username "+dep.get("Jhon"));

		}
			
		
		
		@Then("Featch all user with name and department")
		public void featch_all_user_with_name_and_department() {
		  
		}


@Given("user name with passsword and there expiry")
public void user_name_with_passsword_and_there_expiry(DataTable dataTable) {

	
}
}