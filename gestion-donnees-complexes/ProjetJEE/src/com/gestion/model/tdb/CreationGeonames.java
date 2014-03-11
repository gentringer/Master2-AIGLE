package com.gestion.model.tdb;
import com.hp.hpl.jena.query.Dataset ;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.tdb.store.DatasetGraphTDB;

import java.util.*;

import com.hp.hpl.jena.util.FileManager;

public class CreationGeonames
{
	public static final String rdf_file_ontology = "geonames_v3.rdf";
	public static final String rdf_herault = "herault.rdf";
	public static final String rdf_gironde = "gironde.rdf";


    public void CreationGeonamesOntology()
    {
        // Make a TDB-back Jena model in the named directory.
        String directory = "/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/Projet/tdb/Geonames-Database" ;
        Dataset ds = TDBFactory.createDataset(directory) ;
        Model reg = ds.getNamedModel( "geonamesdatabase" );       
        FileManager.get().readModel( reg, rdf_file_ontology );
        FileManager.get().readModel( reg, rdf_herault );        
        FileManager.get().readModel( reg, rdf_gironde );

        Model	model = null;     
        Iterator<String> graphNames = ds.listNames();
        while (graphNames.hasNext()) {
            String graphName = graphNames.next();       
            model = ds.getNamedModel(graphName);
      		System.out.println("Named graph with Ontology " + graphName + " size: " + model.size());
       	}
        model.close();
        ds.close();
    }
    
    public void InsertionHerault()
    {
        // Make a TDB-back Jena model in the named directory.
        String directory = "/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/Projet/tdb/Geonames-Database" ;
        Dataset ds2 = TDBFactory.createDataset(directory) ;
        Model reg = ds2.getNamedModel( "geonamesdatabase" );       
        FileManager.get().readModel( reg, rdf_herault );
        Model	model2 = null;     
        Iterator<String> graphNames = ds2.listNames();
        while (graphNames.hasNext()) {
            String graphName = graphNames.next();       
            model2 = ds2.getNamedModel(graphName);
      		System.out.println("Named graph with Herault " + graphName + " size: " + model2.size());
       	}  	    
        model2.close();

        ds2.close();
    }
    
    public void InsertionGironde()
    {
        // Make a TDB-back Jena model in the named directory.
        String directory = "/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/Projet/tdb/Geonames-Database" ;
        Dataset ds = TDBFactory.createDataset(directory) ;
        Model reg = ds.getNamedModel( "geonamesdatabase" );       
        FileManager.get().readModel( reg, rdf_gironde );
        Model	model3 = null;     
        Iterator<String> graphNames = ds.listNames();
        while (graphNames.hasNext()) {
            String graphName = graphNames.next();       
            model3 = ds.getNamedModel(graphName);
      		System.out.println("Named graph with Gironde " + graphName + " size: " + model3.size());
       	}  	
        model3.close();

        ds.close();
        
    }
}