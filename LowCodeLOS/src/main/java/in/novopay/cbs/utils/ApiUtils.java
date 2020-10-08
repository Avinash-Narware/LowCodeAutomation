package in.novopay.cbs.utils;

import static com.jayway.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.Reporter;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;






public class ApiUtils extends JavaUtils {

	String stri;
	String fileName;

	int ServerCode;
	Properties velocityProps;

	public HashMap<String, String> getHeaders(HashMap<String, String> usrData) {
		System.out.println(usrData.get("TCID"));
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", usrData.get("Content-Type"));
//		if (!(usrData.get("Authorization").equalsIgnoreCase("no"))) {
//			usrData.put("Authorization", "dynamic");
//		}
		usrData.get("tenant_code");
		usrData.put("tenant_code", "novopay");
		if ((usrData.get("user_handle_value") != "") && (!usrData.get("TCID").toLowerCase().contains("login"))) {
			usrData.put("user_handle_value", usrData.get("user_handle_value"));
		}
		if (usrData.get("Authorization").equalsIgnoreCase("dynamic")) {
			System.out.println(usrData.get("tenant_code") + usrData.get("user_handle_value") + "session");
//			if (null == configProperties
//					.get(usrData.get("tenant_code") + usrData.get("user_handle_value") + "session")) {
//				configProperties.put(usrData.get("tenant_code") + usrData.get("user_handle_value") + "session",
//						new DBUtils().getSessionToken(usrData));
//			}
		//	headers.put("Authorization",
		//			configProperties.get(usrData.get("tenant_code") + usrData.get("user_handle_value") + "session"));
		
		
			headers.put("Authorization",getvalueFromTestDataIni("SessionToken"));
		
		} else {
			headers.put("Authorization", usrData.get(null));
		}

		headers.put("Accept", usrData.get("Accept"));
		System.out.println(headers);
		return headers;
	}


	public String getRequest(String apiName, Map<String, String> headers) {

		String server = configProperties.get("serverHostName");
		String path = "";

		path = configProperties.get("serverPath");

		String serverURL = server + path + apiName;
		System.out.println(serverURL);
		double startTime = System.currentTimeMillis();

		Response post = given().relaxedHTTPSValidation().headers(headers).when().get(serverURL);

		double stopTime = System.currentTimeMillis();

		String response = post.asString();
		Reporter.log("\nServer URL : " + serverURL, true);

		Reporter.log("\nTotal time taken for the get request is : " + ((stopTime - startTime) / 1000) + " Seconds ...",
				true);

		Reporter.log("\nResponse Obtained : " + response, true);
		return response;
	}
	
	public String getLOSRequest(String apiName) {

		String server = configProperties.get("serverHostName");
		String path = "";

		path = configProperties.get("serverPath");

		String serverURL = server + path + apiName;
		System.out.println(serverURL);
		double startTime = System.currentTimeMillis();

//		Response post = given().when().get(serverURL);
		
		Response post = given()
		        .get(serverURL);
		
		
		
//		Response post = given()
//		    	.contentType("application/json").
//		    	body(requestInStringFormat).
//		        when().
//		        post(serverURL);

		double stopTime = System.currentTimeMillis();

		String response = post.asString();
		Reporter.log("\nServer URL : " + serverURL, true);

		Reporter.log("\nTotal time taken for the get request is : " + ((stopTime - startTime) / 1000) + " Seconds ...",
				true);

		Reporter.log("\nResponse Obtained : " + response, true);
		return response;
	}

	public String postRequest(String apiName, String requestInStringFormat, Map<String, String> headers) {

		String server = configProperties.get("serverHostName");
		String path = "";

		path = configProperties.get("serverPath");

		String serverURL = server + path + apiName;
	
		System.out.println(serverURL);
		double startTime = System.currentTimeMillis();

		Response post = given().relaxedHTTPSValidation().headers(headers).body(requestInStringFormat).when()
				.post(serverURL);

		
	
		double stopTime = System.currentTimeMillis();

		String response = post.asString();
		Reporter.log("\nServer URL : " + serverURL, true);
		String timeTaken = String.valueOf((stopTime - startTime) / 1000);
		setTimeTaken(timeTaken);
		Reporter.log("\nTotal time taken for the post request is : " + getTimeTaken() + " Seconds ...", true);

		Reporter.log("\nRequest sent is \n" + requestInStringFormat, true);

		Reporter.log("\nResponse Obtained : " + response, true);
		return response;
	}
	
