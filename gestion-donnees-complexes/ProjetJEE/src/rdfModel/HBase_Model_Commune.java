package rdfModel;


import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.gestion.model.hbase.database.Commune;
import com.gestion.model.hbase.database.CreateTable;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;

public class HBase_Model_Commune {

	public static Model Commune_Model() throws IOException{

		Model inseeModel = ModelFactory.createOntologyModel();
		FileManager.get().readModel(inseeModel, "/home/gentringer/universite/Master2-Aigle/gestion-donnees-complexes/ProjetJEE/files/ontologie-geo-2006.rdf");
		System.out.println(inseeModel.size());

		String prefixInsee =inseeModel.getNsPrefixURI("geo");
		System.out.println(prefixInsee);
		Resource commune = inseeModel.getResource(prefixInsee+"commune");
		Resource Departement = inseeModel.getResource(prefixInsee+"Departement");
		Resource Region = inseeModel.getResource(prefixInsee+"Region");

		Resource code_INSEE = inseeModel.getResource(prefixInsee+"code_INSEE");
		Resource code_Region = inseeModel.getResource(prefixInsee+"code_Region");
		Resource code_Departement = inseeModel.getResource(prefixInsee+"code_departement");

		Resource nom = inseeModel.getResource(prefixInsee+"nom");
		Resource cheflieu = inseeModel.getResource(prefixInsee+"chef-lieu");
		Resource code_commune = inseeModel.getResource(prefixInsee+"code_commune");
		Resource arrondissement = inseeModel.getResource(prefixInsee+"Arrondissement");


		Model communeModel = ModelFactory.createDefaultModel();
		Map <String,String> prefixes = new HashMap<String,String>();
		prefixes.put("insee",prefixInsee);

		communeModel.setNsPrefixes(prefixes);


		List<Commune> listeCommune = CreateTable.getAllRecordList("Communes");
		System.out.println("creation model commune...");
		for(Commune comms:listeCommune){

			Resource com = communeModel.createResource(prefixInsee+"2011/COM_"+comms.departement+comms.COM);
			//Resource depart = communeModel.createResource(Departement.getURI()+comms.departement);
			//Resource regi = communeModel.createResource(Region.getURI()+comms.COM);

			Property propcode_INSEE = communeModel.createProperty(code_INSEE.getURI());
			Property propnom = communeModel.createProperty(nom.getURI());
			Property propcomm = communeModel.createProperty(commune.getURI());
			Property propreg = communeModel.createProperty(Region.getURI());
			Property propdep = communeModel.createProperty(Departement.getURI());

			Property hascheflieuu = communeModel.createProperty(cheflieu.getURI());
			Property code_communeprop = communeModel.createProperty(code_commune.getURI());
			Property code_deprop = communeModel.createProperty(code_Departement.getURI());
			Property code_regrop = communeModel.createProperty(code_Region.getURI());
			
			Property code_arrondissement = communeModel.createProperty(prefixInsee+"code_arrondissement");
			Property subdivision = communeModel.createProperty(prefixInsee+"subdivision");

			

			communeModel.add(com, RDF.type, commune);
			//communeModel.add(depart, RDF.type, Departement);
			//communeModel.add(regi, RDF.type, Region);

			//communeModel.add(com, propdep, depart);
			//communeModel.add(com, propreg, regi);

			//dep1.addProperty(hascheflieuu, "bla");


			com.addProperty(propcode_INSEE, comms.departement+comms.COM);
			com.addProperty(propnom, comms.nom);
			com.addProperty(code_regrop, comms.region);
			com.addProperty(code_deprop, comms.departement);
			com.addProperty(code_deprop, comms.departement);


		}

		//FileOutputStream outStream = new FileOutputStream("communeshbase.rdf");
		// exporte le resultat dans un fichier
		//communeModel.write(outStream, "RDF/XML-ABBREV");
		//outStream.close();

		//OutputStream out = new ByteArrayOutputStream();
		//communeModel.write(out, "RDF/XML-ABBREV");

		//System.out.println(out.toString());
		//out.close();
		return communeModel;


	}

}
