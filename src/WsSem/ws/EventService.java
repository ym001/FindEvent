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
import WsSem.model.JsonAlbum;
import WsSem.model.JsonArtist;
import WsSem.model.JsonEvent;
//import WsSem.model.JsonEvenement;
import WsSem.model.JsonGroupe;


@Path("/EvenementService")
public class EventService {


	@Path("getAlbumsByArtiste")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAlbumsByArtiste(@Context UriInfo uriInfo){

		String idJamendo = uriInfo.getQueryParameters().getFirst("idJamendo");
		String artistName = uriInfo.getQueryParameters().getFirst("artistName");
		List<JsonAlbum> listeAlbum = new ArrayList<JsonAlbum>();
		String stringResult = "";
		try{//Normally Here can catch nothing, exceptions have been catched in getAlbumsByArtiste
			listeAlbum = QueryEndpointFactory.getAlbumsByArtiste(idJamendo, artistName);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(listeAlbum.size()==0){
				stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("500", "fail", null);
			}else{
				stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "success", listeAlbum);
			}
		}
		return stringResult;
	}



	/**
	@Path("getGroupesByGenre")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getGroupesByGenre(@Context UriInfo uriInfo){

		String genre1 = uriInfo.getQueryParameters().getFirst("genre1");
		String genre2 = uriInfo.getQueryParameters().getFirst("genre2");
		String genre3 = uriInfo.getQueryParameters().getFirst("genre3");
		String genre4 = uriInfo.getQueryParameters().getFirst("genre4");
		List<JsonGroupe> listeGroupes = new ArrayList<JsonGroupe>();
		String stringResult = "";
		try{
			listeGroupes = QueryEndpointFactory.getGroupesByGenres(genre1, genre2, genre3, genre4);
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "success", listeGroupes);
		}catch(Exception e){
			e.printStackTrace();
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("500", "fail", null);
		}finally{
			//QueryEndpointFactory.closeQueryExe();
		}
		return stringResult;
	}
	 */


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

		try{//Normally here will catch nothing since in QueryEndPoint, have already catched JenaQuery Exception in 3 cases
			listeArtistes = QueryEndpointFactory.getArtistesByGenres(genre1, genre2, genre3, genre4);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(listeArtistes.size()==0){
				stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("500", "fail", null);
			}else{
				stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "success", listeArtistes);
			}
		}
		return stringResult;
	}


	@SuppressWarnings("finally")
	@Path("/getAllEvenements")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllEvenements(@Context UriInfo uriInfo){

		List<JsonEvent> eventList = new ArrayList<JsonEvent>();
		String stringResult = "";
		String lat = uriInfo.getQueryParameters().getFirst("lat");//"48.857";
		String lgt = uriInfo.getQueryParameters().getFirst("lgt");//"2.379";
		int radius = Integer.parseInt(uriInfo.getQueryParameters().getFirst("radius"));//10;
		String city = uriInfo.getQueryParameters().getFirst("city");//"Montpellier";
		String genre = "";

		try{
			eventList=QueryEndpointFactory.getAllEvents(lat, lgt, radius, city, genre);
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "success", eventList);
		}catch (WebServiceException e){
			e.printStackTrace();
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("500", "fail", eventList);
		}finally{
			//QueryEndpointFactory.closeQueryExe();
		}

		return stringResult;

		/**
		 * Old Version
		 * List<JsonEvenement> listeEvenement = new ArrayList<JsonEvenement>();
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
		return stringResult;*/

	}

}
