import javax.sound.sampled.AudioFileFormat.Type;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.vocabulary.RDF;


public class Q1 {

	public static final String CRLF = System.getProperty("line.separator") ;
	
	public static void main(String[] args) {
		  Model m = ModelFactory.createOntologyModel();
		  
		  final String file = "file:in/ontology_v3.1.rdf";
		  m.read(file);
		  
		  String pref1 = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>";
		  String pref2 = "PREFIX rdf: <" + RDF.getURI() + ">";
		  String pref3 = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
		  String typeClasse = "skos:ConceptScheme"; 
		  
		  String qClasses = pref1 + CRLF + pref2 + CRLF + pref3 + CRLF +
				            "SELECT ?classes ?comments WHERE {?classes rdf:type " + typeClasse + " ." +
				            "?classes rdfs:comment ?comments }";
		  
		  Query Q = QueryFactory.create(qClasses);
		  QueryExecution qexec = QueryExecutionFactory.create(Q, m) ;
		 
		  try {	             
				ResultSet rs = qexec.execSelect() ;
				ResultSetFormatter.out(System.out, rs, Q);

		 }
		 finally {
                qexec.close() ;
         }
		  
		 //m.write(System.out, "N3");
	}

}