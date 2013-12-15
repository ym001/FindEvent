package WsSem.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import Exception.WebServiceException;
import Tools.Tools;
import WsSem.factory.JsonResultFactory;
import WsSem.factory.QueryEndpointFactory;
import WsSem.model.JsonArtist;
import WsSem.model.JsonEvenement;


@Path("/EvenementService")
public class EventService {

	@Path("getArtistsByGenre")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getArtistsByGenre(@Context UriInfo uriInfo){

		String genre1 = uriInfo.getQueryParameters().getFirst("genre1");
		String genre2 = uriInfo.getQueryParameters().getFirst("genre2");
		String genre3 = uriInfo.getQueryParameters().getFirst("genre3");
		String genre4 = uriInfo.getQueryParameters().getFirst("genre4");
		List<JsonArtist> listeArtistes = new ArrayList<JsonArtist>();
		String stringResult = "";

		try{
			listeArtistes = QueryEndpointFactory.getArtistes(genre1, genre2, genre3, genre4);
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "success", listeArtistes);
		}catch(Exception e){
			e.printStackTrace();
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("500", "fail", null);
		}finally{
			//QueryEndpointFactory.closeQueryExe();
		}
		return stringResult;
	}



	@Path("/getAllEvenements")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllEvenements(@Context UriInfo uriInfo){
		List<JsonEvenement> listeEvenement = new ArrayList<JsonEvenement>();
		String stringResult = "";
		float latOrg = Float.valueOf(uriInfo.getQueryParameters().getFirst("lat"));
		float lgtOrg = Float.valueOf(uriInfo.getQueryParameters().getFirst("lgt"));
		float distance = Float.valueOf(uriInfo.getQueryParameters().getFirst("distance"));

		ArrayList<Float> geoFenetre = Tools.createFenetreGeo(latOrg, lgtOrg, distance);

		try{
			listeEvenement=QueryEndpointFactory.getAllEvenements(geoFenetre.get(0), geoFenetre.get(1), geoFenetre.get(2), geoFenetre.get(3));
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "success", listeEvenement);
		}catch (WebServiceException e){
			e.printStackTrace();
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("500", "fail", listeEvenement);
		}finally{
			//QueryEndpointFactory.closeQueryExe();
		}

		return stringResult;

	}

}
