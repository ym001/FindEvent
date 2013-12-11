//package queries;
package Jena;

import java.io.InputStream;

import org.openjena.atlas.io.IndentedWriter;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;

public class LocalQueryMusicEvents {
	public static final String CRLF = System.getProperty("line.separator") ;
	public static final String servicePath = "ressources/musiceventontology.rdf";

	public static void main(String[] args) {
		String sWhere="";
		String sQueries="";
		Model m = ModelFactory.createDefaultModel();
		
		/*
		// !!!!!!!!!!!! avec CreateOntologyModel --> query ne marche pas !!!!!!!!!!!
		m.read(service);*/
		
		
		InputStream in = FileManager.get().open(servicePath);
		if (in == null) {
			throw new IllegalArgumentException( "File: " + servicePath + " not found");
		}

		// read the RDF/XML file
		m.read(in, "");
		

		String sPrefix ="PREFIX rdf: <" + RDF.getURI() + ">" + CRLF;

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
		//sQueries =sQueries + "PREFIX tags: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>" + CRLF;
		
/* requette artiste : trouve un artiste à partir de son nom */
		System.out.println();
		System.out.println("Requette artiste");
		System.out.println();
		String artiste="Akhenaton";

		String sSelect="*";
		sQueries=sPrefix+ "SELECT " + sSelect + CRLF;
		  
		//sWhere="?artist a meo:Artist ?x;";
		//sWhere=sWhere + "?artiste foaf:name \""+artiste+"\" ."+ CRLF;
		//sWhere=sWhere + "?artiste foaf:name \""+artiste+"\" ."+ CRLF;
		sWhere=sWhere + "?artiste meo:Artiste \""+artiste+"\" ."+ CRLF;
		sWhere=sWhere + "OPTIONAL {?artiste foaf:name ?nick}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?artiste foaf:nick ?nick}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?artiste foaf:homepage ?homepage }"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?artiste meo:aPourGenre ?genre }"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?artiste rdfs:seeAlso ?seealso }"+ CRLF;
		
		//sWhere=sWhere + " foaf:based_near ?based ;"+ CRLF;
		  //sWhere=sWhere + " owl:sameAs ?sameas ;";
		  //sWhere=sWhere + " mo:biography ?bio ;";
		  
		//sWhere=sWhere + " foaf:img ?img ;"+ CRLF;
		//sWhere=sWhere + " foaf:made ?made ;"+ CRLF;
		  //sWhere=sWhere + " tags:taggedWithTag ?tag ;";		
		
		 
		sQueries = sQueries+ "WHERE { "+sWhere+" } ";
			 
		// System.out.println(sQueries);	  	  
		  
	    //QueryExecution qexec = QueryExecutionFactory.sparqlService(service, sQueries);
		QueryExecution qexec = QueryExecutionFactory.create(sQueries, m);

		try {	             
				ResultSet rs = qexec.execSelect() ;
				
				while(rs.hasNext())
				{
					QuerySolution soln = rs.nextSolution();
					System.out.println("artiste : "+artiste);
					System.out.println("nick : "+soln.get("?nick"));
					System.out.println("homepage : "+soln.get("?homepage"));
					System.out.println("genre : "+soln.get("?genre"));
					System.out.println("sealso : "+soln.get("?seealso"));
					System.out.println();

				}
		 }
		 finally {
                qexec.close() ;
         }
		
		System.out.println();
		System.out.println("fin requette artiste");
		System.out.println();

		
/*requette groupe : trouve un groupe à partir de son nom.*/
		System.out.println();
		System.out.println("Requette groupe");
		System.out.println();
		
		sSelect="*";
		sQueries=sPrefix+"SELECT " + sSelect + CRLF;
		  