	public String postRequestLow(String apiName, String requestInStringFormat) {

		String server = configProperties.get("serverHostName");
		String path = "";
		Response post = null;
		path = configProperties.get("serverPath");

		String serverURL = server + path + apiName;
	
		System.out.println(serverURL);
		double startTime = System.currentTimeMillis();
		
		

		//Response post = given().body(requestInStringFormat).when().post(serverURL);
		
		try {
			 post = given()
			    	.contentType("application/json").
			    	body(requestInStringFormat).
			        when().
			        post(serverURL);
		} catch (Exception e) {
			System.out.println("Bad Request");
		}
		
		double stopTime = System.currentTimeMillis();

		String response = post.asString();
		System.out.println(response);
		Reporter.log("\nServer URL : " + serverURL, true);
		String timeTaken = String.valueOf((stopTime - startTime) / 1000);
		setTimeTaken(timeTaken);
		Reporter.log("\nTotal time taken for the post request is : " + getTimeTaken() + " Seconds ...", true);

		Reporter.log("\nRequest sent is \n" + requestInStringFormat, true);

		Reporter.log("\nResponse Obtained : " + response, true);
		return response;
	}
	
	public String postRequestNotification(String apiName, String requestInStringFormat) {

		String server = configProperties.get("serverHostName");
		String path = "";

		path = configProperties.get("serverPathNoti");

		String serverURL = server + path + apiName;
	
		System.out.println(serverURL);
		double startTime = System.currentTimeMillis();

		Response post = given().relaxedHTTPSValidation().body(requestInStringFormat).when()
				.post(serverURL);

		double stopTime = System.currentTimeMillis();

		String response = post.asString();
		Reporter.log("\nServer URL : " + serverURL, true);
		String timeTaken = String.valueOf((stopTime - startTime) / 1000);
		setTimeTaken(timeTaken);
		Reporter.log("\nTotal time taken for the post request is : " + getTimeTaken() + " Seconds ...", true);

		Reporter.log("\nRequest sent is \n" + requestInStringFormat, true);

		Reporter.log("\nResponse Obtained : " + response, true);
		return response;
	}
	
	public String postRequestForCashIn(String apiName, String requestInStringFormat, Map<String, String> headers) {

		String server = configProperties.get("serverHostName");
		String path = "";

		path = configProperties.get("serverPathCashIn");

		String serverURL = server + path + apiName;
	
		System.out.println(serverURL);
		double startTime = System.currentTimeMillis();

		Response post = given().relaxedHTTPSValidation().headers(headers).body(requestInStringFormat).when()
				.post(serverURL);

		double stopTime = System.currentTimeMillis();

		String response = post.asString();
		Reporter.log("\nServer URL : " + serverURL, true);
		String timeTaken = String.valueOf((stopTime - startTime) / 1000);
		setTimeTaken(timeTaken);
		Reporter.log("\nTotal time taken for the post request is : " + getTimeTaken() + " Seconds ...", true);

		Reporter.log("\nRequest sent is \n" + requestInStringFormat, true);

		Reporter.log("\nResponse Obtained : " + response, true);
		return response;
	}
	
	
	
	

