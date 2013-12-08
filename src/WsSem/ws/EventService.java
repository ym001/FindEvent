package WsSem.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;


@Path("/EventService")
public class EventService {

	@Path("/getAlblumByArtist")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAlblumByArtist(@Context UriInfo uriInfo){
		
		
		
		return "";
		
	}
	
}
