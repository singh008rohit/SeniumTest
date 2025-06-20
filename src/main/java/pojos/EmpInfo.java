package pojos;

import io.restassured.mapper.ObjectMapper;

public class EmpInfo {
	

	private String name;
	private int age;
	private String gender;
	
	public EmpInfo(String name,int age,String gender){
		this.name=name;
		this.age=age;
		this.gender=gender;
				
		
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setage(int age) {
		this.age=age;
	}
	public int getAge() {
		return age;
	}
	
	public void setGender(String gender) {
		
		this.gender=gender;
		
	}
	public String getGender() {
		return gender;
	}
	
	
	

	
}
