package tdb.create;


import java.util.ArrayList;

import hbase.csv.ReadCSV_Communes;
import hbase.csv.ReadCSV_Impots;

public class Execute {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		// CreateNeo4j hello = new CreateNeo4j();
		// hello.createDb();
		// hello.removeData();
	  //  hello.shutDown();
		
		CreationGeonames creation = new CreationGeonames();
		creation.CreationGeonamesOntology();
		

		CreationINSEE creationinsee = new CreationINSEE();
		creationinsee.CreationINSEEOntology();
		
		CreationArrondissement createarrondissements = new CreationArrondissement();
		createarrondissements.createarrondissement();
		
		CreationCantons createcantons = new CreationCantons();
		createcantons.createcantons();
	}

	
	   

}
