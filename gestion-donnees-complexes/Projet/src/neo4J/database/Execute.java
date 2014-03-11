package neo4j.database;


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

//		 CreateNeo4j hello = new CreateNeo4j();
//		 hello.createDb();
//	//	 hello.removeData();
//	    hello.shutDown();
		ConsultNeo4J consult = new ConsultNeo4J();
		for(Region reg :ConsultNeo4J.consultDbRegions()){
			System.out.println("region" +reg.nom);
		}
		
		for(Departement dep :ConsultNeo4J.consultdbDepartement()){
			System.out.println(dep.nom);
		}

	}

	
	   

}
