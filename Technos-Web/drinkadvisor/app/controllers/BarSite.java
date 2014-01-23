package controllers;

import java.util.ArrayList;

import models.Bar;
import models.BeerInBar;
import models.Drink;
import models.Comment;
import models.Sparql;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class BarSite extends Controller{

	
	public static Result saveSparqlResult(String barname) {
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		Application.sparqlBeer = new ArrayList<String>();
		//String barname = dynamicForm.get("barname");
		String drinkname = dynamicForm.get("sparqlName");
		String drinkdescription = dynamicForm.get("sparqlDescription");
		String drinkcategorie = dynamicForm.get("sparqlCategorie");
		String drinkpays = dynamicForm.get("sparqlPays");
		String drinkville = dynamicForm.get("sparqlVille");
		String drinkprix = dynamicForm.get("sparqlPrix");
		String drinktype = dynamicForm.get("sparqlType");
		Drink dr = new Drink(drinkname);
		dr.description=drinkdescription;
		dr.categorie=drinkcategorie;
		dr.country=drinkpays;
		dr.city=drinkville;
		dr.drinktype=drinktype;
		
		Drink beerfind = Drink.find.where().eq("label", drinkname).findUnique();

		if(beerfind==null){
			Bar findbar = Bar.find.where().eq("name", barname).findUnique();

			dr.save();
			System.out.println("name "+findbar.name);
			System.out.println("size "+findbar.beerlist.size());
			BeerInBar beiba = new BeerInBar(dr.label, Integer.parseInt(drinkprix),dr.categorie);
			beiba.save();
			findbar.beerlist.add(beiba);
			findbar.update();

		}

		return redirect(routes.Application.bartemplate(barname));
	}
	
	public static Result addbartobeer(String barname) {
		
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		String prix = dynamicForm.get("prix");
		System.out.println(barname);
		Application.sparqlBeer = new ArrayList<String>();

		Bar findbar = Bar.find.where().eq("name", barname).findUnique();


		if (dynamicForm.get("drinkname")!=null&&dynamicForm.get("selectprix").equals("")){
			String beername = dynamicForm.get("drinkname");
			String category = dynamicForm.get("categoriename");
			String type = dynamicForm.get("drinktype");
			System.out.println("drinktype "+type);

//			String description = dynamicForm.get("drinkdescription");
			String price = dynamicForm.get("drinkprix");
//			String type = dynamicForm.get("drinktype");
	//		String country = dynamicForm.get("drinkcountry");
//			String city = dynamicForm.get("drinkcity");
			Drink beerfind = Drink.find.where().eq("label", beername).findUnique();

			if(beerfind==null){
				System.out.println("category"+category);
	/*			Drink dr = new Drink(beername);
				dr.categorie=category;
				dr.description=description;
				dr.type=type;
				dr.country=country;
				dr.location=city;
				dr.save();
				System.out.println("name "+findbar.name);
				System.out.println("size "+findbar.beerlist.size());
				BeerInBar beiba = new BeerInBar(dr.label, Integer.parseInt(price),dr.categorie);
				beiba.save();
				findbar.beerlist.add(beiba);
				findbar.update();*/
				Application.sparqlBeer = Sparql.querySparql(beername,price,category,type);
			}
		}
		
		// Boisson existante
		System.out.println(dynamicForm.get("selectedname"));
		if(!dynamicForm.get("selectedname").equals("")&&!dynamicForm.get("selectprix").equals("")){
			System.out.println("selectname");
			String selectname = dynamicForm.get("selectedname");
			String selectprice = dynamicForm.get("selectprix");
			System.out.println("selected prix "+selectprice);
			Drink beerfind = Drink.find.where().eq("label", selectname).findUnique();

			if(beerfind!=null){

				System.out.println("beer not null");
				BeerInBar beiba = new BeerInBar(beerfind.label, Integer.parseInt(selectprice),beerfind.categorie);
				beiba.save();
				if(!findbar.beerlist.contains(beerfind)){
					findbar.beerlist.add(beiba);
					findbar.update();
				}

			}
		}

		return redirect(routes.Application.bartemplate(barname));
	}

	public static Result addComment() {


		//		Bar findbar = Bar.find.where().eq("name", barname).findUnique();
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		String commentaire = dynamicForm.get("commentaire");
		String userid = dynamicForm.get("userid");
		String username = dynamicForm.get("username");
		String barname = dynamicForm.get("barname");
		System.out.println(barname);
		System.out.println("add comment");
		Bar findbar = Bar.find.where().eq("name", barname).findUnique();

		System.out.println(userid);
		System.out.println("commentaire" + dynamicForm.get("commentaire"));

		Comment com = new Comment(userid,username,commentaire,barname);
		System.out.println(com.userid);
		System.out.println(com.username);
		System.out.println(com.comment);
		com.save();

		findbar.comments.add(com);


		findbar.update();

		//	 System.out.println(commentaire);
		//	System.out.println(barname);
		//	System.out.println(userid);
		//	System.out.println(commentaire);
		return redirect(routes.Application.bartemplate(barname));




	}


	public static Result updateBar(){


		DynamicForm dynamicForm = Form.form().bindFromRequest();

		String name = dynamicForm.get("inputName");
		String phone = dynamicForm.get("inpputtelephone");
		String mail = dynamicForm.get("inputmail");
		String website = dynamicForm.get("inputwebsite");
		String adress = dynamicForm.get("inputadresse");
		Bar findbar = Bar.find.where().eq("name", name).findUnique();

		if(!adress.equals("")){
			findbar.adresse=adress;
		}
		if(!mail.equals("")){
			findbar.mail=mail;
		}
		if(!website.equals("")){
			findbar.website=website;
		}
		if(!phone.equals("")){
			findbar.telephone=phone;
		}

		findbar.update();

		System.out.println("name update"+name);
		return redirect(routes.Application.bartemplate(name));
	}



}
