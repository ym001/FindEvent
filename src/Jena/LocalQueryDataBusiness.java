//package queries;
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

public class LocalQueryDataBusiness {
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
		sPrefix =sPrefix+ "PREFIX mo: <http://purl.org/ontology/mo/>" + CRLF;
		sPrefix =sPrefix+ "PREFIX igeo: <http://rdf.insee.fr/def/geo#>" + CRLF;
		sPrefix =sPrefix+ "PREFIX gps: <http://www.w3.org/2003/01/geo/wgs84_pos#>" + CRLF;
		sPrefix =sPrefix+ "PREFIX lode: <http://linkedevents.org/ontology/>" + CRLF;
		sPrefix =sPrefix+ "PREFIX schema: <http://schema.org/Event>" + CRLF;
		sPrefix =sPrefix+ "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#>" + CRLF;
		sPrefix =sPrefix+ "PREFIX tags: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>" + CRLF;
		

/*requette annotation : trouve les évènements annotés.*/
		System.out.println();
		System.out.println("Requette data business : les évènements annotés");
		System.out.println();
		
		String sSelect="*";
		sQueries=sPrefix+"SELECT DISTINCT" + sSelect + CRLF;
		  
		String tagg ="Pop";
		sWhere="";
		sWhere=sWhere + "?databusiness meo:annotation ?annotation."+ CRLF;
		sWhere=sWhere + "OPTIONAL {?databusiness meo:tagg ?tagg}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?databusiness meo:EvenementMusical ?event}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?databusiness dc:date ?date}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?databusiness gps:lat ?lat}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?databusiness gps:long ?long}"+ CRLF;		
		sWhere=sWhere + "OPTIONAL {?databusiness foaf:name ?nom}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?databusiness foaf:mail ?mail}"+ CRLF;
		sQueries = sQueries+ "WHERE { "+sWhere+" } ";
			 
		//System.out.println(sQueries);	  	  
		QueryExecution qexec = QueryExecutionFactory.create(sQueries, m);

		try {	             
				ResultSet rs = qexec.execSelect() ;
				
				while(rs.hasNext())
				{
					QuerySolution soln = rs.nextSolution();
					System.out.println("databusiness : "+soln.get("?databusiness"));
					System.out.println("tagg : "+soln.get("?tagg"));
					System.out.println("annotation : "+soln.get("?annotation"));
					System.out.println("event : "+soln.get("?event"));
					System.out.println("date d'annotation : "+soln.get("?date"));
					System.out.println("latitude d'annotation : "+soln.get("?lat"));
					System.out.println("longitude d'annotation : "+soln.get("?long"));
					System.out.println("nom : "+soln.get("?nom"));
					System.out.println("mail : "+soln.get("?mail"));
					System.out.println();
					
				}
		 }
		 finally {
                qexec.close() ;
         }
		 
		System.out.println();
		System.out.println("Fin requette data business : données annotation");
		System.out.println();
		
		
		/*requette tagg : trouve les évènements taggés pop.*/
		System.out.println();
		System.out.println("Requette data business : les évènements taggés pop");
		System.out.println();
		
		sSelect="*";
		sQueries=sPrefix+"SELECT " + sSelect + CRLF;
		  
		tagg ="Pop";
		sWhere="";
		//sWhere=sWhere + "?databusiness meo:tagg ?tagg.FILTER regex(?tag,\""+tagg+"\")"+ CRLF;
		sWhere=sWhere + "?databusiness meo:tagg ?tagg.FILTER regex(?tagg,\""+tagg+"\")"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?databusiness meo:annotation ?annotation }"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?databusiness meo:EvenementMusical ?event}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?databusiness dc:date ?date}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?databusiness gps:lat ?lat}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?databusiness gps:long ?long}"+ CRLF;		
		sWhere=sWhere + "OPTIONAL {?databusiness foaf:name ?nom}"+ CRLF;
		sWhere=sWhere + "OPTIONAL {?databusiness foaf:mail ?mail}"+ CRLF;
		sQueries = sQueries+ "WHERE { "+sWhere+" } ";
			 
		//System.out.println(sQueries);	  	  
		qexec = QueryExecutionFactory.create(sQueries, m);

		try {	             
				ResultSet rs = qexec.execSelect() ;
				
				while(rs.hasNext())
				{
					QuerySolution soln = rs.nextSolution();
					System.out.println("databusiness : "+soln.get("?databusiness"));
					System.out.println("tagg : "+soln.get("?tagg"));
					System.out.println("annotation : "+soln.get("?annotation"));
					System.out.println("event : "+soln.get("?event"));
					System.out.println("date d'annotation : "+soln.get("?date"));
					System.out.println("latitude d'annotation : "+soln.get("?lat"));
					System.out.println("longitude d'annotation : "+soln.get("?long"));
					System.out.println("nom : "+soln.get("?nom"));
					System.out.println("mail : "+soln.get("?mail"));
					System.out.println();
					
				}
		 }
		 finally {
                qexec.close() ;
         }
		 
		System.out.println();
		System.out.println("Fin requette data business : données taggées pop");
		System.out.println();
	}

}


