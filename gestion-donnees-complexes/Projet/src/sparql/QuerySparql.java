package sparql;

import java.io.IOException;

import rdfModel.Model_Commune;
import rdfModel.Model_Departements;
import rdfModel.Model_Regions;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.vocabulary.RDF;

public class QuerySparql {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		Model mod = Model_Regions.Region_Model();
		Model mod2 = Model_Commune.Commune_Model();
		Model mod3 = Model_Departements.Departements_Model();
		mod.add(mod2);
		mod.add(mod3);

		String NL = System.getProperty("line.separator") ;
		String geon ="http://rdf.insee.fr/geo/";
		String prolog_e = "PREFIX vocab: <"+geon+">" ;
		String prolog_r = "PREFIX rdf: <"+RDF.getURI()+">" ;

		String queryString = prolog_e + NL + prolog_r + NL +
				"SELECT ?s ?code ?nom where {" +
				"?s vocab:code_region ?code ." +
				"?s vocab:nom ?nom"+
				"}";
		
		 
        String gn ="http://rdf.insee.fr/geo/";
	
    	
		String prolog1 = "PREFIX geonames: <"+gn+">" ;
        String prolog2 = "PREFIX rdf: <"+RDF.getURI()+">" ;
        
		 queryString = prolog1 + NL + prolog2 + NL +
				"SELECT  ?coderegion ?nomRegion  ?codechef ?cheflieu where {" +
				"?s geonames:nom ?nomRegion . " +
				"FILTER regex (?nomRegion, \"^ILE-DE-FRANCE\")." +
				"?s geonames:code_region ?coderegion ." +
				"?s geonames:chef-lieu ?cheflieu ." +
				"?cheflieu geonames:code_commune ?codechef ."+
				
				
				"}";
		 
		 queryString = prolog1 + NL + prolog2 + NL +
					"SELECT DISTINCT ?nomCheflieu ?codeCommune  where {" +
					"?s geonames:chef-lieu ?cheflieu . " +
					"?cheflieu geonames:nom ?nomCheflieu ."+
					"?cheflieu geonames:code_commune ?codeCommune ."+
					"}";
		 
		 // Toutes les communes de l'herault + sa région d'appartenance
		 queryString = prolog1 + NL + prolog2 + NL +
					"SELECT  ?nomDepartement ?code ?codeRegion ?nomregion ?z ?nomCommune where {" +
					"?s rdf:type geonames:Departement  . " +
					"?s geonames:nom ?nomDepartement ."+
					"?s geonames:code_departement ?code ." +
					"?s geonames:code_region ?codeRegion ." +
					"?y rdf:type geonames:region ." +
					"?y geonames:nom ?nomregion ." +
					"?y geonames:code_region ?codeRegion ." +
					
					"?z rdf:type geonames:commune ." +
					"?z geonames:nom ?nomCommune ." +
					"?z geonames:code_departement ?code ."+
					"FILTER regex(?codeRegion,\"^91\") ." +
					"FILTER regex(?code,\"^34\") ." +

					//"?s vocab:nom ?nomDepartement ."+
					//"?nomDepartement vocab:code_arrondissement ?arrond ."+
					//"?code vocab:nom ?nomRegion ."+ 
					//"?s vocab:code_arrondissement ?arrondissement"+

					"}";
		 
		System.out.println(queryString);  
		System.out.println(mod.size());
        Query query = QueryFactory.create(queryString) ;
        QueryExecution qexec = QueryExecutionFactory.create(query, mod) ;
        ResultSet results = qexec.execSelect() ;
        ResultSetFormatter.out(results) ;
        qexec.close() ;
		
	}

}
