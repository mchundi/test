package sample;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;
import org.json.JSONObject;

public class LaunchAPI {

	String getAPIResponse(String inStr){
		String testResponse = new String();

		try{
			URL testURL = new URL(inStr);
			HttpsURLConnection testConnection = (HttpsURLConnection)testURL.openConnection();
			testConnection.setRequestMethod("GET");
			testConnection.setRequestProperty("Accept", "application/json");
			testConnection.connect();
			int respCode = testConnection.getResponseCode();

			if(respCode != 200)
				throw new RuntimeException("HttpResponseCode:"+respCode);
			else{
				Scanner scan = new Scanner(testURL.openStream());
				while (scan.hasNext())
					testResponse += scan.nextLine();
				scan.close();
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return testResponse;
	}

	void parseAPIResponse(String resp){

		try{
			JSONObject jsonResp = new JSONObject(resp);
			Iterator<JSONObject> iterator = jsonResp.keys();
		    String countryStatus = null;
		    JSONObject nestedField = new JSONObject();
		    while (iterator.hasNext()) {
		    	nestedField = iterator.next();
		    	countryStatus = nestedField.getString("status");
		    	if(countryStatus.equalsIgnoreCase("y"))
		    		System.out.println(nestedField.get("name"));
		    } 
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String args[]){
		new LaunchAPI().getAPIResponse("https://istheapplestoredown.com/api/v1/status/worldwide");
	}
}
