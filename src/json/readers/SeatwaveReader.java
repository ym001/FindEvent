package json.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.client.ClientProtocolException;

import Tools.Tools;
import WsSem.model.JsonEvent;
import WsSem.model.JsonEventLocation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SeatwaveReader extends AbstractJsonReader {
	private static String sSeatwaveVenueQuery = "http://api-sandbox.seatwave.com/v2/discovery/venue/%d?apikey=20a22ad8eba6459896e64fe643399304";

	class SeatwaveVenueReader extends AbstractJsonReader {

		/*
		 * Récupération des infos sur le lieu de concert d'après son ID
		 * Utilisation de StringBuffer afin de renvoyer des valeurs modifiées de paramètres
		 */
		public String getVenueDataFromId(int venueId, StringBuffer lat, StringBuffer lng, StringBuffer adress) throws ClientProtocolException, IOException {
			String jFile = GetJsonFileFromUrl(String.format(sSeatwaveVenueQuery, venueId));
			//System.out.println(jFile);
			
			ObjectNode root = null;
			try {
				root = (ObjectNode) new ObjectMapper().readTree(jFile);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Latitude / longitude
			lat.append(root.get("Venue").get("Lat").toString());
			lng.append(root.get("Venue").get("Long").toString());
			// Adresse
			ObjectNode venueAddress = (ObjectNode) root.get("Venue").get("Address");
			adress.append(Tools.uppercaseWords(venueAddress.get("Line1").toString()) + ", " +
					 Tools.uppercaseWords(venueAddress.get("PostCode").toString().trim()) + " " +
					 Tools.uppercaseWords(venueAddress.get("Town").toString()));
			
			return root.get("Venue").get("Name").toString();
		}

		@Override
		public ArrayList<JsonEvent> ParseJsonFile(String jFile) {
			return null;
		}
	}
	
	@Override
	public ArrayList<JsonEvent> ParseJsonFile(String jFile) {
		ArrayList<JsonEvent> result = new ArrayList<JsonEvent>();
		
		ObjectNode root = null;
		try {
			root = (ObjectNode) new ObjectMapper().readTree(jFile);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayNode events = (ArrayNode) root.get("Events");
		// Navigation dans le tableau d'évènements
		for (Iterator<JsonNode> iEvents = events.iterator(); iEvents.hasNext();) {
			JsonEvent je = new JsonEvent();
			
			// Récup de l'élément Json
			ObjectNode event = (ObjectNode) iEvents.next();
			
			// Titre & date
			String perf = Tools.uppercaseWords(event.get("EventGroupName").toString()); 
			je.setName(perf);
			je.AddPerformer(perf);
			je.setDate(event.get("Date").toString());
						
			int venueId = event.get("VenueId").asInt();
			
			// Récup des infos du lieu (!! répété à chaque évènement !!)
			// --> comment éviter les répétitions ?
			StringBuffer lat = new StringBuffer("");
			StringBuffer lng = new StringBuffer("");
			StringBuffer address = new StringBuffer("");
			try {
				new SeatwaveVenueReader().getVenueDataFromId(venueId, lat, lng, address);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			je.setLocation(new JsonEventLocation(Tools.uppercaseWords(event.get("VenueName").toString()), 
					lat.toString(), 
					lng.toString(),
					address.toString()));
			
			result.add(je);
		}
		
		return result;
	}

}
