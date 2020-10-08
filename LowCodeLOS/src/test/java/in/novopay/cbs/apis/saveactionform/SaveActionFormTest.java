package in.novopay.cbs.apis.saveactionform;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jayway.restassured.path.json.JsonPath;

import in.novopay.cbs.utils.ApiUtils;
import in.novopay.cbs.utils.JavaUtils;
import in.novopay.cbs.utils.JsonLib;

public class SaveActionFormTest extends ApiUtils{
	


	public String APINAME = "saveActionForm", Filepath ="./test-data/saveActionForm.json";
	JavaUtils javaUtils = new JavaUtils();
	
	JsonLib objJsonLib = new JsonLib();
	
	@BeforeSuite
	public void readConfig() {
		javaUtils.readConfigProperties();
	}
	
	@Test(dataProvider = "getTestData")
	public void saveActionFormTest(HashMap<String, JSONObject> usrData) {
		
		System.out.println("Executing test case id -  "+ usrData.get("TestCase_ID"));
		
		String requestBody = usrData.get("request").toJSONString();
		
		String response = postRequestLow(APINAME, requestBody);
		
		JsonPath jpath = new JsonPath(response);
		if (jpath.getString("status").equalsIgnoreCase("SUCCESS")) {
			String FormDocId = jpath.getString("formDocId");
			javaUtils.addTestDataIni("FormDocId", FormDocId);
		}
		
		objJsonLib.assertAPIResponseJsonStatus(APINAME, usrData, response);
		
		
		System.out.println("************************************************************************************");
	}

	@DataProvider
	public Object[][] getTestData() throws Exception {
		
		Object[][] allValues = objJsonLib.getValues(Filepath);

		return allValues;

	}
}
