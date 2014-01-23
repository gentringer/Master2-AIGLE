package models;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.xml.parsers.ParserConfigurationException;


import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.VCARD;



import play.data.Form;
import play.data.validation.Constraints;
import com.hp.hpl.jena.rdf.model.Model;


public class GenerateRDF  {


	public static final String NS_FOAF = "http://xmlns.com/foaf/0.1/";
	public static final String RDF_GEO = "http://www.w3.org/2003/01/geo/wgs84_pos#";
	public static final String RDF_TAGS = "http://ns.inria.fr/nicetag/2009/09/25/voc#";
	public static final String SIOG_TAG = "http://rdfs.org/sioc/ns#";
	public static final String RATING_TAG = "http://www.tvblob.com/ratings/#";
	public static final String DRINKURI ="http://localhost:9000/";
	public static final String geonames = "http://www.geonames.org/ontology#";
	public static final String dbpedia = "http://www.dbpedia.org/ontology/";

	static String pref1 = "PREFIX foaf: <"+NS_FOAF+">" ;
	static String pref2 = "PREFIX rdf: <"+RDF.getURI()+">" ;
	static String pref3 = "PREFIX rdfs: <"+RDFS.getURI()+">" ;
	static String pref4 = "PREFIX geo: <"+RDF_GEO+">" ;
	static String pref5 = "PREFIX siog: <"+SIOG_TAG+">" ;
	static String pref6 = "PREFIX rating: <"+RATING_TAG+">" ;
	static String pref7 = "PREFIX drinkadvisor: <"+DRINKURI+">" ;
	static String pref8 = "PREFIX dbpedia: <"+dbpedia+">" ;


	public static ArrayList<String> existingsprefixes = new ArrayList<String>();


