<<<<<<< HEAD
<<<<<<< HEAD
package queries;

import org.openjena.atlas.io.IndentedWriter;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.vocabulary.RDF;

public class LocalQueryMusicEvents {
	public static final String NL = System.getProperty("line.separator");

	public static void main(String[] args) {
		Model m = ModelFactory.createDefaultModel();
		// !!!!!!!!!!!! avec CreateOntologyModel --> query ne marche pas !!!!!!!!!!!
		
		String s = "file:D:/S�b/Dropbox/M2 AIGLE/Cours/Web s�mantique (A. Seilles) (aka Technos avanc�es du web)/Projet/exemples/musiceventontology.rdf";
		m.read(s);
		
		String rdq = "PREFIX rdf: <" + RDF.getURI() + ">" + NL + 
			 //"PREFIX owl: <http://www.w3.org/2002/07/owl#>" + NL + 
			 "PREFIX meo: <http://www.musicevents.com/#>" + NL +
			 "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" + NL +
			 /*"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + NL +
			 "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>" + NL +
			 "PREFIX dcterms: <http://purl.org/dc/terms/>" + NL +
			 "PREFIX dc: <http://purl.org/dc/elements/1.1/>" + NL +
			 "PREFIX mo: <http://purl.org/ontology/mo/>" + NL +
			 "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" + NL +
			 "PREFIX igeo: <http://rdf.insee.fr/def/geo#>" + NL +
			 "PREFIX gps: <http://www.w3.org/2003/01/geo/wgs84_pos#>" + NL +	*/
			 //"PREFIX lode: <http://linkedevents.org/ontology/>" + NL +		
			 //"PREFIX schema: <http://schema.org/Event>" + NL +		
			 "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#>" + NL +	
			 "SELECT * WHERE {" + NL +
			 "  ?s ?p ?o }" ;
		  
		Query query = QueryFactory.create(rdq);
	  
		// afficher la requete
		//query.serialize(new IndentedWriter(System.out, true)) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, m);

		System.out.println() ;
		System.out.println("Les �l�ments du mod�le : ");
		try {	
			ResultSet rs = qexec.execSelect();
		    ResultSetFormatter.out(System.out, rs, query);	
		}
		finally {
			qexec.close() ;
		}	

	}

}
=======
/*//package queries;
=======
//package queries;
>>>>>>> 66410d5a163fff41fecc46739219774083d15dea
package Jena;

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
import com.hp.hpl.jena.vocabulary.RDF;

public class LocalQueryMusicEvents {
	public static final String CRLF = System.getProperty("line.separator") ;


	public static void main(String[] args) {
		String sWhere="";
		String sQueries="";
		String sFilter="";
		Model m = ModelFactory.createDefaultModel();
		Model m2 = ModelFactory.createDefaultModel();
		// !!!!!!!!!!!! avec CreateOntologyModel --> query ne marche pas !!!!!!!!!!!
		String file1 = "file:ressources/meo-model.rdf";
		String file2 = "file:ressources/meo-data.rdf";
		m.read(file1);
		m2.read(file2);
		m.add(m2);
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

		// requette artiste : trouve un artiste à partir de son nom
		System.out.println();
		System.out.println("Requette artiste");
		System.out.println();
		String artiste="Akhenaton";

		String sSelect="*";
		sQueries=sPrefix+ "SELECT " + sSelect + CRLF;

		//sWhere="?artist a meo:Artist ?x;";
		//sWhere=sWhere + "?artiste meo:Artiste \""+artiste+"\" ."+ CRLF;
		//sWhere=sWhere + "?artiste meo:Artiste $a."+ CRLF;
		sWhere=sWhere + "?artiste foaf:name ?name."+ CRLF;
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

		sFilter="FILTER (?name=\""+artiste+"\"^^xsd:string)"+ CRLF;
		sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+" } ORDER BY ?name LIMIT 20 ";


		//System.out.println(sQueries);	  	  

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


		//requette groupe : trouve un groupe à partir de son nom.
		System.out.println();
		System.out.println("Requette groupe");
		System.out.println();

		sSelect="*";
		sQueries=sPrefix+"SELECT " + sSelect + CRLF;

		String groupe ="IAM";
		//String homepage ="http://www.iam.tm.fr/";
		sWhere="";
		sWhere=sWhere + "?groupe rdfs:label ?label."+ CRLF;
		sWhere=sWhere + "OPTIONAL {?groupe meo:aPourGenre ?genre }"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?groupe meo:aPourMembre ?membre}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?groupe foaf:homepage ?homepage}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?groupe foaf:img ?img }"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?genre skos:notation ?n} "+ CRLF;


		sFilter="FILTER (?label=\""+groupe+"\"^^xsd:string)"+ CRLF;
		sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+" } ORDER BY ?name LIMIT 20 ";

		System.out.println(sQueries);	  	  

		qexec = QueryExecutionFactory.create(sQueries, m);

		try {	             
			ResultSet rs = qexec.execSelect() ;

			while(rs.hasNext())
			{
				QuerySolution soln = rs.nextSolution();
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

		//requette lieu : trouver un lieu à partir de son nom
		System.out.println();
		System.out.println("Requette lieu");
		System.out.println();

		sSelect="*";
		sQueries=sPrefix+"SELECT " + sSelect + CRLF;

		String lieu ="Rockstore";
		sWhere="";
		sWhere=sWhere + "?lieu rdfs:label ?label."+ CRLF;
		sWhere=sWhere + "OPTIONAL {?lieu foaf:homepage ?homepage }"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?lieu igeo:Commune ?commune}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?lieu gps:lat ?lat}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?lieu gps:long ?long}"+ CRLF;
		//je n'ai pas le prefix pour address!!!!
		//sWhere=sWhere + "?lieu address:streetAddress ?streetaddress."+ CRLF;
		//sWhere=sWhere + "?lieu address:addressLocality ?addresslocality."+ CRLF;
		//Where=sWhere + "?lieu address:postalCode ?postalcode."+ CRLF;
		//sWhere=sWhere + "?lieu address:addressCountry ?addresscountry."+ CRLF;


		sFilter="FILTER (?label=\""+lieu+"\"^^xsd:string)"+ CRLF;
		sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+" } ORDER BY ?name LIMIT 20 ";			 
		//System.out.println(sQueries);	  	  

		qexec = QueryExecutionFactory.create(sQueries, m);

		try {	             
			ResultSet rs = qexec.execSelect() ;

			while(rs.hasNext())
			{
				QuerySolution soln = rs.nextSolution();
				System.out.println("lieu : "+soln.get("?label"));
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








		//requette festival : trouver un festival à partir de son nom
		System.out.println();
		System.out.println("Requette festival");
		System.out.println();

		sSelect="*";
		sQueries=sPrefix+"SELECT " + sSelect + CRLF;

		String festival="Garance Reggae Festival";
		sWhere="";
		//sWhere=sWhere + "?festival foaf:name \""+festival+"\" ;"+ CRLF;
		sWhere=sWhere + "?festival rdfs:label ?label ."+ CRLF;
		sWhere=sWhere + "OPTIONAL {?festival foaf:homepage ?homepage}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?festival schema:startDate ?startdate }"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?festival schema:endDate ?enddate}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?festival meo:seDerouleA ?sederoulea}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?festival meo:seComposeDe ?secomposede}"+ CRLF;

		//sWhere=sWhere + "?festival meo:aPourParticipant ?apourparticipant."+ CRLF;
		//sWhere=sWhere + "?festival dc:date ?date."+ CRLF;

		sFilter="FILTER (?label=\""+festival+"\"^^xsd:string)"+ CRLF;
		sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+" } ORDER BY ?name LIMIT 20 ";				 
		// System.out.println(sQueries);	  	  

		qexec = QueryExecutionFactory.create(sQueries, m);

		try {	             
			ResultSet rs = qexec.execSelect() ;

			while(rs.hasNext())
			{
				QuerySolution soln = rs.nextSolution();
				//System.out.println("groupe : "+groupe);
				System.out.println("festival : "+soln.get("?label"));
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



		//requette latitude longitude : trouve un évènement en fonction d'une fenetre de latitude et de longitude
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
		sWhere=sWhere + "OPTIONAL {?lieu rdfs:label ?label }"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?lieu foaf:homepage ?homepage }"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?lieu igeo:Commune ?commune}"+ CRLF;
		sWhere=sWhere + "?lieu gps:lat ?lat."+ CRLF;
		sWhere=sWhere + "?lieu gps:long ?long ."+ CRLF;	
		sFilter="FILTER ( xsd:double(?lat) > "+latitudeMin+" && xsd:double(?lat) < "+latitudeMax+" && xsd:double(?long) > "+longitudeMin+" && xsd:double(?long) < "+longitudeMax+" )"+ CRLF;
		sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+" } ORDER BY ?name LIMIT 20 ";

		//System.out.println(sQueries);	  	  

		qexec = QueryExecutionFactory.create(sQueries, m);

		try {ResultSet rs = qexec.execSelect() ;

		while(rs.hasNext())
		{
			QuerySolution soln = rs.nextSolution();
			System.out.println("lieu : "+soln.get("?label"));
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







		//requette latitude et date : retrouve un évènement dans une fenetre de lat et long à partir d'une date.

		System.out.println();
		System.out.println("Requette latitude et date");
		System.out.println();

		sSelect="*";
		sQueries=sPrefix+"SELECT " + sSelect + CRLF;

		latitudeMax="44";
		latitudeMin="40";
		longitudeMax="4";
		longitudeMin="3";
		//String genre="http://fr.wikipedia.org/wiki/Jazz";

		String date="2012-07-26";
		sWhere="";
		sWhere=sWhere + "OPTIONAL {?event meo:aPourParticipant ?participant}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?event rdfs:label ?label}"+ CRLF;
		sWhere=sWhere + "?event dc:date ?date."+ CRLF;
		sWhere=sWhere + "?event gps:lat ?lat."+ CRLF;
		sWhere=sWhere + "?event gps:long ?long ."+ CRLF;	
		//sWhere=sWhere + "?event mo:genre ?genre ."+ CRLF;
		//ne fonctionne pas	sWhere=sWhere + "?event meo:aPourGenre ?genre ."+ CRLF;
		sFilter="FILTER ( xsd:double(?lat) > "+latitudeMin+" && xsd:double(?lat) < "+latitudeMax+" && xsd:double(?long) > "+longitudeMin+" && xsd:double(?long) < "+longitudeMax+" && xsd:date(?date) > \""+date+"\"^^xsd:date )"+ CRLF;
		//&& (?genre=\""+genre1+"\"^^xsd:string || ?genre=\""+genre2+"\"^^xsd:string )
		sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+" } ORDER BY ?name LIMIT 20 ";

		//		System.out.println(sQueries);	  	  

		qexec = QueryExecutionFactory.create(sQueries, m);

		try {ResultSet rs = qexec.execSelect() ;

		while(rs.hasNext())
		{
			QuerySolution soln = rs.nextSolution();
			System.out.println("nom : "+soln.get("?label"));
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
		System.out.println("fin requette latitude et date");
		System.out.println();

		
		
		
		
		
		//requette participant : retrouve les participants d'un évènement musical à partir de son nom.

		System.out.println();
		System.out.println("Requette participant à un évènement");
		System.out.println();
		String event="Garance Reggae Festival 2013 j2";
		sSelect="*";
		sQueries=sPrefix+"SELECT " + sSelect + CRLF;

		sWhere="";
		sWhere=sWhere + "?event rdfs:label ?label ."+ CRLF;
		sWhere=sWhere + "OPTIONAL {?event dc:date ?date}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?event mo:genre ?genre}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?event meo:aPourParticipant ?participant}"+ CRLF;
		sFilter="FILTER (?label=\""+event+"\"^^xsd:string)"+ CRLF;
		sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+" } ORDER BY ?name LIMIT 20 ";				 		  


		qexec = QueryExecutionFactory.create(sQueries, m);
		//System.out.println(sQueries);	  	  

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
		
		
		
		
		

		//requette artiste : trouve les artistes pour un genre ou un autre genre
		System.out.println();
		System.out.println("Requette trouve les artistes pour un genre ou un autre genre");
		System.out.println();

		String genre1="Jazz";
		String genre2="Funk";

		sSelect="*";
		sQueries=sPrefix+ "SELECT " + sSelect + CRLF;
		sWhere="";
		sWhere=sWhere + "?artiste meo:aPourGenre ?genre "+ CRLF;
		sWhere=sWhere + "OPTIONAL {?artiste foaf:name ?name}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?artiste foaf:nick ?nick}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?artiste foaf:homepage ?homepage }"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?artiste rdfs:seeAlso ?seealso }"+ CRLF;


		sFilter="FILTER (?genre=\""+genre1+"\"^^xsd:string || ?genre=\""+genre2+"\"^^xsd:string)"+ CRLF;
		sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+" } ORDER BY ?name LIMIT 20 ";
		//System.out.println(sQueries);	  	  

		qexec = QueryExecutionFactory.create(sQueries, m);
		try {	             
			ResultSet rs = qexec.execSelect() ;
			while(rs.hasNext())
			{
				QuerySolution soln = rs.nextSolution();
				System.out.println("artiste : "+soln.get("?name"));
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
		System.out.println("fin requette trouve les artistes pour un genre ou un autre genre");
		System.out.println();
	}

}
<<<<<<< HEAD


*/
>>>>>>> ea63653450464348f58bad9de982b17a8bda6e71
=======
>>>>>>> 66410d5a163fff41fecc46739219774083d15dea
