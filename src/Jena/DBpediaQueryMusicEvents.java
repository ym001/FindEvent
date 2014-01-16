package Jena;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.vocabulary.RDF;


public class DBpediaQueryMusicEvents{

	public static final String CRLF = System.getProperty("line.separator") ;
	public static final String service = "http://www.dbpedia.org/sparql";
     
	public static void main(String[] args) {
		 		  		
	      String sQueries="" ;
	      String sSelect="";
	      String sWhere="";
	      
		  sQueries =sQueries + "PREFIX rdf: <" + RDF.getURI() + ">" + CRLF;
		  sQueries =sQueries + "PREFIX prop: <http://dbpedia.org/property/>" + CRLF;
		  sQueries =sQueries + "PREFIX dc: <http://purl.org/dc/elements/1.1/>" + CRLF;
		  sQueries =sQueries + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + CRLF;
		  sQueries =sQueries + "PREFIX dbpedia-owl:<http://dbpedia.org/ontology/>" + CRLF;
		  sQueries =sQueries + "PREFIX mo:<http://purl.org/ontology/mo/>" + CRLF;
		  sQueries =sQueries + "PREFIX dce: <http://purl.org/dc/elements/1.1/>" + CRLF;
		  sQueries =sQueries + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + CRLF;
		  sQueries =sQueries + "PREFIX foaf:  <http://xmlns.com/foaf/0.1/>" + CRLF;
		  sQueries =sQueries + "PREFIX owl: <http://www.w3.org/2002/07/owl#>" + CRLF;
		  sQueries =sQueries + "PREFIX tags: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>" + CRLF;

		  
/* requette artiste : trouver toutes les informations sur un artiste. */
	      String artiste="";
		  //artiste="Buddy_Guy";
		  
		  
	      artiste="Vincent_J";
			System.out.println();
			System.out.println("Requette info artiste : "+artiste);
			System.out.println();
			
			
		
		  sSelect="SELECT DISTINCT *";
		  sQueries=sQueries + sSelect + CRLF;	  

		  sWhere=sWhere + "?artiste a dbpedia-owl:Artist .FILTER regex(?artiste,\""+artiste+"\")";
		  sWhere=sWhere + "OPTIONAL{?artiste foaf:name ?name.}";
		  sWhere=sWhere + "OPTIONAL{?artiste dbpedia-owl:abstract  ?resume.FILTER(lang(?resume) = 'fr')}";
		  sWhere=sWhere + "OPTIONAL{?artiste foaf:depiction ?depiction.}";
		  sWhere=sWhere + "OPTIONAL{?artiste dbpedia-owl:birthDate ?anif.}";
		  sWhere=sWhere + "OPTIONAL{?artiste dbpedia-owl:genre ?genre.}";
		  sWhere=sWhere + "OPTIONAL{?artiste dbpedia-owl:instrument ?instrument.}";
		  sWhere=sWhere + "OPTIONAL{?artiste dbpedia-owl:wikiPageExternalLink ?link.}";
		  sWhere=sWhere + "OPTIONAL{?artiste dc:description ?description.FILTER(lang(?description) = 'fr')}";
		  sWhere=sWhere + "OPTIONAL{?artiste rdfs:comment ?comment.FILTER(lang(?comment) = 'fr')}";
		  //sWhere=sWhere + "OPTIONAL{?artiste rdfs:label ?label.}";		 
		  sWhere=sWhere + "OPTIONAL{<http://dbpedia.org/resource/"+artiste+"> dbpedia-owl:producer ?producteur}";
		 
		 
		  sQueries = sQueries+ "WHERE { "+sWhere+" } ";
	      QueryExecution qexec = QueryExecutionFactory.sparqlService(service, sQueries);
		  try {	             
				ResultSet rs = qexec.execSelect() ;
				
				while(rs.hasNext())
				{
					QuerySolution soln = rs.nextSolution();
					
					System.out.println("nom  : "+soln.get("?name"));
					//System.out.println("label  : "+soln.get("?label"));
					System.out.println("resumé  : "+soln.get("?resume"));
					System.out.println("commentaire  : "+soln.get("?comment"));
					System.out.println("depiction  : "+soln.get("?depiction"));
					System.out.println("anniversaire  : "+soln.get("?anif"));
					System.out.println("genre  : "+soln.get("?genre"));
					System.out.println("instrument  : "+soln.get("?instrument"));
					System.out.println("lien  : "+soln.get("?link"));
					System.out.println("description  : "+soln.get("?description"));
					System.out.println("producteur : "+soln.get("?producer"));

					System.out.println();
				}
		 }
		 finally {
                qexec.close() ;
         }
			 System.out.println("fin requette infos artiste");

/* requette album : trouver les albums d'un artistes */

		  artiste="Vincent_J";
		System.out.println("fin requette infos artiste : "+artiste);

			System.out.println();
			System.out.println("Requette album de l'artiste : "+artiste);
			System.out.println();
		  
			
		  sQueries ="PREFIX rdf: <" + RDF.getURI() + ">" + CRLF;		  
		  sQueries =sQueries + "PREFIX prop: <http://dbpedia.org/property/>" + CRLF;
		  sQueries =sQueries + "PREFIX dc: <http://purl.org/dc/elements/1.1/>" + CRLF;
		  sQueries =sQueries + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + CRLF;
		  sQueries =sQueries + "PREFIX dbpedia-owl:<http://dbpedia.org/ontology/>" + CRLF;
		  sQueries =sQueries + "PREFIX mo:<http://purl.org/ontology/mo/>" + CRLF;
		  sQueries =sQueries + "PREFIX dce: <http://purl.org/dc/elements/1.1/>" + CRLF;
		  sQueries =sQueries + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + CRLF;
		  sQueries =sQueries + "PREFIX foaf:  <http://xmlns.com/foaf/0.1/>" + CRLF;
		  sQueries =sQueries + "PREFIX owl: <http://www.w3.org/2002/07/owl#>" + CRLF;
		  sQueries =sQueries + "PREFIX tags: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>" + CRLF;

		  sSelect="SELECT DISTINCT *";
		  sQueries=sQueries + sSelect + CRLF;
		  		  
		  sWhere= "?album prop:artist  <http://dbpedia.org/resource/"+artiste+"> .";
		 
		  sQueries = sQueries+ "WHERE { "+sWhere+" } ";
	      qexec = QueryExecutionFactory.sparqlService(service, sQueries);
		  try {	             
				ResultSet rs = qexec.execSelect() ;
				while(rs.hasNext())
				{
					QuerySolution soln = rs.nextSolution();
					System.out.println("album : "+soln.get("?album"));
					System.out.println();
					
				}
		 }
		 finally {
                qexec.close() ;
         }
			 System.out.println("fin requette album");

/*requette info album: trouver les infos sur un albums */
			 
		  String album="Damn_Right,_I've_Got_the_Blues";
		      System.out.println();
			  System.out.println("Requette infos album : "+album);
			  System.out.println();
			  
			
			  sQueries ="PREFIX rdf: <" + RDF.getURI() + ">" + CRLF;		  
			  sQueries =sQueries + "PREFIX prop: <http://dbpedia.org/property/>" + CRLF;
			  sQueries =sQueries + "PREFIX dc: <http://purl.org/dc/elements/1.1/>" + CRLF;
			  sQueries =sQueries + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + CRLF;
			  sQueries =sQueries + "PREFIX dbpedia-owl:<http://dbpedia.org/ontology/>" + CRLF;
			  sQueries =sQueries + "PREFIX mo:<http://purl.org/ontology/mo/>" + CRLF;
			  sQueries =sQueries + "PREFIX dce: <http://purl.org/dc/elements/1.1/>" + CRLF;
			  sQueries =sQueries + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + CRLF;
			  sQueries =sQueries + "PREFIX foaf:  <http://xmlns.com/foaf/0.1/>" + CRLF;
			  sQueries =sQueries + "PREFIX owl: <http://www.w3.org/2002/07/owl#>" + CRLF;
			  sQueries =sQueries + "PREFIX tags: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>" + CRLF;
	  
			  sSelect="SELECT DISTINCT *";
			  sQueries=sQueries + sSelect + CRLF;
					 
			sWhere=" ?album prop:artist ?artiste .FILTER regex(?album,\""+album+"\") ";
			sWhere=sWhere + "OPTIONAL{ ?album prop:name ?name . }";
			sWhere=sWhere + "OPTIONAL{ ?album rdf:type <http://dbpedia.org/ontology/Album> . }";
			sWhere=sWhere + "OPTIONAL{ ?album rdf:type <http://schema.org/MusicAlbum> .} ";
			sWhere=sWhere + "OPTIONAL{ ?album foaf:depiction ?depiction.}";
			sWhere=sWhere + "OPTIONAL{ ?album dbpedia-owl:abstract ?abstract.}";
			sWhere=sWhere + "OPTIONAL{ ?album rdfs:comment ?comment.}";
			sWhere=sWhere + "OPTIONAL{ ?album prop:cover ?cover.}";
			sWhere=sWhere + "OPTIONAL{ ?album prop:genre ?genre.}";
			sWhere=sWhere + "OPTIONAL{ ?album dbpedia-owl:producer ?producer.}";
			sWhere=sWhere + "OPTIONAL{ ?album <http://dbpedia.org/ontology/releaseDate> ?date .}"; 
			sWhere=sWhere + "OPTIONAL{ ?album dbpedia-owl:recordLabel ?label}";
			  
			 
			  sQueries = sQueries+ "WHERE { "+sWhere+" } ";
				 			  
		      qexec = QueryExecutionFactory.sparqlService(service, sQueries);
			  try {	             
					ResultSet rs = qexec.execSelect() ;
					
					while(rs.hasNext())
					{
						QuerySolution soln = rs.nextSolution();
						System.out.println("album : "+soln.get("?album"));
						System.out.println("nom : "+soln.get("?name"));
						System.out.println("img : "+soln.get("?depiction"));
						System.out.println("resumé : "+soln.get("?abstract"));
						System.out.println("commentaire : "+soln.get("?comment"));
						System.out.println("couverture : "+soln.get("?cover"));
						System.out.println("genre : "+soln.get("?genre"));
						System.out.println("producteur : "+soln.get("?producer"));
						System.out.println("label : "+soln.get("?label"));
						System.out.println("date : "+soln.get("?date"));
						System.out.println();
					}
			 }
			 finally {
	                qexec.close() ;
	         }
			 System.out.println("fin requette infos album");
		  
	}

}

