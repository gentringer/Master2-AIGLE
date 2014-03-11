package com.gestion.model.tdb;
import com.hp.hpl.jena.query.Dataset ;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.tdb.store.DatasetGraphTDB;

import java.io.File;
import java.util.*;

import com.hp.hpl.jena.util.FileManager;

public class CreationCantons
{
	

    public void createcantons()
    {
        // Make a TDB-back Jena model in the named directory.
        String directory = "/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/ProjetJEE/WebContent/tdb/cantons-database" ;
        Dataset ds = TDBFactory.createDataset(directory) ;
        Model reg = ds.getNamedModel( "cantons" ); 
        File folder = new File("/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/ProjetJEE/WebContent/rdf-data/Insee-Cantons"); 
        ArrayList<String> arronds = listFiles(folder);
        for(String arron : arronds){
        	System.out.println(arron);
            FileManager.get().readModel( reg, "/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/ProjetJEE/WebContent/rdf-data/Insee-Cantons/"+arron );        
        }
      

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
    
    public ArrayList<String> listFiles(final File folder) {
    	ArrayList<String> filesfound = new ArrayList<String>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
            	listFiles(fileEntry);
            } else {
            	filesfound.add(fileEntry.getName());
                //System.out.println(fileEntry.getName());
            }
        }
		return filesfound;
    }
    
}