		String groupe ="IAM";
		//String homepage ="http://www.iam.tm.fr/";
		sWhere="";
		sWhere=sWhere + "?groupe meo:GroupeMusique ?groupemusic."+ CRLF;
		//sWhere=sWhere + "?groupe foaf:name \""+groupe+"\" ."+ CRLF;
		//sWhere=sWhere + "?groupe foaf:name \""+groupe+"\" ."+ CRLF;
		sWhere=sWhere + "OPTIONAL {?groupe meo:aPourGenre ?genre }"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?groupe meo:aPourMembre ?membre}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?groupe foaf:homepage ?homepage}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?groupe foaf:img ?img }"+ CRLF;
				
		
		 
		sQueries = sQueries+ "WHERE { "+sWhere+" } ";
			 
		// System.out.println(sQueries);	  	  
		  
		qexec = QueryExecutionFactory.create(sQueries, m);

		try {	             
				ResultSet rs = qexec.execSelect() ;
				
				while(rs.hasNext())
				{
					QuerySolution soln = rs.nextSolution();
					//System.out.println("groupe : "+groupe);
					System.out.println("groupe : "+groupe);
					//System.out.println("groupemusic : "+soln.get("?groupemusic"));
					System.out.println("genre : "+soln.get("?genre"));
					System.out.println("membre : "+soln.get("?membre"));
					System.out.println("homepage : "+soln.get("?homepage"));
					System.out.println("image : "+soln.get("?img"));
					System.out.println();

					//System.out.println("sealso : "+soln.get("?seealso"));
					
				}
		 }
		 finally {
                qexec.close() ;
         }
		 
		System.out.println();
		System.out.println("fin requette groupe");
		System.out.println();
		
/*requette lieu : trouver un lieu à partir de son nom*/
		System.out.println();
		System.out.println("Requette lieu");
		System.out.println();
		
		sSelect="*";
		sQueries=sPrefix+"SELECT " + sSelect + CRLF;
		  
		String lieu ="rockstore";
		sWhere="";
		//sWhere=sWhere + "?groupe meo:GroupeMusique \""+groupe+"\" ."+ CRLF;
		//meo:LieuConcert rdf:about
		//sWhere=sWhere + "?groupe foaf:name \""+groupe+"\" ."+ CRLF;

		//sWhere=sWhere + "?lieu foaf:name \""+lieu+"\" ."+ CRLF;
		sWhere=sWhere + "?lieu meo:LieuConcert ?lieuconcert."+CRLF;
		sWhere=sWhere + "OPTIONAL {?lieu foaf:homepage ?homepage }"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?lieu igeo:Commune ?commune}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?lieu gps:lat ?lat}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?lieu gps:long ?long}"+ CRLF;
		//sWhere=sWhere + "?lieu place:address address:streetAddress ?streetaddress."+ CRLF;
		//sWhere=sWhere + "?lieu address:addressLocality ?addresslocality."+ CRLF;
		//Where=sWhere + "?lieu address:postalCode ?postalcode."+ CRLF;
		//sWhere=sWhere + "?lieu address:addressCountry ?addresscountry."+ CRLF;
		
		 
		sQueries = sQueries+ "WHERE { "+sWhere+" } ";
			 
		 //System.out.println(sQueries);	  	  
		  
		qexec = QueryExecutionFactory.create(sQueries, m);

		try {	             
				ResultSet rs = qexec.execSelect() ;
				
				while(rs.hasNext())
				{
					QuerySolution soln = rs.nextSolution();
					//System.out.println("groupe : "+groupe);
					System.out.println("lieu : "+soln.get("?lieuconcert"));
					System.out.println("homepage : "+soln.get("?homepage"));
					System.out.println("commune : "+soln.get("?commune"));
					System.out.println("latitude : "+soln.get("?lat"));
					System.out.println("longitude : "+soln.get("?long"));
					
					
				}
		 }
		 finally {
                qexec.close() ;
         }
		 
		System.out.println();
		System.out.println("fin requette lieu");
		System.out.println();
		
		
		
		
		
		
		
		
