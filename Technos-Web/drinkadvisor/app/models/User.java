package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
public class User extends Model {

	
	@Id
	public String idfacebook;
	
	public String name;
	public String username;
	public String birthday;
	public String gender;
	public String email;
	public String hometown;
	public String location;
	public String link;
	
    @ManyToMany
	public List<Friend> friendList;
	
	
	@ManyToMany
	public List<Bar> barlike;

	@ManyToMany
	public List<Bar> barsplusone;


    
    @ManyToMany
    @JoinTable(name="FRIENDSHIP",
        joinColumns=
            @JoinColumn(name="FRIEND" ,referencedColumnName="idfacebook"),
        inverseJoinColumns=
            @JoinColumn(name="FRIENDOF" ,referencedColumnName="idfacebook")
        )
    public List<User> userfriends;
    
    
    public User(String id) {
		// TODO Auto-generated constructor stub
    	this.idfacebook = id;
	}
    
    
    
    public User(String idFacebook, String name, String username, 
			String birthday, String gender, String email, String hometown, 
			String location, String link) 
	{
		this.idfacebook = idFacebook;
		this.name = name;
		this.username = username;
		this.birthday = birthday;
		this.gender = gender;
		this.email = email;
		this.hometown = hometown;
		this.location = location;
		this.link = link;
		this.friendList = new ArrayList<Friend>();
		this.userfriends = new ArrayList<User>();
	}

	
	public static Model.Finder<Long, User> find = new Model.Finder
			<Long, User>(Long.class,User.class);
	
	
	
	/*public static void getFriends(String idFace)
	{
		List<User> listfriends = 
		
		for(User f : listfriends)
		{
			System.out.println(f.idfacebook);
		}
		
		
		//return find.fetch("friendList").where().eq("idFacebook",idFacebook).findList();
	}*/
	
	
	public static String getInfoName(String idFacebook)
	{
		User us = User.find.byId(Long.parseLong(idFacebook));
		String name = us.name;
		return name;
	}
	
	public static String getInfoUsername(String idFacebook)
	{
		User us = User.find.byId(Long.parseLong(idFacebook));
		String username = us.username;
		return username;
	}
	
	public static String getInfoBirthday(String idFacebook)
	{
		User us = User.find.byId(Long.parseLong(idFacebook));
		String birthday = us.birthday;
		return birthday;
	}
	
	public static String getInfoGender(String idFacebook)
	{
		User us = User.find.byId(Long.parseLong(idFacebook));
		String gender = us.gender;
		return gender;
	}
	
	public static String getInfoEmail(String idFacebook)
	{
		User us = User.find.byId(Long.parseLong(idFacebook));
		String email = us.email;
		return email;
	}
	
	public static String getInfoHometown(String idFacebook)
	{
		User us = User.find.byId(Long.parseLong(idFacebook));
		String hometown = us.hometown;
		return hometown;
	}
	
	public static String getInfoLocation(String idFacebook)
	{
		User us = User.find.byId(Long.parseLong(idFacebook));
		String location = us.location;
		return location;
	}
	
	public static String getInfoLink(String idFacebook)
	{
		User us = User.find.byId(Long.parseLong(idFacebook));
		String link = us.link;
		return link;
	}
	
	
	
}