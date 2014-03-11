package com.gestion.model.tdb;


import java.util.ArrayList;

import com.gestion.model.hbase.csv.ReadCSV_Communes;


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
