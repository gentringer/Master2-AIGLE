package rdfModel;

import com.gestion.model.tdb.CreationArrondissement;
import com.gestion.model.tdb.CreationCantons;
import com.gestion.model.tdb.CreationDepartements;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.tdb.TDBFactory;

public class Model_tdb_Departements {
	
	
	
	public static Model tdbModel(){
		
		CreationDepartements createdepartements = new CreationDepartements();
		createdepartements.createmodel();		
		
		String directory = "/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/ProjetJEE/WebContent/tdb/departements-database" ;
        Dataset ds = TDBFactory.createDataset(directory) ;
        Model arrondissement_model = ds.getNamedModel("departements"); 

		
		return arrondissement_model;
		
		
	}

}
