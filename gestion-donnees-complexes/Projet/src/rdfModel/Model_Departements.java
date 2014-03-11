package rdfModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import neo4j.database.ConsultNeo4J;
import neo4j.database.Departement;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;

public class Model_Departements {

	public static Model Departements_Model() throws IOException{
	
	Model inseeModel = ModelFactory.createOntologyModel();
	FileManager.get().readModel(inseeModel, "ontologie-geo-2006.rdf");
	System.out.println(inseeModel.size());

	String prefixInsee =inseeModel.getNsPrefixURI("geo");
	Resource departement = inseeModel.getResource(prefixInsee+"Departement");
	Resource code_departement = inseeModel.getResource(prefixInsee+"code_departement");
	Resource code_region = inseeModel.getResource(prefixInsee+"code_region");

	Resource nom = inseeModel.getResource(prefixInsee+"nom");
	Resource commune = inseeModel.getResource(prefixInsee+"commune");
	Resource cheflieu = inseeModel.getResource(prefixInsee+"chef-lieu");
	Resource code_commune = inseeModel.getResource(prefixInsee+"code_commune");

	System.out.println(departement.getLocalName());
	System.out.println(departement.getURI());
	
	Model departementsModel = ModelFactory.createDefaultModel();
	
	Map <String,String> prefixes = new HashMap<String,String>();
	prefixes.put("geo",prefixInsee);
	
	departementsModel.setNsPrefixes(prefixes);
	List<Departement> listDepartement = ConsultNeo4J.consultdbDepartement();
	Resource resourceDepart = departementsModel.createResource(departement.getURI());

	for(Departement deps : listDepartement){
		Resource dep = departementsModel.createResource(departement.getURI()+"/"+deps.codedepartement);
		Resource communeChefleiu = departementsModel.createResource(commune.getURI()+"/"+deps.cheflieu);

		Property prop1 = departementsModel.createProperty(code_departement.getURI());
		Property propnom = departementsModel.createProperty(nom.getURI());
		Property propcomm = departementsModel.createProperty(commune.getURI());
		Property hascheflieuu = departementsModel.createProperty(cheflieu.getURI());
		Property code_communeprop = departementsModel.createProperty(code_commune.getURI());
		Property code_arrondissement = departementsModel.createProperty(prefixInsee+"code_arrondissement");
		Property subdivision = departementsModel.createProperty(prefixInsee+"subdivision");
		Property propCode_region = departementsModel.createProperty(code_region.getURI());
		
		departementsModel.add(communeChefleiu, RDF.type, commune);

	
		communeChefleiu.addProperty(code_communeprop, deps.cheflieu);

		departementsModel.add(dep, RDF.type, departement);
		departementsModel.add(dep, hascheflieuu, communeChefleiu);

		//dep1.addProperty(hascheflieuu, "bla");

		
		dep.addProperty(prop1, deps.codedepartement);
		dep.addProperty(propnom, deps.nom);
		dep.addProperty(propCode_region, deps.coderegion);
	}

	
//	Resource dep1 = departementsModel.createResource(departement.getURI()+"/DEP_01");
//	Resource dep2 = departementsModel.createResource(departement.getURI()+"/DEP_02");
//	Resource commechefleiu = departementsModel.createResource(Commune.getURI()+"/COM_34172");
//	Resource arrondiss = departementsModel.createResource(arrondissement.getURI()+"/ARR_341");

//	Property prop1 = departementsModel.createProperty(code_departement.getURI());
//	Property propnom = departementsModel.createProperty(nom.getURI());
//	Property propcomm = departementsModel.createProperty(Commune.getURI());
//	Property hascheflieuu = departementsModel.createProperty(cheflieu.getURI());
//	Property code_communeprop = departementsModel.createProperty(code_commune.getURI());
//	Property code_arrondissement = departementsModel.createProperty(prefixInsee+"code_arrondissement");
//	Property subdivision = departementsModel.createProperty(prefixInsee+"subdivision");

	
//	departementsModel.add(commechefleiu, RDF.type, Commune);
//	departementsModel.add(arrondiss, RDF.type, arrondissement);
//
//	arrondiss.addProperty(code_arrondissement, "341");
//	arrondiss.addProperty(propnom,"Béziers");
//	commechefleiu.addProperty(propnom, "Montpellier");
//	commechefleiu.addProperty(code_communeprop, "34172");
//
//	departementsModel.add(dep1, RDF.type, departement);
//	departementsModel.add(dep1, hascheflieuu, commechefleiu);
//	departementsModel.add(dep1, subdivision, arrondiss);
//
//
//	departementsModel.add(dep2, RDF.type, departement);
//	//dep1.addProperty(hascheflieuu, "bla");
//
//	
//	dep1.addProperty(prop1, "34");
//	dep1.addProperty(propnom, "hérault");
	
	FileOutputStream outStream = new FileOutputStream("departementsnoej.rdf");
//    // exporte le resultat dans un fichier
	departementsModel.write(outStream, "RDF/XML-ABBREV");
    outStream.close();
//    
//	OutputStream out = new ByteArrayOutputStream();
//	departementsModel.write(out, "RDF/XML-ABBREV");
//	
//	System.out.println(out.toString());
//	out.close();
	return departementsModel;
	}
}
