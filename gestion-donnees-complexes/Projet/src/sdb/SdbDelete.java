package sdb;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.Store;
import com.hp.hpl.jena.sdb.StoreDesc;
import com.hp.hpl.jena.sdb.sql.JDBC;
import com.hp.hpl.jena.sdb.sql.SDBConnection;
import com.hp.hpl.jena.sdb.store.DatabaseType;
import com.hp.hpl.jena.sdb.store.DatasetStore;
import com.hp.hpl.jena.sdb.store.LayoutType;
import com.hp.hpl.jena.vocabulary.RDF;

public class SdbDelete {
	
	public static final String NL = System.getProperty("line.separator") ;

    static public void main(String...argv)
    {
    	
        
        StoreDesc storeDesc = new StoreDesc(LayoutType.LayoutTripleNodesHash, DatabaseType.Oracle) ;
        
        JDBC.loadDriverOracle() ;    // Multiple choices for Derby - load the embedded driver
        String jdbcURL = "jdbc:oracle:thin:@localhost:1521:xe"; 
        
        // Passing null for user and password causes them to be extracted
        // from the environment variables SDB_USER and SDB_PASSWORD
        SDBConnection conn = new SDBConnection(jdbcURL, "gentringer", "959426") ; 
        // Make store from connection and store description. 
        Store store = SDBFactory.connectStore(conn, storeDesc) ;
        
        store.getTableFormatter().truncate();
        
      
       // Dataset ds = DatasetStore.create(store) ;
       
        store.close() ;
    }

}
