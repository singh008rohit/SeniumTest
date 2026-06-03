package mis;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import utlity.JsonUtils;

public class ValidateResponse2 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String path ="/src/test/java/resources/flightSearch.json";
		//Get company name
	String origin=	JsonUtils.getJsonObjectAsString(path, "$..meta");
	
	//System.out.println(origin);
	
	List<Map<String,Object>> li=JsonUtils.getListvalueFromJson(path, "$.projects[*]");
	
	for(Map<String,Object> s:li) {
	
	System.out.println(s);}
	
	
	System.out.println(JsonUtils.getListvalueFromJson(path, "$.projects[*].team[*].name"));
	
	
	System.out.println(JsonUtils.getListvalueFromJson(path, "$..logs[?(@.level=='ERROR')].message"));

	
	System.out.println(JsonUtils.getListvalueFromJson(path, "$..features[*].testCases[?(@.status=='FAILED')].error.code"));

}}

