package Jena;
//import javax.sound.sampled.AudioFileFormat.Type;

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

public class Q1 {

	public static final String CRLF = System.getProperty("line.separator") ;
	//public static final String inputFileName  = "resources/users.rdf";
	//public static final String inputFileName  = "resources/test";
	//public static final String inputFileName  = "resources/ontology_v3.1.rdf";
	//final String file = "https://github.com/ym001/FindEvent/blob/master/src/ontology_v3.1.rdf";
	//public static final String inputFileName = "file:///home/master/Dropbox/github/FindEvent/resources/concert.rdf";
	public static final String inputFileName = "file:///home/master/Dropbox/github/FindEvent/resources/users.rdf";

	public static void main(String[] args) {

		  Model m = ModelFactory.createOntologyModel();
		  
		  //InputStream in = FileManager.get().open( inputFileName );
	      //  if (in == null) {
	      //     throw new IllegalArgumentException( "File: " + inputFileName + " not found");
	      //  }
		 		  		
		  m.read(inputFileName);
	      String sQueries="" ;
	      String sSelect="";
	      String sWhere="";
		  sQueries =sQueries + "PREFIX rdf: <" + RDF.getURI() + ">" + CRLF;
		  sQueries =sQueries + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + CRLF;
		  sQueries =sQueries + "PREFIX owl: <http://www.w3.org/2002/07/owl#>" + CRLF;
		  sQueries =sQueries + "PREFIX dc: <http://purl.org/dc/elements/1.1#>" + CRLF;
		  sQueries =sQueries + "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>" + CRLF;
		  sQueries =sQueries + "PREFIX event: <http://schema.org/Event#>" + CRLF;
		  sQueries =sQueries + "PREFIX place: <http://schema.org/Place#>" + CRLF;
		  sQueries =sQueries + "PREFIX address: <http://schema.org/PostalAddress#>" + CRLF;
		  sQueries =sQueries +  "PREFIX geo: <http://schema.org/GeoCoordinates#>" + CRLF;
		  sQueries =sQueries + "PREFIX schema: <http://schema.org#>" + CRLF;
		  sQueries =sQueries + "PREFIX foaf:   <http://xmlns.com/foaf/0.1/> " + CRLF;
		  sQueries =sQueries + "PREFIX vCard: <http://www.w3.org/2001/vcard-rdf/3.0#>" + CRLF;
		  sQueries =sQueries + "prefix xs: <http://www.w3.org/2001/XMLSchema#>" + CRLF;
		 
		  sSelect="*";
		  // sSelect="?mbox";
		  sQueries=sQueries+ "SELECT " + sSelect + CRLF;
		  
		  //sQueries = sQueries+ "FROM <https://github.com/ym001/FindEvent/blob/master/src/users.rdf>";
		  
		  // sWhere="foaf:title ?person.";
		 // sWhere="?x foaf:name \"Johnny Lee Outlaw\" . "+CRLF;
				 // +"?x foaf:mbox ?mbox"+ CRLF;
		  
		 sWhere="?personne rdf:type foaf:Person.";
		 sWhere=sWhere + " ?personne foaf:name ?name.";
		 sWhere=sWhere + " ?personne foaf:family_name ?family_name.";
		 sWhere=sWhere + " ?personne foaf:givenname ?givenname.";
		 sWhere=sWhere + " ?personne foaf:mbox ?mbox.";
		 sWhere=sWhere + " ?personne foaf:nick ?nick.";
		 sWhere=sWhere + " ?personne foaf:knows ?knows.";
		  //sWhere=sWhere + "?person1 foaf:knows ?person2 ."+CRLF+"?person1 foaf:name  ?name1 ."+CRLF+" ?person2 foaf:name  ?name2 .";
			// sWhere=" ?event rdf:type rdf:resource.";
		  //sWhere=" ?place a place:geo . ?place geo:lat ?lat."+ CRLF;
		   //sWhere=sWhere+"?place geo:long ?long ";
		  sQueries = sQueries+ "WHERE { "+sWhere+" } ";
			 
		  System.out.println(sQueries);	  	  
		  
		  Query Q = QueryFactory.create(sQueries);
		  QueryExecution qexec = QueryExecutionFactory.create(Q, m) ; 
		  try {	             
				ResultSet rs = qexec.execSelect() ;
				
				//while(rs.hasNext())
				{
					//QuerySolution soln = rs.nextSolution();
					//System.out.println(soln.toString());
					//System.out.println(soln.get("?personne "));
					//System.out.println(soln.get("?comment"));
				}
							
				ResultSetFormatter.out(System.out, rs, Q);

		 }
		 finally {
                qexec.close() ;
         }
		  
			 m.write(System.out, "RDF/JSON");
	}

}

