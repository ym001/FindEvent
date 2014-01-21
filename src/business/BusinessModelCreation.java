package business;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.FileManager;
import Tools.Tools;

public class BusinessModelCreation {

    public static Model getModel()
    {
    	String rdf_file = "file:"+Tools.getBusinessRdfPath();
    	Model model = ModelFactory.createDefaultModel();
        FileManager.get().readModel( model, rdf_file );
        return model;
    }
    
}
