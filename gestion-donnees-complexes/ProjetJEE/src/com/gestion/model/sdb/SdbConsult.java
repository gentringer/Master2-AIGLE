package com.gestion.model.sdb;
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

public class SdbConsult {
	
	public static final String NL = System.getProperty("line.separator") ;

    static public void main(String...argv)
    {
    	String geon ="http://rdf.insee.fr/geo/";
		String prolog_e = "PREFIX vocab: <"+geon+">" ;
		String prolog_r = "PREFIX rdf: <"+RDF.getURI()+">" ;
    	
		String queryString = prolog_e + NL + prolog_r + NL +
				"SELECT ?s ?code ?nom where {" +
				"?s vocab:code_region ?code ." +
				"?s vocab:nom ?nom"+
				"}";
    		
    	queryString = "SELECT * { ?s ?p ?o }" ;
        Query query = QueryFactory.create(queryString) ;
        
        StoreDesc storeDesc = new StoreDesc(LayoutType.LayoutTripleNodesHash, DatabaseType.Oracle) ;
        
        JDBC.loadDriverOracle() ;    // Multiple choices for Derby - load the embedded driver
        String jdbcURL = "jdbc:oracle:thin:@localhost:1521:xe"; 
        
        // Passing null for user and password causes them to be extracted
        // from the environment variables SDB_USER and SDB_PASSWORD
        SDBConnection conn = new SDBConnection(jdbcURL, "gentringer", "959426") ; 
        // Make store from connection and store description. 
        Store store = SDBFactory.connectStore(conn, storeDesc) ;
      
        Dataset ds = DatasetStore.create(store) ;
        QueryExecution qe = QueryExecutionFactory.create(query, ds) ;
        try {
            ResultSet rs = qe.execSelect() ;
            ResultSetFormatter.out(rs) ;
        } finally { qe.close() ; }
        store.close() ;
    }

}
