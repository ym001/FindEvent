<<<<<<< HEAD
package Jena;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.vocabulary.RDF;


public class JamendoQueryMusicEvents{

	public static final String CRLF = System.getProperty("line.separator") ;
	public static final String service = "http://dbtune.org/jamendo/sparql/";
     
	public static void main(String[] args) {
		 		  		
	      String sQueries="" ;
	      String sSelect="";
	      String sWhere="";
	      
		  sQueries =sQueries + "PREFIX rdf: <" + RDF.getURI() + ">" + CRLF;
			  
		  sQueries =sQueries + "PREFIX mo:<http://purl.org/ontology/mo/>" + CRLF;
		  sQueries =sQueries + "PREFIX dce: <http://purl.org/dc/elements/1.1/>" + CRLF;
		  sQueries =sQueries + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + CRLF;
		  sQueries =sQueries + "PREFIX foaf:  <http://xmlns.com/foaf/0.1/>" + CRLF;
		  sQueries =sQueries + "PREFIX owl: <http://www.w3.org/2002/07/owl#>" + CRLF;
		  sQueries =sQueries + "PREFIX tags: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>" + CRLF;

		  
		  /*requette artiste : trouver un artiste à partir de son nom.*/
	      String artiste="";
		  artiste="vincent j";
		  
		  //artiste="vavrek";
			System.out.println();
			System.out.println("Requette artiste");
			System.out.println();
			
		  sSelect="*";
		  sQueries=sQueries+ "SELECT " + sSelect + CRLF;	  
		  
		  sWhere=sWhere + "?artist foaf:name \""+artiste+"\" .";
		  sWhere=sWhere + "OPTIONAL {?artist foaf:homepage ?homepage }";
		  sWhere=sWhere + "OPTIONAL {?artist foaf:img ?img }";
		  sWhere=sWhere + "OPTIONAL {?artist foaf:based_near ?based }";
		  sWhere=sWhere + "OPTIONAL {?artist foaf:made ?album }";
		  sWhere=sWhere + "OPTIONAL {?artist mo:biography ?bio }";
		 
		  sQueries = sQueries+ "WHERE { "+sWhere+" } ";
			 		  
	      QueryExecution qexec = QueryExecutionFactory.sparqlService(service, sQueries);
		  try {	             
				ResultSet rs = qexec.execSelect() ;
				
				while(rs.hasNext())
				{
					QuerySolution soln = rs.nextSolution();
					System.out.println("artiste : "+soln.get("?artist"));
					System.out.println("biographie : "+soln.get("?bio"));
					System.out.println("homepage : "+soln.get("?homepage"));
					System.out.println("image : "+soln.get("?img"));
					System.out.println("lieu de résidence  : "+soln.get("?based"));
					System.out.println("album  : "+soln.get("?album"));

					System.out.println();
				}
		 }
		 finally {
                qexec.close() ;
         }
		  
		  /*requette album : trouver les enregistrements télechargeable d'un artistes à partir de son numero d'album sur jamendo*/
		  
			System.out.println();
			System.out.println("Requette album");
			System.out.println();
		  
		  
			String record="http://dbtune.org/jamendo/record/";
			record=record+"103";
			
		  sQueries ="PREFIX rdf: <" + RDF.getURI() + ">" + CRLF;		  
		  sQueries =sQueries + "PREFIX mo:<http://purl.org/ontology/mo/>" + CRLF;
		  sQueries =sQueries + "PREFIX dce: <http://purl.org/dc/elements/1.1/>" + CRLF;
		  sQueries =sQueries + "PREFIX dc: <http://purl.org/dc/elements/1.1/>" + CRLF;
		  
		  sQueries =sQueries + "PREFIX dce: <http://purl.org/dc/elements/1.1/>" + CRLF;
		  sQueries =sQueries + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + CRLF;
		  sQueries =sQueries + "PREFIX foaf:  <http://xmlns.com/foaf/0.1/>" + CRLF;
		  sQueries =sQueries + "PREFIX tags: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>" + CRLF;
		    
		  sSelect="*";
		  sQueries=sQueries+ "SELECT " + sSelect + CRLF;
		  
		  sWhere="<"+record+"> a mo:Record ;";
		  sWhere=sWhere + "dce:title ?title ;";	
		  sWhere=sWhere + "mo:image ?img ;";	
		  //sWhere=sWhere + "dce:date ?datedce ;";
		  sWhere=sWhere + "dc:date ?datedc ;";
		  sWhere=sWhere + "mo:available_as ?lien;";
		  sWhere=sWhere + "tags:taggedWithTag ?tag .";		
		 
		  sQueries = sQueries+ "WHERE { "+sWhere+" } ";
			 		  
	      qexec = QueryExecutionFactory.sparqlService(service, sQueries);
		  try {	             
				ResultSet rs = qexec.execSelect() ;
				while(rs.hasNext())
				{
					QuerySolution soln = rs.nextSolution();
					System.out.println("titre : "+soln.get("?title"));
					System.out.println("image : "+soln.get("?img"));
					//System.out.println("datedce : "+soln.get("?datedce"));
					System.out.println("datedc : "+soln.get("?datedc"));
					System.out.println("lien : "+soln.get("?lien"));
					System.out.println("tag : "+soln.get("?tag"));
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

=======
package Jena;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.vocabulary.RDF;


public class JamendoQueryMusicEvents{

	public static final String CRLF = System.getProperty("line.separator") ;
	public static final String service = "http://dbtune.org/jamendo/sparql/";
     
	public static void main(String[] args) {
		 		  		
	      String sQueries="" ;
	      String sSelect="";
	      String sWhere="";
	      
		  sQueries =sQueries + "PREFIX rdf: <" + RDF.getURI() + ">" + CRLF;
			  
		  sQueries =sQueries + "PREFIX mo:<http://purl.org/ontology/mo/>" + CRLF;
		  sQueries =sQueries + "PREFIX dce: <http://purl.org/dc/elements/1.1/>" + CRLF;
		  sQueries =sQueries + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + CRLF;
		  sQueries =sQueries + "PREFIX foaf:  <http://xmlns.com/foaf/0.1/>" + CRLF;
		  sQueries =sQueries + "PREFIX owl: <http://www.w3.org/2002/07/owl#>" + CRLF;
		  sQueries =sQueries + "PREFIX tags: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>" + CRLF;

		  
		// requette artiste : trouver un artiste à partir de son nom.
	      String artiste="";
		  artiste="vincent j";
	      //artiste = "Konshens";
		  
		  //artiste="vavrek";
			System.out.println();
			System.out.println("Requette artiste");
			System.out.println();
			
		  sSelect="*";
		  sQueries=sQueries+ "SELECT " + sSelect + CRLF;	  
		  
		  sWhere=sWhere + "?artist foaf:name \""+artiste+"\" .";
		  sWhere=sWhere + "OPTIONAL {?artist foaf:homepage ?homepage }";
		  sWhere=sWhere + "OPTIONAL {?artist foaf:img ?img }";
		  sWhere=sWhere + "OPTIONAL {?artist foaf:based_near ?based }";
		  sWhere=sWhere + "OPTIONAL {?artist foaf:made ?album }";
		  sWhere=sWhere + "OPTIONAL {?artist mo:biography ?bio }";
		 
		  sQueries = sQueries+ "WHERE { "+sWhere+" } ";
			 		  
	      QueryExecution qexec = QueryExecutionFactory.sparqlService(service, sQueries);
		  try {	             
				ResultSet rs = qexec.execSelect() ;
				
				while(rs.hasNext())
				{
					QuerySolution soln = rs.nextSolution();
					System.out.println("artiste : "+soln.get("?artist"));
					System.out.println("biographie : "+soln.get("?bio"));
					System.out.println("homepage : "+soln.get("?homepage"));
					System.out.println("image : "+soln.get("?img"));
					System.out.println("lieu de résidence  : "+soln.get("?based"));
					System.out.println("album  : "+soln.get("?album"));

					System.out.println();
				}
		 }
		 finally {
                qexec.close() ;
         }
		  
		 /* requette album : trouver les enregistrements télechargeable d'un artistes à partir de son numero d'album sur jamendo
		  
			System.out.println();
			System.out.println("Requette album");
			System.out.println();
		  
		  
			String record="http://dbtune.org/jamendo/record/";
			record=record+"103";
			
		  sQueries ="PREFIX rdf: <" + RDF.getURI() + ">" + CRLF;		  
		  sQueries =sQueries + "PREFIX mo:<http://purl.org/ontology/mo/>" + CRLF;
		  sQueries =sQueries + "PREFIX dce: <http://purl.org/dc/elements/1.1/>" + CRLF;
		  sQueries =sQueries + "PREFIX dc: <http://purl.org/dc/elements/1.1/>" + CRLF;
		  
		  sQueries =sQueries + "PREFIX dce: <http://purl.org/dc/elements/1.1/>" + CRLF;
		  sQueries =sQueries + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + CRLF;
		  sQueries =sQueries + "PREFIX foaf:  <http://xmlns.com/foaf/0.1/>" + CRLF;
		  sQueries =sQueries + "PREFIX tags: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>" + CRLF;
		    
		  sSelect="*";
		  sQueries=sQueries+ "SELECT " + sSelect + CRLF;
		  
		  sWhere="<"+record+"> a mo:Record ;";
		  sWhere=sWhere + "dce:title ?title ;";	
		  sWhere=sWhere + "mo:image ?img ;";	
		  //sWhere=sWhere + "dce:date ?datedce ;";
		  sWhere=sWhere + "dc:date ?datedc ;";
		  sWhere=sWhere + "mo:available_as ?lien;";
		  sWhere=sWhere + "tags:taggedWithTag ?tag .";		
		 
		  sQueries = sQueries+ "WHERE { "+sWhere+" } ";
			 		  
	      qexec = QueryExecutionFactory.sparqlService(service, sQueries);
		  try {	             
				ResultSet rs = qexec.execSelect() ;
				while(rs.hasNext())
				{
					QuerySolution soln = rs.nextSolution();
					System.out.println("titre : "+soln.get("?title"));
					System.out.println("image : "+soln.get("?img"));
					//System.out.println("datedce : "+soln.get("?datedce"));
					System.out.println("datedc : "+soln.get("?datedc"));
					System.out.println("lien : "+soln.get("?lien"));
					System.out.println("tag : "+soln.get("?tag"));
					System.out.println();
					
				}
		 }
		 finally {
                qexec.close() ;
         }
		  	  
		 */
		  
		  
		  //requette genre : trouver les artistes ayant fait des albums de rock et de punk
		  
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
//			  sWhere=sWhere + "OPTIONAL {?artist foaf:homepage ?homepage }";
//			  sWhere=sWhere + "OPTIONAL {?artist foaf:img ?img }";
//			  sWhere=sWhere + "OPTIONAL {?artist foaf:based_near ?based }";
//			  sWhere=sWhere + "OPTIONAL {?artist foaf:made ?album }";
//			  sWhere=sWhere + "OPTIONAL {?artist mo:biography ?bio }";
			  
			  
			  
			  String sFilter = "FILTER(?tag=<http://dbtune.org/jamendo/tag/"+tag1+"> || ?tag=<http://dbtune.org/jamendo/tag/"+tag2+">)";		
			
			  sQueries = sQueries+ "WHERE { "+sWhere+" "+sFilter+" } ";
				 			  
		      qexec = QueryExecutionFactory.sparqlService(service, sQueries);
			  try {	             
					ResultSet rs = qexec.execSelect() ;
					
					while(rs.hasNext())
					{
						QuerySolution soln = rs.nextSolution();
						System.out.println("artiste : "+soln.get("?artist"));
						System.out.println("nom : "+soln.get("?name"));
						System.out.println("tag : "+soln.get("?tag"));
						
//						System.out.println("biographie : "+soln.get("?bio"));
//						System.out.println("homepage : "+soln.get("?homepage"));
//						System.out.println("image : "+soln.get("?img"));
//						System.out.println("lieu de résidence  : "+soln.get("?based"));
//						System.out.println("album  : "+soln.get("?album"));
						
						System.out.println();
					}
			 }
			 finally {
	                qexec.close() ;
	         }
			 
		  
		  
		  
		  
		  
		  
	}

}

>>>>>>> ea63653450464348f58bad9de982b17a8bda6e71
