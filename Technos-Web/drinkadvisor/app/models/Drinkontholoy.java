package models;

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

//http://web-semantique.developpez.com/faq/?page=jena#jena-serialiser-ontologie
public class Drinkontholoy {

	private static boolean singleton = false;
	
	public static int BLONDE = 1;
	public static int BRUNE = 1;
	public static int ROUSSE = 1;
	public static int BLANC = 1;
	public static int ROUGE = 1;
	public static int ROSE = 1;
	
	private static String URI = "http://localhost:9000";
	//NAMESPACE
	public static String NS = URI+"/alcoholonthology#";
	public static String NS_GEO = "http://www.w3.org/2003/01/geo/wgs84_pos#";
	public static String NS_FOAF = "http://xmlns.com/foaf/0.1/";
	
	public static OntModel alcoholonthology = ModelFactory.createOntologyModel( OntModelSpec.RDFS_MEM );;
	//CLASSES
	public static OntClass boisson = alcoholonthology.createClass( NS + "Boisson" );
	public static OntClass alcoolfort = alcoholonthology.createClass( NS + "AlcoolFort" );
	public static OntClass sansalcool = alcoholonthology.createClass( NS + "SansAlcool" );
	public static OntClass biere = alcoholonthology.createClass(NS + "Biere");
	public static OntClass blonde = alcoholonthology.createClass(NS + "Blonde");
	public static OntClass brune = alcoholonthology.createClass(NS + "Brune");
	public static OntClass rousse = alcoholonthology.createClass(NS + "Rousse");
	public static OntClass vin = alcoholonthology.createClass(NS + "Vin");
	public static OntClass blanc = alcoholonthology.createClass(NS + "Blanc");
	public static OntClass rouge = alcoholonthology.createClass(NS + "Rouge");
	public static OntClass rose = alcoholonthology.createClass(NS + "Rose");


   //PROPRIETES
   public static OntProperty locationCountry = alcoholonthology.createOntProperty( NS_GEO + "location" );
   public static OntProperty label = alcoholonthology.createOntProperty( NS_FOAF + "name" );
   //locationVille
   //description

	
	public Drinkontholoy(){
		if (!singleton){
			creerModel();
			singleton = true;
		}
	}
	
	public void creerModel(){
		
		alcoholonthology = ModelFactory.createOntologyModel( OntModelSpec.RDFS_MEM );
		
		

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

       locationCountry = alcoholonthology.createOntProperty( NS_GEO + "location" );
       locationCountry.addRange(boisson);
       label = alcoholonthology.createOntProperty( NS_FOAF + "name" );
       label.addRange(boisson);
       
       alcoholonthology.setNsPrefix("geo", NS_GEO);
       alcoholonthology.setNsPrefix("name", NS_FOAF);
      
	}
	
	
	public void print(OutputStream out){
      alcoholonthology.write( out, "RDF/XML-ABBREV" );
	}
}
