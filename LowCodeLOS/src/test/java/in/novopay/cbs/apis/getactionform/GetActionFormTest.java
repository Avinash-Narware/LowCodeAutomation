package in.novopay.cbs.apis.getactionform;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import in.novopay.cbs.utils.ApiUtils;
import in.novopay.cbs.utils.JavaUtils;
import in.novopay.cbs.utils.JsonLib;

public class GetActionFormTest extends  ApiUtils{
	


	public String APINAME = "getActionForm", Filepath ="./test-data/getActionForm.json";
	JavaUtils javaUtils = new JavaUtils();
	
	JsonLib objJsonLib = new JsonLib();
	
	@BeforeSuite
	public void readConfig() {
		javaUtils.readConfigProperties();
	}
	
	@Test(dataProvider = "getTestData")
	public void getActionFormTest(HashMap<String, JSONObject> usrData) {
		
		System.out.println("Executing test case id -  "+ usrData.get("TestCase_ID"));
		
		 String APIAppend = APINAME + "?formId=" +usrData.get("formId");
		String response = getLOSRequest(APIAppend);
		
		objJsonLib.assertAPIResponseJsonStatus(APINAME, usrData, response);

		objJsonLib.assertResponse(APINAME, usrData, response);
	}

	@DataProvider
	public Object[][] getTestData() throws Exception {
		
		Object[][] allValues = objJsonLib.getValues(Filepath);

		return allValues;

	}
}
