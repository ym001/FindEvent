package json.readers;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import WsSem.model.JsonEvent;

public interface IJsonReader {
	public ArrayList<JsonEvent> JsonToEvent(String url) throws ClientProtocolException, IOException;
}
