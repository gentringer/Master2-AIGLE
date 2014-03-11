package sdb;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.Store;
import com.hp.hpl.jena.sdb.store.StoreFactory;
import com.hp.hpl.jena.util.FileManager;

public class SdbCreation {
	
	
	static public void main(String...argv)
    {
        Store store = StoreFactory.create("sdb-mysql.ttl") ;
        Model model = SDBFactory.connectDefaultModel(store) ;
     // creation des tables et index pour le SDB store.
        store.getTableFormatter().truncate();
        store.getTableFormatter().create();
        Dataset ds = SDBFactory.connectDataset(store);
        
        FileManager.get().readModel(model, "popleg-2011.ttl");
        System.out.println(model.size());
      //  System.out.println("size: "+model.size());
        ds.getDefaultModel().add(model);
//        StmtIterator sIter = model.listStatements() ;
//        for ( ; sIter.hasNext() ; )
//        {
//            Statement stmt = sIter.nextStatement() ;
//            System.out.println(stmt) ;
//        }
//        sIter.close() ; 
        store.close() ;
    }

}