	public void assertAPIResponseStatus(String api, HashMap<String, String> usrData, String response) {

		int flag = 0;

		String expectedStatus = usrData.get("status");
		String expecteddesc = usrData.get("desc");
		String expectedMessage = usrData.get("message");
		String expectedFormDocId = usrData.get("responseFormId");
		
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
	
	
	

	public String getPasswordWithOutSHA(String publickey, String password) {

		byte[] publicBytes = Base64.decodeBase64(publickey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
		KeyFactory keyFactory;
		try {
			keyFactory = KeyFactory.getInstance("RSA");

			PublicKey pubKey = keyFactory.generatePublic(keySpec);
			// byte[] byteArray = HashUtil.generateSha512Hash(password);
			String withDate = new Date().getTime() + "_" + password;
			// String withDate = new
			// Date().getTime()+"_"+CryptoUtil.convertByteToHex(byteArray);
			System.out.println("withDate " + withDate);
			byte[] encryptedData = CryptoUtil.encryptUsingPublicKey(pubKey, withDate.getBytes());

			System.out.println(
					"base64 encryptedData, use it as auth_value to login :" + HashUtil.toBase64(encryptedData));
			return HashUtil.toBase64(encryptedData);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getPasswordWithSHA(String publickey, String password) {

		byte[] publicBytes = Base64.decodeBase64(publickey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
		KeyFactory keyFactory;
		try {
			keyFactory = KeyFactory.getInstance("RSA");

			PublicKey pubKey = keyFactory.generatePublic(keySpec);
			byte[] byteArray = HashUtil.generateSha512Hash(password);
			String withDate = new Date().getTime() + "_" + CryptoUtil.convertByteToHex(byteArray);
			System.out.println("withDate " + withDate);
			byte[] encryptedData = CryptoUtil.encryptUsingPublicKey(pubKey, withDate.getBytes());

			System.out.println(
					"base64 encryptedData, use it as auth_value to login :" + HashUtil.toBase64(encryptedData));
			return HashUtil.toBase64(encryptedData);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//Bulk approval using same request with variable id. 
	/**
	 * 
	 * @return
	 */

	
	
	public static List<String> permissionlist(List<String> permissionlist,String response) {
		JsonPath jpath = new JsonPath(response);
		JSONObject jsonObject = new JSONObject(response);
		JSONArray jsonArray = jsonObject.getJSONArray("permission_list");
		System.out.println(jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj1 = jsonArray.getJSONObject(i);
			JSONArray JSONFeatureArray = obj1.getJSONArray("features");
			// System.out.println(JSONFeatureArray.length());

			for (int j = 0; j < JSONFeatureArray.length(); j++) {
				JSONObject obj2 = JSONFeatureArray.getJSONObject(j);
				JSONArray JSONUserStory = obj2.getJSONArray("user_stories");

				for (int k = 0; k < JSONUserStory.length(); k++) {
					JSONObject obj3 = JSONUserStory.getJSONObject(k);
					JSONArray JSONPermissionStory = obj3.getJSONArray("permissions");

					for (int l = 0; l < JSONPermissionStory.length(); l++) {
						JSONObject obj4 = JSONPermissionStory.getJSONObject(l);
						permissionlist.add(obj4.getString("value"));

					}

				}

			}

		}

		return permissionlist;
	}
	
	
	

	public HashMap<String, String> getRandomNameAndImages() {
		URL obj;
		try {
			obj = new URL("https://randomuser.me/api/?nat=us");

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			HashMap<String, String> nameImage = new HashMap<String, String>();
			JsonPath path = new JsonPath(response.toString());
			List<HashMap<String, HashMap<String, String>>> result = path.get("results");
			nameImage.put("NAME", result.get(0).get("name").get("title") + " " + result.get(0).get("name").get("first")
					+ " " + result.get(0).get("name").get("last"));
			nameImage.put("IMAGE", getImageInBase64Encoded(result.get(0).get("picture").get("medium")));
			nameImage.put("EMAIL", String.valueOf(result.get(0).get("email")));
			nameImage.put("USERNAME", result.get(0).get("login").get("username"));
			nameImage.put("SALUTATION", result.get(0).get("name").get("title"));
			nameImage.put("FIRSTNAME", result.get(0).get("name").get("first"));
			nameImage.put("LASTNAME", result.get(0).get("name").get("last"));
			return nameImage;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public long getDate(int count) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, count);
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
		long stamp = calendar.getTimeInMillis();
		return stamp;
	}
	public String formatVersion(String version){
	
		String formatVersion =String.format("%04d", Integer.parseInt(version));
		System.out.println(formatVersion);
		return formatVersion;
	}

	public String getImageInBase64Encoded(String url) throws IOException {
		URL obj;
		obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");

		// return
		// Base64.encodeBase64String((IOUtils.toByteArray(con.getInputStream())));
		byte[] buffer = new byte[con.getInputStream().available()];
		File outputImage = new File(configProperties.get("imageFolder") + "image.png");
		OutputStream outStream = new FileOutputStream(outputImage);
		outStream.write(buffer);
		outStream.close();
		return configProperties.get("imageFolder") + "image.png";
	}
	
	
	

}