package in.novopay.cbs.apis.getformmetadata;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import in.novopay.cbs.utils.ApiUtils;
import in.novopay.cbs.utils.JavaUtils;
import in.novopay.cbs.utils.JsonLib;

public class GetFormMetaDataTest extends  ApiUtils{
	
	public String APINAME = "getFormMetaData", Filepath ="./test-data/getFormMetaData.json";
	JavaUtils javaUtils = new JavaUtils();
	
	JsonLib objJsonLib = new JsonLib();
	
	@BeforeSuite
	public void readConfig() {
		javaUtils.readConfigProperties();
	}
	
	@Test(dataProvider = "getTestData")
	public void getFormMetaDataTest(HashMap<String, JSONObject> usrData) {
		
		System.out.println("Executing test case id -  "+ usrData.get("TestCase_ID"));
		
		 String APIAppend = APINAME + "?formDocId=" +usrData.get("formDocId");
		String response = getLOSRequest(APIAppend);
		//System.out.println("Executing Response -  "+ response);

		objJsonLib.assertAPIResponseJsonStatus(APINAME, usrData, response);
		
		
		
		System.out.println("************************************************************************************");
	}

	@DataProvider
	public Object[][] getTestData() throws Exception {
		
		Object[][] allValues = objJsonLib.getValues(Filepath);

		return allValues;

	}
}
