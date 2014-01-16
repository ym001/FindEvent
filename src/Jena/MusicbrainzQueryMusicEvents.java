package Jena;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.vocabulary.RDF;


public class MusicbrainzQueryMusicEvents{

	public static final String CRLF = System.getProperty("line.separator") ;
	public static final String service = "http://dbtune.org/musicbrainz/sparql";
     
	public static void main(String[] args) {
		 		  		
	      String sQueries="" ;
	      String sSelect="";
	      String sWhere="";
	      
		  sQueries =sQueries + "PREFIX rdf: <" + RDF.getURI() + ">" + CRLF;
			  
		 // sQueries =sQueries + "PREFIX mo:<http://purl.org/ontology/mo/>" + CRLF;
		 // sQueries =sQueries + "PREFIX dce: <http://purl.org/dc/elements/1.1/>" + CRLF;
		 // sQueries =sQueries + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + CRLF;
		 // sQueries =sQueries + "PREFIX foaf:  <http://xmlns.com/foaf/0.1/>" + CRLF;
		 // sQueries =sQueries + "PREFIX owl: <http://www.w3.org/2002/07/owl#>" + CRLF;
		 // sQueries =sQueries + "PREFIX tags: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>" + CRLF;

		sQueries =sQueries + "PREFIX map: <file:/home/moustaki/work/motools/musicbrainz/d2r-server-0.4/mbz_mapping_raw.n3#>" + CRLF;
		sQueries =sQueries + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + CRLF;
		sQueries =sQueries + "PREFIX owl: <http://www.w3.org/2002/07/owl#>" + CRLF;
		sQueries =sQueries + "PREFIX event: <http://purl.org/NET/c4dm/event.owl#>" + CRLF;
		sQueries =sQueries + "PREFIX rel: <http://purl.org/vocab/relationship/>" + CRLF;
		sQueries =sQueries + "PREFIX lingvoj: <http://www.lingvoj.org/ontology#>" + CRLF;
		sQueries =sQueries + "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" + CRLF;
		sQueries =sQueries + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" + CRLF;
		sQueries =sQueries + "PREFIX tags: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>" + CRLF;
		sQueries =sQueries + "PREFIX db: <http://dbtune.org/musicbrainz/resource/>" + CRLF;
		sQueries =sQueries + "PREFIX geo: <http://www.geonames.org/ontology#>" + CRLF;
		sQueries =sQueries + "PREFIX dc: <http://purl.org/dc/elements/1.1/>" + CRLF;
		sQueries =sQueries + "PREFIX bio: <http://purl.org/vocab/bio/0.1/>" + CRLF;
		sQueries =sQueries + "PREFIX mo: <http://purl.org/ontology/mo/>" + CRLF;
		sQueries =sQueries + "PREFIX vocab: <http://dbtune.org/musicbrainz/resource/vocab/>" + CRLF;
		sQueries =sQueries + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + CRLF;
		sQueries =sQueries + "PREFIX mbz: <http://purl.org/ontology/mbz#>" + CRLF;
		  
		  /*requette artiste : trouver toutes les informations sur un artiste.*/
	      String artiste="";
		  artiste="Blasé DeBris";
		  
		  //artiste="Blasé DeBris";
			System.out.println();
			System.out.println("Requette artiste");
			System.out.println();
			
		  sSelect="*";
		  sQueries=sQueries+ "SELECT " + sSelect + CRLF;	  
		  
		  sWhere=sWhere + "?artist foaf:name \""+artiste+"\".";
		  //sWhere=sWhere + "?artist a mo:MusicArtist ;";
		  sWhere=sWhere + "OPTIONAL {?artist foaf:homepage ?homepage }";
		  //sWhere=sWhere + "OPTIONAL {?artist foaf:img ?img }";
		 // sWhere=sWhere + "OPTIONAL {?artist foaf:based_near ?based }";
		  //sWhere=sWhere + "OPTIONAL {?artist foaf:made ?album }";
		  
		  sWhere=sWhere + "OPTIONAL {?artist foaf:maker ?album }";
		  sWhere=sWhere + "OPTIONAL {?artist bio:event ?bio }";
		 
		  sQueries = sQueries+ "WHERE { "+sWhere+" } ";
	      QueryExecution qexec = QueryExecutionFactory.sparqlService(service, sQueries);
		  try {	             
				ResultSet rs = qexec.execSelect() ;
				
				while(rs.hasNext())
				{
					QuerySolution soln = rs.nextSolution();
					System.out.println("artiste : "+soln.get("?artist"));
					System.out.println("biographie event: "+soln.get("?bio"));
					System.out.println("homepage : "+soln.get("?homepage"));
					System.out.println("album  : "+soln.get("?album"));
					System.out.println();
				}
		 }
		 finally {
                qexec.close() ;
         }
		  
		
		  
		  /*requette album : trouver les enregistrements du type track d'un artistes à partir de son numero d'artiste*/
		  
			System.out.println();
			System.out.println("Requette album track");
			System.out.println();
		  
		  
			artiste="http://dbtune.org/musicbrainz/resource/artist/";
			artiste=artiste+"284cd70c-25d3-4fd5-b8c0-e1201de79537";
			
		  
			
			
			
			sQueries ="PREFIX map: <file:/home/moustaki/work/motools/musicbrainz/d2r-server-0.4/mbz_mapping_raw.n3#>" + CRLF;
			sQueries =sQueries + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + CRLF;
			sQueries =sQueries + "PREFIX owl: <http://www.w3.org/2002/07/owl#>" + CRLF;
			sQueries =sQueries + "PREFIX event: <http://purl.org/NET/c4dm/event.owl#>" + CRLF;
			sQueries =sQueries + "PREFIX rel: <http://purl.org/vocab/relationship/>" + CRLF;
			sQueries =sQueries + "PREFIX lingvoj: <http://www.lingvoj.org/ontology#>" + CRLF;
			sQueries =sQueries + "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" + CRLF;
			sQueries =sQueries + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" + CRLF;
			sQueries =sQueries + "PREFIX tags: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>" + CRLF;
			sQueries =sQueries + "PREFIX db: <http://dbtune.org/musicbrainz/resource/>" + CRLF;
			sQueries =sQueries + "PREFIX geo: <http://www.geonames.org/ontology#>" + CRLF;
			sQueries =sQueries + "PREFIX dc: <http://purl.org/dc/elements/1.1/>" + CRLF;
			sQueries =sQueries + "PREFIX bio: <http://purl.org/vocab/bio/0.1/>" + CRLF;
			sQueries =sQueries + "PREFIX mo: <http://purl.org/ontology/mo/>" + CRLF;
			sQueries =sQueries + "PREFIX vocab: <http://dbtune.org/musicbrainz/resource/vocab/>" + CRLF;
			sQueries =sQueries + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + CRLF;
			sQueries =sQueries + "PREFIX mbz: <http://purl.org/ontology/mbz#>" + CRLF;
			 
			
		  sSelect="*";
		  sQueries=sQueries+ "SELECT " + sSelect + CRLF;
		  
		  sWhere="?album foaf:maker <"+artiste+">.";
		  sWhere=sWhere + "OPTIONAL {?album rdfs:label ?title }";	
		  sWhere=sWhere + "OPTIONAL {?album mo:image ?img }";	
		  //sWhere=sWhere + "OPTIONAL {?album mo:amazon_asin ?amazon }";
		  sWhere=sWhere + "OPTIONAL {?album dc:date ?datedc }";
		  sWhere=sWhere + "OPTIONAL {?album mo:musicbrainz ?musicbrain }";
		  sWhere=sWhere + "OPTIONAL {?album mo:track ?track}";
		  sWhere=sWhere + "OPTIONAL {?album rdf:type \"http://purl.org/ontology/mo/Track \"}";
		  
		  sQueries = sQueries+ "WHERE { "+sWhere+" } ";
	      qexec = QueryExecutionFactory.sparqlService(service, sQueries);
		  try {	             
				ResultSet rs = qexec.execSelect() ;
				while(rs.hasNext())
				{
					QuerySolution soln = rs.nextSolution();
					System.out.println("titre : "+soln.get("?title"));
					System.out.println("image : "+soln.get("?img"));
					System.out.println("datedc : "+soln.get("?datedc"));
					System.out.println("lien musicbrain : "+soln.get("?musicbrain"));
					System.out.println("track : "+soln.get("?track"));
					//System.out.println("type : "+soln.get("?type"));
					System.out.println();
					
				}
		 }
		 finally {
              qexec.close() ;
       }
		  	  
		  
		  /*requette genre : trouver les artistes ayant fait des albums de rock et de punk*/
		      System.out.println();
			  System.out.println("Requette genre");
			  System.out.println();
			  
			  String tag1="punk";
			  String tag2="rock";
			  sQueries ="PREFIX rdf: <" + RDF.getURI() + ">" + CRLF;
			  
			  sQueries =sQueries + "PREFIX mo:<http://purl.org/ontology/mo/>" + CRLF;
			  sQueries =sQueries + "PREFIX dce: <http://purl.org/dc/elements/1.1/>" + CRLF;
			  sQueries =sQueries + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + CRLF;
			  sQueries =sQueries + "PREFIX foaf:  <http://xmlns.com/foaf/0.1/>" + CRLF;
			  sQueries =sQueries + "PREFIX owl: <http://www.w3.org/2002/07/owl#>" + CRLF;
			  sQueries =sQueries + "PREFIX tags: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>" + CRLF;
			  sQueries =sQueries + "PREFIX geo: <http://www.geonames.org/ontology#>" + CRLF;
			  sQueries =sQueries + "PREFIX wgs: <http://www.w3.org/2003/01/geo/wgs84_pos#>" + CRLF;
			  sQueries =sQueries + "" + CRLF;
				  
			  sSelect="*";
			  sQueries=sQueries + "SELECT " + sSelect + CRLF;
					 
			  sWhere="?artist a mo:MusicArtist ;";
			  sWhere=sWhere + " foaf:name ?name ;";
			  sWhere=sWhere + " foaf:made ?album .";
			  sWhere=sWhere + " ?album tags:taggedWithTag ?tag .";		
			  sWhere=sWhere + "FILTER(?tag=<http://dbtune.org/jamendo/tag/"+tag1+"> || ?tag=<http://dbtune.org/jamendo/tag/"+tag2+">)";		
			
			 
			  sQueries = sQueries+ "WHERE { "+sWhere+" } ";
				 			  
		      qexec = QueryExecutionFactory.sparqlService(service, sQueries);
			  try {	             
					ResultSet rs = qexec.execSelect() ;
					
					while(rs.hasNext())
					{
						QuerySolution soln = rs.nextSolution();
						System.out.println("artiste : "+soln.get("?artist"));
						System.out.println("nom : "+soln.get("?name"));
						System.out.println("tag : "+soln.get("?tag"));
						System.out.println();
					}
			 }
			 finally {
	                qexec.close() ;
	         }
			 
		  
	}

}

