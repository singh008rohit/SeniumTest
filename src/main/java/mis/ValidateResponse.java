package mis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.mongodb.util.JSON;

import utlity.JsonUtils;

public class ValidateResponse {
	
	
	
	
	
	static void getResponse() throws IOException {
		String path ="/src/test/java/resources/test.json";
		//Get company name
	String st=	JsonUtils.getJsonObjectAsString(path, "company");
	//System.out.println(st);
	//System.out.println(JsonUtils.getListvalueFromJson(path, "departments[*].name"));// get all department name
	
	//System.out.println(JsonUtils.getJsonObjectAsString(path, "departments[0].name"));// from specific index
	
	//System.out.println(JsonUtils.getJsonObjectAsString(path, "departments[1].name"));// from specific index
	
	//Get name employee name from all department
	//System.out.println(JsonUtils.getListvalueFromJson(path, "departments[*].employees[*].name"));
	
	//Get skills of Engineering department employees
	//System.out.println(JsonUtils.getListvalueFromJson(path, "departments[0].employees[*].skills[*]"));
	
	//Get salary of employee with employeeId == ‘ENG2002’
	//[?()] Filter expression  used to filter array elements.
   //@  Represents the current element in the iteration.
	
	//System.out.println(JsonUtils.getListvalueFromJson(path, "departments[*].employees[?(@.employeeId == 'ENG2002')].salary"));
	
	//Get all active employees’ names
	List<Object> li=JsonUtils.getListvalueFromJson(path, "departments[*].employees[?(@.active == true)].name");
	
	for(Object s: li) {
		
		System.out.println(s);	
	}
	
	//Get project titles with budget > 150000
	
	System.out.println("Get project titles with budget > 150000 => "+JsonUtils.getListvalueFromJson(path,"projects[?(@.budget>150000)].title").get(0));
	
	//Get names of department heads
	System.out.println("Get names of department heads => "+JsonUtils.getListvalueFromJson(path, "departments[*].head.name"));
	
	//Get the list of all unique skills used in the company
	
	System.out.println("Get the list of all unique skills used in the company => "+JsonUtils.getListvalueFromJson(path, "departments[*].employees[*].skills[*]"));
	
	//Get all project IDs where status = ‘Active’
	System.out.println("Get all project IDs where status = ‘Active’ => "+JsonUtils.getListvalueFromJson(path, "projects[?(@.status=='Active')].projectId"));
	
	
	System.out.println(JsonUtils.getListvalueFromJson(path,"departments[?(@.name=='HR')].employees"));
	                                                         
	

	
	
	}
	
	public static void main(String ar[]) throws IOException {
		getResponse();
	}

}