/*requette festival : trouver un festival à partir de son nom*/
		System.out.println();
		System.out.println("Requette festival");
		System.out.println();
		
		sSelect="*";
		sQueries=sPrefix+"SELECT " + sSelect + CRLF;
		  
		String festival="Garance Reggae Festival";
		sWhere="";
		//sWhere=sWhere + "?festival foaf:name \""+festival+"\" ;"+ CRLF;
		sWhere=sWhere + "?festival meo:EvenementMusical \""+festival+"\" ;"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?festival foaf:homepage ?homepage}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?festival schema:startDate ?startdate }"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?festival schema:endDate ?enddate}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?festival meo:seDerouleA ?sederoulea}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?festival meo:seComposeDe ?secomposede}"+ CRLF;
		
		//sWhere=sWhere + "?festival meo:aPourParticipant ?apourparticipant."+ CRLF;
		//sWhere=sWhere + "?festival dc:date ?date."+ CRLF;
				 
		sQueries = sQueries+ "WHERE { "+sWhere+" }ORDER BY ?name LIMIT 20 ";
			 
		// System.out.println(sQueries);	  	  
		  
		qexec = QueryExecutionFactory.create(sQueries, m);

		try {	             
				ResultSet rs = qexec.execSelect() ;
				
				while(rs.hasNext())
				{
					QuerySolution soln = rs.nextSolution();
					//System.out.println("groupe : "+groupe);
					System.out.println("festival : "+festival);
					System.out.println("début : "+soln.get("?startdate"));
					System.out.println("fin : "+soln.get("?enddate"));
					System.out.println("homepage : "+soln.get("?homepage"));
					System.out.println("se déroule à : "+soln.get("?sederoulea"));
					System.out.println("se compose de : "+soln.get("?secomposede"));					
					System.out.println("");					
					
				}
		 }
		 finally {
                qexec.close() ;
         }
		 
		System.out.println();
		System.out.println("fin requette festival");
		System.out.println();
		
		
		
/*requette latitude longitude : trouve un évènement en fonction d'une fenetre de latitude et de longitude*/
		System.out.println();
		System.out.println("Requette latitude");
		System.out.println();
		
		String latitudeMax="44";
		String latitudeMin="40";
		String longitudeMax="4";
		String longitudeMin="3";
		
		sSelect="*";
		sQueries=sPrefix+"SELECT " + sSelect + CRLF;
		
		sWhere="";
		sWhere=sWhere + "OPTIONAL {?lieu foaf:name ?name }"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?lieu foaf:homepage ?homepage }"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?lieu igeo:Commune ?commune}"+ CRLF;
		sWhere=sWhere + "?lieu gps:lat ?lat."+ CRLF;
		sWhere=sWhere + "?lieu gps:long ?long ."+ CRLF;	
		String sFilter="FILTER ( xsd:double(?lat) > "+latitudeMin+" && xsd:double(?lat) < "+latitudeMax+" && xsd:double(?long) > "+longitudeMin+" && xsd:double(?long) < "+longitudeMax+" )"+ CRLF;
		sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+" } ORDER BY ?name LIMIT 20 ";
			 
		//System.out.println(sQueries);	  	  
		  
		qexec = QueryExecutionFactory.create(sQueries, m);

		try {ResultSet rs = qexec.execSelect() ;
		
			while(rs.hasNext())
			{
			QuerySolution soln = rs.nextSolution();
			System.out.println("lieu : "+soln.get("?name"));
			System.out.println("homepage : "+soln.get("?homepage"));

			System.out.println("commune : "+soln.get("?commune"));
			System.out.println("latitude : "+soln.get("?lat"));
			System.out.println("longitude : "+soln.get("?long"));
				
			}
		 }
		 finally {
                qexec.close() ;
         }
		 
		System.out.println();
		System.out.println("fin requette latitude");
		System.out.println();
		
		
		
		
		
