package mis;

import java.io.File;
import java.util.List;

import io.restassured.path.json.JsonPath;

public class ValidateResponse1 {

	
	static void validate() {
		
		File file= new File(System.getProperty("user.dir")+"/src/test/java/resources/test1.json");
		
		JsonPath js= new JsonPath(file);
		
	String st=	js.getString("data.roles[1]");
	
	//System.out.println(js.getString("data.profile"));
	//System.out.println(st);
	//find the city
	//System.out.println(js.getString("data.profile.address.city"));
	
	//Find all the roles
	
	List<String> li=js.getList("data.roles",String.class);
	for(String s:li) {
	//System.out.println(s);
		}
	
	
	//find the Job id of QA
	
	//System.out.println(js.getInt("data.projects.team.flatten().find { it.role == 'QA' }.id"));

	System.out.println(js.getString("data.permissions.findAll{it.resource=='reports'}.actions.flatten()"));
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		validate();

	}

}
