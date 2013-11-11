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

public class Qevent {

	public static final String CRLF = System.getProperty("line.separator") ;
	//public static final String inputFileName = "file:///home/master/Dropbox/github/FindEvent/resources/concert.rdf";
	public static final String inputFileName = "http://github.com/ym001/FindEvent/blob/master/resources/users.rdf";
	//public static final String inputFileName = "file:///home/master/Dropbox/github/FindEvent/resources/cibul.json";

	public static double distance( double lat1,double long1,double lat2,double long2) {
		 long1= Math.toRadians(long1);
		 long2= Math.toRadians(long2);
		 lat1= Math.toRadians(lat1);
		 lat2= Math.toRadians(lat2);
		double distance=0.0;
		final double R=6378;
		double dlon = long2 - long1;
		double dlat = lat2 - lat1;
		double a = Math.pow(Math.sin(dlat/2),2)*(Math.sin(dlat/2)) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon/2),2);
		double c = 2 * Math.atan2( Math.sqrt(a), Math.sqrt(1-a) );
		distance = R * c ;
		return distance;
	}
		public static void main(String[] args) {

		  Model m = ModelFactory.createOntologyModel();
		 		  		
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
		  sQueries =sQueries + "PREFIX geo: <http://schema.org/GeoCoordinates#>" + CRLF;
		  sQueries =sQueries + "PREFIX schema: <http://schema.org#>" + CRLF;
		  sQueries =sQueries + "PREFIX foaf:   <http://xmlns.com/foaf/0.1/> " + CRLF;
		  sQueries =sQueries + "PREFIX vCard: <http://www.w3.org/2001/vcard-rdf/3.0#>" + CRLF;
		  sQueries =sQueries + "prefix xs: <http://www.w3.org/2001/XMLSchema#>" + CRLF;
		 
		  sSelect="*";
		  // sSelect="?mbox";
		  sQueries=sQueries+ "SELECT " + sSelect + CRLF;
		  
		  //sQueries = sQueries+ "FROM <https://github.com/ym001/FindEvent/blob/master/src/users.rdf>";
		  
		 sWhere="?event rdf:type ?y.";
		 //sWhere=sWhere + " ?event event:name ?name.";
		
			// sWhere=" ?event rdf:type rdf:resource.";
		  //sWhere=" ?place a place:geo . ?place geo:lat ?lat."+ CRLF;
		  sWhere=sWhere+"?place geo:long ?long ";
		  sQueries = sQueries+ "WHERE { "+sWhere+" } ";
		  
		  System.out.println("distance ="+distance( 43.6000000 ,3.8833300,43.2969500 ,5.3810700)+" km.");	  	  
		  
		  System.out.println(sQueries);	  	  
		  
		  Query Q = QueryFactory.create(sQueries);
		  QueryExecution qexec = QueryExecutionFactory.create(Q, m) ; 
		  try {	             
				ResultSet rs = qexec.execSelect() ;		
				ResultSetFormatter.out(System.out, rs, Q);

		 }
		 finally {
                qexec.close() ;
         }
		  
			 m.write(System.out, "RDF/JSON");
	}

}
