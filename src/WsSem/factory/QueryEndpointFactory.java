package WsSem.factory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import json.MainJsonReadData;
import json.QueryParams;
import business.BusinessModelCreation;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;

import exception.WebServiceException;
import Tools.Tools;
import WsSem.model.*
;
public class QueryEndpointFactory{

	private final static String CRLF = System.getProperty("line.separator") ;
	public static final String serviceJamendo = "http://dbtune.org/jamendo/sparql/";
	public static final String serviceMusicBrainz = "http://dbtune.org/musicbrainz/sparql/";
	public static final String serviceDBpedia = "http://www.dbpedia.org/sparql";
//	private final static String servicePath1 = "file:rdf_model/meo-data.rdf";
//	private final static String servicePath2 = "file:rdf_model/meo-data.rdf";
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
		//sPrefix =sPrefix + "PREFIX mo: <http://purl.org/ontology/mo/>" + CRLF;
		sPrefix =sPrefix+ "PREFIX igeo: <http://rdf.insee.fr/def/geo#>" + CRLF;
		sPrefix =sPrefix+ "PREFIX gps: <http://www.w3.org/2003/01/geo/wgs84_pos#>" + CRLF;
		sPrefix =sPrefix+ "PREFIX lode: <http://linkedevents.org/ontology/>" + CRLF;
		sPrefix =sPrefix+ "PREFIX schema: <http://schema.org/Event>" + CRLF;
		sPrefix =sPrefix+ "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#>" + CRLF;

		sPrefix =sPrefix + "PREFIX prop: <http://dbpedia.org/property/>" + CRLF;
		sPrefix =sPrefix + "PREFIX dbpedia-owl:<http://dbpedia.org/ontology/>" + CRLF;

		sPrefix =sPrefix + "PREFIX map: <file:/home/moustaki/work/motools/musicbrainz/d2r-server-0.4/mbz_mapping_raw.n3#>" + CRLF;
		sPrefix =sPrefix + "PREFIX event: <http://purl.org/NET/c4dm/event.owl#>" + CRLF;
		sPrefix =sPrefix + "PREFIX rel: <http://purl.org/vocab/relationship/>" + CRLF;
		sPrefix =sPrefix + "PREFIX lingvoj: <http://www.lingvoj.org/ontology#>" + CRLF;
		sPrefix =sPrefix + "PREFIX db: <http://dbtune.org/musicbrainz/resource/>" + CRLF;
		sPrefix =sPrefix + "PREFIX bio: <http://purl.org/vocab/bio/0.1/>" + CRLF;
		sPrefix =sPrefix + "PREFIX mbz: <http://purl.org/ontology/mbz#>" + CRLF;
		
		sPrefix =sPrefix + "PREFIX oa: <http://www.w3.org/ns/oa#>" + CRLF;


