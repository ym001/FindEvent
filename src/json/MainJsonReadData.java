package json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import json.readers.BandsInTownReader;
import json.readers.CibulReader;
import json.readers.CustomReader;
import json.readers.JsonReader;
import json.readers.SeatgeekReader;
import json.readers.SeatwaveReader;

import org.apache.http.client.ClientProtocolException;

import WsSem.model.JsonEvent;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class MainJsonReadData {
	private Model m;
	private QueryParams qp = null;

	private static String sCibulQuery = "https://api.cibul.net/v1/events?what=concert&lat=%s&lng=%s&radius=%d&lang=fr&key=4477e6eb9372ce28ab421ecb41834bb7";
	private static String sBandsInTownQuery = "http://api.bandsintown.com/events/search?location=%s&radius=%d&format=json&app_id=MusicEvents";
	private static String sSeatgeekQuery = "http://api.seatgeek.com/2/events?venue.city=%s";
	// http://api-sandbox.seatwave.com/v2/discovery/events?apikey={apiKey}&siteId={siteid}&pgnumber={pageNumber}&pgsize={pageSize}&what={what}&where={where}&when_from={whenFrom}&when_to={whenTo}&max_price={maxPrice}&eventsWithoutTix={eventsWithoutTix}
	private static String sSeatwaveQuery = "http://api-sandbox.seatwave.com/v2/discovery/events?apikey=20a22ad8eba6459896e64fe643399304&where=%s&eventsWithoutTix=true";
	private static String sGigatoolsQuery = "http://api.gigatools.com/city.json?cities[]=%s&api_key=39c00a17acbc3be810";
	//private static String sCustomQuery = "D:/S�b/Dropbox/M2 AIGLE/Cours/Web s�mantique (A. Seilles) (aka Technos avanc�es du web) (GMIN309)/Projet/parsing json/json-events-custom.json";
	
	public MainJsonReadData(QueryParams qp) {
		super();
		this.qp = qp;
	}
	public MainJsonReadData() {
		this(new QueryParams());
	}
	
	public ArrayList<JsonEvent> run() {
		ArrayList<JsonEvent> result = null;
		
		result = JsonReader.read(new CibulReader(), String.format(sCibulQuery, qp.getLatitude(), qp.getLongitude(), 3000));
		result.addAll(JsonReader.read(new BandsInTownReader(), String.format(sBandsInTownQuery, qp.getCity(), 10)));
		result.addAll(JsonReader.read(new SeatgeekReader(), String.format(sSeatgeekQuery, qp.getCity())));
		result.addAll(JsonReader.read(new SeatwaveReader(), String.format(sSeatwaveQuery, qp.getCity())));
		//result.addAll(JsonReader.read(new CustomReader(), sCustomQuery));
		return result;
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		//JacksonDemo.run();
		ArrayList<JsonEvent> events = null;
		//String city = "Montpellier";
		
		MainJsonReadData reader = new MainJsonReadData(new QueryParams("48.857", "2.379", 10, "Montpellier", ""));
		events = reader.run();
		
		/*
		System.out.println("=== 1. Cibul ===");
		events = JsonReader.read(new CibulReader(), String.format(sCibulQuery, "48.857", "2.379", 3000));
		
		System.out.println("=== 2. BandsInTown ===");
		events.addAll(JsonReader.read(new BandsInTownReader(), String.format(sBandsInTownQuery, city, 10)));
		
		System.out.println("=== 3. Seatgeek ===");
		events.addAll(JsonReader.read(new SeatgeekReader(), String.format(sSeatgeekQuery, city)));
		
		System.out.println("=== 4. Seatwave ===");
		events.addAll(JsonReader.read(new SeatwaveReader(), String.format(sSeatwaveQuery, city)));
		
		/*
		System.out.println("=== 1. Cibul ===");
		String sQuery = String.format(sCibulQuery, "48.857", "2.379", 3000);
		AbstractJsonReader cibul = new CibulReader();
		events = cibul.JsonToEvent(sQuery);
		*/
		System.out.println("=== FINAL ===");
		for (Iterator<JsonEvent> i = events.iterator(); i.hasNext(); )
		  System.out.println(i.next().toString());
	}

}
