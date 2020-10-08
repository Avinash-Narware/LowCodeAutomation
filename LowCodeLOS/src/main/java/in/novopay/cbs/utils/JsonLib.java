package in.novopay.cbs.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.Reporter;

import com.jayway.restassured.path.json.JsonPath;

public class JsonLib extends JavaUtils {
	ApiUtils apiUtils = new ApiUtils();
	
	public JSONArray readFileJson(String filePath) throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(filePath);
		Object obj = jsonParser.parse(reader);
		JSONObject jo = (JSONObject) obj;
		return (JSONArray) jo.get("Payloads");
	}
	
	
public Object[][] getValues(String Filepath) throws Exception, ParseException{
		
	JsonLib objJsonLib = new JsonLib();
		JSONArray fileData = objJsonLib.readFileJson(Filepath);
		Object[][] allValues = new Object[fileData.size()][1];

		
			for (int i = 0; i < fileData.size(); i++) {
				
				JSONObject rObj = (JSONObject) fileData.get(i);
				
				allValues[i][0] = rObj;
			}
		return allValues;
		
	}


public void assertAPIResponseJsonStatus(String api, HashMap<String, JSONObject> usrData, String response) {

	int flag = 0;
	String expectedFormDocId ;
	String expectedStatus = usrData.get("response").get("status").toString();
	String expecteddesc = usrData.get("response").get("desc").toString();
	String expectedMessage = usrData.get("response").get("message").toString();
	if (api.contains("ActionForm")) {
		 expectedFormDocId = usrData.get("response").get("formId").toString();
	} else {
		 expectedFormDocId = usrData.get("response").get("formDocId").toString();
	}
	
	
	JsonPath jsonPath = new JsonPath(response);
	String actualMessage = jsonPath.get("message");

	String actualStatus = jsonPath.get("status");
	String actualdesc = jsonPath.get("desc");
	String actualFormDocId = jsonPath.get("formDocId");
	StringBuilder msg = new StringBuilder();
	if (actualStatus.equalsIgnoreCase("SUCCESS")) {

		try {
			Assert.assertEquals(actualdesc, expecteddesc,
					"FAILURE..! Assertion for " + api + " response desc failed..!");
			Reporter.log("\nResponse desc Assertion successful..!", true);
		} catch (AssertionError ae) {
			msg.append("Response desc assertion failed. Expected [" + expecteddesc + "] Actual [" + actualdesc
					+ "]\n");
			Reporter.log(ae.getMessage(), true);
			flag = 1;
		}

		try {
			Assert.assertEquals(actualStatus, expectedStatus,
					"FAILURE..! Assertion for " + api + " response status failed..!");
			Reporter.log("\nResponse status assertion successful..!", true);
		} catch (AssertionError ae) {
			msg.append("Response status assertion failed. Expected [" + expectedStatus + "], Actual ["
					+ actualStatus + "]\n");
			Reporter.log(ae.getMessage(), true);
			flag = 1;

		}
		
		try {
			Assert.assertEquals(actualMessage, expectedMessage,
					"FAILURE..! Assertion for " + api + " response message failed..!");
			Reporter.log("\nResponse message Assertion successful..!", true);
		} catch (AssertionError ae) {
			msg.append("Response status assertion failed. Expected [" + expectedMessage + "], Actual ["
					+ actualMessage + "]\n");
			Reporter.log(ae.getMessage(), true);
			flag = 1;
		}
		
		try {
			Assert.assertNotEquals(actualFormDocId, expectedFormDocId,
					"FAILURE..! Assertion for " + api + " response formDocID failed..!");
			Reporter.log("\nResponse formDocID Assertion successful..!", true);
		} catch (AssertionError ae) {
			msg.append("Response formDocID assertion failed. Expected [" + expectedFormDocId + "], Actual ["
					+ actualFormDocId + "]\n");
			Reporter.log(ae.getMessage(), true);
			flag = 1;
		}
		
		
	} else {
		try {
			Assert.assertEquals(actualdesc, expecteddesc,
					"FAILURE..! Assertion for " + api + " response desc failed..!");
			Reporter.log("\nResponse desc Assertion successful..!", true);
		} catch (AssertionError ae) {
			msg.append("Response desc assertion failed. Expected [" + expecteddesc + "] Actual [" + actualdesc
					+ "]\n");
			Reporter.log(ae.getMessage(), true);
			flag = 1;
		}

		try {
			Assert.assertEquals(actualStatus, expectedStatus,
					"FAILURE..! Assertion for " + api + " response status failed..!");
			Reporter.log("\nResponse status assertion successful..!", true);
		} catch (AssertionError ae) {
			msg.append("Response status assertion failed. Expected [" + expectedStatus + "], Actual ["
					+ actualStatus + "]\n");
			Reporter.log(ae.getMessage(), true);
			flag = 1;
		}

		try {
			Assert.assertEquals(actualMessage, expectedMessage,
					"FAILURE..! Assertion for " + api + " response message failed..!");
			Reporter.log("\nResponse message Assertion successful..!", true);
		} catch (AssertionError ae) {
			msg.append("Response status assertion failed. Expected [" + expectedMessage + "], Actual ["
					+ actualMessage + "]\n");
			Reporter.log(ae.getMessage(), true);
			flag = 1;
		}
		
		try {
			Assert.assertNotEquals(actualFormDocId, expectedFormDocId,
					"FAILURE..! Assertion for " + api + " response message failed..!");
			Reporter.log("\nResponse message Assertion successful..!", true);
		} catch (AssertionError ae) {
			msg.append("Response status assertion failed. Expected [" + expectedFormDocId + "], Actual ["
					+ actualFormDocId + "]\n");
			Reporter.log(ae.getMessage(), true);
			flag = 1;
		}

	}

	if (flag == 1) {
		setFailureReason(msg.toString());
		Assert.fail("TEST FAILED!!! Assertion error..!");
	}

}

public void assertResponse(String api, HashMap<String, JSONObject> usrData, String response) {

	
	System.out.println(apiUtils.ServerCode );
	//String expectedStatus = usrData.get("response").get("status").toString();

}


}
