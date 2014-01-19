package WsSem.ws;

import java.util.ArrayList;
import java.util.List;

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
import WsSem.model.User;


@Path("/LoginService")
public class LoginService {

	@Path("/loginCheck")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String checkUser(@Context UriInfo info) {

		String username=info.getQueryParameters().getFirst("username");
		String password = info.getQueryParameters().getFirst("password");


		EntityManager em = Persistence.createEntityManagerFactory("FindEvent").createEntityManager();
		Query q = em.createQuery("SELECT distinct u from User u WHERE u.username = ?1 AND u.password = ?2", User.class);
		q.setParameter(1, username);
		q.setParameter(2, password);

		List<User> users = q.getResultList();
		if(q.getResultList().size()>0){
			return JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "success", users);	
		}else{
			return JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "fail", null);
		}
	}


}
