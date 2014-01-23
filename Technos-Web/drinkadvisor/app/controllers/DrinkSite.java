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

public class DrinkSite extends Controller{

	
	

	


	public static Result updateDrink(){


		DynamicForm dynamicForm = Form.form().bindFromRequest();

		String name = dynamicForm.get("inputName");
		String categorie = dynamicForm.get("inputcategorie");
		String type = dynamicForm.get("inputype");
		String description = dynamicForm.get("inputdescription");
		String country = dynamicForm.get("inputcountry");
		String city = dynamicForm.get("inputcity");

		Drink findbar = Drink.find.where().eq("label", name).findUnique();

		findbar.label=name;
		findbar.categorie=categorie;
		findbar.drinktype=type;
		findbar.description=description;
		findbar.country=country;
		findbar.city=city;

		
		findbar.update();

		System.out.println("drink update"+name);
		return redirect(routes.Application.drinktemplate(name));
	}



}
