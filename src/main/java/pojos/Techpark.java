package pojos;

import java.util.List;

public class Techpark {
	
	private String company;
	
	private Location location;
	private List <Department> department;
	private int foundedyear;
	private boolean public1;
	

	
	public void setLocation(Location location) {
		
		this.location=location;
		
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setCompany(String company) {
		
		this.company= company;
		
	}
	
	public String getCompany() {
		return company;
	}

	public void setDepartment(List <Department> department) {
		
		this.department=department;
		
	}
	public List<Department> getDepartment() { return department;}
	
	public void setFoundedYear(int foundedyear) {
		this.foundedyear=foundedyear;
	}
	public int getFoundedyear() { return foundedyear;}
	
	public void setPublic1(boolean public1) {
		
		this.public1=public1;
		
	}
	public boolean getPublic1() {return public1;}
	
	
	
}