		return sPrefix;
	}


	public static Model createMyModel(){
		return BusinessModelCreation.getModel();
	}

	public static void closeQueryExe(){
		qexec.close();
	}
	
	
	
	
	
	//Requette Business trouve les évènements annotés.
	public static List<JsonBusinessObject> getSampleBusinessEvent(){
		
		String sPrefix = createPrefix();
		List<JsonBusinessObject> listeBusiness = new ArrayList<JsonBusinessObject>();
		Model m = createMyModel();
		String sSelect, sQueries, sFilter, sWhere ="";
		ResultSet rs;
		QueryExecution qexec;
		
		sSelect="*";
		sQueries=sPrefix+"SELECT DISTINCT " + sSelect + CRLF;
		
		sWhere="";
		sWhere=sWhere + "?databusiness meo:annotation ?annotation."+ CRLF;
		sWhere=sWhere + "OPTIONAL {?databusiness meo:numAnno ?numAnno}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?databusiness meo:tagg ?tagg}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?databusiness meo:EvenementMusical ?event}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?databusiness oa:annotatedAt: ?date}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?databusiness gps:lat ?lat}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?databusiness gps:lgt ?lgt}"+ CRLF;		
		sWhere=sWhere + "OPTIONAL {?databusiness foaf:mail ?mail}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?databusiness foaf:name ?nom}"+ CRLF;
		sQueries = sQueries+ "WHERE { "+sWhere+" } ";
			 
		//System.out.println(sQueries);	  	  
		qexec = QueryExecutionFactory.create(sQueries, m);
		try {	             
				rs = qexec.execSelect() ;
				while(rs.hasNext())
				{
					QuerySolution soln = rs.nextSolution();
					JsonBusinessObject item = new JsonBusinessObject();
					if(soln.get("?numAnno")!=null)item.setNumAnno(Integer.parseInt(soln.get("?numAnno").toString()));
					if(soln.get("?tagg")!=null)item.setTag(soln.get("?tagg").toString());
					if(soln.get("?annotation")!=null)item.setAnnotation(soln.get("?annotation").toString());
					if(soln.get("?event")!=null)item.setEvent(soln.get("?event").toString());
					if(soln.get("?date")!=null)item.setDate(soln.get("?date").toString());
					if(soln.get("?lat")!=null)item.setLat(soln.get("?lat").toString());
					if(soln.get("?lgt")!=null)item.setLgt(soln.get("?lgt").toString());
					if(soln.get("?nom")!=null)item.setNom(soln.get("?nom").toString());
					if(soln.get("?mail")!=null)item.setMail(soln.get("?mail").toString());
					listeBusiness.add(item);
				}
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 finally {
                qexec.close() ;
         }

		return listeBusiness;
		
	}
	
	
	

	//Requette artiste : trouve les artistes pour un genre ou un autre genre*/
	public static List<JsonArtist> getArtistesByGenres(String tag1, String tag2, String tag3, String tag4){

		String sPrefix = createPrefix();
		List<JsonArtist> listeArtistes = new ArrayList<JsonArtist>();
		String sSelect, sQueries, sFilter, sWhere ="";
		ResultSet rs;
		QueryExecution qexec;

		/**
		 * Jamendo SPARQL implementation
		 */
		sSelect="*";
		sQueries=sPrefix + "SELECT " + sSelect + CRLF;	
		sWhere="?artist a mo:MusicArtist ;";
		sWhere=sWhere + " foaf:name ?name ;";
		sWhere=sWhere + " foaf:made ?album .";
		sWhere=sWhere + "?album a mo:Record ;";
		sWhere=sWhere + "dce:title ?title .";
		sWhere=sWhere + " ?album tags:taggedWithTag ?tag .";
		sWhere=sWhere + "OPTIONAL {?artist foaf:homepage ?homepage }";
		sWhere=sWhere + "OPTIONAL {?artist foaf:img ?imgArtist }";
		sWhere=sWhere + "OPTIONAL {?artist foaf:based_near ?based }";
		sWhere=sWhere + "OPTIONAL {?artist mo:biography ?bio }";

		sFilter = "";
		if(tag1!=null||tag2!=null||tag3!=null||tag4!=null){
			sFilter+= "FILTER(";
			if(tag1!=null){
				sFilter+="?tag=<http://dbtune.org/jamendo/tag/"+tag1+">";
			}
			if(tag2!=null){
				sFilter+="||?tag=<http://dbtune.org/jamendo/tag/"+tag2+">";
			}if(tag3!=null){
				sFilter+="||?tag=<http://dbtune.org/jamendo/tag/"+tag3+">";
			}
			if(tag4!=null){
				sFilter+="||?tag=<http://dbtune.org/jamendo/tag/"+tag4+">";
			}
			sFilter+=")";
		}

		sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+" } ";
		qexec = QueryExecutionFactory.sparqlService(serviceJamendo, sQueries);
		
		try{//Here will already catch exception. Otherwhere will not catch this one
			rs = qexec.execSelect() ;
			while(rs.hasNext())
			{
				QuerySolution soln = rs.nextSolution();
				JsonArtist item = new JsonArtist();
				item.setIdJamendo( Tools.getLastItemInLink(soln.get("?artist").toString()) );
				item.setName( Tools.getFirstTitle(soln.get("?name").toString()) );
				if(soln.get("?tag")!=null)item.setTag(Tools.getLastItemInLink(soln.get("?tag").toString()));
				if(soln.get("?homepage")!=null)item.setHomepage(soln.get("?homepage").toString());
				if(soln.get("?bio")!=null)item.setBio(soln.get("?bio").toString());
				if(soln.get("?imgArtist")!=null)item.setImg(soln.get("?imgArtist").toString());
				listeArtistes.add(item);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			qexec.close();
		}


		/**
		 * Musicbrainz implementation
		 */

		sSelect="SELECT DISTINCT *";
		sQueries=sPrefix + sSelect + CRLF;	

		sWhere="?artist a mo:MusicArtist ;";
		sWhere=sWhere + " foaf:name ?name ;";
		sWhere=sWhere + " foaf:made ?album .";
		sWhere=sWhere + " ?album tags:taggedWithTag ?tag .";
		sWhere=sWhere + "OPTIONAL {?artist foaf:homepage ?homepage }";
		sWhere=sWhere + "OPTIONAL {?artist foaf:img ?img }";
		sWhere=sWhere + "OPTIONAL {?artist foaf:based_near ?based }";
		sWhere=sWhere + "OPTIONAL {?artist foaf:maker ?album }";
		sWhere=sWhere + "OPTIONAL {?artist bio:event ?bio }";

		sFilter = "";
		if(tag1!=null||tag2!=null||tag3!=null||tag4!=null){
			sFilter+= "FILTER(";
			if(tag1!=null){
				sFilter+="?tag=<http://dbtune.org/jamendo/tag/"+tag1+">";
			}
			if(tag2!=null){
				sFilter+="||?tag=<http://dbtune.org/jamendo/tag/"+tag2+">";
			}if(tag3!=null){
				sFilter+="||?tag=<http://dbtune.org/jamendo/tag/"+tag3+">";
			}
			if(tag4!=null){
				sFilter+="||?tag=<http://dbtune.org/jamendo/tag/"+tag4+">";
			}
			sFilter+=")";
		}

		sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+"} ";
		qexec = QueryExecutionFactory.sparqlService(serviceMusicBrainz, sQueries);
		try{//Here will already catch exception. Otherwhere will not catch this one
			rs = qexec.execSelect() ;
			while(rs.hasNext())
			{
				QuerySolution soln = rs.nextSolution();
				JsonArtist item = new JsonArtist();
				item.setIdJamendo( Tools.getLastItemInLink(soln.get("?artist").toString()) );
				item.setName( Tools.getFirstTitle(soln.get("?name").toString()) );
				if(soln.get("?tag")!=null)item.setTag(soln.get("?tag").toString());
				if(soln.get("?homepage")!=null)item.setHomepage(soln.get("?homepage").toString());
				if(soln.get("?bio")!=null)item.setBio(soln.get("?bio").toString());
				if(soln.get("?img")!=null)item.setImg(soln.get("?img").toString());
				listeArtistes.add(item);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			qexec.close();
		}
	

		/**
		 * DBpedia implementation
		 */

		sSelect="SELECT DISTINCT *";
		sQueries=sPrefix + sSelect + CRLF; 
		sWhere=sWhere + "OPTIONAL{?artiste foaf:name ?name.}";
		sWhere=sWhere + "OPTIONAL{?artiste dbpedia-owl:abstract  ?resume.}";
		//sWhere=sWhere + "OPTIONAL{?artiste foaf:depiction ?depiction.}";
		//sWhere=sWhere + "OPTIONAL{?artiste dbpedia-owl:birthDate ?anif.}";
		sWhere=sWhere + "OPTIONAL{?artiste dbpedia-owl:genre ?genre.}";
		//sWhere=sWhere + "OPTIONAL{?artiste dbpedia-owl:instrument ?instrument.}";
		//sWhere=sWhere + "OPTIONAL{?artiste dc:description ?description.}";
		//sWhere=sWhere + "OPTIONAL{?artiste rdfs:comment ?comment.}";

		sFilter = "";
		if(tag1!=null||tag2!=null||tag3!=null||tag4!=null){
			sFilter+= "FILTER(";
			if(tag1!=null){
				sFilter+="?genre="+Tools.myString(tag1);
			}
			if(tag2!=null){
				sFilter+="||?genre="+Tools.myString(tag2);
			}if(tag3!=null){
				sFilter+="||?genre="+Tools.myString(tag3);
			}
			if(tag4!=null){
				sFilter+="||?genre="+Tools.myString(tag4);
			}
			sFilter+=")";
		}


		sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+" } ";
		//System.out.println(sQueries);

		qexec = QueryExecutionFactory.sparqlService(serviceDBpedia, sQueries);   
		try{//Here will already catch exception. Otherwhere will not catch this one
			rs = qexec.execSelect() ;
			while(rs.hasNext())
			{
				QuerySolution soln = rs.nextSolution();
				JsonArtist item = new JsonArtist();
				item.setName( Tools.getFirstTitle(soln.get("?name").toString()) );
				if(soln.get("?genre")!=null)item.setTag(soln.get("?genre").toString());
				if(soln.get("?homepage")!=null)item.setHomepage(soln.get("?homepage").toString());
				if(soln.get("?resume")!=null)item.setResume(soln.get("?resume").toString());
				listeArtistes.add(item);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			qexec.close();
		}
		
		
		return listeArtistes;
	}


	//Requette albums: trouver les albums par selon un artiste
		public static List<JsonAlbum> getAlbumsByArtiste(String idJamendoArtiste, String nameOfArtist){

			String sPrefix = createPrefix();
            List<JsonAlbum> listeAlbum = new ArrayList<JsonAlbum>();
            JsonAlbum item;

            String sSelect="DISTINCT *";
            String sQueries=sPrefix + "SELECT " + sSelect + CRLF;        
            String sWhere="?artist a mo:MusicArtist ;";
            sWhere=sWhere + " foaf:name ?name ;";
            sWhere=sWhere + " foaf:made ?album .";
            sWhere=sWhere + "?album a mo:Record ;";
            sWhere=sWhere + "dce:title ?title ;";
            sWhere=sWhere + "dc:date ?datedc ;";
            sWhere=sWhere + "mo:image ?imgAlbum ;";
            sWhere=sWhere + "mo:available_as ?lien;";
            sWhere=sWhere + "tags:taggedWithTag ?tag .";


            String sFilter="FILTER(?artist=<http://dbtune.org/jamendo/artist/"+idJamendoArtiste+">)"; 

            sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+" } ";
            //System.out.println(sQueries);

            qexec = QueryExecutionFactory.sparqlService(serviceJamendo, sQueries);  
            try{
            	ResultSet rs = qexec.execSelect() ;
                while(rs.hasNext())
                {
                        QuerySolution soln = rs.nextSolution();
                        item = new JsonAlbum();
                        item.setDatedc(soln.get("?datedc").toString());
                        item.setIdJamendo(soln.get("?album").toString());
                        item.setImgAlbum(soln.get("?imgAlbum").toString());
                        item.setLienTelecharge(soln.get("?lien").toString());
                        item.setTag( Tools.getLastItemInLink(soln.get("?tag").toString() ));
                        item.setTitle(Tools.getFirstTitle(soln.get("?title").toString()));
                        listeAlbum.add(item);
                }
            }catch(Exception e){
            	e.printStackTrace();
            }finally{
            	qexec.close();
            }
			

			/**
			 * DBpedia Implementation
			 */
			/*
			String artiste=nameOfArtist;
			
			sSelect="SELECT DISTINCT *";
			sQueries=sQueries + sSelect + CRLF;
			sWhere= "?album prop:artist  <http://dbpedia.org/resource/"+artiste+"> .";
			sWhere=sWhere + "OPTIONAL{ ?album prop:name ?name . }";
			sWhere=sWhere + "OPTIONAL{ ?album rdf:type <http://dbpedia.org/ontology/Album> . }";
			sWhere=sWhere + "OPTIONAL{ ?album rdf:type <http://schema.org/MusicAlbum> .} ";
			sWhere=sWhere + "OPTIONAL{ ?album foaf:depiction ?depiction.}";
			sWhere=sWhere + "OPTIONAL{ ?album dbpedia-owl:abstract ?abstract.}";
			sWhere=sWhere + "OPTIONAL{ ?album rdfs:comment ?comment.}";
			sWhere=sWhere + "OPTIONAL{ ?album prop:cover ?cover.}";
			sWhere=sWhere + "OPTIONAL{ ?album prop:genre ?genre.}";
			sWhere=sWhere + "OPTIONAL{ ?album dbpedia-owl:producer ?producer.}";
			sWhere=sWhere + "OPTIONAL{ ?album <http://dbpedia.org/ontology/releaseDate> ?date .}"; 
			sWhere=sWhere + "OPTIONAL{ ?album dbpedia-owl:recordLabel ?label}";

			sQueries = sQueries+ "WHERE { "+sWhere+" } ";

			qexec = QueryExecutionFactory.sparqlService(serviceDBpedia, sQueries);
			try{
				rs = qexec.execSelect() ;
				while(rs.hasNext())
				{
					QuerySolution soln = rs.nextSolution();
					System.out.println("album : "+soln.get("?album"));
					System.out.println("nom : "+soln.get("?name"));
					System.out.println("img : "+soln.get("?depiction"));
					System.out.println("resumé : "+soln.get("?abstract"));
					System.out.println("commentaire : "+soln.get("?comment"));
					System.out.println("couverture : "+soln.get("?cover"));
					System.out.println("genre : "+soln.get("?genre"));
					System.out.println("producteur : "+soln.get("?producer"));
					System.out.println("label : "+soln.get("?label"));
					System.out.println("date : "+soln.get("?date"));
					System.out.println();
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				qexec.close();
			}
			*/

			return listeAlbum;
		}

	
	//Requette les evenements et les participants
	@SuppressWarnings("finally")
	public static List<JsonEvent> getAllEvents(String lat, String lgt, int radius, String city, String genre) throws WebServiceException{
		ArrayList<JsonEvent> events = null;
		MainJsonReadData reader = new MainJsonReadData(new QueryParams(lat, lgt, radius, city, genre));
		events = reader.run();
		return events;
	}


	/**
	 *  Old version
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

//		String sWhere="";
//		sWhere=sWhere + "OPTIONAL {?event meo:EvenementMusical ?name }"+ CRLF;
//		sWhere=sWhere + "OPTIONAL {?event meo:aPourParticipant ?participant}"+ CRLF;
//		sWhere=sWhere + "?event dc:date ?date."+ CRLF;
//		sWhere=sWhere + "?event gps:lat ?lat."+ CRLF;
//		sWhere=sWhere + "?event gps:long ?long ."+ CRLF;	
//		sWhere=sWhere + "?event mo:genre ?genre ."+ CRLF;

		String sWhere="";
		sWhere=sWhere + "OPTIONAL {?event meo:aPourParticipant ?participant}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?event rdfs:label ?label}"+ CRLF;
		sWhere=sWhere + "?event dc:date ?date."+ CRLF;
		sWhere=sWhere + "?event gps:lat ?lat."+ CRLF;
		sWhere=sWhere + "?event gps:long ?long ."+ CRLF;	

		//ne fonctionne pas	sWhere=sWhere + "?event meo:aPourGenre ?genre ."+ CRLF;
		//		
		//String sFilter="FILTER ( xsd:double(?lat) > "+latitudeMin+" && xsd:double(?lat) < "+latitudeMax+" && xsd:double(?long) > "+longitudeMin+" && xsd:double(?long) < "+longitudeMax+" && (?genre=\""+genre1+"\"^^xsd:string || ?genre=\""+genre2+"\"^^xsd:string )&& xsd:date(?date) > \""+date+"\"^^xsd:date )"+ CRLF;
		//sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+" } ORDER BY ?name ";
		//		

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

//			item.setNom(soln.get("?name").toString());
//			item.setDate(soln.get("?date").toString());
//			item.setGenre(soln.get("?genre").toString());
//			item.setLatitude(Float.valueOf(soln.get("?lat").toString()));
//			item.setLongitude(Float.valueOf(soln.get("?long").toString()));
//			item.setParticipant(Tools.getLastItemInLink(soln.get("?participant").toString()));
//			item.setWikilink(soln.get("?participant").toString());
//			

			item.setNom(soln.get("?label").toString());
			item.setParticipant(Tools.getLastItemInLink(soln.get("?participant").toString()));
			item.setDate(soln.get("?date").toString());
			item.setLatitude(Float.valueOf(soln.get("?lat").toString()));
			item.setLongitude(Float.valueOf(soln.get("?long").toString()));

			listeEvenements.add(item);
		}
		return listeEvenements;

	}
	 */


}