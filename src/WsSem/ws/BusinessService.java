package WsSem.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.GET;

import org.openjena.atlas.json.JsonObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import business.BusinessModelManip;
import WsSem.factory.JsonResultFactory;
import WsSem.factory.QueryEndpointFactory;
import WsSem.model.JsonBusinessObject;
import WsSem.model.Style;
import WsSem.model.User;


@Path("/BusinessService")
public class BusinessService {

	@SuppressWarnings("finally")
	@Path("/getSampleBusinessObjects")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getSampleBusinessObjects(){

		String stringResult = "";
		List<JsonBusinessObject> listeBusiness = new ArrayList<JsonBusinessObject>();
		try{
			listeBusiness = QueryEndpointFactory.getSampleBusinessEvent();
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "success", listeBusiness);
		}catch(Exception e){
			e.printStackTrace();
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("500", "fail", null);
		}finally{
			return stringResult;
		}
	}


	@Path("/addAnnotation")
	@POST
	@Consumes(value={MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public String addAnnotation(String jsonobj){

		String stringResult = "";
		ObjectMapper mapper = new ObjectMapper();
		JsonBusinessObject obj;

		try {
			obj = mapper.readValue(jsonobj, JsonBusinessObject.class);
			BusinessModelManip.addBusinessObject(obj);
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("200", "success", null);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("500", "fail", null);
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("500", "fail", null);
		} catch (IOException e1) {
			e1.printStackTrace();
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("500", "fail", null);
		}catch(Exception e){
			e.printStackTrace();
			stringResult = JsonResultFactory.getJsonResultFactory().createJsonResultString("500", "fail", null);
		}

		return stringResult;
	}

}
