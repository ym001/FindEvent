package WsSem.ws;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.GET;

import WsSem.factory.JsonResultFactory;
import WsSem.model.Style;


@Path("/AccueilService")
public class AccueilService {
	
	@Path("/styles")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllStyles(){
		
		EntityManager em = Persistence.createEntityManagerFactory("FindEvent").createEntityManager();
		Query q = em.createNativeQuery("SELECT * FROM Style s WHERE 1=1");
		List<Style> styles = q.getResultList();
		
		String stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "success", styles);
		return stringResult;				
	}
	
	
	@Path("/stylesByUsername")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getStylesByUsername(@Context UriInfo uriInfo){
		
		String username=uriInfo.getQueryParameters().getFirst("username");
		EntityManager em = Persistence.createEntityManagerFactory("WsSem").createEntityManager();
		
		String jpql = "SELECT distinct s FROM Style s join fetch s.users as us WHERE us.username=?1";
		Query q = em.createQuery(jpql, Style.class);
		q.setParameter(1, username);
		
		List<Style> styles = q.getResultList();
		String stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "success", styles);
		return stringResult;
		
	}
	
}
