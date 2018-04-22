package framework.framework.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import framework.framework.bean.CellBean;
import framework.framework.bean.ConfigBean;
import framework.framework.bean.KeywordBean;
import framework.framework.bean.StepBean;
import framework.framework.bean.TestCaseBean;

public class FrameworkCustomFunctions {
	  private ConfigBean config = null;
	  private FileInputStream keystream =null;
      private FileInputStream stepstream = null;
      private HSSFSheet keysheet = null;
      private HSSFSheet stepsheet = null;
        public void SetConfig(ConfigBean bean){
        	this.config = bean;
        	try{
        	keystream = new FileInputStream(new File(bean.getKeywordfile()));
        	stepstream = new FileInputStream(new File(bean.getStepfile()));
        	keysheet = this.createsheet(keystream, 0);
        	stepsheet = this.createsheet(stepstream, 0);
        	}
        	catch(Exception ex){
        		ex.printStackTrace();
        	}
         } 
	HashMap<String, TestCaseBean> getSeleniumTestCasesDetails(
			HSSFSheet testsheet) {
		TestCaseBean testbean = null;
		HashMap<String, TestCaseBean> tests = new HashMap<String, TestCaseBean>();
       ArrayList<CellBean> cellarr = null;
		Iterator rows = testsheet.rowIterator();
		String value = null;
		CellBean cellbean = null;
		while (rows.hasNext()) {
			testbean = new TestCaseBean();
			HSSFRow row = (HSSFRow) rows.next();
			cellarr = new ArrayList<CellBean>();
			Iterator cells = row.cellIterator();

			while (cells.hasNext()) {

				HSSFCell cell = (HSSFCell) cells.next();
				value = getStringValue(cell, "");
				cellbean = new CellBean();
				cellbean.setCellstyle(cell.getCellStyle());
				
				cellbean.setCelltype(cell.getCellType());
				if (cell.getHyperlink() != null) {
					cellbean.setHyperlink(cell.getHyperlink());
				}
				if (cell.getCellComment() != null) {
					cellbean.setComment(cell.getCellComment());
				}
				if (cell.getColumnIndex() == 0) {

					testbean.setTestcasename(value);
				}
				if (cell.getColumnIndex() == 1) {

					testbean.setTestcasedesc(value);
				}
				if (cell.getColumnIndex() == 2) {

					testbean.setTestid(value);
				}
				

				if (cell.getColumnIndex() == 3) {

					testbean.setTestexecutecon(value);
				}
				if (cell.getColumnIndex() == 4) {

					testbean.setTestopenurl(value);
				}
				if (cell.getColumnIndex() == 5) {

					testbean.setResult(value);
				}
			   
              cellarr.add(cellbean);
			}
			ArrayList<KeywordBean> keyarr = this.getTestCaseKeyword(keysheet, testbean.getTestid());
			testbean.setKeywordbean(keyarr);
			testbean.setCellbean(cellarr);
			tests.put(testbean.getTestid(), testbean);
		}

		return tests;

	}

