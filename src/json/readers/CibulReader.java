package json.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import Tools.Tools;
import WsSem.model.JsonEvent;
import WsSem.model.JsonEventLocation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CibulReader extends AbstractJsonReader {
	
	// Description fichiers cibul : http://developers.cibul.net/publishEvents.php
	@Override
	public ArrayList<JsonEvent> ParseJsonFile(String jFile) {
		ArrayList<JsonEvent> result = new ArrayList<JsonEvent>();
		
		JsonNode root = null;
		try {
			root = new ObjectMapper().readTree(jFile);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// R�cup du tableau "data"
		ArrayNode data = (ArrayNode) root.get("data");
		for (Iterator<JsonNode> iData = data.iterator(); iData.hasNext();) {
			JsonEvent je = new JsonEvent();
			
			// R�cup de l'�l�ment Json
			JsonNode event = iData.next();

			// Description
			String description = event.get("description").get("fr").toString() + "\r\n" +
								 event.get("freeText").get("fr").toString();

			ArrayNode loc = (ArrayNode) event.get("locations");
			for (Iterator<JsonNode> iLoc = loc.iterator(); iLoc.hasNext();) {
				ObjectNode elem = (ObjectNode) iLoc.next();

				// Date
				je.setDate(Tools.uppercaseWords(elem.get("dates").get(0).get("date").toString()));
				// Lieu : name, coordonn�es, ...
				je.setLocation(new JsonEventLocation(Tools.uppercaseWords(elem.get("placename").toString()), 
						elem.get("latitude").toString(),
						elem.get("longitude").toString(),
						elem.get("address").toString()));
			}
			
			// Titre
			je.setName(Tools.uppercaseWords(event.get("title").get("fr").toString()));
			je.setDescription(description);
			//je.AddPerformer(p);
			
			result.add(je);

			/*
			System.out.println("title: " + je.getName());
			System.out.println("link: " + event.get("link"));
			System.out.println("date: " + je.getDate());
			System.out.println("desc: " + je.getDescription());
			System.out.println("location: " + je.getLocation().getLocName());
			System.out.println("lat: " + je.getLocation().getLatitude());
			System.out.println("long: " + je.getLocation().getLongitude());

			System.out.println("-----------------------------");
			*/
		}
		return result;
	}
}
