package WsSem.ws;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.GET;

import WsSem.factory.JsonResultFactory;
import WsSem.model.Style;
import WsSem.model.User;


@Path("/AccueilService")
public class AccueilService {

	@Path("/styles")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllStyles(){

		EntityManager em = Persistence.createEntityManagerFactory("FindEvent").createEntityManager();
		Query q = em.createNativeQuery("SELECT * FROM Style s WHERE 1=1");
		//Query q = em.createQuery("SELECT distinct s FROM Style s WHERE 1=1", Style.class); this is better
		List<Style> styles = q.getResultList();
		String stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "success", styles);
		return stringResult;				
	}


	@SuppressWarnings("finally")
	@Path("/stylesAvailableByUserName")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getStylesAvailableByUserName(@Context UriInfo uriInfo){

		String username=uriInfo.getQueryParameters().getFirst("username");
		String stringResult = "";
		EntityManager em = Persistence.createEntityManagerFactory("FindEvent").createEntityManager();

		String jpql = "SELECT distinct s FROM Style s "
				+ "WHERE s.id NOT IN "
				+ "("
				+ "SELECT s2.id FROM Style s2 JOIN s2.users as us2 WHERE us2.username=?1"
				+ ")";

		try{
			Query q = em.createQuery(jpql, Style.class);
			q.setParameter(1, username);
			List<Style> styles = q.getResultList();
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "success", styles);
		}catch(Exception e){
			e.printStackTrace();
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("500", "fail", null);
		}finally{
			em.close();
			return stringResult;
		}

	}

	@SuppressWarnings("finally")
	@Path("/stylesByUserName")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getStylesByUsername(@Context UriInfo uriInfo){

		String username=uriInfo.getQueryParameters().getFirst("username");
		EntityManager em = Persistence.createEntityManagerFactory("FindEvent").createEntityManager();
		String stringResult = "";
		
		try{
			String jpql = "SELECT distinct s FROM Style s join fetch s.users as us WHERE us.username=?1";
			Query q = em.createQuery(jpql, Style.class);
			q.setParameter(1, username);
			List<Style> styles = q.getResultList();
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "success", styles);
		}catch(Exception e){
			e.printStackTrace();
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("500", "fail", null);
		}finally{
			em.close();
			return stringResult;
		}	

	}


	@SuppressWarnings("finally")
	@Path("/userStyles/add")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String addStyleToUser(@Context UriInfo uriInfo){

		String username=uriInfo.getQueryParameters().getFirst("username");
		String style_id = uriInfo.getQueryParameters().getFirst("style_id");
		EntityManager em = Persistence.createEntityManagerFactory("FindEvent").createEntityManager();
		String stringResult = "";

		try{
			em.getTransaction().begin();
			Query q1 = em.createQuery("SELECT distinct u FROM User u WHERE u.username=?1", User.class);
			q1.setParameter(1, username);
			Query q2 = em.createQuery("SELECT distinct s FROM Style s WHERE s.id=?1", Style.class);
			q2.setParameter(1, Integer.parseInt(style_id));
			User user = (User) q1.getSingleResult();
			Style style = (Style)q2.getSingleResult();
			user.getStyles().add(style);
			em.getTransaction().commit();

			String jpql = "SELECT distinct s FROM Style s join fetch s.users as us WHERE us.username=?1";
			Query q = em.createQuery(jpql, Style.class);
			q.setParameter(1, username);
			List<Style> styles = q.getResultList();
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "success", styles);

		}catch(Exception e){
			e.printStackTrace();
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("500", "fail", null);
		}finally{
			em.close();
			return stringResult;
		}

	}

	@SuppressWarnings("finally")
	@Path("/userStyles/delete")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteStyleOfUser(@Context UriInfo uriInfo){
		
		String username=uriInfo.getQueryParameters().getFirst("username");
		String style_id = uriInfo.getQueryParameters().getFirst("style_id");
		EntityManager em = Persistence.createEntityManagerFactory("FindEvent").createEntityManager();
		String stringResult = "";

		try{
			em.getTransaction().begin();
			Query q1 = em.createQuery("SELECT distinct u FROM User u WHERE u.username=?1", User.class);
			q1.setParameter(1, username);
			Query q2 = em.createQuery("SELECT distinct s FROM Style s WHERE s.id=?1", Style.class);
			q2.setParameter(1, Integer.parseInt(style_id));
			User user = (User) q1.getSingleResult();
			Style style = (Style)q2.getSingleResult();
			user.getStyles().remove(style);
			em.getTransaction().commit();
			
			String jpql = "SELECT distinct s FROM Style s join fetch s.users as us WHERE us.username=?1";
			Query q = em.createQuery(jpql, Style.class);
			q.setParameter(1, username);
			List<Style> styles = q.getResultList();
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "success", styles);
		}catch(Exception e){
			e.printStackTrace();
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("500", "fail", null);
		}finally{
			em.close();
			return stringResult;
		}
		
	}
}
