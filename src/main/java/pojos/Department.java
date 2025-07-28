package pojos;

import java.util.List;

public class Department {
	
	private String name;
	private Head head;
	private List <Employee> employee;
	
	
	
	
	public void setName(String name){
		
		this.name= name;
		
	}
	
	public String getName() {
		return name;
	}
	
	public void setHead(Head head) {
		this.head= head;
		
	}
	
	public Head getHead() {
		return head;
	}
	
	public void setEmp(List <Employee> employee) {
		
		this.employee= employee;
		
	}
	
	public List<Employee> getEmp() {
		return employee;
	}
	

}
