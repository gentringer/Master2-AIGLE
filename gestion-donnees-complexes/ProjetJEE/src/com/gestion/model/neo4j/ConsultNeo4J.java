package com.gestion.model.neo4j;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.helpers.collection.IteratorUtil;


public class ConsultNeo4J {

	private static final String DB_PATH = "/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/ProjetJEE/files/neo4j-database/regionsDepartements";
	public static ArrayList<String[]> departements = ReadCSV_Departements.run();
	public static ArrayList<String[]> regions = ReadCSV_Regions.run();
	public String greeting;

	// START SNIPPET: vars
	GraphDatabaseService graphDb;
	Node regionNode;
	Node departementNode;


	Relationship relationship;
	Relationship relationship2;

	public ConsultNeo4J(){

	}
	// END SNIPPET: vars

	// START SNIPPET: createReltype
	private static enum RelTypes implements RelationshipType
	{
		BOLEONGSTO
	}
	// END SNIPPET: createReltype

	/*public static void main( final String[] args )
    {
        EmbeddedNeo4j hello = new EmbeddedNeo4j();
        hello.createDb();
        hello.removeData();
        hello.shutDown();
    }*/

	public void consultDb()
	{

		// START SNIPPET: startDb
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );
		//registerShutdownHook( graphDb );
		// END SNIPPET: startDb

		//ExecutionEngine engine = new ExecutionEngine( graphDb );

		//ExecutionResult result;


		// START SNIPPET: transaction
		Transaction tx = graphDb.beginTx();
		String nodeResult;
		String rows = "";
		String resultString;
		String columnsString;
		try
		{
			Iterable<Node> nodes = graphDb.getAllNodes();

			for(Node node : nodes){
				//System.out.println(node.toString());
				if(node.hasProperty("region")){
					//System.out.println(node.getProperty("coderegion"));
					//System.out.println(node.getProperty("region"));
				}
				else if(node.hasProperty("departement")){
					if(node.getProperty("departement").equals("HERAULT")){
						System.out.println(node.getProperty("departement"));
						System.out.println(node.getProperty("cheflieudepart"));

					}

				}
			}


			ExecutionEngine engine = new ExecutionEngine( graphDb );

			ExecutionResult result;
			try 
			{
				result = engine.execute( "start n=node(*) return n" );
				// END SNIPPET: execute
				// START SNIPPET: items
				Iterator<Node> n_column = result.columnAs( "n" );
				for ( Node node : IteratorUtil.asIterable( n_column ) )
				{
					// note: we're grabbing the name property from the node,
					// not from the n.name in this case.
					if(node.hasProperty("region")){
						nodeResult = node + ": " + node.getProperty( "region" );
						// System.out.println(nodeResult);
					}
				}
				// END SNIPPET: items
			}
			finally
			{

			}

			// START SNIPPET: columns
			List<String> columns = result.columns();
			// END SNIPPET: columns

			// the result is now empty, get a new one
			result = engine.execute( "start n=node(*) return n" );
			// START SNIPPET: rows
			for ( Map<String, Object> row : result )
			{
				for ( Entry<String, Object> column : row.entrySet() )
				{
					rows += column.getKey() + ": " + column.getValue() + "; ";
				}
				rows += "\n";
				//     System.out.println(rows);
			}
			// END SNIPPET: rows
			resultString = engine.execute( "start n=node(*) return n" ).dumpToString();
			columnsString = columns.toString();
			graphDb.shutdown();

			graphDb.shutdown();
		}
		finally
		{
			tx.finish();
		}
		// END SNIPPET: transaction
	}


	public static List<Region> consultDbRegions()
	{

		GraphDatabaseService graphDb2 = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );

		ArrayList<Region> listRegion = new ArrayList<Region>();

		//Transaction tx = graphDb2.beginTx();

		ExecutionEngine engine = new ExecutionEngine( graphDb2 );

		ExecutionResult result;
		try 
		{
			result = engine.execute( "start n=node(*) return n" );
			// END SNIPPET: execute
			// START SNIPPET: items
			Iterator<Node> n_column = result.columnAs( "n" );
			for ( Node node : IteratorUtil.asIterable( n_column ) )
			{
				// note: we're grabbing the name property from the node,
				// not from the n.name in this case.
				if(node.hasProperty("region")){
					
					String nom = node.getProperty("region").toString();
					System.out.println(nom);
					String coderegion = node.getProperty("coderegion").toString();
					String cheflieu = node.getProperty("cheflieu").toString();
					String tncc = node.getProperty("tncc").toString();

					Region reg = new Region(nom, coderegion, cheflieu, tncc);
					listRegion.add(reg);
				}
			}
			// END SNIPPET: items
		}
		finally
		{
			graphDb2.shutdown();
		}
	

		return listRegion;
		// END SNIPPET: transaction
	}

	
	public static List<Departement> consultdbDepartement()
	{

		GraphDatabaseService graphDb2 = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );

		ArrayList<Departement> listDepartement = new ArrayList<Departement>();

		//Transaction tx = graphDb2.beginTx();

		ExecutionEngine engine = new ExecutionEngine( graphDb2 );

		ExecutionResult result;

		try 
		{
			result = engine.execute( "start n=node(*) return n" );
			// END SNIPPET: execute
			// START SNIPPET: items
			Iterator<Node> n_column = result.columnAs( "n" );
			for ( Node node : IteratorUtil.asIterable( n_column ) )
			{
				if(node.hasProperty("departement")){
					String nom = node.getProperty("departement").toString();
					String coderegion = node.getProperty("coderegion").toString();
					String codeDepartement = node.getProperty("codedepartement").toString();
					String cheflieu = node.getProperty("cheflieudepart").toString();
					String tnccdepartement = node.getProperty("tnccdepart").toString();

					Departement dep = new Departement(nom, coderegion, codeDepartement, cheflieu, tnccdepartement);
					listDepartement.add(dep);
				}
			}
		
		// END SNIPPET: items
	}
	finally
	{
		graphDb2.shutdown();
	}


	return listDepartement;
		// END SNIPPET: transaction
	}


}
