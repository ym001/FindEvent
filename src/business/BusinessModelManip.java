package business;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import Tools.Tools;


import WsSem.model.JsonBusinessObject;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;

public class BusinessModelManip{
	
	public static void addBusinessObject(JsonBusinessObject obj){
		try {

			String rdf_file = "file:"+Tools.getBusinessRdfPath();
			Model model = ModelFactory.createDefaultModel();
			FileManager.get().readModel( model, rdf_file );

			String meo = model.getNsPrefixURI("meo");
			String oa = model.getNsPrefixURI("oa");
			String gps = model.getNsPrefixURI("gps");
			String foaf = model.getNsPrefixURI("foaf");

			Resource newDataBusiness;
			Property tagg, annotation, numAnno, EvenementMusical; //meo
			Property annotatedAt;
			Property lat, lgt; //gps
			Property name, mail; //foaf

			//Count num of resources and then add by 1
			int count = 0;
			Resource xx = model.getResource( meo+"DataBusiness");
	    	ResIterator res_i = model.listSubjectsWithProperty( RDF.type, xx);
	    	while (res_i.hasNext())
	    	{ Resource s = res_i.nextResource();
	    		count++;
	    	}
	    	int numAnnotation = count+1;
			
	    	
			newDataBusiness = model.createResource(meo+""+(numAnnotation));
			numAnno = model.createProperty(meo+"numAnno");
			tagg = model.createProperty(meo+"tagg");
			annotation = model.createProperty(meo+"annotation");
			EvenementMusical = model.createProperty(meo+"EvenementMusical");
			annotatedAt = model.createProperty(oa+"annotatedAt");
			lat = model.createProperty(gps+"lat");
			lgt = model.createProperty(gps+"lgt");
			name = model.createProperty(foaf+"name");
			mail = model.createProperty(foaf+"mail");

			model.add(newDataBusiness, RDF.type, model.getResource(meo+"DataBusiness"));
			model.add(newDataBusiness, numAnno, Integer.toString(numAnnotation));
			model.add(newDataBusiness, tagg, Tools.nullToString(obj.getTag()));
			model.add(newDataBusiness, annotation, Tools.nullToString(obj.getAnnotation()));
			model.add(newDataBusiness, EvenementMusical, Tools.nullToString(obj.getEvent()));
			model.add(newDataBusiness, annotatedAt, Tools.nullToString(obj.getDate()));
			model.add(newDataBusiness, lat, Tools.nullToString(obj.getLat()));
			model.add(newDataBusiness, lgt, Tools.nullToString(obj.getLgt()));
			model.add(newDataBusiness, name, Tools.nullToString(obj.getNom()));
			model.add(newDataBusiness, mail, Tools.nullToString(obj.getMail()));

			
			//Write into rdf file
			File out_file = new File(Tools.getBusinessRdfPath());
			FileOutputStream ost = new FileOutputStream(out_file);
			//model.write(System.out, "RDF/XML-ABBREV");
			model.write(ost, "RDF/XML-ABBREV" ); 
			ost.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
	}


}
