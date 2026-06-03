package requestBody;

import java.util.HashMap;
import java.util.Map;

import pageConstant.FlightSearchConstants;

public class FlightSearch {
	
	
	public static Map<String,Object> getFlightSearchdata() {
		
		
		HashMap<String, Object> requestBody = new HashMap<>();
		
		requestBody.put("flightNumber", FlightSearchConstants.FLIGHT_NUMBER);
		requestBody.put("departureDate", FlightSearchConstants.DEPARTURE_DATE);
		
		return requestBody;
		   
		
	}

}
