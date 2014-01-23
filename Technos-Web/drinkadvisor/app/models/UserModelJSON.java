package models;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;

public class UserModelJSON 
{
	/********************
	 * 
	 * Namespaces
	 */
	public static final String rdfns = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	public static final String rdfsns = "http://www.w3.org/2000/01/rdf-schema#";
	public static final String foafns = "http://xmlns.com/foaf/0.1/";
	public static final String siocns = "http://rdfs.org/sioc/ns#";
	public static final String geons = "http://www.geonames.org/ontology#";
	public static final String DRINKURI ="http://localhost:9000/";
	
	public static void createUserModel(String idfb)
	{
		//Model model = ModelFactory.createOntologyDefaultModel(OntModelSpec.RDFS_MEM);
		Model model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
		model.setNsPrefix("FOAF", foafns);
		model.setNsPrefix("SIOC",siocns);
		model.setNsPrefix("GEO",geons);
		model.setNsPrefix("drinkadvisor", DRINKURI);
		
		
		String userURI = "http://localhost:9000/infouser?idvar="+idfb;

		Resource person = model.createResource(userURI);


		Resource ressourceUser = model.createResource(DRINKURI+"User");

		Property id = model.createProperty(siocns+"id");
		Property homeTown = model.createProperty(geons+"hometown");
		Property locati = model.createProperty(geons+"location");
		Property knowsDA = model.createProperty(DRINKURI+"knowsDA");

		model.add(person, RDF.type, ressourceUser);
		
		User user = User.find.where().eq("idfacebook", idfb).findUnique();

		Resource locat = model.createResource(geons+user.location);

		person.addProperty(id, idfb);
		person.addProperty(FOAF.name, user.name);
		person.addProperty(FOAF.nick, user.username);
		person.addProperty(FOAF.birthday, user.birthday);
		person.addProperty(FOAF.gender, user.gender);
		person.addProperty(FOAF.mbox, user.email);
		person.addProperty(FOAF.accountName, user.link);
		person.addProperty(homeTown, user.hometown);
		person.addProperty(FOAF.based_near, locat.addProperty(locati, user.location));

		User us = User.find.where().eq("idfacebook", idfb).findUnique();
		List<User> listefriendda = us.userfriends;
		int i =0;
		for(User userfriendDA : listefriendda)
		{

			String userURIDA = "http://localhost:9000/infouser?idvar="+userfriendDA.idfacebook;

			Resource personfriend = model.createResource(userURIDA);
			personfriend.addProperty(id, userfriendDA.idfacebook);
			model.add(personfriend, RDF.type, ressourceUser);

			model.add(person,FOAF.knows,personfriend);

			System.out.println("userfriendDA" + userfriendDA.idfacebook);
			System.out.println("userfriendDA" + userfriendDA.link);
			i++;

			//				person.addProperty(knowsDA, personfriend.addProperty(FOAF.accountName, user.link));
		}
		org.openjena.riot.RIOT.init(); //wires RIOT readers/writers into Jena
		java.io.OutputStream os = null;
		// Serialize over an outputStream
		os = new java.io.ByteArrayOutputStream();

		model.write(os, "RDF/JSON");

		System.out.println(os.toString());
		try {
				FileOutputStream ost = new FileOutputStream("./public/data/"+idfb+".json");
				System.out.println("Writing on file...");
				//m.write(System.out, "N3" );
				//model.write(System.out, "RDF/XML-ABBREV");
				model.write(ost, "RDF/JSON"); 
				try {
					ost.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					ost.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			catch (FileNotFoundException e) 
			{
				System.out.println("problème de fichier");
			} 
		
		//model.write(System.out, "RDF/XML-ABBREV");
	}

}
