package rdfModel;

import com.gestion.model.tdb.CreationArrondissement;
import com.gestion.model.tdb.CreationCantons;
import com.gestion.model.tdb.CreationDepartements;
import com.gestion.model.tdb.CreationRegions;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.tdb.TDBFactory;

public class Model_tdb_Regions {
	
	
	
	public static Model tdbModel(){
		
		CreationRegions createdepartements = new CreationRegions();
		createdepartements.createmodel();		
		
		String directory = "/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/" +
				"ProjetJEE/WebContent/tdb/regions-database" ;
        Dataset ds = TDBFactory.createDataset(directory) ;
        Model regionmodel = ds.getNamedModel("regions"); 

		
		return regionmodel;
		
		
	}

}
