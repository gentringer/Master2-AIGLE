package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.hp.hpl.jena.query.QuerySolution;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Drink extends Model{

	public static List<QuerySolution> alldrinks;

	@Id
	public int id;
	public String label;
	@Lob
	public String description;
	public String categorie;
	public String drinktype;
	public String country;
	public String city;


	public static Finder<Long,Drink> find = new Finder(Long.class, Drink.class);

	public Drink(String label) {
		this.label = label;
	}

	public Drink() {
		// TODO Auto-generated constructor stub
	}

	/********************/
	public void update(String label, String desc, String cat, String typ){
		this.label = label;
		this.description = desc;
		this.categorie = cat;
		this.drinktype = typ;
		this.save();
	}

	/***STATIC***********/
	public static List<Drink> all() {
		return find.all();
	}

	public static Drink getById(long id2) {
		return find.byId(id2);
	}

	public static void update(Drink drink) {
		Drink d = getById(drink.id);
		d.update(d.label, d.description, d.categorie, d.drinktype);
		d.save();
	}

	public void fillList(String name){
		alldrinks = Sparql.retourlist(name);
	}

	public static List<QuerySolution> all(String name)
	{
		alldrinks = Sparql.retourlist(name);
		for(QuerySolution q: alldrinks){
			if(q.get("country")==null){
				System.out.println("null");
			}
		}
		return alldrinks;
	}

}
