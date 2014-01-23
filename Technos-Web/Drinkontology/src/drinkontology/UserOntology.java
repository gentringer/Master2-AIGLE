package drinkontology;
import java.io.FileOutputStream;
import java.io.IOException;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;


public class UserOntology {
	
	private static String URI = "http://localhost:9000";
	//NAMESPACE
	private static String NS = "http://localhost:9000/drinkadvisor#";
	private static String NS_GEO = "http://www.geonames.org/ontology#";
	private static String NS_FOAF = "http://xmlns.com/foaf/0.1/#";
	private static String NS_SIOC = "http://rdfs.org/sioc/ns#";
	public static final String XSD_TAG ="http://www.w3.org/TR/xmlschema-2/#";
	
	public static Model useront() throws IOException
	{
		
		OntModel modelOntology = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);

		
		modelOntology.setNsPrefix("drinkadvior", NS);
		modelOntology.setNsPrefix("geo", NS_GEO);
		modelOntology.setNsPrefix("foaf", NS_FOAF);
		modelOntology.setNsPrefix("sioc", NS_SIOC);
		
		OntClass person = modelOntology.createClass(FOAF.Person.getURI());
		OntClass user = modelOntology.createClass(NS+"User");
		//OntClass place = modelOntology.createClass(NS_GEO+"Place");
		OntClass string = modelOntology.createClass(XSD_TAG+"String");
		//OntClass location = modelOntology.createClass(NS_GEO+"location");
		//OntClass hometown = modelOntology.createClass(NS_GEO+"hometown");
		
		
		OntProperty hasId = modelOntology.createOntProperty(NS_SIOC+"id");
		hasId.addDomain(user);
		hasId.addRange(string);
		
		OntProperty hasName = modelOntology.createOntProperty(FOAF.name.getURI());
		hasName.addDomain(user);
		hasName.addRange(string);
		
		OntProperty hasUsername = modelOntology.createOntProperty(FOAF.nick.getURI());
		hasUsername.addDomain(user);
		hasUsername.addRange(string);
		
		OntProperty hasBirthday = modelOntology.createOntProperty(FOAF.birthday.getURI());
		hasBirthday.addDomain(user);
		hasBirthday.addRange(string);
		
		OntProperty hasGender = modelOntology.createOntProperty(FOAF.gender.getURI());
		hasGender.addDomain(user);
		hasGender.addRange(string);
		
		OntProperty hasMBox = modelOntology.createOntProperty(FOAF.mbox.getURI());
		hasMBox.addDomain(user);
		hasMBox.addRange(string);
		
		OntProperty hasAccount = modelOntology.createOntProperty(FOAF.accountName.getURI());
		hasAccount.addDomain(user);
		hasAccount.addRange(string);
		
		OntProperty hasLocation = modelOntology.createOntProperty(FOAF.based_near.getURI());
		hasLocation.addDomain(user);
		hasLocation.addRange(string);
		
		OntProperty hasHometown = modelOntology.createOntProperty(NS_GEO+"hometown");
		hasHometown.addDomain(user);
		hasHometown.addRange(string);
		
		OntProperty hasFriends = modelOntology.createOntProperty(FOAF.knows.getURI());
		hasFriends.addDomain(user);
		hasFriends.addRange(user);
		
		user.addSuperClass(person);
		//location.addSuperClass(place);
		//hometown.addSuperClass(place);
		
		return modelOntology;
		
		/*modelOntology.write( System.out, "RDF/XML-ABBREV" );
		FileOutputStream outStream = new FileOutputStream("UserOntology.rdf");
        // exporte le resultat dans un fichier
		modelOntology.write(outStream, "RDF/XML-ABBREV");
        outStream.close();*/
	}
	
	public static void main(String[] args) throws IOException {
    	new UserOntology();
    }

}
