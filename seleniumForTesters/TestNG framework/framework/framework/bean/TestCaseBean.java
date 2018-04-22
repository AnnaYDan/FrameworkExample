package framework.framework.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

public class TestCaseBean implements Serializable{
	private String testcasename;
	private String testcasedesc;
	private String testid;
	private String testexecutecon;
	private String testopenurl;
	private String value;
	private String isrun;
    private ArrayList<CellBean> cellbean;
    private boolean exeuserclass;
    private String classname;
    private ArrayList<KeywordBean> keywordbean;
    private String result;
	public boolean isExeuserclass() {
		return exeuserclass;
	}

	public void setExeuserclass(boolean exeuserclass) {
		this.exeuserclass = exeuserclass;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getIsrun() {
		return isrun;
	}

	

	public ArrayList<CellBean> getCellbean() {
		return cellbean;
	}

	public void setCellbean(ArrayList<CellBean> cellbean) {
		this.cellbean = cellbean;
	}

	public String isIsrun() {
		return isrun;
	}

	public void setIsrun(String isrun) {
		this.isrun = isrun;
	}

	public String getTestcasename() {
		return testcasename;
	}

	public void setTestcasename(String testcasename) {
		this.testcasename = testcasename;
	}

	public String getTestcasedesc() {
		return testcasedesc;
	}

	public void setTestcasedesc(String testcasedesc) {
		this.testcasedesc = testcasedesc;
	}

	public String getTestid() {
		return testid;
	}

	public void setTestid(String testid) {
		this.testid = testid;
	}

	public String getTestexecutecon() {
		return testexecutecon;
	}

	public void setTestexecutecon(String testexecutecon) {
		this.testexecutecon = testexecutecon;
	}

	public String getTestopenurl() {
		return testopenurl;
	}

	public void setTestopenurl(String testopenurl) {
		this.testopenurl = testopenurl;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ArrayList<KeywordBean> getKeywordbean() {
		return keywordbean;
	}

	public void setKeywordbean(ArrayList<KeywordBean> keywordbean) {
		this.keywordbean = keywordbean;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	
}
