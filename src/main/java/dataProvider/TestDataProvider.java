package dataProvider;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import utlity.CSVUtils;
import utlity.JsonDataReaderUtils;

public class TestDataProvider {
	
	final	String CSVFilePath ="/src/test/java/resources/loginDetails.csv";
	final String JsonFilePath = "/src/test/java/resources/testdata.json";
	final String LocalizationFilePath = "/src/test/java/resources/localization.json";
	
	@DataProvider(name="testDataFromCSV")
	
	public Object[][] getDataFromCSV() throws IOException{
	
		
	List<HashMap<String,String>> listData=CSVUtils.getvalue(System.getProperty("user.dir")+CSVFilePath);
	
	Object[][] data= new Object[listData.size()][1];
	
	for(int i=0;i<listData.size();i++) {
		
		data[i][0]=listData.get(i);
		
	}
	return data;
	
	
		
	}
	
	@DataProvider(name="testDataFromJson")
	
	public Object[][] getDataFromJson() throws StreamReadException, DatabindException, IOException{
	List<HashMap<String,String>> listdata=	JsonDataReaderUtils.getDate(System.getProperty("user.dir")+JsonFilePath);
		Object[][] obj= new Object[listdata.size()][1];
		
		for(int i=0;i<listdata.size();i++) {
			obj[i][0]=listdata.get(i);
		}
		return obj;
		
	}
	
	@DataProvider(name = "getDataForLocalization")
	public Object[][] getDataForLocalization() throws IOException {

	    List<HashMap<String, Object>> listData =
	            JsonDataReaderUtils.getLocalizationData(
	                    System.getProperty("user.dir") + LocalizationFilePath);

	    Object[][] data = new Object[listData.size()][1];

	    for (int i = 0; i < listData.size(); i++) {
	        data[i][0] = listData.get(i);
	    }

	    return data;
	}
	

}
