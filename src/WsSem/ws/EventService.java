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
import WsSem.model.JsonEvenement;


@Path("/EvenementService")
public class EventService {

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
		}catch (WebServiceException e){
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("500", "fail", listeEvenement);
		}finally{
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "success", listeEvenement);
		}
		
		return stringResult;
		
	}
	
}
