package com.gestion.controller;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import rdfModel.HBase_Model_Commune;
import rdfModel.Neo4j_Model_Departements;
import rdfModel.Neo4j_Model_Regions;
import rdfModel.Model_tdb_Arrondissement;
import rdfModel.Model_tdb_Cantons;
import rdfModel.Model_tdb_Departements;
import rdfModel.Model_tdb_Regions;

import com.gestion.model.d2rq.GereateD2RQModel;
import com.gestion.model.neo4j.CreateNeo4j;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.Store;
import com.hp.hpl.jena.sdb.store.StoreFactory;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.vocabulary.RDF;

public class RDFModelServlet extends HttpServlet {
	public static final String PATH ="/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/ProjetJEE/files/";
	public static final String pathsdb = "/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/ProjetJEE/WebContent/sdb/";

	/**
	 * 
	 */
	private static final long serialVersionUID = -3313637566220550982L;

	public static Model model;
	ArrayList<String> sources;
	public static boolean geonames = false;

	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{


		System.out.println("posted");
		String[] myParams = request.getParameterValues("choices[]");
		String selectposter = request.getLocalAddr();
		model = ModelFactory.createDefaultModel(); 
		sources = new ArrayList<String>();

		for(int ii = 0; ii<myParams.length;ii++){

			String selectedsource = myParams[ii];

			System.out.println("selected source : "+ selectedsource);



			if(selectedsource.equals("tdb-Arrondissements")){

				Model mod =  Model_tdb_Arrondissement.tdbModel();
				model.add(mod);
				System.out.println("size in controller arrondissement : "+model.size());
				sources.add("tdb-Arrondissements");

			}

			if(selectedsource.equals("tdb-Cantons")){

				Model mod =  Model_tdb_Cantons.tdbModel();
				model.add(mod);
				System.out.println("size in controller Cantons : "+model.size());
				sources.add("tdb-Cantons");

			}

			if(selectedsource.equals("tdb-Departements")){
				Model mod =  Model_tdb_Departements.tdbModel();
				System.out.println(mod.size());
				model.add(mod);
				System.out.println("size in controller departements : "+model.size());
				model.setNsPrefix("insee", "http://rdf.insee.fr/geo/");

				System.out.println(model.getNsPrefixMap().size());
				sources.add("tdb-Departements");


			}

			if(selectedsource.equals("tdb-Regions")){

				Model mod =  Model_tdb_Regions.tdbModel();
				model.add(mod);
				model.setNsPrefix("insee", "http://rdf.insee.fr/geo/");

				System.out.println("size in controller Regions : "+model.size());
				sources.add("tdb-Regions");
				mod.setNsPrefix("insee", "http://rdf.insee.fr/geo/");

				System.out.println(model.getNsPrefixMap().size());


			}

			if(selectedsource.equals("hbase-Communes")){

				Model mod =  HBase_Model_Commune.Commune_Model();
				model.add(mod);
				System.out.println("size in controller hbase-Communes : "+model.size());
			
				sources.add("hbase-Communes");
			}
			if(selectedsource.equals("neo4j-Regions")){

				Model mod =  Neo4j_Model_Regions.Region_Model();
				model.add(mod);
				System.out.println("size in controller neo4j-Regions : "+model.size());
				sources.add("neo4j-Regions");
			}
			if(selectedsource.equals("neo4j-Departements")){

				Model mod =  Neo4j_Model_Departements.Departements_Model();
				model.add(mod);
				System.out.println("size in controller neo4j-Departements : "+model.size());
				sources.add("neo4j-Departements");
			}

			if(selectedsource.equals("geonames-Departements")){

				String directory = "/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/" +
						"ProjetJEE/WebContent/tdb/geonames-departements" ;

				Dataset ds = TDBFactory.createDataset(directory) ;
				Model reg = ds.getNamedModel( "geonamesdepartements" );     
				
				model.add(reg);
				System.out.println("size in controller geonames-Departements : "+model.size());
				
				sources.add("geonames-Departements");

			}

			if(selectedsource.equals("d2rq-Impots")){

				System.out.println("generate model impots");
				Model reg = GereateD2RQModel.d2rqModelImpots();
				
				model.add(reg);
				System.out.println("size in controller d2rq-impots : "+model.size());

				sources.add("d2rq-Impots");

			}
			
			if(selectedsource.equals("SDB-Population")){
				
				System.out.println("generate model sdb population");
				Store store = StoreFactory.create(pathsdb+"sdb-mysql.ttl") ;
				Model reg = SDBFactory.connectDefaultModel(store) ;
				model.add(reg);
				System.out.println("size in controller SDB-Population : "+model.size());

				sources.add("sdb-population");
			}







		}
	}



}
