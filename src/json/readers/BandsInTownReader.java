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

public class BandsInTownReader extends AbstractJsonReader {
	
	@Override
	public ArrayList<JsonEvent> ParseJsonFile(String jFile) {
		ArrayList<JsonEvent> result = new ArrayList<JsonEvent>();
		
		ArrayNode root = null;
		try {
			root = (ArrayNode) new ObjectMapper().readTree(jFile);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Navigation dans le tableau principal
		for (Iterator<JsonNode> iRoot = root.iterator(); iRoot.hasNext();) {
			JsonEvent je = new JsonEvent();
			
			// Récup de l'élément Json
			ObjectNode event = (ObjectNode) iRoot.next();

			// Tableau d'artiste(s)
			String title = "";
			ArrayNode artists = (ArrayNode) event.get("artists");
			for (Iterator<JsonNode> iArt = artists.iterator(); iArt.hasNext();) {
				ObjectNode elem = (ObjectNode) iArt.next();
				
				String perf = Tools.uppercaseWords(elem.get("name").toString()); 
				je.AddPerformer(perf);
				title += perf + ", ";
			}
			
			// Pas de titre --> aggrégation des artistes
			je.setName(title.substring(0, title.lastIndexOf(",")));
			
			// Date (formatage : de 2014-01-25T19:00:00 à 2014-01-25)
			String date = event.get("datetime").toString();
			je.setDate(date.substring(0, date.indexOf("T")));
			// Nom du lieu, coordonnées, ...
			ObjectNode loc = (ObjectNode) event.get("venue");
			// Avoir la 1ère lettre en MAJ ?
			je.setLocation(new JsonEventLocation(Tools.uppercaseWords(loc.get("name").toString()), 
					loc.get("latitude").toString(),
					loc.get("longitude").toString(),
					Tools.uppercaseWords(loc.get("city").toString())));
			
			
			result.add(je);
		}
		return result;
	}

}
