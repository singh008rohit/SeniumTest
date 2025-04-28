package utlity;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GetDataFromExcel {
	
	
	void get() throws IOException {
		
		FileInputStream fs= new FileInputStream("D//data.xls");
		
		XSSFWorkbook book = new XSSFWorkbook(fs);
		XSSFSheet sheet=	book.getSheet(null);
		
		
	int rowCount=	sheet.getPhysicalNumberOfRows();
	
	XSSFRow row=	sheet.getRow(0);
	
	int columncount=row.getLastCellNum();
	Object data[][]= new Object[rowCount-1][columncount];
	
	for(int i=0;i<rowCount;i++) {
		
		row =sheet.getRow(i+1);
		
		for(int j=0; j<columncount;j++) {
			
		XSSFCell cell= row.getCell(j);
		data[i][j]=row.getCell(j);;
		
		}
	}
		
		
	}
	

}