	public static void generateRDFSingleBar(String barname){

		String barnameURI = containsWhiteSpace(barname);

		System.out.println(barname);
		System.out.println("in rdfbar");
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("foaf", NS_FOAF);
		model.setNsPrefix("geo", RDF_GEO);
		model.setNsPrefix("nt", RDF_TAGS);
		model.setNsPrefix("sioc", SIOG_TAG);
		model.setNsPrefix("drinkadvisor", DRINKURI);
		model.setNsPrefix("rating", RATING_TAG);
		model.setNsPrefix("dbpedia", dbpedia);



		String barURI = "http://localhost:9000/bar?name="+barnameURI;
		Bar rdfbar = Bar.find.where().eq("name", barname).findUnique();
		String lon = rdfbar.lon;
		String lat = rdfbar.lat;
		String adresse = rdfbar.adresse;
		String mail = rdfbar.mail;
		int note = rdfbar.note;
		String website = rdfbar.website;
		String telephone = rdfbar.telephone;

		List<Comment> commentlist = rdfbar.comments;
		List<BeerInBar> drinklist=rdfbar.beerlist;
		List<User> userlikes = rdfbar.likes;

		RDFNode[] elems = new RDFNode[userlikes.size()];

		Property siocauthor = model.createProperty(SIOG_TAG+"has_creator");
		Property sioctopic = model.createProperty(SIOG_TAG+"topic");
		Property siocpost = model.createProperty(SIOG_TAG+"post");


		Property rating  = model.createProperty( RATING_TAG + "value" );
		Property latprop = model.createProperty( RDF_GEO + "lat" );
		Property lonprop = model.createProperty( RDF_GEO + "lon" );
		Property geolocation = model.createProperty( RDF_GEO + "location" );

		Property drinkcommentProp = model.createProperty( DRINKURI + "hasComment" );
		Property drinklike = model.createProperty( DRINKURI + "hasLike" );
		Property drinkhasdrink = model.createProperty(DRINKURI+"hasdrink");
		Property drinkmessage = model.createProperty(DRINKURI+"message");

		Resource post = model.createResource(SIOG_TAG+"POST");
		Resource drinkcomment = model.createResource(DRINKURI+"Comment");
		Resource drinkres = model.createResource(DRINKURI+"drink");
		Resource baruser = model.createResource(DRINKURI+"user");



		Resource drinkress = model.createResource( DRINKURI + "Drink" );
		Resource alcoolfort = model.createResource( DRINKURI + "AlcoolFort" );
		Resource sansalcool = model.createResource( DRINKURI + "SansAlcool" );
		Resource biere = model.createResource(DRINKURI + "Biere");
		Resource blonde = model.createResource(DRINKURI + "Blonde");
		Resource blanche = model.createResource(DRINKURI + "Blanche");
		Resource brune = model.createResource(DRINKURI + "Brune");
		Resource rousse = model.createResource(DRINKURI);
		Resource vin = model.createResource(DRINKURI + "Vin");
		Resource blanc = model.createResource(DRINKURI + "Blanc");
		Resource rouge = model.createResource(DRINKURI + "Rouge");
		Resource rose = model.createResource(DRINKURI + "Rose");

		Resource bar = model.createResource(barURI);
		Resource ressourceBar = model.createResource(DRINKURI+"Bar");

		model.add(bar, RDF.type, ressourceBar);
		Resource like = model.createResource(DRINKURI+"Like");

		bar.addProperty(RDFS.label, barname);
		bar.addProperty(lonprop, lon);
		bar.addProperty(latprop, lat);
		bar.addProperty(FOAF.homepage, website);
		bar.addProperty(FOAF.phone, telephone);
		bar.addProperty(FOAF.mbox, mail);

		if(adresse!=null){
			bar.addProperty(VCARD.ADR, adresse);
		}



		int commentcounter=0;
		for(Comment com:commentlist){
			Resource comment = model.createResource(DRINKURI+"bar?name="+barnameURI+"#comment"+commentcounter);
			comment.addProperty(siocauthor, com.username);
			comment.addProperty(sioctopic, com.barname);
			comment.addProperty(RDFS.comment, com.comment);
			model.add(comment,RDF.type,drinkcomment);
			model.add(bar,drinkcommentProp,comment);
			commentcounter++;
			//list2.add(comment);
		}


		for(BeerInBar dr: drinklist){
			Drink drinkinbar = Drink.find.where().eq("label", dr.drinkname).findUnique();
			System.out.println("category:"+drinkinbar.categorie);
			Resource drink = model.createResource(DRINKURI+"drink?name="+drinkinbar.label);
			//				drink.addProperty(FOAF.name,drinkinbar.label);
			//				drink.addProperty(geolocation, drinkinbar.city);


			if(drinkinbar.drinktype.equals("blonde")){		
				model.add(drink, RDF.type, blonde);
				model.add(bar,drinkhasdrink,drink);
			}

			else if(drinkinbar.drinktype.equals("brune")){		
				model.add(drink, RDF.type, brune);
				model.add(bar,drinkhasdrink,drink);
			}

			else if(drinkinbar.drinktype.equals("rousse")){		
				model.add(drink, RDF.type, rousse);
				model.add(bar,drinkhasdrink,drink);
			}
			else if(drinkinbar.drinktype.equals("blanche")){		
				model.add(drink, RDF.type, blanche);
				model.add(bar,drinkhasdrink,drink);

			}
			else if(drinkinbar.drinktype.equals("rouge")){		
				model.add(drink, RDF.type, rouge);
				model.add(bar,drinkhasdrink,drink);

			}else if(drinkinbar.drinktype.equals("blanc")){		
				model.add(drink, RDF.type, blanc);
				model.add(bar,drinkhasdrink,drink);

			}else if(drinkinbar.drinktype.equals("rose")){		
				model.add(drink, RDF.type, rose);
				model.add(bar,drinkhasdrink,drink);

			}
			else if(drinkinbar.drinktype.equals("rose")){		
				model.add(drink, RDF.type, rose);
				model.add(bar,drinkhasdrink,drink);
			}
			else if(drinkinbar.drinktype.equals("Alcools forts")){		
				model.add(drink, RDF.type, alcoolfort);
				model.add(bar,drinkhasdrink,drink);

			}
			else if(drinkinbar.drinktype.equals("Sans alcool")){		
				model.add(drink, RDF.type, sansalcool);
				model.add(bar,drinkhasdrink,drink);

			}

		}
		System.out.println("likes size : "+userlikes.size());

		for(int ii=0; ii<userlikes.size();ii++){
			User user = userlikes.get(ii);
			Resource likeresource = model.createResource(DRINKURI+"infouser?idvar="+user.idfacebook);
			model.add(likeresource,RDF.type,baruser);
			model.add(bar,drinklike,likeresource);
		}




		String notestring = Double.toString(note);

		bar.addProperty(rating,notestring);

		OutputStream out = new ByteArrayOutputStream();

		model.write(out, "RDF/XML-ABBREV");

		System.out.println(out.toString());
		//return ok(out.toString());

		// exporte le resultat dans un fichier

		barname = containsWhiteSpace(barname);
		File file = new File("public/data/"+barname+".rdf");

		try (FileOutputStream fop = new FileOutputStream(file)) {

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			//byte[] contentInBytes = model.toString().getBytes();
			model.write(fop, "RDF/XML-ABBREV");
			//fop.write(contentInBytes);
			fop.flush();
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 model.write(outStream, "RDF/XML-ABBREV");


		FileOutputStream outStream =null;
		outStream = new FileOutputStream("public/data/"+barname+".rdf");

		outStream.close();
		if(outStream!=null){
			outStream.close();
		}*/

	}


	public static void generateRDFAllBars(String listbar) throws UnsupportedEncodingException{
		String [] split = listbar.split(",");
		Model model = ModelFactory.createDefaultModel();
		existingsprefixes = new ArrayList<String>();
		existingsprefixes.add("foaf: <"+ NS_FOAF+">");
		existingsprefixes.add("geo: <"+ RDF_GEO+">");
		existingsprefixes.add("nt : <"+ RDF_TAGS+">");
		existingsprefixes.add("sioc: <"+ SIOG_TAG+">");
		existingsprefixes.add("drinkadvisor: <"+ DRINKURI+">");
		existingsprefixes.add("rating: <"+ RATING_TAG+">");
		existingsprefixes.add("geonames: <"+geonames+">");
		existingsprefixes.add("dbpedia: <"+ dbpedia+">");

		for(int i = 0 ; i< split.length; i++){
			String barname = split[i];
			barname = new String(barname.getBytes(),"UTF-8");

			if(barname.contains("&#x27;")){
				System.out.println("found strange caracter");
				barname = 	barname.replaceAll("&#x27;", "'");
			}

			if(barname.contains("&amp;")){
				barname = 	barname.replaceAll("&amp;", "&");

			}

			String barnameURI = containsWhiteSpace(barname);

			barnameURI = new String(barnameURI.getBytes(),"UTF-8");
			System.out.println(barname);
			System.out.println("in rdfbar");



			model.setNsPrefix("foaf", NS_FOAF);
			model.setNsPrefix("geo", RDF_GEO);
			model.setNsPrefix("nt", RDF_TAGS);
			model.setNsPrefix("sioc", SIOG_TAG);
			model.setNsPrefix("drinkadvisor", DRINKURI);
			model.setNsPrefix("rating", RATING_TAG);
			model.setNsPrefix("geonames",geonames);
			model.setNsPrefix("dbpedia",dbpedia);




			String barURI = "http://localhost:9000/bar?name="+barnameURI;
			Bar rdfbar = Bar.find.where().eq("name", barname).findUnique();
			String lon = rdfbar.lon;
			String lat = rdfbar.lat;
			String adresse = rdfbar.adresse;
			String mail = rdfbar.mail;
			int note = rdfbar.note;
			String website = rdfbar.website;
			String telephone = rdfbar.telephone;

			List<Comment> commentlist = rdfbar.comments;
			List<BeerInBar> drinklist=rdfbar.beerlist;
			List<User> userlikes = rdfbar.likes;

			RDFNode[] elems = new RDFNode[userlikes.size()];

			Property siocauthor = model.createProperty(SIOG_TAG+"has_creator");
			Property sioctopic = model.createProperty(SIOG_TAG+"topic");
			Property siocpost = model.createProperty(SIOG_TAG+"post");

			Resource drinkress = model.createResource( DRINKURI + "Drink" );
			Resource alcoolfort = model.createResource( DRINKURI + "AlcoolFort" );
			Resource sansalcool = model.createResource( DRINKURI + "SansAlcool" );
			Resource biere = model.createResource(DRINKURI + "Biere");
			Resource blonde = model.createResource(DRINKURI + "Blonde");
			Resource blanche = model.createResource(DRINKURI + "Blanche");
			Resource brune = model.createResource(DRINKURI + "Brune");
			Resource rousse = model.createResource(DRINKURI);
			Resource vin = model.createResource(DRINKURI + "Vin");
			Resource blanc = model.createResource(DRINKURI + "Blanc");
			Resource rouge = model.createResource(DRINKURI + "Rouge");
			Resource rose = model.createResource(DRINKURI + "Rose");

			Property rating  = model.createProperty( RATING_TAG + "value" );
			Property latprop = model.createProperty( RDF_GEO + "lat" );
			Property lonprop = model.createProperty( RDF_GEO + "lon" );
			Property geolocation = model.createProperty( RDF_GEO + "location" );

			Property drinkcommentProp = model.createProperty( DRINKURI + "hasComment" );
			Property drinklike = model.createProperty( DRINKURI + "hasLike" );
			Property drinkhasdrink = model.createProperty(DRINKURI+"hasdrink");
			Property drinkmessage = model.createProperty(DRINKURI+"message");
			Property baruser = model.createProperty(DRINKURI+"user");
			Property drinkprix = model.createProperty( DRINKURI + "drinkprix" );



			Property propcountry = model.createProperty(RDF_GEO, "locationCountry");
			Property propcity = model.createProperty(RDF_GEO, "locationCity");

			Resource post = model.createResource(SIOG_TAG+"POST");
			Resource drinkcomment = model.createResource(DRINKURI+"Comment");
			Resource beer = model.createResource(DRINKURI+"drink");

			Resource bar = model.createResource(barURI);
			Resource ressourceBar = model.createResource(DRINKURI+"Bar");
			model.add(bar, RDF.type, ressourceBar);
			Resource like = model.createResource(DRINKURI+"Like");

			bar.addProperty(RDFS.label, barname);
			bar.addProperty(lonprop, lon);
			bar.addProperty(latprop, lat);
			bar.addProperty(FOAF.homepage, website);
			bar.addProperty(FOAF.phone, telephone);
			bar.addProperty(FOAF.mbox, mail);

			if(adresse!=null){
				bar.addProperty(VCARD.ADR, adresse);
			}



			int commentcounter=0;
			for(Comment com:commentlist){
				Resource comment = model.createResource(DRINKURI+barnameURI+"#comment"+commentcounter);
				comment.addProperty(siocauthor, com.username);
				comment.addProperty(sioctopic, com.barname);
				comment.addProperty(RDFS.comment, com.comment);
				model.add(comment,RDF.type,drinkcomment);
				model.add(bar,drinkcommentProp,comment);
				commentcounter++;
				System.out.println(com.comment);
				//list2.add(comment);
			}


			for(BeerInBar dr: drinklist){
				Drink drinkinbar = Drink.find.where().eq("label", dr.drinkname).findUnique();
				System.out.println("category:"+drinkinbar.categorie);
				Resource drink = model.createResource(DRINKURI+"drink?name="+drinkinbar.label);


				drink.addProperty(RDFS.label, drinkinbar.label);
				drink.addProperty(RDFS.comment , drinkinbar.description);
				drink.addProperty(propcountry, drinkinbar.country);
				drink.addProperty(propcity, drinkinbar.city);



				if(drinkinbar.drinktype.equals("blonde")){		
					model.add(drink, RDF.type, blonde);
					model.add(bar,drinkhasdrink,drink);
				}

				else if(drinkinbar.drinktype.equals("brune")){		
					model.add(drink, RDF.type, brune);
					model.add(bar,drinkhasdrink,drink);
				}

				else if(drinkinbar.drinktype.equals("rousse")){		
					model.add(drink, RDF.type, rousse);
					model.add(bar,drinkhasdrink,drink);
				}
				else if(drinkinbar.drinktype.equals("blanche")){		
					model.add(drink, RDF.type, blanche);
					model.add(bar,drinkhasdrink,drink);

				}
				else if(drinkinbar.drinktype.equals("rouge")){		
					model.add(drink, RDF.type, rouge);
					model.add(bar,drinkhasdrink,drink);

				}else if(drinkinbar.drinktype.equals("blanc")){		
					model.add(drink, RDF.type, blanc);
					model.add(bar,drinkhasdrink,drink);

				}else if(drinkinbar.drinktype.equals("rose")){		
					model.add(drink, RDF.type, rose);
					model.add(bar,drinkhasdrink,drink);

				}
				else if(drinkinbar.drinktype.equals("rose")){		
					model.add(drink, RDF.type, rose);
					model.add(bar,drinkhasdrink,drink);
				}
				else if(drinkinbar.drinktype.equals("Alcools forts")){		
					model.add(drink, RDF.type, alcoolfort);
					model.add(bar,drinkhasdrink,drink);

				}
				else if(drinkinbar.drinktype.equals("Sans alcool")){		
					model.add(drink, RDF.type, sansalcool);
					model.add(bar,drinkhasdrink,drink);

				}

			}
			System.out.println("likes size : "+userlikes.size());

			for(int ii=0; ii<userlikes.size();ii++){
				User user = userlikes.get(ii);
				Resource likeresource = model.createResource(DRINKURI+"infouser?idvar="+user.idfacebook);
				model.add(likeresource,RDF.type,baruser);
				model.add(bar,drinklike,likeresource);
			}





			String notestring = Double.toString(note);

			bar.addProperty(rating,notestring);

		}

		List<User> userliste = User.find.all();



		for(User user : userliste){

			String userURI = "http://localhost:9000/infouser?idvar="+user.idfacebook;

			Resource person = model.createResource(userURI);


			Resource ressourceUser = model.createResource(DRINKURI+"User");

			Property id = model.createProperty(SIOG_TAG+"id");
			Property homeTown = model.createProperty(geonames+"hometown");
			Property locati = model.createProperty(geonames+"location");
			Property knowsDA = model.createProperty(DRINKURI+"knowsDA");

			model.add(person, RDF.type, ressourceUser);

			Resource locat = model.createResource(geonames+user.location);

			person.addProperty(id, user.idfacebook);
			person.addProperty(FOAF.name, user.name);
			person.addProperty(FOAF.nick, user.username);
			person.addProperty(FOAF.birthday, user.birthday);
			person.addProperty(FOAF.gender, user.gender);
			person.addProperty(FOAF.mbox, user.email);
			person.addProperty(FOAF.accountName, user.link);
			person.addProperty(homeTown, user.hometown);
			person.addProperty(FOAF.based_near, locat.addProperty(locati, user.location));

			List<User> listefriendda = user.userfriends;
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

		}


		List<Drink> drinkliste = Drink.find.all();
		Resource ressourceUser = model.createResource(DRINKURI+"User");


		Resource drinkres = model.createResource( DRINKURI + "Drink" );
		Resource alcoolfort = model.createResource( DRINKURI + "AlcoolFort" );
		Resource sansalcool = model.createResource( DRINKURI + "SansAlcool" );
		Resource biere = model.createResource(DRINKURI + "Biere");
		Resource blonde = model.createResource(DRINKURI + "Blonde");
		Resource blanche = model.createResource(DRINKURI + "Blanche");
		Resource brune = model.createResource(DRINKURI + "Brune");
		Resource rousse = model.createResource(DRINKURI);
		Resource vin = model.createResource(DRINKURI + "Vin");
		Resource blanc = model.createResource(DRINKURI + "Blanc");
		Resource rouge = model.createResource(DRINKURI + "Rouge");
		Resource rose = model.createResource(DRINKURI + "Rose");

		Property locationCountry = model.createProperty( DRINKURI + "country" );
		Property locationCity = model.createProperty( DRINKURI + "city" );

		for(Drink d : drinkliste){

			String drinkuri = "http://localhost:9000/drink?name="+d.label;

			Resource drink = model.createResource(drinkuri);

			drink.addProperty( RDFS.label, d.label);
			drink.addProperty(RDFS.comment , d.description);

			if(d.country!=null && !d.country.equals("")){
				drink.addProperty(locationCountry, d.country);
			}
			if(d.city!=null && !d.city.equals("")){
				drink.addProperty(locationCity, d.city);
			}

			if(d.drinktype.equals("blonde")){		
				model.add(drink, RDF.type, blonde);}

			else if(d.drinktype.equals("brune")){		
				model.add(drink, RDF.type, brune);}

			else if(d.drinktype.equals("rousse")){		
				model.add(drink, RDF.type, rousse);}
			else if(d.drinktype.equals("blanche")){		
				model.add(drink, RDF.type, blanche);
			}
			else if(d.drinktype.equals("rouge")){		
				model.add(drink, RDF.type, rouge);
			}else if(d.drinktype.equals("blanc")){		
				model.add(drink, RDF.type, blanc);
			}else if(d.drinktype.equals("rose")){		
				model.add(drink, RDF.type, rose);
			}
			else if(d.drinktype.equals("rose")){		
				model.add(drink, RDF.type, rose);
			}
			else if(d.drinktype.equals("Alcools forts")){		
				model.add(drink, RDF.type, alcoolfort);
			}
			else if(d.drinktype.equals("Sans alcool")){		
				model.add(drink, RDF.type, sansalcool);
			}


		}

		System.out.println("avant creation");

		SparqlEndpoint.CreationTDBModel(model);


		OutputStream out = new ByteArrayOutputStream();

		model.write(out, "RDF/XML-ABBREV");
		//System.out.println(out.toString());
		//return ok(out.toString());

		// exporte le resultat dans un fichier

		File file = new File("public/data/allBars.rdf");

		try (FileOutputStream fop = new FileOutputStream(file)) {

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			//byte[] contentInBytes = model.toString().getBytes();
			model.write(fop, "RDF/XML-ABBREV");

			//model.write(System.out, "RDF/XML-ABBREV");

			//fop.write(contentInBytes);
			fop.flush();
			fop.close();

			System.out.println("Done");


		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 model.write(outStream, "RDF/XML-ABBREV");


		FileOutputStream outStream =null;
		outStream = new FileOutputStream("public/data/"+barname+".rdf");

		outStream.close();
		if(outStream!=null){
			outStream.close();
		}*/

		Form<Query> requeteform = Form.form(Query.class);
	}


	public static void generateRDFSingleUser(String barname){


	}


	public static void generateRDFSingleDrink(String drinkname){
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("foaf", NS_FOAF);
		model.setNsPrefix("geo", RDF_GEO);
		model.setNsPrefix("nt", RDF_TAGS);
		model.setNsPrefix("sioc", SIOG_TAG);
		model.setNsPrefix("drinkadvisor", DRINKURI);
		model.setNsPrefix("rating", RATING_TAG);
		model.setNsPrefix("dbpedia",dbpedia);

		Resource drinkres = model.createResource( DRINKURI + "Drink" );
		Resource alcoolfort = model.createResource( DRINKURI + "AlcoolFort" );
		Resource sansalcool = model.createResource( DRINKURI + "SansAlcool" );
		Resource biere = model.createResource(DRINKURI + "Biere");
		Resource blonde = model.createResource(DRINKURI + "Blonde");
		Resource blanche = model.createResource(DRINKURI + "Blanche");
		Resource brune = model.createResource(DRINKURI + "Brune");
		Resource rousse = model.createResource(DRINKURI);
		Resource vin = model.createResource(DRINKURI + "Vin");
		Resource blanc = model.createResource(DRINKURI + "Blanc");
		Resource rouge = model.createResource(DRINKURI + "Rouge");
		Resource rose = model.createResource(DRINKURI + "Rose");

		Property locationCountry = model.createProperty( DRINKURI + "country" );
		Property locationCity = model.createProperty( DRINKURI + "city" );


		Drink d =Drink.find.where().eq("label", drinkname).findUnique();

		String drinkuri = "http://localhost:9000/drink?name="+d.label;

		Resource drink = model.createResource(drinkuri);
		Property propcountry = model.createProperty(RDF_GEO, "locationCountry");
		Property propcity = model.createProperty(RDF_GEO, "locationCity");

		drink.addProperty(RDFS.label, d.label);
		drink.addProperty(RDFS.comment , d.description);
		drink.addProperty(propcountry, d.country);
		drink.addProperty(propcity, d.city);

		if(d.country!=null && !d.country.equals("")){
			drink.addProperty(locationCountry, d.country);
		}
		if(d.city!=null && !d.city.equals("")){
			drink.addProperty(locationCity, d.city);
		}

		if(d.drinktype.equals("blonde")){		
			model.add(drink, RDF.type, blonde);}

		else if(d.drinktype.equals("brune")){		
			model.add(drink, RDF.type, brune);}

		else if(d.drinktype.equals("rousse")){		
			model.add(drink, RDF.type, rousse);}
		else if(d.drinktype.equals("blanche")){		
			model.add(drink, RDF.type, blanche);
		}
		else if(d.drinktype.equals("rouge")){		
			model.add(drink, RDF.type, rouge);
		}else if(d.drinktype.equals("blanc")){		
			model.add(drink, RDF.type, blanc);
		}else if(d.drinktype.equals("rose")){		
			model.add(drink, RDF.type, rose);
		}
		else if(d.drinktype.equals("rose")){		
			model.add(drink, RDF.type, rose);
		}
		else if(d.drinktype.equals("Alcools forts")){		
			model.add(drink, RDF.type, alcoolfort);
		}
		else if(d.drinktype.equals("Sans alcool")){		
			model.add(drink, RDF.type, sansalcool);
		}


		OutputStream out = new ByteArrayOutputStream();

		model.write(out, "RDF/XML-ABBREV");

		System.out.println(out.toString());
		//return ok(out.toString());

		// exporte le resultat dans un fichier

		drinkname = containsWhiteSpace(drinkname);
		File file = new File("public/data/Drink-"+drinkname+".rdf");

		try (FileOutputStream fop = new FileOutputStream(file)) {

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			//byte[] contentInBytes = model.toString().getBytes();
			model.write(fop, "RDF/XML-ABBREV");
			//fop.write(contentInBytes);
			fop.flush();
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}



	}


	public static String containsWhiteSpace(String testCode){
		if(testCode != null){
			System.out.println(testCode);

			for(int i = 0; i < testCode.length(); i++){
				if(Character.isWhitespace(testCode.charAt(i))){


					StringBuilder test = new StringBuilder(testCode);
					test.setCharAt(i,'+');
					testCode=test.toString();
					System.out.println("found white space");
				}
			}
		}
		System.out.println(testCode);
		return testCode;

	}



}