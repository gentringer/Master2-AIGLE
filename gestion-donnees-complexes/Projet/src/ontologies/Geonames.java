package ontologies;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDFS;

public class Geonames {

	public static void create(){

		Model inseeModel = ModelFactory.createOntologyModel();
		FileManager.get().readModel(inseeModel, "/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/ProjetJEE/WebContent/ontologies/insee-cog-2006.rdf");

		Model geonamesModel = ModelFactory.createOntologyModel();
		FileManager.get().readModel(geonamesModel, "/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/ProjetJEE/WebContent/ontologies/geonames.rdf");

		
		
		String prefixInsee =inseeModel.getNsPrefixURI("geo");
		
		String prefixGeonames =geonamesModel.getNsPrefixURI("gn");
		
		Resource Departement = inseeModel.getResource(prefixInsee+"Departement");

		Resource adm2 = geonamesModel.getResource(prefixGeonames+"A.ADM2");
		
		geonamesModel.createResource(Departement);
		inseeModel.setNsPrefix("gn", prefixGeonames);
		inseeModel.createResource(adm2);
		
		inseeModel.add(Departement,RDFS.subClassOf,adm2);
		
		try {
			FileOutputStream ost = new FileOutputStream("out.rdf");
			//
			//generalmodel.write(System.out, "RDF/XML-ABBREV");
			inseeModel.write(ost, "RDF/XML-ABBREV" ); }
		catch (FileNotFoundException e) {
			System.out.println("pb de fichier");
		}

	}
}
