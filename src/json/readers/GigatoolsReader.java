package json.readers;

import java.util.ArrayList;

import WsSem.model.JsonEvent;

public class GigatoolsReader extends AbstractJsonReader {

	@Override
	public ArrayList<JsonEvent> ParseJsonFile(String jFile) {
		System.out.println(jFile);
		return null;
	}

}
