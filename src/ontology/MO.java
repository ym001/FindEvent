package ontology;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;

/*
 * Classe MusicOntology
 * @author Sébastien Paradis
 */
public class MO {
	private static Model m = ModelFactory.createDefaultModel();

	public static final Resource MusicGroup = m.createProperty(Namespaces.mo_ns + "MusicGroup");
	public static final Resource MusicArtist = m.createProperty(Namespaces.mo_ns + "MusicArtist");
	public static final Property activity_start = m.createProperty(Namespaces.mo_ns + "activity_start");
	
	public static String getUri() {
		return Namespaces.mo_ns;
	}

	public static Property getProperty(String pName) {
		return new PropertyImpl(getUri(), pName); 
	}
	
	public static Resource getResource(String pName) {
		return new ResourceImpl(getUri(), pName); 
	}
}
