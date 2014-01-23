package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.ebean.Model;

@Entity
public class Comment extends Model {

	
	@Id
	public Long id;
	
	public String userid;
	
	public String username;
	
	public String barname;
	
	public String comment;
	@Temporal(TemporalType.DATE)
	public Date date;
	
	public Comment(String userid, String username, String comment, String barname){
		   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		   this.date = new Date();
		   //System.out.println(dateFormat.format(date));
		this.userid = userid;
		this.username = username;
		this.comment=comment;
		this.barname = barname;
		dateFormat.format(this.date);
	}
	
	
	
	
	public static Model.Finder<Long, Comment> find = new Model.Finder
			<Long, Comment>(Long.class,Comment.class);
	
}
