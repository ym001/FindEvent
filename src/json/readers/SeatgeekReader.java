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

public class SeatgeekReader extends AbstractJsonReader {

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

		ArrayNode events = (ArrayNode) root.get("events");
		// Navigation dans le tableau d'évènements
		for (Iterator<JsonNode> iEvents = events.iterator(); iEvents.hasNext();) {
			JsonEvent je = new JsonEvent();
			
			// Récup de l'élément Json
			ObjectNode event = (ObjectNode) iEvents.next();
			
			// Titre & date
			je.setName(Tools.uppercaseWords(event.get("title").toString()));
			String date = event.get("datetime_local").toString();
			je.setDate(date.substring(0, date.indexOf("T")));
			
			// Artistes
			ArrayNode performers = (ArrayNode) event.get("performers");
			for (Iterator<JsonNode> iPerfs = performers.iterator(); iPerfs.hasNext();) {
				ObjectNode perf = (ObjectNode) iPerfs.next();
				//je.AddPerformer(Tools.uppercaseWords(perf.get("name").toString()));
				je.AddPerformer(perf.get("name").toString());
			}

			ObjectNode loc = (ObjectNode) event.get("venue");
			String address = Tools.uppercaseWords(loc.get("address").toString()) + ", " +
					Tools.uppercaseWords(loc.get("postal_code").toString()) + " " +
					Tools.uppercaseWords(loc.get("display_location").toString());
			je.setLocation(new JsonEventLocation(Tools.uppercaseWords(loc.get("name").toString()), 
					loc.get("location").get("lat").toString(),
					loc.get("location").get("lon").toString(),
					address));
			
			
			result.add(je);
			/*
			System.out.println("title: " + je.getName() + " (type = " + event.get("type").toString() + ")");
			System.out.println("date: " + je.getDate());
			System.out.println("-----------------------------");
			*/
		}
		
		return result;
	}

}
