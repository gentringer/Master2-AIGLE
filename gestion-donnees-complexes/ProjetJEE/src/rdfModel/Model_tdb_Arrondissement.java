package rdfModel;

import com.gestion.model.tdb.CreationArrondissement;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.tdb.TDBFactory;

public class Model_tdb_Arrondissement {
	
	
	
	public static Model tdbModel(){
		
		CreationArrondissement createarrondissements = new CreationArrondissement();
		createarrondissements.createarrondissement();
		
		
		String directory = "/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/ProjetJEE/" +
				"WebContent/tdb/arrondissement-database" ;
        Dataset ds = TDBFactory.createDataset(directory) ;
        Model arrondissement_model = ds.getNamedModel("arrondissements"); 

		
		
		
		
		return arrondissement_model;
		
		
	}

}
