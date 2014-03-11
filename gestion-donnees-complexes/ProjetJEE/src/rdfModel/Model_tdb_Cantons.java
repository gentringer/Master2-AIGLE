package rdfModel;

import com.gestion.model.tdb.CreationArrondissement;
import com.gestion.model.tdb.CreationCantons;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.tdb.TDBFactory;

public class Model_tdb_Cantons {
	
	
	
	public static Model tdbModel(){
		
		CreationCantons createcantons = new CreationCantons();
		createcantons.createcantons();		
		
		String directory = "/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/ProjetJEE/" +
				"WebContent/tdb/cantons-database" ;
        Dataset ds = TDBFactory.createDataset(directory) ;
        Model arrondissement_model = ds.getNamedModel("cantons"); 

		
		
		
		
		return arrondissement_model;
		
		
	}

}
