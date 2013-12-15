/*package Jena;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public class ReadJSONData {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		String sCibul ="https://api.cibul.net/v1/events?what=concert&lat=48.857&lng=2.379&radius=2000&lang=fr&key=4477e6eb9372ce28ab421ecb41834bb7";
		String sBandsInTown = "http://api.bandsintown.com/events/search?location=Montpellier&radius=10&format=json&app_id=MusicEvents";
		String sSeatgeek = "http://api.seatgeek.com/2/events?venue.city=montpellier";

		System.out.println("=== 1. CIBUL ===");
		JSONCibulReader cibul = new JSONCibulReader();
		cibul.RequestCibul(sCibul);

		System.out.println("=== 2. BandsInTown ===");
		JSONBandsInTownReader bit = new JSONBandsInTownReader();
		bit.RequestBandsInTown(sBandsInTown);
	}

}
*/