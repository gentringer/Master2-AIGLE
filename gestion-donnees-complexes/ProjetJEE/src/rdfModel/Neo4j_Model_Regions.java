package rdfModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.gestion.model.neo4j.ConsultNeo4J;
import com.gestion.model.neo4j.Region;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;

public class Neo4j_Model_Regions {


	public static Model Region_Model() throws IOException{
		
		Model inseeModel = ModelFactory.createOntologyModel();
		FileManager.get().readModel(inseeModel, "/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/ProjetJEE/files/ontologie-geo-2006.rdf");
		System.out.println(inseeModel.size());

		String prefixInsee =inseeModel.getNsPrefixURI("geo");
		Resource pays = inseeModel.getResource(prefixInsee+"pays");
		Resource region = inseeModel.getResource(prefixInsee+"region");
		Resource code_region = inseeModel.getResource(prefixInsee+"code_region");
		Resource nom = inseeModel.getResource(prefixInsee+"nom");
		Resource commune = inseeModel.getResource(prefixInsee+"commune");
		Resource cheflieu = inseeModel.getResource(prefixInsee+"chef-lieu");
		Resource code_commune = inseeModel.getResource(prefixInsee+"code_commune");
		Resource arrondissement = inseeModel.getResource(prefixInsee+"Arrondissement");

		System.out.println(region.getLocalName());
		System.out.println(region.getURI());

		Model regionsModel = ModelFactory.createDefaultModel();

		Map <String,String> prefixes = new HashMap<String,String>();
		prefixes.put("insee",prefixInsee);

		regionsModel.setNsPrefixes(prefixes);
		List<Region> listRegions = ConsultNeo4J.consultDbRegions();

		Resource paysfr = regionsModel.createResource(pays.getURI()+"/PAYS_FR");

		//	Resource resourcereg = regionsModel.createResource(region.getURI());
		Property prop1 = regionsModel.createProperty(code_region.getURI());
		Property propnom = regionsModel.createProperty(nom.getURI());
		Property propcomm = regionsModel.createProperty(commune.getURI());
		Property hascheflieuu = regionsModel.createProperty(cheflieu.getURI());
		Property code_communeprop = regionsModel.createProperty(code_commune.getURI());
		Property code_arrondissement = regionsModel.createProperty(prefixInsee+"code_arrondissement");
		Property subdivision = regionsModel.createProperty(prefixInsee+"subdivision");

		regionsModel.add(paysfr, RDF.type, pays);
		paysfr.addProperty(propnom, "France");
		System.out.println("creation model regions...");

		for(Region regs : listRegions){

			Resource reg = regionsModel.createResource(prefixInsee+"2011/REG_"+regs.coderegion);
			regionsModel.add(paysfr, subdivision, reg);

			Resource communeChefleiu = regionsModel.createResource(prefixInsee+"2011/COM_"+regs.cheflieu);
			
			regionsModel.add(communeChefleiu, RDF.type, commune);


			//communeChefleiu.addProperty(code_communeprop, regs.cheflieu);

			regionsModel.add(reg, RDF.type, region);
			regionsModel.add(communeChefleiu,RDF.type,cheflieu);
			regionsModel.add(reg, hascheflieuu, communeChefleiu);

			//reg1.addProperty(hascheflieuu, "bla");

			reg.addProperty(prop1, regs.coderegion);
			reg.addProperty(propnom, regs.nom);
		}

//		FileOutputStream outStream = new FileOutputStream("regionsneoj.rdf");
//		// exporte le resultat dans un fichier
//		regionsModel.write(outStream, "RDF/XML-ABBREV");
//		outStream.close();
//
//		OutputStream out = new ByteArrayOutputStream();
//		regionsModel.write(out, "RDF/XML-ABBREV");
//
//		//System.out.println(out.toString());
//		out.close();
		
		return regionsModel;

	}
	
	
}
