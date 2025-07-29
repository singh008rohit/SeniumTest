package driver;

import enums.DriverType;

public class DriverManagerFactory {
	
public static DriverManager_OC getManager(DriverType driverType) {
	
	
	switch(driverType) {
	
	case CHROME:{
		return new DriverManagerChrome();
		}
	case FIREFOX:{
		return new DriverManagerFirefox();
		}
	case EDGE:{
		return new DriverManagerEdge();
		}
	default : throw new IllegalArgumentException("Invalid Driver: " + driverType);	

		
	
	}
		
	}

}
