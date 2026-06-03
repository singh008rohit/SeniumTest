package utlity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDataReaderUtils {
	
	public static List<HashMap<String,String>> getDate(String filePath) throws StreamReadException, DatabindException, IOException{
		ObjectMapper mapper= new ObjectMapper();
	return	mapper.readValue(new File(filePath),
				new TypeReference<List<HashMap<String,String>>>() {}
				
			
		);
	}
	
	public static List<HashMap<String, Object>> getLocalizationData(String filePath)
	        throws IOException {

	    ObjectMapper mapper = new ObjectMapper();

	    return mapper.readValue(
	            new File(filePath),
	            new TypeReference<List<HashMap<String, Object>>>() {});
	}

}
