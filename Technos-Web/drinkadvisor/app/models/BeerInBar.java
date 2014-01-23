package models;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import play.db.ebean.Model;

@Entity
public class BeerInBar extends Model {

	
	/**
	 * 
	 */
	@Id
	public int id;
	
	public String drinkname;
	public int prix;
	@ManyToMany
	public Bar bar;
	public String categorie;
	
	public BeerInBar(String drinkname, int prix, String categorie){
		this.drinkname=drinkname;
		this.prix=prix;
		this.categorie=categorie;
	}
	
	
}