	ArrayList<KeywordBean> getTestCaseKeyword(HSSFSheet testsheet,
			String testid) {
		ArrayList<KeywordBean> keywords = new ArrayList<KeywordBean>();

		Iterator rows = testsheet.rowIterator();
		KeywordBean keybean = null;
		int count = 0;
		String value = null;
		String valuetype = null;
		String stepid = null;
		boolean test_keyword = false;
		while (rows.hasNext()) {
			keybean = new KeywordBean();
			HSSFRow row = (HSSFRow) rows.next();
			if (row.getRowNum() != 0) {
				test_keyword = false;
				Iterator cells = row.cellIterator();
				while (cells.hasNext()) {

					HSSFCell cell = (HSSFCell) cells.next();
					if (cell.getColumnIndex() == 7
							&& keybean.getValuetype() != null
							&& keybean.getValuetype().length() > 0)
						value = getStringValue(cell, valuetype);
					else
						value = getStringValue(cell, "");

					if (cell.getColumnIndex() == 0) {
						if (value.equalsIgnoreCase(testid)) {
							test_keyword = true;

							keybean.setTestcaseid(value);
						}

					}
					if (test_keyword) {
						if (cell.getColumnIndex() == 1) {

							keybean.setKeywordname(value);
						}
						if (cell.getColumnIndex() == 2) {

							keybean.setStepid(value);

						}

						if (keybean.getStepid() != null
								&& keybean.getStepid().equalsIgnoreCase("NA")) {
							if (cell.getColumnIndex() == 3) {

								keybean.setElementtype(value);

							}
							if (cell.getColumnIndex() == 4) {

								keybean.setLocatetype(value);
							}
							if (cell.getColumnIndex() == 5) {

								keybean.setLocatevalue(value);
							}

							if (cell.getColumnIndex() == 6) {

								keybean.setValuetype(value);
								valuetype = value;
							}
							if (cell.getColumnIndex() == 7) {

								keybean.setValue(value);
							}
							if (cell.getColumnIndex() == 8) {

								keybean.setStore_get_key(value);
							}

						}
						else{
							keybean.setHasSteps(true);
							
						}
					}
				}
				if (test_keyword){
					   if(keybean.isHasSteps()){
						  ArrayList<StepBean> steps = this.getSteps(stepsheet, keybean.getStepid());   
					      keybean.setSteps(steps);
					   }
					  
					keywords.add(keybean);
				}
			}
		}

		return keywords;

	}

	HSSFSheet createsheet(FileInputStream fstream, int fileindex) {
		HSSFSheet sheet = null;
		try {

			POIFSFileSystem fs = new POIFSFileSystem(fstream);
			HSSFWorkbook wb = new HSSFWorkbook(fs);

			sheet = wb.getSheetAt(fileindex);
		} catch (IOException ex) {
			ex.printStackTrace();
			WebdriverFunctions.AlertBox(ex.getMessage());
		}
		return sheet;
	}

	ArrayList<StepBean> getSteps(HSSFSheet stepssheet, String stepid) {
		int count = 0;
		ArrayList<StepBean> steps = new ArrayList<StepBean>();
		StepBean stepbean = null;
		Iterator rows = stepssheet.rowIterator();
		String valuetype = null;
		String value = null;
		boolean step_keyword = false;
		while (rows.hasNext()) {
			stepbean = new StepBean();
			HSSFRow row = (HSSFRow) rows.next();
			if (row.getRowNum() != 0) {
				step_keyword = false;
				Iterator cells = row.cellIterator();
				while (cells.hasNext()) {

					HSSFCell cell = (HSSFCell) cells.next();
					if (cell.getColumnIndex() == 6
							&& stepbean.getValuetype() != null
							&& stepbean.getValuetype().length() > 0)
						value = getStringValue(cell, valuetype);
					else
						value = getStringValue(cell, "");

					if (cell.getColumnIndex() == 0) {
						if (value.equalsIgnoreCase(stepid)) {

							step_keyword = true;

							stepbean.setStepid(value);
						}

					}

					if (step_keyword) {

						if (cell.getColumnIndex() == 1) {

							stepbean.setStepname(value);

						}
						if (cell.getColumnIndex() == 2) {

							stepbean.setElementtype(value);
						}
						if (cell.getColumnIndex() == 3) {

							stepbean.setLocatetype(value);
						}
						if (cell.getColumnIndex() == 4) {

							stepbean.setLocatevalue(value);
						}

						if (cell.getColumnIndex() == 5) {

							stepbean.setValuetype(value);
						}
						if (cell.getColumnIndex() == 6) {

							stepbean.setValue(value);
						}
						if (cell.getColumnIndex() == 7) {

							stepbean.setStore_get_key(value);
						}

					}

				}

				if (step_keyword) {

					steps.add(stepbean);
				}
			}
		}
		return steps;

	}

