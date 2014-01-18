/*package Jena;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONBandsInTownReader {
	
	public void RequestBandsInTown(String url) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		 Get the response
		BufferedReader rd = new BufferedReader
		  (new InputStreamReader(response.getEntity().getContent()));
		    
		String jsonFile = "";
		String sIter = "";
		while ((sIter = rd.readLine()) != null) {
			jsonFile += sIter;
		}
		System.out.println(jsonFile);
		
		ParseBandsInTown(jsonFile);
	}
	
	private static void ParseBandsInTown(String jFile) {
		JSONParser parser = new JSONParser();
		try {
	         Object obj = parser.parse(jFile);
	         JSONObject jo = (JSONObject) obj;
	         System.out.println("ticket_status: " + jo.get("ticket_status"));
	         System.out.println("id: " + jo.get("id"));
	         
	         /*System.out.println("Nb of results: " + jo.get("total"));
	         System.out.println();
	         
	          R�cup du tableau "data"
	         JSONArray data = (JSONArray) jo.get("data");
	         Iterator<String> iter = data.iterator();
	         while (iter.hasNext()) {         
	        	 JSONObject jso = (JSONObject)(Object) iter.next();
		         System.out.println("jso = " + jso);
		         
		          "title"
	             obj = parser.parse(jso.get("title").toString());
	             JSONObject jsoTitle = (JSONObject) obj;
		         System.out.println("================================");
	             System.out.println("===== " + jsoTitle.get("fr") + " =====");
	             
	              "freetext"
	             obj = parser.parse(jso.get("freeText").toString());
	             JSONObject jsoDesc = (JSONObject) obj;
	             System.out.println("description :\n" + jsoDesc.get("fr"));

		         System.out.println();
	             System.out.println("lien: " + jso.get("link"));
	             System.out.println("spacetimeinfo: " + jso.get("spacetimeinfo"));
	             System.out.println("image: " + jso.get("image"));

	             JSONObject cellData;
		         
		          Tableau "locations"
	             obj = parser.parse(jso.get("locations").toString());
	             JSONArray tabData = (JSONArray) obj;
	             for(int i = 0; i < tabData.size(); i++) {
		              cellData = (JSONObject)tabData.get(i);
		              System.out.println("lieu: " + cellData.get("placename"));
		              System.out.println("adresse: " + cellData.get("address"));
	             }

	             obj = parser.parse(uj.get("address").toString());
		         System.out.println(obj);
		         System.out.println();
		         
	        }
		}
		catch(ParseException pe) {
	         System.out.println("position: " + pe.getPosition());
	         System.out.println(pe);
	    }
	}

}
*/