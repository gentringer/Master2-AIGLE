package controllers;

import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.query.QuerySolution;


import models.Drink;
import play.*;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

public class BeerInfo extends Controller {

	static Form<Drink> beerform = Form.form(Drink.class);

	@play.db.ebean.Transactional
	public static Result listBeers(String name)
	{
		List<QuerySolution>  beer;
		beer = Drink.all(name);
		List<Drink> drinks = new ArrayList<Drink>();

		for(QuerySolution q: beer){
			String splitter = q.get("beerlabel").toString();
			if(splitter.contains("@en")){
				Drink b = new Drink();
				if(q.get("country")!=null){
					b.city=q.get("country").toString();
				}




				String [] split  = splitter.split("@");

				String [] splitt  = split[0].split(" ");
				Drink beerfind = Drink.find.where().eq("label", splitt[0]).findUnique();


				b.label=splitt[0];
				b.description=q.get("abstract").toString();
				//b.abstractt="abstract";
				b.country = q.get("test").toString();
				System.out.println(b.description);
				System.out.println(b.city);
				System.out.println(b.label);
				if(beerfind==null){
					b.save();
				}
				drinks.add(b);

			}
		}

		if(request().accepts("text/html"))
			return ok(views.html.infobeers.render(drinks, "Infos sur les bières", beerform, "hello Gilles"));
		else if(request().accepts("application/json"))
			return ok(Json.toJson(beer));
		else if (request().accepts("application/rdf+xml"))
			return ok("this will be RDF XML");

		return badRequest();

	}

	public static Result submitBeers()
	{
		Drink beer = beerform.bindFromRequest().get();
		String test = beerform.bindFromRequest().get().label;
		beer.fillList(test);
		return redirect(routes.BeerInfo.listBeers(test));
	}

	public static Result submitBeer(String beername)
	{
		
		return redirect(routes.BeerInfo.listBeers(beername));
	}
	
	public static Result addBeer(){
		
		return ok(views.html.templatebeer.render("Test"));

		
		
		
	}



}
