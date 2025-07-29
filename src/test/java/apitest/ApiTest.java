  package apitest;

import static io.restassured.RestAssured.given;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import annotation.FrameworkAnnotation;
import base.APITestBase;
import enums.AuthorType;
import enums.CategoryType;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojos.Department;
import pojos.EmpInfo;
import pojos.Employee;
import pojos.Head;
import pojos.Location;
import pojos.Techpark;
import reportManager.ExtentManager;
import reportManager.ExtentReportManager;
import specs.RequestSpecBuilderFactory;

public class ApiTest extends APITestBase {
	
	 @FrameworkAnnotation(author = { AuthorType.ROHIT, AuthorType.ROHIT}, 
				category = { CategoryType.SANITY,CategoryType.SMOKE,CategoryType.REGRESSION })
   @Test(enabled = true)
   void getBookingId() {
      ExtentReportManager.createTest("Get Booking Details API");
      ExtentManager.getExtentTest().log(Status.INFO, "API test execution started for get booking id ");
      
     
      String response = given().log().all().spec(RequestSpecBuilderFactory.getRequestSpecForGet()).when().get("booking").then().extract().response().toString();
      ExtentManager.getExtentTest().log(Status.INFO, "Validate getting all booking ID: ");
      ExtentManager.getExtentTest().log(Status.INFO, "API test execution started for get booking id are : " + response);
   }
   
   @Test(enabled = false)
   void pojo () {
		EmpInfo empinf= new EmpInfo("Rohit",28,"Male");

	   given().log().all().body(empinf).when().post().then().log().all();
   }
   
   
   @FrameworkAnnotation(author = { AuthorType.ROHIT, AuthorType.ROHIT}, 
			category = { CategoryType.SANITY,CategoryType.SMOKE,CategoryType.REGRESSION })
	
	@Test(enabled =false,groups = {"SANITY","SMOKE","REGRESSION"})
   
   void pojo1() {
	   
	   String company = "Infosys";

       // List of Departments
	   Employee anjali = new Employee();
       anjali.setName("Anjali");
       anjali.setEmployeeId("ENG2001");
       anjali.setSkills(Arrays.asList("Java", "Spring", "Docker"));
       anjali.setSalary(85000);
       anjali.setActive(true);

       Employee vikas = new Employee();
       vikas.setName("Vikas");
       vikas.setEmployeeId("ENG2002");
       vikas.setSkills(Arrays.asList("Python", "ML", "AWS"));
       vikas.setSalary(92000);
       vikas.setActive(false);
       Employee sonal = new Employee();
       sonal.setName("Sonal");
       sonal.setEmployeeId("HR2001");
       sonal.setSkills(Arrays.asList("Recruitment", "Communication"));
       sonal.setSalary(60000);
       sonal.setActive(true);


		Head head= new Head();
		head.setEmp("HR1001");
		head.setName("Rohit");
		Head head1= new Head();
		head1.setEmp("HR2001");
		head1.setName("Mohit");

       Department d1 = new Department();
       d1.setName("Engineering");
       d1.setHead(head);
       d1.setEmp(Arrays.asList(anjali, vikas));
       Department d2 = new Department();
       d2.setName("Hr");
       d2.setHead(head1);
      
       d2.setEmp(List.of(sonal));
       
       List<Department> departments = Arrays.asList(d1, d2);

       // Founded Year
       int foundedYear = 1981;

       // Location
       Location location = new Location();
       location.setCity("Bangalore");
       location.setCountry("India");

       // Public1
       boolean public1= true;
	   
	   Techpark tp= new Techpark();
	   tp.setCompany(company);
	   tp.setLocation(location);
	   tp.setDepartment(departments);
	   tp.setFoundedYear(foundedYear);
	   tp.setPublic1(public1);
	   RestAssured.baseURI="http://localhost:8081";
		ExtentManager.getExtentTest().log(Status.INFO, "Validate json schema with mock response");
		Response res=	given().log().all().header("Authorization","Bearer valid_token").header("Content-Type", "application/json").when().post("/api/user/1").then().statusCode(200).extract().response();
	
	System.out.println("------------response-----------------"+res.asPrettyString());

   }
}