	private String getStringValue(HSSFCell cell, String valuetype) {
		String strval = null;
		if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			if (valuetype != null) {
				if (valuetype.equalsIgnoreCase("integer")) {
					strval = String.valueOf((long) cell.getNumericCellValue())
							.trim();
				}
				if (valuetype.equalsIgnoreCase("decimal")) {
					strval = String
							.valueOf((double) cell.getNumericCellValue())
							.trim();
				}

			}

		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
			strval = cell.getStringCellValue().trim();
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_ERROR) {
			strval = "";// cell.getErrorCellValue();
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
			try {

				strval = cell.getStringCellValue().trim();
			} catch (Exception e) {
				strval = "";
			}
		} else {
			strval = cell.getStringCellValue().trim();
		}
		return strval;
	}

	static void generateOutPut(HashMap<Integer, TestCaseBean> testcases,
			String opfile) {
		String testout = null;
		int rowcount = 0;
		int cellcount = 0;
		
		try {

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet outsheet = workbook.createSheet("Output Sheet");

			String value = null;
			for (int i=0;i<testcases.size();i++) {
				testout = null;
                TestCaseBean testbean =testcases.get(i);
				HSSFRow newRow = outsheet.createRow(rowcount);
				
				ArrayList<CellBean> cellarr = testbean.getCellbean();
				for (int j = 0; j <= 5; j++) {

					HSSFCell newCell = newRow.createCell(j);
                   
					// Copy style from old cell and apply to new cell
					HSSFCellStyle newCellStyle = workbook.createCellStyle();
					CellBean cellbean = cellarr.get(j);
					newCellStyle.cloneStyleFrom(cellbean.getCellstyle());

					newCell.setCellStyle(newCellStyle);

					// If there is a cell comment, copy
					if (cellbean.getComment() != null) {
						newCell.setCellComment(cellbean.getComment());
					}

					// If there is a cell hyperlink, copy
					if (cellbean.getHyperlink() != null) {
						newCell.setHyperlink(cellbean.getHyperlink());
					}
					if (j == 0) {
						value = testbean.getTestcasename();
					} else if (j == 1) {
						value = testbean.getTestcasedesc();
					} else if (j == 2) {
						value = testbean.getTestid();
					} else if (j == 3) {
						value = testbean.getTestexecutecon();
					} else if (j == 4) {
						value = testbean.getTestopenurl();
					} else if (j == 5) {
						if (testbean.isIsrun() != null
								&& testbean.isIsrun().equalsIgnoreCase("Pass")) {
							value = "Pass";
						} else if (testbean.isIsrun() != null
								&& testbean.isIsrun().equalsIgnoreCase("Fail")) {
							value = "Fail";
						} else {
							value = testbean.getResult();
						}
					}
					 
					newCell.setCellType(cellbean.getCelltype());
                     
					if(j==5 && testbean.isIsrun()!=null){
						cellbean.setCelltype(1);
					}
					switch (cellbean.getCelltype()) {
					case HSSFCell.CELL_TYPE_BLANK:

						newCell.setCellValue("");
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						newCell.setCellValue(Boolean.parseBoolean(value));
						break;
					case HSSFCell.CELL_TYPE_ERROR:
						newCell.setCellErrorValue(Byte.parseByte(value));
						break;
					case HSSFCell.CELL_TYPE_FORMULA:
						newCell.setCellFormula(value);
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						newCell.setCellValue(Double.parseDouble(value));
						break;
					case HSSFCell.CELL_TYPE_STRING:
						newCell.setCellValue(value);
						break;
					}
					
					outsheet.autoSizeColumn(j);
				}
				
				rowcount++;

			}
			FileOutputStream out = new FileOutputStream(opfile);
			workbook.write(out);
			out.close();
		} catch (IOException e) {

			WebdriverFunctions.AlertBox(e.getMessage());
		} catch (Exception e) {
			WebdriverFunctions.AlertBox(e.getMessage());
		}

	}
}
