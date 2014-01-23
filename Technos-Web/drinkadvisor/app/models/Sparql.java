package models;

import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

public class Sparql {
	String location = null;
	String beeruri = null;
	public static  List<QuerySolution> retourlist(String name) {
		List<QuerySolution> res;
		String querystring=
				"PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
						"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
						"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
						"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
						"PREFIX dc: <http://purl.org/dc/elements/1.1/>" +
						"PREFIX dbpedia2: <http://dbpedia.org/property/>" +
						"PREFIX dbpedia: <http://dbpedia.org/>" +
						"PREFIX skos: <http://www.w3.org/2004/02/skos/core#>" +
						"PREFIX : <http://dbpedia.org/resource/>" +
						"PREFIX dcterms: <http://purl.org/dc/terms/>"+
						"PREFIX dcterms: <http://purl.org/dc/terms/>"+
						"PREFIX category: <http://dbpedia.org/resource/Category:> "+
						"PREFIX dbo: <http://dbpedia.org/ontology/>"+
						"SELECT distinct  ?test ?beerlabel ?abstract  ?country ?logo where {"+
						"?beer skos:broader* category:Beer_and_breweries_by_region ."+
						"?test dcterms:subject ?beer ."+
						"?test rdfs:label ?beerlabel ."+
						"?test dbo:abstract ?abstract ."+
						"FILTER regex(?beerlabel, \"^"+name+"\") . "+
						"OPTIONAL {"+
						"{?test dbpedia2:location ?country}"+
						"UNION"+
						"{?test dbo:locationCountry ?country}"+
						"UNION"+
						"{?test dbpedia2:locationCountry ?country}"+
						"}"+

"FILTER langMatches( lang(?beerlabel), \"EN\" ) . "+
"FILTER langMatches( lang(?abstract), \"EN\" ) ."+
"}"

						;
		System.out.println(querystring);
		String service = "http://dbpedia.org/sparql";
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, querystring);

		try {

			ResultSet rs = qe.execSelect() ;
			// ResultSetFormatter.out(System.out, rs);
			res = ResultSetFormatter.toList(rs);    
			for(QuerySolution q : res){

				/*	String location = q.get("country").toString();
	            	String beer = q.get("beer").toString();
	            	System.out.println(location);
	            	System.out.println(beer);*/
			}



		}
		finally {
			qe.close();
		}

		return res;



	}

	public static ArrayList<String> querySparql(String name, String prix, String categorie,String type){

		ArrayList<String> result = new ArrayList<String>();
		List<QuerySolution> res;


		String query = "SELECT ?description1 " +
				"WHERE {  " +
				"<http://dbpedia.org/resource/"+name+"> ?property ?hasValue .  " +
				"?hasValue <http://dbpedia.org/ontology/abstract> ?description1.  " +
				"FILTER langMatches( lang(?description1), 'FR' )  " +
				"OPTIONAL { " +
				"				<http://dbpedia.org/resource/"+name+"> <http://dbpedia.org/ontology/wikiPageRedirects> ?value . " +
				"				?value <http://dbpedia.org/ontology/abstract> ?description1. " +
				"				FILTER langMatches( lang(?description2), 'FR' )}}";
		System.out.println("quer10: " +query);

		String service = "http://dbpedia.org/sparql";
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		System.out.println(query);
		try{

			ResultSet rs = qe.execSelect() ;
			// ResultSetFormatter.out(System.out, rs);
			res = ResultSetFormatter.toList(rs);    
			String namesparql = "";
			String description = "";
			
			for(QuerySolution q : res){
				description = q.get("description1").toString();
				System.out.println(description);
			}
			//System.out.println("taille = "+name.length()+"\n"+name);
			if(description.contains("@")){
				String [] split  = description.split("@");

				description  = split[0];
			}
			String pays = locationCountry(name);
			String ville = locationCity(name);

			result.add(name);
			result.add(prix);
			result.add(description);
			result.add(categorie);
			result.add(type);
			result.add(pays);
			result.add(ville);
		}
		finally {
			qe.close();
		}

		return result;

	}
	
	public static String locationCity(String name){
		String retour = null;
		List<QuerySolution> res;
		String ville="";
		String query = "SELECT ?ville ?label " +
				"WHERE {" +
				"       <http://dbpedia.org/resource/"+name+"> ?property ?hasValue ." +
				"       ?hasValue <http://dbpedia.org/ontology/locationCity> ?ville" +
				"       OPTIONAL {" +
				"	   <http://dbpedia.org/resource/"+name+"> <http://dbpedia.org/ontology/wikiPageRedirects> ?value ." +
				"	   ?value <http://dbpedia.org/ontology/locationCity> ?ville" +
				"           OPTIONAL {" +
				"                    ?ville <http://www.w3.org/2000/01/rdf-schema#label> ?label." +
				"                     FILTER langMatches( lang(?label), 'FR' )}" +
				"	   }" +
				"}";
		
		System.out.println("quer2: " +query);

		String service = "http://dbpedia.org/sparql";
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		System.out.println(query);
		try{
			ResultSet rs = qe.execSelect() ;
			// ResultSetFormatter.out(System.out, rs);
			res = ResultSetFormatter.toList(rs);    
			
			for(QuerySolution q : res){
				if(q.get("label").toString().equals("")){
					ville=q.get("ville").toString();
				}
				else{
					ville=q.get("label").toString();
				}
				
			}
			if(ville.contains("@")){
				String [] split  = ville.split("@");

				ville  = split[0];
			}
			
		}
		finally{
			qe.close();
		}
			
		return ville;
	}
	
	public static String locationCountry(String name){
		List<QuerySolution> res;
		String country="";
		String query = "SELECT ?country ?label " +
				"WHERE {" +
				"       <http://dbpedia.org/resource/"+name+"> ?property ?hasValue ." +
				"       ?hasValue <http://dbpedia.org/ontology/locationCountry> ?country" +
				"       OPTIONAL {" +
				"	   <http://dbpedia.org/resource/"+name+"> <http://dbpedia.org/ontology/wikiPageRedirects> ?value ." +
				"	   ?value <http://dbpedia.org/ontology/locationCountry> ?country" +
				"           OPTIONAL {" +
				"                    ?ville <http://www.w3.org/2000/01/rdf-schema#label> ?label." +
				"                     FILTER langMatches( lang(?label), 'FR' )}" +
				"	   }" +
				"}";
		
		System.out.println("quer1: " +query);
		String service = "http://dbpedia.org/sparql";
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		System.out.println(query);
		try{
			ResultSet rs = qe.execSelect() ;
			// ResultSetFormatter.out(System.out, rs);
			res = ResultSetFormatter.toList(rs);    
			
			for(QuerySolution q : res){
				if(q.get("label").toString().equals("")){
					country=q.get("country").toString();
				}
				else{
					country=q.get("label").toString();
				}
				
			}
			if(country.contains("@")){
				String [] split  = country.split("@");

				country  = split[0];
			}
			
		}
		finally{
			qe.close();
		}
			
		return country;
	}

}

