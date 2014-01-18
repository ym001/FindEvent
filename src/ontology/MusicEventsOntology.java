/*package ontology;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import WsSem.model.JsonArtist;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.DCTerms;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;


 * Mod�le mettant en relation l'ontologies de groupes de musique 
 * et celle des �v�nements musicaux (concerts, festivals, ...)
 * event: http://purl.org/NET/c4dm/event.owl#
 * 
 * @author S�bastien Paradis
 
public class MusicEventsOntology {
	private static Model m;
	
	public static Resource MusicGroup;
	public static Resource Artist;
	public static Resource EventLocation;
	public static Resource MusicEvent;
	public static Resource MusicalGenre;
	
	*//**** Propri�t�s d�finies dans l'ontologie ****//*
	// Propri�t�s applicables � #MusicEvent 
	public static Property hasSubEvent;			// de type #MusicEvent
	public static Property hasPerformer;		// de type #MusicGroup ou #Artist
	public static Property takesPlace;			// de type #EventLocation
	public static Property dateBegin;			// de type dc:Date
	public static Property dateEnd;				// de type dc:Date
	// Propri�t� applicable � #MusicGroup et #Artist 
	public static Property hasForMusicalGenre;	// de type #MusicalGenre
	// Propri�t� applicable � #MusicGroup 
	public static Property hasForMember;		// de type #Artist
	
	*//**** Autres propri�t�s n�cessaires  ****//*
	public static Property hasForWebsite;
	public static Property hasForImage;
	public static Property latitude;
	public static Property longitude;
	public static Property street;
	public static Property city;
	public static Property postalCode;
	public static Property country;
	
	//http://stackoverflow.com/questions/14504804/how-to-create-transitive-properties-usingh-jena-schema
	public void run() {
	    Model m = ModelFactory.createDefaultModel();
		//m.write(System.out, "RDF/XML");

        ShowClassProperties((OntClass) Artist);
        ShowClassProperties((OntClass) MusicEvent);
        ShowClassProperties((OntClass) MusicalGenre);

        Individual paul = CreateResource(m, Artist, "Paul McCartney");
        paul.addProperty(aPourGenre, m.createTypedLiteral("Pop"));
        paul.addProperty(hasForWebsite, "www.beatles.com");
        ShowInstanceProperties(paul, (OntClass)Artist);
	}
	
	public MusicEventsOntology() {
		CreateModel();
	}
	
	private Model CreateModel() {
		//OntModel m = ModelFactory.createOntologyModel();
		m = ModelFactory.createDefaultModel();
		//m.createOntology(ns);
		
		String ns = Namespaces.meo_ns;
		
		m.setNsPrefix("xmlns", ns); 
		m.setNsPrefix("meo", ns); 
		m.setNsPrefix("base", ns); 

		//m.setNsPrefix("owl", ns); 
		m.setNsPrefix("rdfs", Namespaces.rdfs_ns); 
		m.setNsPrefix("skos", Namespaces.skos_ns); 
	    m.setNsPrefix("foaf", Namespaces.foaf_ns);
		m.setNsPrefix("dcterms", Namespaces.dcterms_ns); 
		m.setNsPrefix("dc", Namespaces.dc_ns); 
		m.setNsPrefix("mo", Namespaces.mo_ns);
		m.setNsPrefix("rdf", Namespaces.rdf_ns);
		m.setNsPrefix("igeo", Namespaces.igeo_ns);
		m.setNsPrefix("gps", Namespaces.gps_ns);
		m.setNsPrefix("lode", Namespaces.lode_ns);
		m.setNsPrefix("schema", Namespaces.schema_ns);
		m.setNsPrefix("place", Namespaces.place_ns);
		m.setNsPrefix("address", Namespaces.address_ns);

		//Property RdfsEquivalentClass = m.createProperty(RDFS.getURI() + "equivalentClass");
		
		// Cr�ation des classes 
		Artist = m.createResource(ns + "Artist"); //Artist.class.getName());
		MusicGroup = m.createResource(ns + "MusicGroup");
		EventLocation = m.createResource(ns + "EventLocation");
		MusicEvent = m.createResource(ns + "MusicEvent");
		// SKOS concept ?
		MusicalGenre = m.createResource(ns + "MusicalGenre");

		m.add(Artist, OWL.equivalentClass, MO.MusicArtist);
		m.add(Artist, RDFS.subClassOf, FOAF.Agent);
		m.add(MusicGroup, OWL.equivalentClass, MO.MusicGroup);
		m.add(MusicGroup, RDFS.subClassOf, FOAF.Group);
		m.add(MusicEvent, OWL.equivalentClass, Namespaces.schema_ns + "MusicEvent");
		m.add(MusicEvent, RDFS.subClassOf, Namespaces.lode_ns + "MusicEvent");
		m.add(EventLocation, RDFS.subClassOf, Namespaces.geo_ns + "Feature");

		// Propri�t�s
		hasSubEvent = m.createProperty(ns + "hasSubEvent");
		hasPerformer = m.createProperty(ns + "hasPerformer");
		takesPlace = m.createProperty(ns + "takesPlace");
		dateBegin = m.createProperty(ns + "dateBegin");
		dateEnd = m.createProperty(ns + "dateEnd");
		hasForMusicalGenre = m.createProperty(ns + "hasForMusicalGenre");
		//aPourGenre.addDomain(MusicalGenre);  en OntMdel only
		hasForMember = m.createProperty(ns + "hasForMember");
		// Autres
		hasForWebsite = m.createProperty(Namespaces.foaf_ns + "homepage");
		hasForImage = m.createProperty(Namespaces.foaf_ns + "img");
		latitude = m.createProperty(Namespaces.gps_ns + "latitude");
		longitude = m.createProperty(Namespaces.gps_ns + "longitude");
		street = m.createProperty(Namespaces.address_ns + "streetAddress");
		city = m.createProperty(Namespaces.address_ns + "addressLocality");
		postalCode = m.createProperty(Namespaces.address_ns + "postalCode");
		country = m.createProperty(Namespaces.address_ns + "addressCountry");

		return m;
	}
	
	public static Model getModel() {
		return m;
	}

	public static String getUri() {
		return Namespaces.meo_ns;
	}

	public static Property getProperty(String pName) {
		return new PropertyImpl(getUri(), pName); 
	}
	
	public static Resource getResource(String pName) {
		return new ResourceImpl(getUri(), pName); 
	}
	
	private Individual CreateInstance(OntModel m, OntClass cls, String name) {
        return m.createIndividual(getUri() + name, cls);
    }
	
    private void ShowClassProperties(OntClass c) {
        System.out.println("Class " + c.getURI());
        for (ExtendedIterator<OntProperty> i = c.listDeclaredProperties(); i.hasNext();) {
            System.out.println(" .. has property " + i.next());
        }
    }

    private void ShowInstanceProperties(OntResource r, OntClass c) {
        System.out.println("Instance " + r.getURI() + " has properties: ");
        for (ExtendedIterator<OntProperty> i = c.listDeclaredProperties(true); i.hasNext();) {
            OntProperty p = i.next();
            System.out.println(p.getURI() + " ==> " + r.getPropertyValue(p));
        }
    }
    
    
     * Ajout d'un artiste dans le mod�le
     * 
     * @author S�bastien Paradis
     
    public void addArtist(JsonArtist artist) {
    	String ns = Namespaces.meo_ns;
    	
    	Resource rArtist = m.createResource(ns + artist.getName());
		rArtist.addProperty(RDF.type, this.MusicGroup);
		rArtist.addProperty(RDFS.label, artist.getName());
		//rArtist.addProperty(this.hasForMusicalGenre, );
		rArtist.addProperty(FOAF.homepage, "");
		rArtist.addProperty(FOAF.img, "");
		
		if (!artist.getMembers().isEmpty()) {
			for (JsonArtist a : artist.getMembers()) {
				Resource rMember = m.createResource(ns + a.getName());

				rMember.addProperty(RDF.type, this.MusicGroup);
				rMember.addProperty(FOAF.nick, a.getName());
				
				rArtist.addProperty(this.hasForMember, rMember);
			}
		}
		
		 * 
        Literal l_title = m.createTypedLiteral(l.l_title, XSDDatatype.XSDstring) ;
    	m.add(livre,DC.title,l_title);
    	m.add(livre,RDF.type,Book);
        if(l.subject!=null){
            Resource infoConcept = m.createResource(l.subject);
            m.add(livre,DC.subject,infoConcept);
        }
        
		 
	}
	

    public void addArtists(ArrayList<JsonArtist> artists){
        for (JsonArtist a : artists) {
            this.addArtist(a);
        }
    }
	
	// http://web-semantique.developpez.com/faq/?page=jena#jena-owl	
	public static void main (String args[]) {
		//new MusicEventsOntology().run();
		MusicEventsOntology meo = new MusicEventsOntology();
		
		<meo:EvenementMusical rdf:about="http://fr.wikipedia.org/wiki/Garance_Reggae_Festival"> 
	    <rdfs:label>Garance Reggae Festival</rdfs:label>   
	    <meo:dateDebut>2013-07-24</meo:dateDebut>
	    <meo:dateFin>2013-07-27</meo:dateFin>
	    <foaf:homepage>http://www.garancereggaefestival.com/</foaf:homepage>
	    <meo:seDerouleA rdf:resource="#Parc_Arthur_Rimbaud" />
	    <meo:seComposeDe rdf:resource="#Garance_Reggae_Festival_2013_j1" />
	    <meo:seComposeDe rdf:resource="#Garance_Reggae_Festival_2013_j2" />
	    <meo:seComposeDe rdf:resource="#Garance_Reggae_Festival_2013_j3" />
	    <meo:seComposeDe rdf:resource="#Garance_Reggae_Festival_2013_j4" />
	  </meo:EvenementMusical>
	  
	  <meo:EvenementMusical rdf:about="#Garance_Reggae_Festival_2013_j1">    
	    <dc:date>2013-07-24</dc:date>
	    <meo:aPourParticipant rdf:resource="http://fr.wikipedia.org/wiki/Chinese_Man" />
	  </meo:EvenementMusical>
	  
		JsonArtist ja = new JsonArtist("IAM");
		meo.addArtist(ja);
		
		ja = new JsonArtist("Muse");
		meo.addArtist(ja);
		
		
		 * 

    	GroupeMusique mb1 = new GroupeMusique("Wu-Tang Clan", 1989);
    	GroupeMusique mb2 = new GroupeMusique("Rage Against The Machine", 1992);
    	GroupeMusique mb3 = new GroupeMusique("Beastie Boys", 1987);
    	
    	Artist m1 = new Artist("Russell", "Jones", "Ol' Dirty Bastard");
    	Artist m2 = new Artist("Robert", "Diggs", "RZA", "DJ");
    	Artist m3 = new Artist("Gary", "Grice", "GZA");
    	Artist m4 = new Artist("Russell", "Jones", "Ol' Dirty Bastard");
    	Artist m5 = new Artist("Clifford", "Smith", "Method Man");
    	Artist m6 = new Artist("Dennis", "Coles", "Ghostface Killah");
    	Artist m7 = new Artist("Corey", "Woods", "Raekwon");
    	mb1.addMember(m1);
    	mb1.addMember(m2);
    	mb1.addMember(m3);
    	mb1.addMember(m4);
    	mb1.addMember(m5);
    	mb1.addMember(m6);
    	mb1.addMember(m7);
    	
    	gm.AddMusicBand(mb1);
    	gm.AddMusicBand(mb2);
    	gm.AddMusicBand(mb3);
    	
    	gm.m.write(System.out, "N3");
       
    	
        try {      
        	FileOutputStream os = new FileOutputStream("out/groupes.rdf");
        	gm.m.write(os, "RDF/XML");
        	os.close();
	    }
        catch (FileNotFoundException e) {System.out.println("File not found");}
	    catch (IOException e) {System.out.println("IO problem");}
		 
	}

}
*/