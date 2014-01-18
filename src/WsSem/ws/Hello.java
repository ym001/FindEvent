package WsSem.ws;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import WsSem.model.Style;

// Plain old Java Object it does not extend as class or implements 
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/hello")
public class Hello {


	// This method is called if HTML is request
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sayHtmlHello() {

		EntityManager em = Persistence.createEntityManagerFactory("FindEvent").createEntityManager();
		List<Style> listeStyles= new ArrayList<Style>(em.createQuery("select s from Style s", Style.class).getResultList());
		
		

		ObjectMapper mapper = new ObjectMapper();
		Writer strWriter = new StringWriter();

		Style xx = new Style();
		xx.setId(3);
		xx.setLabel("GOOD");
		
		
		Style xx2 = new Style();
		xx.setId(2);
		xx.setLabel("GOOD2");
		
		List<Style> cc = new ArrayList<Style>();
		cc.add(xx);
		cc.add(xx2);

		String jsonString = new String("xxx");


		try {
			mapper.writeValue(strWriter, listeStyles);
			jsonString= strWriter.toString();
			System.out.println(jsonString);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




		//return "LLPO";

		//return new ArrayList<Style>(listeStyles);
		return jsonString;

	}

} 