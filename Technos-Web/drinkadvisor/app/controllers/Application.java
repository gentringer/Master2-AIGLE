package controllers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.avaje.ebean.Ebean;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFList;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.impl.RDFListImpl;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.VCARD;

import models.Bar;
import models.BeerInBar;
import models.Comment;
import models.Drink;
import models.User;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

	static Form<Drink> barform = Form.form(Drink.class);
	public static List<Drink> beerlist;
	public static Map<String,Integer> beerlist2;
	public static List<BeerInBar> beersinbar;
	public static ArrayList<String> sparqlBeer = new ArrayList<String>();

	public static Result index() {

		return ok(index.render("Your new application is ready."));
	}


	//Générer template pour bars
	public static Result bartemplate(String nom){

		//		List<Drink>drinlist=Drink.find.all();
		//		for(Drink dr:drinlist){
		//			dr.delete();
		//		}
		//		
		//		for(Bar bar:Bar.find.all()){
		//			bar.beerlist = new ArrayList<BeerInBar>();
		//			bar.update();
		//		}

		System.out.println("dans beertemplate");
		beerlist = Drink.find.all();
		beerlist2 = new HashMap<String, Integer>();
		Bar ba = Bar.find.where().eq("name", nom).findUnique();
		beersinbar = new ArrayList<BeerInBar>();
		if(ba!=null&&ba.beerlist!=null){
			beersinbar = ba.beerlist;
			System.out.println("beerlist not null");
		}
		for(Drink b: beerlist){
			System.out.println("beer "+b.label );
			beerlist2.put(b.label, 0);

		}
		ArrayList<String> pluses = new ArrayList<String>();
		if(ba.plusone!=null){
			List<User> plusonelist = ba.plusone;


			for(User u:plusonelist){
				pluses.add(u.idfacebook);
			}
		}

		//sparqlBeer.add("test");

		beerlist2.put("Sélectionner bière existante",0);

		System.out.println(beerlist2.size());
		//rdfBar(nom);
		//System.out.println(beersinbar.size());
		if(request().accepts("text/html")){
			return ok(views.html.bar.render(nom,ba,barform,beerlist2,beersinbar,pluses,sparqlBeer));
		}
		else if (request().accepts("application/rdf+xml")){
			
		}
		return badRequest();
	}


	public static Result drinktemplate(String nom){

		/*		List<Drink>drinlist=Drink.find.all();
				for(Drink dr:drinlist){
					dr.delete();
				}
				
				for(Bar bar:Bar.find.all()){
					bar.beerlist = new ArrayList<BeerInBar>();
					bar.update();
				}
*/
		System.out.println("dans drinktemplate");

		Drink drink = Drink.find.where().eq("label", nom).findUnique();

		System.out.println("drinktype: "+drink.drinktype);
		String label="";
		String contry ="";
		String city="";
		String categorie="";
		String type="";

		List<Bar> barlist = Bar.find.all();
		ArrayList<Bar> barliste = new ArrayList<Bar>();

		for(Bar b :barlist){
			for(BeerInBar bib : b.beerlist){
				if(bib.drinkname.equals(drink.label)){
					barliste.add(b);
				}
			}
		}



		if(request().accepts("text/html")){
			return ok(views.html.drink.render(drink, barliste));
		}
		else if (request().accepts("application/rdf+xml")){
		}
		return badRequest();
	}



}
