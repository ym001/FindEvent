package json.readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import WsSem.model.JsonEvent;

/* 
 * Classe parente des différents readers, implémente IJsonReader.
 * Utilise le template method pattern.
 *  
 * @author Sébastien Paradis
 */
public abstract class AbstractJsonReader implements IJsonReader {

	@Override
	public ArrayList<JsonEvent> JsonToEvent(String url) {
		ArrayList<JsonEvent> events = null;
		try {
			String fileContent = GetJsonFileFromUrl(url);
			if (!(fileContent == null))
				events = ParseJsonFile(fileContent);
			else {
				fileContent = readFile(url);
				events = ParseJsonFile(fileContent);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return events;
	}
	
	/*
	 *  Chargement de l'URL indiquée
	 */
	protected String GetJsonFileFromUrl(String url) throws ClientProtocolException, IOException {
		URI link = null;
		try {
			link = new URI(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(link);
		HttpResponse response = client.execute(request);
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String jsonFile = "";
		try {		    
			String sIter = "";
			while ((sIter = rd.readLine()) != null) {
				jsonFile += sIter;
			}
		} finally {
			rd.close();
		}
		return jsonFile;
	}
	
	/*
	 * Lecture d'un fichier local (pour test only)
	 */
	private String readFile(String fileName) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            //sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}

	public abstract ArrayList<JsonEvent> ParseJsonFile(String jFile);

}
