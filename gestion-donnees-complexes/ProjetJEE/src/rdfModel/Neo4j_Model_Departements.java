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
import com.gestion.model.neo4j.Departement;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;

public class Neo4j_Model_Departements {

	public static Model Departements_Model() throws IOException{

		Model inseeModel = ModelFactory.createOntologyModel();
		FileManager.get().readModel(inseeModel, "/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/ProjetJEE/files/ontologie-geo-2006.rdf");
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
		prefixes.put("insee",prefixInsee);

		departementsModel.setNsPrefixes(prefixes);
		List<Departement> listDepartement = ConsultNeo4J.consultdbDepartement();
		Resource resourceDepart = departementsModel.createResource(departement.getURI());
		System.out.println("creation model departements...");

		for(Departement deps : listDepartement){
			
			Resource dep = departementsModel.createResource(prefixInsee+"2011/DEP_"+deps.codedepartement);
			Resource communeChefleiu = departementsModel.createResource(prefixInsee+"2011/COM_"+deps.cheflieu);

			Property prop1 = departementsModel.createProperty(code_departement.getURI());
			Property propnom = departementsModel.createProperty(nom.getURI());
			Property propcomm = departementsModel.createProperty(commune.getURI());
			Property hascheflieuu = departementsModel.createProperty(cheflieu.getURI());
			Property code_communeprop = departementsModel.createProperty(code_commune.getURI());
			Property code_arrondissement = departementsModel.createProperty(prefixInsee+"code_arrondissement");
			Property subdivision = departementsModel.createProperty(prefixInsee+"subdivision");
			Property propCode_region = departementsModel.createProperty(code_region.getURI());

			departementsModel.add(communeChefleiu, RDF.type, commune);


			//communeChefleiu.addProperty(code_communeprop, deps.cheflieu);

			departementsModel.add(dep, RDF.type, departement);
			departementsModel.add(dep, hascheflieuu, communeChefleiu);

			//dep1.addProperty(hascheflieuu, "bla");


			dep.addProperty(prop1, deps.codedepartement);
			dep.addProperty(propnom, deps.nom);
			dep.addProperty(propCode_region, deps.coderegion);
		}



		FileOutputStream outStream = new FileOutputStream("departementsnoej.rdf");
		// exporte le resultat dans un fichier
		departementsModel.write(outStream, "RDF/XML-ABBREV");
		outStream.close();


		return departementsModel;
	}
}
