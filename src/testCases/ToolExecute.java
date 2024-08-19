package testCases;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import Data.Contans;
import Data.Contans.CaseResult;
import excelExportAndFileIO.ReadExcel;
import excelExportAndFileIO.WriteExcel;
import operation.ReadObject;
import operation.UIOperation;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Properties;
import java.awt.event.ActionEvent;

public class ToolExecute extends JFrame 
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static JTextField txt_SelectExcelFIle;
	public static JTextField txt_SelectObjectFile;
	public static String PathFileTestCase;
	public static String PathFileObject;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ToolExecute frame = new ToolExecute();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ToolExecute() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 584, 348);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JFileChooser chooser = new JFileChooser();
//		final JFileChooser chooser2 = new JFileChooser();
		
		JButton btn_SelectExcelFile = new JButton("Select Excel File:");
		btn_SelectExcelFile.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Select File");
				chooser.setAcceptAllFileFilterUsed(true);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					PathFileTestCase = chooser.getSelectedFile().getPath();
					System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
					txt_SelectExcelFIle.setText(PathFileTestCase);

				}
				else {
					System.out.println("No Selection ");
				}
				
			}
		});
		btn_SelectExcelFile.setBounds(10, 61, 129, 23);
		contentPane.add(btn_SelectExcelFile);
		
		JButton btn_Execute = new JButton("Execute");
		btn_Execute.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				// Execute Test cases
				String drivePath = Contans.HomePath + "chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", drivePath);
		    
				WebDriver driver = new ChromeDriver();
				driver.manage().window().maximize();
				ReadExcel file = new ReadExcel();
		        ReadObject object = new ReadObject();
		        Properties allObjects = null;
				try {
					allObjects = object.getObjectRepository();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		        UIOperation operation = new UIOperation(driver);
		        
		        //Read keyword sheet
		        Sheet Sheet = null;
				try {
					Sheet = file.readExcel(Contans.HomePath, Contans.TestcaseFile , Contans.SheetName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        
		        //Find number of rows in excel file
		    	int rowCount = Sheet.getLastRowNum()-Sheet.getFirstRowNum();
		    	
		    	//Create a loop over all the rows of excel file to read it
		    	for (int i = 1; i < rowCount+1; i++) 
		    	{
		    		//Loop over all the rows
		    		Row row = Sheet.getRow(i);
		    		
		    		//Check if the first cell contain a value, if yes, That means it is the new testcase name
		    		if(row.getCell(0).toString().length()==0){
		    		
		    			//Print testcase detail on console
		    			System.out.println(row.getCell(1).toString()+"----"+ row.getCell(2).toString()+"----"+
		    			row.getCell(3).toString()+"----"+ row.getCell(4).toString());
		    			
		    			//Call perform function to perform operation on UI
		    			try {
							operation.perform(allObjects, row.getCell(1).toString(), row.getCell(2).toString(),
								row.getCell(3).toString(), row.getCell(4).toString());
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		    			
		    			if(CaseResult.IsPass == 1)
		    			{
		    				// Set PASS value and Green color when the case passed 		    				
								try {
									WriteExcel.setCellData(CaseResult.PASS, i, Contans.ResultColumn, Contans.SheetName, PathFileTestCase, CaseResult.GREEN);
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						
		    			}
		    			else 
		    			{
		    				// Set FAIL value and Red color when the case failed 
		    				try {
								WriteExcel.setCellData(CaseResult.FAIL, i, Contans.ResultColumn, Contans.SheetName, PathFileTestCase, CaseResult.RED);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		    			}

		    	    }
		    		else
		    		{
		    			//Print the new  testcase name when it started
		    				System.out.println("New Testcase->"+row.getCell(0).toString() +" Started");
		    			}
		    		}

			}
		});
		btn_Execute.setBounds(10, 11, 129, 23);
		contentPane.add(btn_Execute);
		
		JButton btn_SelectObjectFile = new JButton("Select Object File:");
		btn_SelectObjectFile.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Select File");
				chooser.setAcceptAllFileFilterUsed(true);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					PathFileObject = chooser.getSelectedFile().getPath();
					System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
					txt_SelectObjectFile.setText(PathFileObject);

				}
				else {
					System.out.println("No Selection ");
				}
			}
		});
		btn_SelectObjectFile.setBounds(10, 95, 129, 23);
		contentPane.add(btn_SelectObjectFile);
		
		txt_SelectExcelFIle = new JTextField();
		txt_SelectExcelFIle.setText(Contans.HomePath + Contans.TestcaseFile);
		PathFileTestCase = txt_SelectExcelFIle.getText().trim();
		txt_SelectExcelFIle.setEditable(false);
		txt_SelectExcelFIle.setBounds(149, 62, 419, 20);
		contentPane.add(txt_SelectExcelFIle);
		txt_SelectExcelFIle.setColumns(10);
		
		txt_SelectObjectFile = new JTextField();
		txt_SelectObjectFile.setText(Contans.HomePath + Contans.ObjectName);
		PathFileObject = txt_SelectObjectFile.getText().trim();
		txt_SelectObjectFile.setEditable(false);
		txt_SelectObjectFile.setColumns(10);
		txt_SelectObjectFile.setBounds(149, 96, 419, 20);
		contentPane.add(txt_SelectObjectFile);
	}
}
