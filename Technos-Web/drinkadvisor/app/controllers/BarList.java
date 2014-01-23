package controllers;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import play.libs.Akka;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.libs.F.Function0;
import play.libs.F.Promise;

import javax.persistence.PrePersist;
import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import models.Bar;
import models.Drink;
import models.Friend;
import models.User;
import models.UserModelRDF;
import play.Routes;
import play.data.DynamicForm;
import play.data.Form;
import play.db.DB;
import play.mvc.Call;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.SimpleResult;
import play.mvc.WebSocket;
import scala.concurrent.duration.Duration;

public class BarList extends Controller{

	static Form<User> entryForm = Form.form(User.class);
	static List<Bar> barlist ;



	@PrePersist
	@play.db.ebean.Transactional
	public static Result BarsFromOSM() throws SQLException{
		
		/*Bar bar = Bar.find.where().eq("name", "testbar").findUnique();
		bar.delete();
		Bar bar2 = Bar.find.where().eq("name", "bar universite").findUnique();
		bar2.delete();*/
		
		
		//if(Bar.find.all()!=null){
		barlist = Bar.find.all();
		Collections.sort(barlist, new Comparator<Bar>(){
			public int compare(Bar s1, Bar s2) {
				return s1.name.compareToIgnoreCase(s2.name);
			}
		});



		Akka.system().scheduler().scheduleOnce(Duration.create(5, SECONDS),

				new Runnable() {
			public void run() {
				try {
					listBars();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}},

			Akka.system().dispatcher());


		if(request().accepts("text/html")){
			return ok(views.html.bars.render(barlist, "Bars Montpellier", "Trinkadvisor"));

		}
		return badRequest();


	}


	static List<User> listFriendsDA;
	static UserModelRDF rdfUser = null;

	public static Result Application2(String id){
		
		//listFriendsDA = new ArrayList<>();

		String name = User.getInfoName(id);
		String username = User.getInfoUsername(id);
		String birthday = User.getInfoBirthday(id);
		String gender = User.getInfoGender(id);
		String email = User.getInfoEmail(id);
		String hometown = User.getInfoHometown(id);
		String location = User.getInfoLocation(id);
		String link = User.getInfoLink(id);
		
		System.out.println("blabla1");
		
		listFriendsDA = User.find.where().eq("idfacebook", id).findUnique().userfriends;
		
		System.out.println(listFriendsDA.size());
		//System.out.println("userID = "+ User.find.where().eq("idfacebook", id).findUnique());
		
		for(User us : listFriendsDA)
		{
			System.out.println("name user's friend = "+us.name);
		}
		
		rdfUser = new UserModelRDF();
		
		rdfUser.createUserModel(id, name, username, birthday, email, gender, link, hometown, location, listFriendsDA);
		
		if(request().accepts("text/html"))
		{
			redirect("/infouser?idvar="+id);
			System.out.println("ID = "+id);

			return ok(views.html.infoUser.render(id,name,username,birthday,gender,email,hometown,location,link,listFriendsDA));
		}
		//return badRequest();
		return null;


	}



	public static int ab = 0;
	public static void listBars() throws IOException, SAXException, ParserConfigurationException, SQLException
	{
		List<Bar> bars ;
		bars = Bar.allBars();

		for(Bar osm:bars){
			Bar barfind = Bar.find.where().eq("idbar", osm.idbar).findUnique();
			if(barfind==null){
				osm.searchAdress(osm);
				osm.save();
				barlist.add(barfind);
			}
			else{
				if(barfind.adresse.equals("")){
					osm.searchAdress(osm);
					osm.update();
					System.out.println("adresse: "+osm.adresse);
				}

			}
		}
		
		List<Bar> allbars = Bar.find.all();
		for(Bar bar : allbars){
			if(bar.adresse.equals("")){
				bar.searchAdress(bar);
				bar.update();
				System.out.println("adresse: "+bar.adresse);
			}
		}
	}
	
	public static Result addBar(){


		DynamicForm dynamicForm = Form.form().bindFromRequest();

		String name = dynamicForm.get("barname");
		String phone = dynamicForm.get("bartelephone");
		String mail = dynamicForm.get("barmail");
		String website = dynamicForm.get("barwebsite");
		String adress = dynamicForm.get("baradresse");
		String lon = dynamicForm.get("barlon");
		String lat = dynamicForm.get("barlat");
		
		Bar findbar = Bar.find.where().eq("name", name).findUnique();
		List<Bar> test = Bar.find.all();
		String id = "";
		for(Bar ba : test){
			id=ba.idbar;
		}
		
		int idint = Integer.parseInt(id)+1; 

		if(findbar==null){
			Bar bar = new Bar(String.valueOf(idint+1),name,adress,phone,mail,website,lon,lat);
			
			bar.save();
		}
		

		

		System.out.println("Saved bar with name "+name);
		return redirect(routes.Application.bartemplate(name));
	}


}
