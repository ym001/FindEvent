package Jena;

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
import com.hp.hpl.jena.util.FileManager;
import java.util.ArrayList;
import java.util.List;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.query.*;
import java.io.InputStream;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.sparql.engine.http.QueryExceptionHTTP;

public class Qjamendo{

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
		 
		  sSelect="*";
		  sQueries=sQueries+ "SELECT " + sSelect + CRLF;
		  
		  
		 sWhere="?artist a mo:MusicArtist ;";
		 sWhere=sWhere + " foaf:homepage ?homepage ;";
		 sWhere=sWhere + "foaf:img ?img ;";
		 sWhere=sWhere + "foaf:made ?made ;";
		 sWhere=sWhere + " foaf:name \"vincent j\" .";
		
		 
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
					System.out.println();
				}
		 }
		 finally {
                qexec.close() ;
         }
		 
	  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  sQueries ="PREFIX rdf: <" + RDF.getURI() + ">" + CRLF;		  
		  sQueries =sQueries + "PREFIX mo:<http://purl.org/ontology/mo/>" + CRLF;
		  sQueries =sQueries + "PREFIX dce: <http://purl.org/dc/elements/1.1/>" + CRLF;
		  sQueries =sQueries + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + CRLF;
		  sQueries =sQueries + "PREFIX foaf:  <http://xmlns.com/foaf/0.1/>" + CRLF;
		 
		  sSelect="*";
		  sQueries=sQueries+ "SELECT " + sSelect + CRLF;
		  
		 sWhere="<http://dbtune.org/jamendo/record/1186> a mo:Record ;";
		 sWhere=sWhere + " dce:date ?date ;";
		 sWhere=sWhere + "mo:available_as ?lien;";
		 sWhere=sWhere + "dce:title ?title .";		
		 
		  sQueries = sQueries+ "WHERE { "+sWhere+" } ";
			 
		 // System.out.println(sQueries);	  	  
		  
	      qexec = QueryExecutionFactory.sparqlService(service, sQueries);
		  try {	             
				ResultSet rs = qexec.execSelect() ;
				while(rs.hasNext())
				{
					QuerySolution soln = rs.nextSolution();
					System.out.println("titre : "+soln.get("?title"));
					System.out.println("date : "+soln.get("?date"));
					System.out.println("lien : "+soln.get("?lien"));
					System.out.println();
				}
		 }
		 finally {
                qexec.close() ;
         }
	}

}

