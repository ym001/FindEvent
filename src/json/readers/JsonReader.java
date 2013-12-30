package json.readers;

import java.util.ArrayList;

import WsSem.model.JsonEvent;

public class JsonReader {
	public static ArrayList<JsonEvent> read(AbstractJsonReader reader, String jFile) {
		return reader.JsonToEvent(jFile);
	}

}
