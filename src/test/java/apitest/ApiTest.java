  package apitest;

import static io.restassured.RestAssured.given;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.APITestBase;
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
   @Test(enabled = false)
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
   
   
   @Test(enabled = false)
   
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
	//Response res=   given().header("Content-Type", "application/json").baseUri("http://localhost:8080").log().all().body(tp).when().post("/users/1").then().statusCode(200).extract().response();
	
	//System.out.println("------------response-----------------"+res.asPrettyString());

   }
}
