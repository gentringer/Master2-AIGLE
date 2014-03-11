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

import com.gestion.model.geonamesapi.HttpGeonames;
import com.gestion.model.neo4j.CreateNeo4j;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;

public class RDFQueryServlet extends HttpServlet {
	public static final String PATH ="/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/ProjetJEE/files/";

	/**
	 * 
	 */
	private static final long serialVersionUID = -3313637566220550982L;

	public static Model model;
	ArrayList<String> sources;

	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{


		System.out.println("posted query");
		String querystring = request.getParameter("query");

		String selectposter = request.getLocalAddr();
		model = RDFModelServlet.model;
		String NL = System.getProperty("line.separator") ;

		String [] prefixes = querystring.split("///");
		querystring=querystring.replaceAll("///", NL);
		/*StringBuilder stringBuilder = new StringBuilder();
		for(int i =0;i<prefixes.length;i++){
			System.out.println(prefixes[i]);
			stringBuilder.append(prefixes[i]);
			//stringBuilder.append(NL);
		}
		String querystring =stringBuilder.toString();*/
		System.out.println(querystring);


		System.out.println("model size for query"+model.size());




		Query query = QueryFactory.create(querystring) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, model) ;

		try {
			ResultSet results = qexec.execSelect() ;
			//			List<QuerySolution> res;
			//
			//			res = ResultSetFormatter.toList(results);    
			//			for(QuerySolution q : res){
			//				System.out.println(q.get("ucase").toString());
			//			}
			/*	if(RDFModelServlet.geonames=true){
				HttpGeonames http = new HttpGeonames();


				System.out.println("geonames selected");
				List<QuerySolution> res;

				res = ResultSetFormatter.toList(results);    

				System.out.println(res.size());
				String path="/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/ProjetJEE/WebContent/geonames-data/";
				String directory = "/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/ProjetJEE/WebContent/tdb/geonames-departements" ;

				Dataset ds = TDBFactory.createDataset(directory) ;
				Model reg = ds.getNamedModel( "geonamesdepartements" );      
				for(QuerySolution q : res){
					if(q.get("nomDepartement")!=null){
						String nom = q.get("nomDepartement").toString();
						String [] nomsplit =nom.split("@");
						nom = nomsplit[0];
						System.out.println(nom);
						nom = containsWhiteSpace(nom);
						http.search(nom);
						FileManager.get().readModel(reg, path+nom+"http.rdf");
					}

					System.out.println("modsize"+reg.size());
		           Model modeltdb = ds.getNamedModel("geonamesdepartements");
		           System.out.println("modeltdb"+modeltdb.size());

				}
				reg.close();
				ds.close();
			}*/




			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			ResultSetFormatter.outputAsJSON(response.getOutputStream(),results);

			qexec.close() ;
			model.close();

		}finally {
			qexec.close();
		}

	}


	public static String containsWhiteSpace(String testCode){
		if(testCode != null){
			System.out.println(testCode);

			for(int i = 0; i < testCode.length(); i++){
				if(Character.isWhitespace(testCode.charAt(i))){


					StringBuilder test = new StringBuilder(testCode);
					test.setCharAt(i,'+');
					testCode=test.toString();
					System.out.println("found white space");
				}
			}
		}
		System.out.println(testCode);
		return testCode;

	}



}
