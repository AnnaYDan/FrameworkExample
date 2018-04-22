package framework.framework.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import framework.framework.bean.ConfigBean;



public class Framework_Config extends DefaultHandler{
	
	private ArrayList<ConfigBean> config = new ArrayList<ConfigBean>();
	private String content =null;
	private ConfigBean bean = null;
	public Framework_Config(InputStream is) throws ParserConfigurationException, SAXException, IOException{
		SAXParserFactory sax = SAXParserFactory.newInstance();
		SAXParser sp = sax.newSAXParser();
		
			sp.parse(is, this);
			
	}
			
			@Override
			public void startElement(String arg0, String arg1, String arg2,
					Attributes arg3) throws SAXException {
				// TODO Auto-generated method stub
				if ("frame-config".equals(arg2)) {
					bean = new ConfigBean();
					
				}
				
			}

			@Override
			public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
				// TODO Auto-generated method stub
				content = new String(arg0, arg1, arg2);

				content = content.trim();

			}

			@Override
			public void endElement(String arg0, String arg1, String arg2)
					throws SAXException {
				// TODO Auto-generated method stub
				if ("frame-config".equals(arg2)) {
					config.add(bean);

				}
				if ("suitename".equals(arg2)) {
					bean.setSuitename(content);
					
				}
				if ("testcase-file".equals(arg2)) {
					bean.setTestfile(content);
					
				}
				if ("keyword-file".equals(arg2)) {
					bean.setKeywordfile(content);
				}
				if ("step-file".equals(arg2)) {
					bean.setStepfile(content);
				}
				if ("op-gen-file".equals(arg2)) {
					bean.setOpfile(content);
					
				}
				
			}
	public ArrayList<ConfigBean> getFrameworkConfig(){
	
			return config;
			
		}
}
