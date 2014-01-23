package drinkontology;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Writer;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

//http://web-semantique.developpez.com/faq/?page=jena#jena-serialiser-ontologie
public class Drinkontholoy {

	private static boolean singleton = false;

	public static int BLONDE = 1;
	public static int BRUNE = 1;
	public static int ROUSSE = 1;
	public static int BLANC = 1;
	public static int ROUGE = 1;
	public static int ROSE = 1;

	//private static String URI = "http://localhost:9000";
	//NAMESPACE
	private static String NS = "http://localhost:9000/drinkadvisor#";
	public static String NS_GEO = "http://www.w3.org/2003/01/geo/wgs84_pos#";
	public static String NS_FOAF = "http://xmlns.com/foaf/0.1/";
	public static final String dbpedia = "http://www.dbpedia.org/ontology/";


	public Drinkontholoy(){
		if (!singleton){
			creerModel();
			singleton = true;
		}
	}

	public static Model creerModel(){

		OntModel alcoholonthology = ModelFactory.createOntologyModel( OntModelSpec.RDFS_MEM );


		alcoholonthology.setNsPrefix("geo", NS_GEO);
		alcoholonthology.setNsPrefix("rdfs", RDFS.getURI());
		alcoholonthology.setNsPrefix("rdf", RDF.getURI());
		alcoholonthology.setNsPrefix("dbpedia", dbpedia);

		//CLASSES
		OntClass boisson = alcoholonthology.createClass( NS + "Boisson" );
		OntClass alcoolfort = alcoholonthology.createClass( NS + "AlcoolFort" );
		OntClass sansalcool = alcoholonthology.createClass( NS + "SansAlcool" );
		OntClass biere = alcoholonthology.createClass(NS + "Biere");
		OntClass blonde = alcoholonthology.createClass(NS + "Blonde");
		OntClass brune = alcoholonthology.createClass(NS + "Brune");
		OntClass rousse = alcoholonthology.createClass(NS + "Rousse");
		OntClass vin = alcoholonthology.createClass(NS + "Vin");
		OntClass blanc = alcoholonthology.createClass(NS + "Blanc");
		OntClass rouge = alcoholonthology.createClass(NS + "Rouge");
		OntClass rose = alcoholonthology.createClass(NS + "Rose");


		//PROPRIETES
		OntProperty locationCountry = alcoholonthology.createOntProperty( NS_GEO + "locationCountry" );
		OntProperty label = alcoholonthology.createOntProperty( RDFS.label + "label" );
		OntProperty description = alcoholonthology.createOntProperty(RDFS.comment + "comment");
		OntProperty locationCity = alcoholonthology.createOntProperty( dbpedia + "locationCity" );



		boisson.addSubClass( alcoolfort );
		boisson.addSubClass(biere);
		biere.addSubClass(blonde);
		biere.addSubClass(brune);
		biere.addSubClass(rousse);
		boisson.addSubClass(vin);
		boisson.addSubClass(rose);
		boisson.addSubClass(blanc);
		boisson.addSubClass(rouge);
		boisson.addSubClass(sansalcool);

		locationCountry.addRange(boisson);
		label.addRange(boisson);
		description.addRange(boisson);
		locationCity.addRange(boisson);

		return alcoholonthology;

	}


	public void print(OutputStream out){
	//	alcoholonthology.write( out, "RDF/XML-ABBREV" );
	}
}
