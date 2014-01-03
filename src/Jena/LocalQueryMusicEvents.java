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
		
		String s = "file:D:/Séb/Dropbox/M2 AIGLE/Cours/Web sémantique (A. Seilles) (aka Technos avancées du web)/Projet/exemples/musiceventontology.rdf";
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
		System.out.println("Les éléments du modèle : ");
		try {	
			ResultSet rs = qexec.execSelect();
		    ResultSetFormatter.out(System.out, rs, query);	
		}
		finally {
			qexec.close() ;
		}	

	}

}