/*requette participant : retrouve les participants d'un évènement musical à partir de son nom.*/
		
		System.out.println();
		System.out.println("Requette participant à un évènement");
		System.out.println();
		//String event="toto";
		String event="Garance_Reggae_Festival_2013_j2";
		sSelect="*";
		sQueries=sPrefix+"SELECT " + sSelect + CRLF;
		
		sWhere="";
		sWhere=sWhere + "?event foaf:name \""+event+"\" ;"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?event dc:date ?date}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?event mo:genre ?genre}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?event meo:aPourParticipant ?participant}"+ CRLF;
		sQueries = sQueries+ "WHERE { "+sWhere+" } ORDER BY ?event LIMIT 20 ";
			 		  
		qexec = QueryExecutionFactory.create(sQueries, m);

		try {ResultSet rs = qexec.execSelect() ;
		
			while(rs.hasNext())
			{
			QuerySolution soln = rs.nextSolution();
			System.out.println("lieu : "+soln.get("?event"));
			System.out.println("date : "+soln.get("?date"));
			System.out.println("genre : "+soln.get("?genre"));
			System.out.println("a pour participant : "+soln.get("?participant"));				
			}
		 }
		 finally {
                qexec.close() ;
         }
		 
		System.out.println();
		System.out.println("fin requette participant");
		System.out.println();
		
		
		
		
/*requette latitude et genre : retrouve un évènement dans une fenetre de lat et long et d'un genre musical ou d'un autre à partir d'une date.*/
		
		System.out.println();
		System.out.println("Requette latitude, genre et date");
		System.out.println();

		sSelect="*";
		sQueries=sPrefix+"SELECT " + sSelect + CRLF;
		  
		latitudeMax="44";
		latitudeMin="40";
		longitudeMax="4";
		longitudeMin="3";
		//String genre="http://fr.wikipedia.org/wiki/Jazz";
		String genre1="Jazz";
		String genre2="Funk";
		String date="2012-07-26";
		sWhere="";
		sWhere=sWhere + "OPTIONAL {?event meo:EvenementMusical ?name }"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?event meo:aPourParticipant ?participant}"+ CRLF;
		sWhere=sWhere + "?event dc:date ?date."+ CRLF;
		sWhere=sWhere + "?event gps:lat ?lat."+ CRLF;
		sWhere=sWhere + "?event gps:long ?long ."+ CRLF;	
		sWhere=sWhere + "?event mo:genre ?genre ."+ CRLF;
	//ne fonctionne pas	sWhere=sWhere + "?event meo:aPourGenre ?genre ."+ CRLF;
		sFilter="FILTER ( xsd:double(?lat) > "+latitudeMin+" && xsd:double(?lat) < "+latitudeMax+" && xsd:double(?long) > "+longitudeMin+" && xsd:double(?long) < "+longitudeMax+" && (?genre=\""+genre1+"\"^^xsd:string || ?genre=\""+genre2+"\"^^xsd:string )&& xsd:date(?date) > \""+date+"\"^^xsd:date )"+ CRLF;
		sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+" } ORDER BY ?name LIMIT 20 ";
			 
		//System.out.println(sQueries);	  	  
		  
		qexec = QueryExecutionFactory.create(sQueries, m);

		try {ResultSet rs = qexec.execSelect() ;
		
			while(rs.hasNext())
			{
			QuerySolution soln = rs.nextSolution();
			System.out.println("nom : "+soln.get("?name"));
			System.out.println("genre : "+soln.get("?genre"));
			System.out.println("a pour participant : "+soln.get("?participant"));
			System.out.println("date : "+soln.get("?date"));
			System.out.println("latitude : "+soln.get("?lat"));
			System.out.println("longitude : "+soln.get("?long"));
				
			}
		 }
		 finally {
                qexec.close() ;
         }
		 
		System.out.println();
		System.out.println("fin requette latitude, genre et date");
		System.out.println();
		
	}

}


