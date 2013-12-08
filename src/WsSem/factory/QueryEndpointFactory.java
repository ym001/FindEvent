package WsSem.factory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;

import Exception.WebServiceException;
import WsSem.model.*
;
public class QueryEndpointFactory{

	private final static String CRLF = System.getProperty("line.separator") ;
	private final static String servicePath = "ressources/musiceventontology.rdf";
	private static String sPrefix;
	private static Model m;

	public static String createPrefix(){
		sPrefix ="PREFIX rdf: <" + RDF.getURI() + ">" + CRLF;
		sPrefix =sPrefix+ "PREFIX mo:<http://purl.org/ontology/mo/>" + CRLF;
		sPrefix =sPrefix+ "PREFIX dce: <http://purl.org/dc/elements/1.1/>" + CRLF;
		sPrefix =sPrefix+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + CRLF;
		sPrefix =sPrefix+ "PREFIX foaf:  <http://xmlns.com/foaf/0.1/>" + CRLF;
		sPrefix =sPrefix+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>" + CRLF;
		sPrefix =sPrefix+ "PREFIX tags: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>" + CRLF;
		sPrefix =sPrefix+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>" + CRLF;
		sPrefix =sPrefix+ "PREFIX meo: <http://www.musicevents.com/#>" + CRLF;
		sPrefix =sPrefix+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" + CRLF;
		sPrefix =sPrefix+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + CRLF;
		sPrefix =sPrefix+ "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>" + CRLF;
		sPrefix =sPrefix+ "PREFIX dcterms: <http://purl.org/dc/terms/>" + CRLF;
		sPrefix =sPrefix+ "PREFIX dc: <http://purl.org/dc/elements/1.1/>" + CRLF;
		//sQueries =sQueries + "PREFIX mo: <http://purl.org/ontology/mo/>" + CRLF;
		sPrefix =sPrefix+ "PREFIX igeo: <http://rdf.insee.fr/def/geo#>" + CRLF;
		sPrefix =sPrefix+ "PREFIX gps: <http://www.w3.org/2003/01/geo/wgs84_pos#>" + CRLF;
		sPrefix =sPrefix+ "PREFIX lode: <http://linkedevents.org/ontology/>" + CRLF;
		sPrefix =sPrefix+ "PREFIX schema: <http://schema.org/Event>" + CRLF;
		sPrefix =sPrefix+ "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#>" + CRLF;

		return sPrefix;
	}



	@SuppressWarnings("finally")
	public static List<JsonEvenement> getAllEvenements(float latMax, float latMin, float lgtMax, float lgtMin) throws WebServiceException{

		m = ModelFactory.createDefaultModel();
		List<JsonEvenement> listeEvenements = new ArrayList<JsonEvenement>();
		JsonEvenement item;

		InputStream in = FileManager.get().open(servicePath);
		if (in == null) {
			throw new IllegalArgumentException( "File: " + servicePath + " not found");
		}

		// read the RDF/XML file
		m.read(in, "");

		//RDF request
		String sPrefix = createPrefix();
		String sSelect="*";
		String sQueries=sPrefix+"SELECT " + sSelect + CRLF;
		
		String latitudeMax=String.valueOf(latMax);
		String latitudeMin=String.valueOf(latMin);
		String longitudeMax=String.valueOf(lgtMax);
		String longitudeMin=String.valueOf(lgtMin);
		
		
		//String genre="http://fr.wikipedia.org/wiki/Jazz";
		String genre1="Jazz";
		String genre2="Funk";
		String date="2012-07-26";
		String sWhere="";
		sWhere=sWhere + "OPTIONAL {?event meo:EvenementMusical ?name }"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?event meo:aPourParticipant ?participant}"+ CRLF;
		sWhere=sWhere + "?event dc:date ?date."+ CRLF;
		sWhere=sWhere + "?event gps:lat ?lat."+ CRLF;
		sWhere=sWhere + "?event gps:long ?long ."+ CRLF;	
		sWhere=sWhere + "?event mo:genre ?genre ."+ CRLF;
		//ne fonctionne pas	sWhere=sWhere + "?event meo:aPourGenre ?genre ."+ CRLF;
		String sFilter="FILTER ( xsd:double(?lat) > "+latitudeMin+" && xsd:double(?lat) < "+latitudeMax+" && xsd:double(?long) > "+longitudeMin+" && xsd:double(?long) < "+longitudeMax+" && (?genre=\""+genre1+"\"^^xsd:string || ?genre=\""+genre2+"\"^^xsd:string )&& xsd:date(?date) > \""+date+"\"^^xsd:date )"+ CRLF;
		sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+" } ORDER BY ?name ";

		//System.out.println(sQueries);	  	  

		QueryExecution qexec = QueryExecutionFactory.create(sQueries, m);

		ResultSet rs = qexec.execSelect() ;
		while(rs.hasNext())
		{
			item = new JsonEvenement();
			QuerySolution soln = rs.nextSolution();

			item.setNom(soln.get("?name").toString());
			item.setDate(soln.get("?date").toString());
			item.setGenre(soln.get("?genre").toString());
			item.setLatitude(Float.valueOf(soln.get("?lat").toString()));
			item.setLongitude(Float.valueOf(soln.get("?long").toString()));
			item.setParticipant(soln.get("?participant").toString());
			listeEvenements.add(item);
		}
		return listeEvenements;

	}




}