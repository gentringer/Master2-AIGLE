package drinkontology;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;


public class BarOntology {

	private static String URI = "http://localhost:9000";
	//NAMESPACE
	private static String NS = "http://localhost:9000/drinkadvisor#";
	private static String NS_GEO = "http://www.w3.org/2003/01/geo/wgs84_pos#";
	private static String NS_FOAF = "http://xmlns.com/foaf/0.1/";
    public static final String SIOG_TAG = "http://rdfs.org/sioc/ns#";
    public static final String RATING_TAG = "http://www.tvblob.com/ratings/#";
	public static final String NICE_TAGS = "http://ns.inria.fr/nicetag/2009/09/25/voc#";
	public static final String XSD_TAG ="http://www.w3.org/TR/xmlschema-2/#";
	public static final String VCARD_tag ="http://www.w3.org/2001/vcard-rdf/3.0#";

	public static Model barontology() throws IOException{
		OntModel modelOntology = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);

		
		modelOntology.setNsPrefix("drinkadvior", NS);
		modelOntology.setNsPrefix("geo", NS_GEO);
		modelOntology.setNsPrefix("foaf", NS_FOAF);
		modelOntology.setNsPrefix("rating", RATING_TAG);
		modelOntology.setNsPrefix("rat", RATING_TAG);
		modelOntology.setNsPrefix("xsd", XSD_TAG);
		modelOntology.setNsPrefix("vcard", VCARD_tag);
		modelOntology.setNsPrefix("rdf", RDF.getURI());
		modelOntology.setNsPrefix("vcard", RDFS.getURI());

		OntClass bar = modelOntology.createClass(NS+"Bar");
		OntClass comment = modelOntology.createClass(NS+"Comment");
		OntClass like = modelOntology.createClass(NS+"Like");
		OntClass post = modelOntology.createClass(SIOG_TAG+"POST");
		OntClass foaforganisation = modelOntology.createClass(FOAF.Organization.getURI());
		OntClass drink = modelOntology.createClass(NS+"Drink");
		OntClass user = modelOntology.createClass(NS+"User");
		OntClass rating = modelOntology.createClass(RATING_TAG+"Rating");

		OntClass string = modelOntology.createClass(XSD_TAG+"String");

		
		OntProperty vcardadress = modelOntology.createOntProperty(VCARD_tag+"ADR");
		vcardadress.addDomain(bar);
		vcardadress.addRange(string);
		
		OntProperty hasdrink = modelOntology.createOntProperty(NS+"hasdrink");
		hasdrink.addDomain(bar);
		hasdrink.addRange(drink);

		OntProperty haslike = modelOntology.createOntProperty(NS+"hasLike");
		haslike.addDomain(bar);
		haslike.addRange(user);
		
		OntProperty hascomment = modelOntology.createOntProperty(NS+"hasComment");
		hascomment.addDomain(bar);
		hascomment.addRange(comment);
		
		OntProperty geolocation = modelOntology.createOntProperty( NS_GEO + "location" );
		geolocation.addDomain(bar);
		geolocation.addRange(string);
		
		OntProperty sioctopic = modelOntology.createOntProperty(SIOG_TAG+"topic");
		sioctopic.addDomain(comment);
		sioctopic.addRange(string);
		sioctopic.addSuperProperty(hascomment);

		OntProperty siochas_creator = modelOntology.createOntProperty(SIOG_TAG+"has_creator");
		siochas_creator.addDomain(comment);
		siochas_creator.addRange(user);
		siochas_creator.addSuperProperty(hascomment);
		
		OntProperty foafname = modelOntology.createOntProperty(FOAF.name.getURI());
		foafname.addDomain(drink);
		foafname.addRange(string);
		
		OntProperty foafmbox = modelOntology.createOntProperty(FOAF.mbox.getURI());
		foafmbox.addDomain(bar);
		foafmbox.addRange(string);
		
		OntProperty rdfslabel = modelOntology.createOntProperty(RDFS.label.getURI());
		rdfslabel.addDomain(bar);
		rdfslabel.addRange(string);
		
		OntProperty geolat = modelOntology.createOntProperty(NS_GEO+"lat");
		geolat.addDomain(bar);
		geolat.addRange(string);
		
		OntProperty geolon = modelOntology.createOntProperty(NS_GEO+"lon");
		geolon.addDomain(bar);
		geolon.addRange(string);
		
		OntProperty foafphone = modelOntology.createOntProperty(FOAF.phone.getURI());
		foafphone.addDomain(bar);
		foafphone.addRange(string);
		
		OntProperty foafhomepage = modelOntology.createOntProperty(FOAF.homepage.getURI());
		foafhomepage.addDomain(bar);
		foafhomepage.addRange(string);
		
		OntProperty ratingvalue = modelOntology.createOntProperty(RATING_TAG+"value");
		ratingvalue.addDomain(bar);
		ratingvalue.addRange(string);
		
		
		comment.addSuperClass(post);
		like.addSuperClass(rating);
		bar.addSuperClass(foaforganisation);
//		
//		bar.addSubClass( modelOntology.createAllValuesFromRestriction( null, hascomment, comment ));
//		bar.addSubClass( modelOntology.createAllValuesFromRestriction( null, haslike, like ));


		return modelOntology;
		/*
		modelOntology.write( System.out, "RDF/XML-ABBREV" );
		FileOutputStream outStream = new FileOutputStream("drinkOntology.rdf");
        // exporte le resultat dans un fichier
		modelOntology.write(outStream, "RDF/XML-ABBREV");
        outStream.close();*/
	}
	
    public static void main(String[] args) throws IOException {
    	new BarOntology();
    }

}
