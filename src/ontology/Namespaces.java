package ontology;

import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.DCTerms;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

public class Namespaces {
	public static final String meo_ns = "http://www.musicevents.com/#";
	public static final String rdf_ns = RDF.getURI();
	public static final String rdfs_ns = RDFS.getURI(); // "http://www.w3.org/2000/01/rdf-schema#";
	public static final String skos_ns = "http://www.w3.org/2004/02/skos/core#";
	public static final String foaf_ns = FOAF.getURI(); // "http://xmlns.com/foaf/0.1/";
	public static final String dc_ns = DC.getURI(); 
	public static final String dcterms_ns = DCTerms.getURI();
	public static final String mo_ns = "http://purl.org/ontology/mo/";
	public static final String gps_ns = "http://www.w3.org/2003/01/geo/wgs84_pos#";
	public static final String xsd_ns = "http://www.w3.org/2001/XMLSchema#";
	public static final String igeo_ns = "http://rdf.insee.fr/def/geo#";
	public static final String geo_ns = "http://www.geonames.org/ontology#";
	public static final String lode_ns = "http://linkedevents.org/ontology/";
	public static final String schema_ns = "http://schema.org/Event";
	public static final String place_ns = "http://schema.org/Place";
	public static final String address_ns = "http://schema.org/PostalAddress";
}
