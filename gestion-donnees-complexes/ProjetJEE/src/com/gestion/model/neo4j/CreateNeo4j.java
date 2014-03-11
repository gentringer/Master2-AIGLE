package com.gestion.model.neo4j;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;



public class CreateNeo4j {

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

	public CreateNeo4j(){

	}
	// END SNIPPET: vars

	// START SNIPPET: createReltype
	private static enum RelTypes implements RelationshipType
	{
		BELONGSTO
	}
	// END SNIPPET: createReltype

	/*public static void main( final String[] args )
    {
        EmbeddedNeo4j hello = new EmbeddedNeo4j();
        hello.createDb();
        hello.removeData();
        hello.shutDown();
    }*/

	public void createDb()
	{

		clearDb();
		// START SNIPPET: startDb
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );
		registerShutdownHook( graphDb );
		// END SNIPPET: startDb

		// START SNIPPET: transaction
		Transaction tx = graphDb.beginTx();
		try
		{
			regions.remove(0);
			for(String[] reg : regions){
				// Database operations go here
				// END SNIPPET: transaction
				// START SNIPPET: addData
				regionNode = graphDb.createNode();
				regionNode.setProperty("region", reg[3]);
				regionNode.setProperty("coderegion", reg[0]);
				regionNode.setProperty("cheflieu", reg[1]);
				regionNode.setProperty("tncc", reg[2]);

				for(String [] dep: departements){
					if(dep[0].equals(reg[0])){

						departementNode = graphDb.createNode();
						departementNode.setProperty( "departement", dep[4] );
						departementNode.setProperty( "coderegion", dep[0] );
						departementNode.setProperty( "codedepartement", dep[1] );
						departementNode.setProperty( "cheflieudepart", dep[2] );
						departementNode.setProperty( "tnccdepart", dep[3] );

						//String dx = (String)firstNode.getProperty("departement");
						//System.out.println("dx "+dx);
						relationship = departementNode.createRelationshipTo(regionNode, RelTypes.BELONGSTO );
						relationship.setProperty( "appartient", "fait partie de" );

						// END SNIPPET: addData
						Iterable<Relationship> test; 
						test = regionNode.getRelationships();

						for(Relationship tes : test){
							System.out.println(0);
							Node nod = tes.getEndNode();

							//System.out.println(nod.getProperty( "region" ));
							//System.out.println(tes.getNodes()[0].toString());
						}

						Iterable<Relationship> test2; 
						test2 = departementNode.getRelationships();

						for(Relationship tes : test2){

							Node nod = tes.getStartNode();

							System.out.println(nod.getProperty( "departement" ));
							//System.out.println(tes.getNodes()[0].toString());
						}

						// START SNIPPET: readData
						System.out.println("firstNode : " +  departementNode.getProperty( "departement" ) );
						System.out.println("relationship : " +  relationship.getProperty( "appartient" ) );
						System.out.println("secondNode : " +  regionNode.getProperty( "region" ) );
						// END SNIPPET: readData

						greeting = ( (String) departementNode.getProperty( "departement" ) )
								+ ( (String) relationship.getProperty( "appartient" ) )
								+ ( (String) regionNode.getProperty( "region" ) );

						System.out.println(greeting);
						// START SNIPPET: transaction

					}
				}
				tx.success();
			}

		}
		finally
		{
			tx.finish();
		}
		// END SNIPPET: transaction
	}

	private void clearDb()
	{
		try
		{
			FileUtils.deleteRecursively( new File( DB_PATH ) );
		}
		catch ( IOException e )
		{
			throw new RuntimeException( e );
		}
	}

	void removeData()
	{
		Transaction tx = graphDb.beginTx();
		try 
		{
			// START SNIPPET: removingData
			// let's remove the data
			//regionNode.getSingleRelationship( RelTypes.BELONGSTO, Direction.OUTGOING ).delete();
			//regionNode.delete();
			//departementNode.delete();
			// END SNIPPET: removingData

			tx.success();
		}
		finally
		{
			tx.finish();
		}
	}

	public void shutDown()
	{
		System.out.println();
		System.out.println( "Shutting down database ..." );
		// START SNIPPET: shutdownServer
		graphDb.shutdown();
		// END SNIPPET: shutdownServer
	}

	// START SNIPPET: shutdownHook
	private static void registerShutdownHook( final GraphDatabaseService graphDb )
	{
		// Registers a shutdown hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
		// running application).
		Runtime.getRuntime().addShutdownHook( new Thread()
		{
			@Override
			public void run()
			{
				graphDb.shutdown();
			}
		} );
	}
	// END SNIPPET: shutdownHook

}
