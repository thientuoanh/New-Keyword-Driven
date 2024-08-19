package excelExportAndFileIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;


public class WriteExcel 
{

	public static void setCellData(String Result, int RowNum, int ColNum, String SheetName, String PathFile, 
			String Color) throws Exception 
	{
		try 
		{
			
			FileInputStream excelFile = new FileInputStream(new File(PathFile));
			
			XSSFSheet ExcelWSheet;
			XSSFWorkbook ExcelWBook = new XSSFWorkbook(excelFile);
			XSSFCell Cell = null;
			
//			ExcelWSheet = ExcelWBook.getSheetAt(0);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			XSSFRow Row = ExcelWSheet.getRow(RowNum);
			Cell = Row.getCell(ColNum, org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
			if (Cell == null) {
				Cell = Row.createCell(ColNum);
				Cell.setCellValue(Result);
			} else {
				Cell.setCellValue(Result);
			}

			// Set Cell Color
			XSSFCellStyle style = ExcelWBook.createCellStyle();
			if (Color == "red") {
				style.setFillForegroundColor(IndexedColors.RED.getIndex());
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			} else if (Color == "green") {
				style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			}
			Cell.setCellStyle(style);
			
			// Constant variables Test Data path and Test Data file name
			FileOutputStream fileOut = new FileOutputStream(PathFile);
			ExcelWBook.write(fileOut);
			fileOut.close();
			
			


		} catch (Exception e) {
		}
	}
	


}
