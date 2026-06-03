package mis;

import java.io.File;

import io.restassured.path.json.JsonPath;

public class ValidateJson {
	
	public static void find() {
		
		File fe= new File(System.getProperty("user.dir")+"/src/test/java/resources/test2.json");
		
		
		JsonPath js= new JsonPath(fe);
		System.out.println(js.getString("organization.name"));
		
		System.out.println(js.getString("organization.departments.teams.members.flatten().findAll{ it.isRemote == false }.name"));
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		find();

	}

}
