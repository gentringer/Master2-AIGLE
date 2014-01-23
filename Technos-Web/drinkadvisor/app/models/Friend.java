package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
public class Friend extends Model {

	
	@Id
	public String idfacebook;
	
	@ManyToMany
	public User user;
	
	public Friend(String idfacebook){
		this.idfacebook = idfacebook;
	}
	
	
	
	
	public static Model.Finder<Long, Friend> find = new Model.Finder
			<Long, Friend>(Long.class,Friend.class);
	
}
