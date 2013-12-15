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
import Tools.Tools;
import WsSem.model.*
;
public class QueryEndpointFactory{

	private final static String CRLF = System.getProperty("line.separator") ;
	private final static String servicePath1 = "ressources/meo-model.rdf";
	private final static String servicePath2 = "ressources/meo-data.rdf";
	private static String sPrefix;
	private static QueryExecution qexec;
	//private static Model m1, m2, m;

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


	public static Model createMyModel(){

		InputStream in1 = FileManager.get().open(servicePath1);
		if (in1 == null) {
			throw new IllegalArgumentException( "File: " + servicePath1 + " not found");
		}

		InputStream in2 = FileManager.get().open(servicePath2);
		if (in2 == null) {
			throw new IllegalArgumentException( "File: " + servicePath2 + " not found");
		}

		Model m = ModelFactory.createDefaultModel();
		Model m2 = ModelFactory.createDefaultModel();
		m.read(in1, "");
		m2.read(in2, "");
		m.add(m2);

		return m;
	}

	public static void closeQueryExe(){
		qexec.close();
	}
	
	//Requette artiste : trouve les artistes pour un genre ou un autre genre*/
	public static List<JsonArtist> getArtistes(String genre1, String genre2, String genre3, String genre4){

		String sPrefix = createPrefix();
		Model m = createMyModel();
		List<JsonArtist> listeArtistes = new ArrayList<JsonArtist>();
		JsonArtist item;
		
		String sSelect="*";
		String sQueries=sPrefix+ "SELECT " + sSelect + CRLF;
		String sWhere="";
		sWhere=sWhere + "?artiste meo:aPourGenre ?genre "+ CRLF;
		sWhere=sWhere + "OPTIONAL {?artiste foaf:name ?name}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?artiste foaf:nick ?nick}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?artiste foaf:homepage ?homepage }"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?artiste rdfs:seeAlso ?seealso }"+ CRLF;
		
		String sFilter = "";
		if(genre1!=null||genre2!=null||genre3!=null||genre4!=null){
			sFilter+= "FILTER(";
				if(genre1!=null){
					sFilter+="?genre=\""+genre1+"\"^^xsd:string";
				}
				if(genre2!=null){
					sFilter+="||?genre=\""+genre2+"\"^^xsd:string";
				}if(genre3!=null){
					sFilter+="||?genre=\""+genre3+"\"^^xsd:string";
				}
				if(genre4!=null){
					sFilter+="||?genre=\""+genre4+"\"^^xsd:string";
				}
			sFilter+=")";
		}
		
		
		sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+" } ORDER BY ?name";
		//System.out.println(sQueries);	  	  

		qexec = QueryExecutionFactory.create(sQueries, m);	             
		ResultSet rs = qexec.execSelect() ;
		while(rs.hasNext())
		{
			QuerySolution soln = rs.nextSolution();
			item = new JsonArtist();
			item.setGenre(soln.get("?genre").toString());
			item.setHomepage(soln.get("?homepage").toString());
			item.setName(soln.get("?name").toString());
			item.setNick(soln.get("?nick").toString());
			item.setSeeAlso(soln.get("?seealso").toString());
			listeArtistes.add(item);
		}

		return listeArtistes;
	}


	//Requette les evenements et les participants
	@SuppressWarnings("finally")
	public static List<JsonEvenement> getAllEvenements(float latMax, float latMin, float lgtMax, float lgtMin) throws WebServiceException{

		Model m = createMyModel();
		List<JsonEvenement> listeEvenements = new ArrayList<JsonEvenement>();
		JsonEvenement item;

		//RDF request
		String sPrefix = createPrefix();
		String sSelect="*";
		String sQueries=sPrefix+"SELECT " + sSelect + CRLF;

		String latitudeMax=String.valueOf(latMax);
		String latitudeMin=String.valueOf(latMin);
		String longitudeMax=String.valueOf(lgtMax);
		String longitudeMin=String.valueOf(lgtMin);

		//		String latitudeMax="44";
		//		String latitudeMin="40";
		//		String longitudeMax="4";
		//		String longitudeMin="3";


		System.out.println("test latMax:"+latitudeMax);
		System.out.println("test latMin:"+latitudeMin);
		System.out.println("test lgtMax:"+longitudeMax);
		System.out.println("test lgtMax:"+longitudeMin);


		//String genre="http://fr.wikipedia.org/wiki/Jazz";
		String genre1="Jazz";
		String genre2="Funk";
		String date="2012-07-26";
		/*String sWhere="";
		sWhere=sWhere + "OPTIONAL {?event meo:EvenementMusical ?name }"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?event meo:aPourParticipant ?participant}"+ CRLF;
		sWhere=sWhere + "?event dc:date ?date."+ CRLF;
		sWhere=sWhere + "?event gps:lat ?lat."+ CRLF;
		sWhere=sWhere + "?event gps:long ?long ."+ CRLF;	
		sWhere=sWhere + "?event mo:genre ?genre ."+ CRLF;*/

		String sWhere="";
		sWhere=sWhere + "OPTIONAL {?event meo:aPourParticipant ?participant}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?event rdfs:label ?label}"+ CRLF;
		sWhere=sWhere + "?event dc:date ?date."+ CRLF;
		sWhere=sWhere + "?event gps:lat ?lat."+ CRLF;
		sWhere=sWhere + "?event gps:long ?long ."+ CRLF;	

		//ne fonctionne pas	sWhere=sWhere + "?event meo:aPourGenre ?genre ."+ CRLF;
		/*String sFilter="FILTER ( xsd:double(?lat) > "+latitudeMin+" && xsd:double(?lat) < "+latitudeMax+" && xsd:double(?long) > "+longitudeMin+" && xsd:double(?long) < "+longitudeMax+" && (?genre=\""+genre1+"\"^^xsd:string || ?genre=\""+genre2+"\"^^xsd:string )&& xsd:date(?date) > \""+date+"\"^^xsd:date )"+ CRLF;
		sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+" } ORDER BY ?name ";*/

		String sFilter="FILTER ( xsd:double(?lat) > "+latitudeMin+" && xsd:double(?lat) < "+latitudeMax+" && xsd:double(?long) > "+longitudeMin+" && xsd:double(?long) < "+longitudeMax+" && xsd:date(?date) > \""+date+"\"^^xsd:date )"+ CRLF;
		//&& (?genre=\""+genre1+"\"^^xsd:string || ?genre=\""+genre2+"\"^^xsd:string )
		sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+" } ORDER BY ?name";

		//System.out.println(sQueries);	  	  

		qexec = QueryExecutionFactory.create(sQueries, m);
		ResultSet rs = qexec.execSelect() ;
		while(rs.hasNext())
		{			
			item = new JsonEvenement();
			QuerySolution soln = rs.nextSolution();
			/*item.setNom(soln.get("?name").toString());
			item.setDate(soln.get("?date").toString());
			item.setGenre(soln.get("?genre").toString());
			item.setLatitude(Float.valueOf(soln.get("?lat").toString()));
			item.setLongitude(Float.valueOf(soln.get("?long").toString()));
			item.setParticipant(Tools.getLastItemInLink(soln.get("?participant").toString()));
			item.setWikilink(soln.get("?participant").toString());*/

			item.setNom(soln.get("?label").toString());
			item.setParticipant(Tools.getLastItemInLink(soln.get("?participant").toString()));
			item.setDate(soln.get("?date").toString());
			item.setLatitude(Float.valueOf(soln.get("?lat").toString()));
			item.setLongitude(Float.valueOf(soln.get("?long").toString()));

			listeEvenements.add(item);
		}
		return listeEvenements;

	}



}