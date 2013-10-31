import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
//import com.hp.hpl.jena.vocabulary.RDFS;
public class WriteRDFXML 
{	
	private static Model userModel = ModelFactory.createDefaultModel();
	public static String userURI;
	public static String name;
	public static String title;
	public static String givenName;
	public static String familyName;
	public static String nick;
	public static String mbox;
	public static String person1;
	public static String person2;
	public static String person3;

	public static Model writeRessource()
	{
	userModel.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
	//model.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
	userModel.setNsPrefix("foaf", FOAF.NS);
	Resource ressourceUser = userModel.createResource(userURI, userModel.createResource(userModel.expandPrefix("foaf:Person")));	
	ressourceUser.addProperty(FOAF.name, name);
	ressourceUser.addProperty(FOAF.title, title);
    ressourceUser.addProperty(FOAF.givenname, givenName);
    ressourceUser.addProperty(FOAF.family_name, familyName);
    ressourceUser.addProperty(FOAF.mbox, mbox);
    ressourceUser.addProperty(FOAF.nick, nick);
    ressourceUser.addProperty(FOAF.knows, person1);
    ressourceUser.addProperty(FOAF.knows, person2);
    ressourceUser.addProperty(FOAF.knows, person3);
    return userModel;
	}
	
	
	public static void main(String args[])
	{
	// some definitions
	userURI    = "http://somewhere/YvesMercadier";
	//userURI="toto";
	name    = "Yves Mercadier";
	title    = "Mr.";
	givenName    = "Yves";
	familyName   = "Mercadier";
	nick   = "ym001";
	mbox   = "mailto:yves.mercadier@gmail.com";
	person1   = "Sebastien Paradis";
	person2   = "Chenyang Gao";
	person3   = "Yassine Motie";
	
	writeRessource();
	userModel.write(System.out, "RDF/XML-ABBREV");

}
}	