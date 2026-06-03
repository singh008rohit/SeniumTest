package utlity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVUtils {
	
	
	public static List<HashMap<String,String>> getvalue(String filePath) throws IOException{
		
		BufferedReader reader= new BufferedReader(new FileReader(filePath));
		List<HashMap<String,String>> dataList=new  ArrayList<>();
	String[] header=	reader.readLine().split(",");
	String line1;
	while((line1=reader.readLine())!=null) {
		String[] line=line1.split(",");
		HashMap<String,String> mp= new HashMap<>();

		for(int i=0;i<header.length;i++) {
			
			mp.put(header[i].trim(), line[i].trim());
		}    
		dataList.add(mp);
		
	}
	reader.close();
	return dataList;
		
	}
	


}
