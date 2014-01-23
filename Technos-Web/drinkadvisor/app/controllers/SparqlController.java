package controllers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.hp.hpl.jena.query.QuerySolution;

import models.Drink;
import models.GenerateRDF;
import models.Query;
import models.ResultQuery;
import models.Sparql;
import models.SparqlEndpoint;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class SparqlController extends Controller{

	static Form<Query> requeteform = Form.form(Query.class);

	public static String test ="";
	public static ResultQuery result;
	public static Result creatsiteempty()
	{

		if(request().accepts("text/html"))
			return ok(views.html.sparql.render(GenerateRDF.existingsprefixes));


		return badRequest();

	}

	public static Result creatsite()
	{

		System.out.println("create site");
		return ok(views.html.sparqlresult.render(result,GenerateRDF.existingsprefixes));

	}


	public static Result submitRequete() throws JSONException
	{
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		String requete = dynamicForm.get("requete");

		System.out.println(requete);
		String[][] res = SparqlEndpoint.executeForResult(requete);
		result = new ResultQuery(res);
		System.out.println("before create site");
		//creatsite();
		return redirect(routes.SparqlController.creatsite());
	}


}
