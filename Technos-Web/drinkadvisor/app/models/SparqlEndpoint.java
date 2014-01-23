package models;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import play.mvc.Result;


import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.VCARD;

import controllers.routes;

public class SparqlEndpoint {
	
	public static final String NS_FOAF = "http://xmlns.com/foaf/0.1/";
	public static final String RDF_GEO = "http://www.w3.org/2003/01/geo/wgs84_pos#";
	public static final String RDF_TAGS = "http://ns.inria.fr/nicetag/2009/09/25/voc#";
	public static final String SIOG_TAG = "http://rdfs.org/sioc/ns#";
	public static final String RATING_TAG = "http://www.tvblob.com/ratings/#";
	public static final String DRINKURI ="http://localhost:9000/";

	static String pref1 = "PREFIX foaf: <"+NS_FOAF+">" ;
	static String pref2 = "PREFIX rdf: <"+RDF.getURI()+">" ;
	static String pref3 = "PREFIX rdfs: <"+RDFS.getURI()+">" ;
	static String pref4 = "PREFIX geo: <"+RDF_GEO+">" ;
	static String pref5 = "PREFIX sioc: <"+SIOG_TAG+">" ;
	static String pref6 = "PREFIX rating: <"+RATING_TAG+">" ;
	static String pref7 = "PREFIX drinkadvisor: <"+DRINKURI+">" ;

	public static void CreationTDBModel(Model mod)
	{
		System.out.println("hello");
		System.out.println(mod.size());
		// Make a TDB-back Jena model in the named directory.
		String directory = "drinkModel" ;
		
		Dataset ds = TDBFactory.createDataset(directory) ;
	
		ds.begin(ReadWrite.WRITE) ;
		Model modelold = ds.getNamedModel("allbars");
		modelold.removeAll();
		modelold.commit();
		
		ds.addNamedModel("allbars", mod);

		Model	model = null;     
		Iterator<String> graphNames = ds.listNames();
		while (graphNames.hasNext()) {
			String graphName = graphNames.next();  
			System.out.println("graphname"+graphName);
			model = ds.getNamedModel(graphName);
			System.out.println("Named graph with Ontology " + graphName + " size: " + model.size());
		}
		model.close();

		ds.commit();
		ds.end();
		ds.close();

	}



	public static String[][] executeForResult(String query) throws JSONException{

		System.out.println("query "+query);
		String directory = "drinkModel" ;
		Dataset ds = TDBFactory.createDataset(directory) ;

		ds.begin(ReadWrite.READ) ;
	

		Model	model = null;     
		Iterator<String> graphNames = ds.listNames();
		System.out.println("before while");
		while (graphNames.hasNext()) {
			String graphName = graphNames.next();       
			model = ds.getNamedModel(graphName);
			System.out.println("Named graph with Ontology " + graphName + " size: " + model.size());
		}


		String NL =  System.getProperty("line.separator") ;
		String	 queryString = pref1 + NL + pref2 + NL + pref3 + NL + pref4 + NL +
				pref5 + NL + pref6 + NL + pref7 + NL +

				query
				
				;

		System.out.println(queryString);  
		Query querfac = QueryFactory.create(queryString) ;
		QueryExecution qexec = QueryExecutionFactory.create(querfac, model) ;
		String ress ="" ;
		List<QuerySolution> res;
		String [][] resultjson ;
		try {

			ress = "";
			// ResultSetFormatter.out(System.out, rs);
			ResultSet rs = qexec.execSelect() ;
			OutputStream out = new ByteArrayOutputStream();
			//	model.write(out,"RDF/JSON");


			ResultSetFormatter.outputAsJSON(out,rs);

			JSONObject jsonobj = new JSONObject(out.toString());
			//System.out.println(out.toString());

			System.out.println(jsonobj.toString());


			JSONObject products = jsonobj.getJSONObject("head");
			JSONArray headd = products.getJSONArray("vars");


			JSONObject results = jsonobj.getJSONObject("results");
			JSONArray bindings = results.getJSONArray("bindings");
			System.out.println(bindings.length());
			resultjson = new String [bindings.length()+1] [headd.length()];


			for(int i=0; i<headd.length();i++){
				resultjson[0][i]=headd.get(i).toString();

				for(int ii=1; ii<=bindings.length();ii++){
					JSONObject s = bindings.getJSONObject(ii-1);
					JSONObject str = s.getJSONObject(headd.get(i).toString());
					resultjson[ii][i]=str.getString("value");


				}


			}

			for(int i =0;i<resultjson.length;i++){
				System.out.println(resultjson[i][0]);
				System.out.println(resultjson[i][1]);

			}

		}finally {
			qexec.close();
		}
		//	System.out.println(rest);
		model.close();

		ds.commit();
		ds.end();
		ds.close();
		return resultjson;

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

