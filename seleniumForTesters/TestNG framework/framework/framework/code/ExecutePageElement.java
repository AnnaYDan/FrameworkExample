package framework.framework.code;

import java.util.HashMap;

import org.junit.Assert;

import framework.framework.bean.KeywordBean;
import framework.framework.bean.StepBean;

public class ExecutePageElement {
	private HashMap<String, String> storevalue = new HashMap<String, String>();
	void executeSteps(KeywordBean stepbean) throws Exception {

		String attribute = null;
		String title = null;

		String elementtype = stepbean.getElementtype();
		if (elementtype != null)
			elementtype = elementtype.trim();
		String locatetype = stepbean.getLocatetype();
		if (locatetype != null)
			locatetype = locatetype.trim();
		String locatevalue = stepbean.getLocatevalue();
		if (locatevalue != null)
			locatevalue = locatevalue.trim();

		String value = stepbean.getValue();
		if (value != null)
			value = value.trim();
		String store_get_key = stepbean.getStore_get_key();
		if (store_get_key != null) {
			store_get_key = store_get_key.trim();
		}

		try {
			if (elementtype.equalsIgnoreCase("type"))
				WebdriverFunctions.Type(locatetype, locatevalue, value);
			else if (elementtype.equalsIgnoreCase("click"))
				WebdriverFunctions.click_element(WebdriverFunctions.getWebElement(locatetype,
						locatevalue));
			else if (elementtype.equalsIgnoreCase("select"))
				WebdriverFunctions.selectList(locatetype, locatevalue, value);
			else if (elementtype.equalsIgnoreCase("waitforelement"))
				WebdriverFunctions.waitForElement(locatetype, locatevalue);
			else if (elementtype.equalsIgnoreCase("pause"))
				WebdriverFunctions.sleep(value);
			else if (elementtype.equalsIgnoreCase("storetitle"))
				storevalue.put(store_get_key, WebdriverFunctions.getTitle());
			else if (elementtype.equalsIgnoreCase("storeattribute"))
				storevalue.put(store_get_key,
						WebdriverFunctions.getAttributeName(locatetype, locatevalue));
			else if (elementtype.equalsIgnoreCase("echo"))
				System.out.println(storevalue.get(store_get_key));
			else if (elementtype.equalsIgnoreCase("asserttitle")) {

				if (value.equals(WebdriverFunctions.getTitle())) {
					//WebdriverFunctions.AlertBox("Title matched");
					System.out.println(stepbean.getKeywordname() + " : " + "title matched");

				} else {
					  
					WebdriverFunctions.AlertBox("Title did not match");
					Assert.fail();

				}

			}
		} catch (Exception e) {
			if (elementtype == null) {
				throw new Exception("Command_null");
			}
		  e.printStackTrace();
		}

	}
}
