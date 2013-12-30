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

/*
 * Fichier bidon créé en local, pour démo
 * 
 * @author Sébastien Paradis
 */
public class CustomReader extends AbstractJsonReader {

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

		ArrayNode events = (ArrayNode) root.get("Events");
		// Navigation dans le tableau d'évènements
		for (Iterator<JsonNode> iEvents = events.iterator(); iEvents.hasNext();) {
			JsonEvent je = new JsonEvent();
			
			// Récup de l'élément Json
			ObjectNode event = (ObjectNode) iEvents.next();
			
			// Titre & date
			je.setName(Tools.uppercaseWords(event.get("EventGroupName").toString()));
			je.setDate(Tools.uppercaseWords(event.get("Date").toString()));
			
			// Artistes
			ArrayNode performers = (ArrayNode) event.get("artists");
			for (Iterator<JsonNode> iPerfs = performers.iterator(); iPerfs.hasNext();) {
				ObjectNode perf = (ObjectNode) iPerfs.next();

				je.AddPerformer(Tools.uppercaseWords(perf.get("name").toString()));
			}
			
			// Lieu
			ObjectNode loc = (ObjectNode) event.get("venue");
			String address = Tools.uppercaseWords(loc.get("Address").get("Line1").toString()) + ", " +
					Tools.uppercaseWords(loc.get("Address").get("PostCode").toString()) + " " +
					Tools.uppercaseWords(loc.get("Address").get("Town").toString());
			je.setLocation(new JsonEventLocation(Tools.uppercaseWords(loc.get("name").toString()), 
					loc.get("latitude").toString(),
					loc.get("longitude").toString(),
					address));
			
			result.add(je);
		}
		
		return result;
	}

}
