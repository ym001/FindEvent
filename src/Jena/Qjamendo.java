package Jena;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.vocabulary.RDF;


public class Qjamendo{

	public static final String CRLF = System.getProperty("line.separator") ;
	public static final String service = "http://dbtune.org/jamendo/sparql/";
     
	public static void main(String[] args) {
		 		  		
	      String sQueries="" ;
	      String sSelect="";
	      String sWhere="";
	      String artiste="vincent j";
	      
		  sQueries =sQueries + "PREFIX rdf: <" + RDF.getURI() + ">" + CRLF;
			  
		  sQueries =sQueries + "PREFIX mo:<http://purl.org/ontology/mo/>" + CRLF;
		  sQueries =sQueries + "PREFIX dce: <http://purl.org/dc/elements/1.1/>" + CRLF;
		  sQueries =sQueries + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + CRLF;
		  sQueries =sQueries + "PREFIX foaf:  <http://xmlns.com/foaf/0.1/>" + CRLF;
		  sQueries =sQueries + "PREFIX owl: <http://www.w3.org/2002/07/owl#>" + CRLF;
		  sQueries =sQueries + "PREFIX tags: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>" + CRLF;

		  sSelect="*";
		  sQueries=sQueries+ "SELECT " + sSelect + CRLF;
		  
		  
		  sWhere="?artist a mo:MusicArtist ;";
		  sWhere=sWhere + " foaf:homepage ?homepage ;";
		  sWhere=sWhere + " foaf:based_near ?based ;";
		  //sWhere=sWhere + " owl:sameAs ?sameas ;";
		  //sWhere=sWhere + " mo:biography ?bio ;";
		  
		  sWhere=sWhere + " foaf:img ?img ;";
		  sWhere=sWhere + " foaf:made ?made ;";
		  //sWhere=sWhere + " tags:taggedWithTag ?tag ;";		
		  sWhere=sWhere + " foaf:name \""+artiste+"\" .";
		
		 
		  sQueries = sQueries+ "WHERE { "+sWhere+" } ";
			 
		 // System.out.println(sQueries);	  	  
		  
	      QueryExecution qexec = QueryExecutionFactory.sparqlService(service, sQueries);
		  try {	             
				ResultSet rs = qexec.execSelect() ;
				
				while(rs.hasNext())
				{
					QuerySolution soln = rs.nextSolution();
					System.out.println("artiste : "+soln.get("?artist"));
					System.out.println("homepage : "+soln.get("?homepage"));
					System.out.println("image : "+soln.get("?img"));
					System.out.println("album  : "+soln.get("?made"));
					System.out.println("lieu de résidence  : "+soln.get("?based"));
					//System.out.println("biographie  : "+soln.get("?bio"));
					System.out.println("voir aussi  : "+soln.get("?sameas"));
					System.out.println();
				}
		 }
		 finally {
                qexec.close() ;
         }
		 
	  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  String record="http://dbtune.org/jamendo/record/1186";
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
		  sWhere=sWhere + "dce:date ?datedce ;";
		  sWhere=sWhere + "dc:date ?datedc ;";
		  sWhere=sWhere + "mo:available_as ?lien;";
		  sWhere=sWhere + "dce:title ?title ;";		
		  sWhere=sWhere + "tags:taggedWithTag ?tag .";		
		 
		  sQueries = sQueries+ "WHERE { "+sWhere+" } ";
			 
		 // System.out.println(sQueries);	  	  
		  
	      qexec = QueryExecutionFactory.sparqlService(service, sQueries);
		  try {	             
				ResultSet rs = qexec.execSelect() ;
				while(rs.hasNext())
				{
					QuerySolution soln = rs.nextSolution();
					System.out.println("titre : "+soln.get("?title"));
					System.out.println("datedce : "+soln.get("?datedce"));
					System.out.println("datedc : "+soln.get("?datedc"));
					System.out.println("lien : "+soln.get("?lien"));
					System.out.println("tag : "+soln.get("?tag"));
					System.out.println();
					System.out.println();
					System.out.println();
				}
		 }
		 finally {
                qexec.close() ;
         }
		  
		  
		  
		  /*requette genre
		  System.out.println();
			System.out.println("Requette genre");
			System.out.println();
		  
			 sSelect="*";
			  sQueries=sQueries+ "SELECT " + sSelect + CRLF;
			  
			  
			  sWhere="?artist a mo:MusicArtist ;";
			  sWhere=sWhere + " foaf:homepage ?homepage ;";
			  sWhere=sWhere + " foaf:based_near ?based ;";
			  //sWhere=sWhere + " owl:sameAs ?sameas ;";
			  //sWhere=sWhere + " mo:biography ?bio ;";
			  
			  sWhere=sWhere + " foaf:img ?img ;";
			  sWhere=sWhere + " foaf:made ?made ;";
			  //sWhere=sWhere + " tags:taggedWithTag ?tag ;";		
			  sWhere=sWhere + " foaf:name \""+artiste+"\" .";
			
			 
			  sQueries = sQueries+ "WHERE { "+sWhere+" } ";
				 
			 // System.out.println(sQueries);	  	  
			  
		      QueryExecution qexec = QueryExecutionFactory.sparqlService(service, sQueries);
			  try {	             
					ResultSet rs = qexec.execSelect() ;
					
					while(rs.hasNext())
					{
						QuerySolution soln = rs.nextSolution();
						System.out.println("artiste : "+soln.get("?artist"));
						System.out.println("homepage : "+soln.get("?homepage"));
						System.out.println("image : "+soln.get("?img"));
						System.out.println("album  : "+soln.get("?made"));
						System.out.println("lieu de résidence  : "+soln.get("?based"));
						//System.out.println("biographie  : "+soln.get("?bio"));
						System.out.println("voir aussi  : "+soln.get("?sameas"));
						System.out.println();
					}
			 }
			 finally {
	                qexec.close() ;
	         }
			 */
		  
	}

}

