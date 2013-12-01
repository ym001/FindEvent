package WsSem.ws;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import WsSem.factory.JsonResultFactory;
import WsSem.model.Style;


@Path("/loginCheck")
public class LoginService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String checkUser(@Context UriInfo info) {

		String username=info.getQueryParameters().getFirst("username");
		String password = info.getQueryParameters().getFirst("password");


		EntityManager em = Persistence.createEntityManagerFactory("FindEvent").createEntityManager();
		Query q = em.createNativeQuery("SELECT * from User u WHERE u.username = ?1 AND u.password = ?2");
		q.setParameter(1, username);
		q.setParameter(2, password);

		if(q.getResultList().size()>0){
			return JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "success", null);	
		}else{
			return JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "fail", null);
		}
	}


}
