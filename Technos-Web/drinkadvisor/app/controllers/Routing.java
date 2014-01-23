package controllers;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Bar;
import models.BeerInBar;
import models.Friend;
import models.GenerateJSON;
import models.GenerateRDF;
import models.User;
import models.UserModelJSON;
import models.UserModelRDF;
import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;


public class Routing extends Controller{



	static ArrayList<String> listAmis;

	public static Result addUser(String idfacebook, String name, String username,  String birthday,
			String gender, String email, String hometown, String location, String link, 
			String listfriend)
	{

		/*String val = "26";
		id=val;
		name=val;
		location=val;
		email=val;
		birthday=val;
		gender=val;
		listfriend="1234"+","+"798294398";*/
		User userfind = User.find.where().eq("idfacebook",idfacebook).findUnique();

		if(userfind==null)
		{


			User user = new User(idfacebook, name, username, birthday, 
					gender, email, hometown, location, link);

			System.out.println("friendlist = " + listfriend);

			System.out.println(user.idfacebook);
			System.out.println(user.name);
			System.out.println(user.username);
			System.out.println(user.birthday);
			System.out.println(user.gender);
			System.out.println(user.email);
			System.out.println(user.hometown);
			System.out.println(user.location);
			System.out.println(user.link);



			user.save();
			System.out.println("user saved "+user);	



			System.out.println("blabla2");

			/*----------------------------------------------------------------------------------------------------------------------*/
			/*
			 * 
			 * Récuperer la liste d'amis FB de l'utilisateur
			 * 
			 */

			String [] split = listfriend.split(",");
			for(int i = 0 ; i< split.length; i++)
			{
				Friend f = new Friend(split[i]);

				Friend findfriend = Friend.find.where().eq("idfacebook", split[i]).findUnique();

				if(findfriend==null)
				{
					f.save();
					user.friendList.add(f);
					user.update();
					System.out.println("Added new friend to user");
				}
				else
				{
					user.friendList.add(findfriend);
					user.update();
					//				System.out.println("Updated user's friend");
				}
			}


			/*----------------------------------------------------------------------------------------------------------------------*/

			listAmis = new ArrayList<>();

			for(Friend fr : user.friendList)
			{
				User userfriend = User.find.where().eq("idfacebook",fr.idfacebook).findUnique();

				System.out.println("idFacebook of friend : "+fr.idfacebook); // affiche les amis facebook d'un utilisateur

				listAmis.add(fr.idfacebook); // les ID des amis FB en string

				if(userfriend!=null)
				{
					System.out.println("IdUserFriend: "+userfriend.name);
					if(!user.userfriends.contains(userfriend))
					{
						user.userfriends.add(userfriend);
						user.update();
					}

					if(!userfriend.userfriends.contains(user))
					{
						userfriend.userfriends.add(user);
						userfriend.update();
					}
				}
			}





			/*	for(Friend f : test2)
		{
			System.out.println("friend test2 = "+f.idfacebook);
		}*/

			/*---------------------------------------------------------------------------------------------------------------------*/

			System.out.println("dans les tests");

			User userTest1 = new User("123","testname1","testUsername1", "00/00/0000", "female", "test@test.fr", "hometest", "locationtest", "linktest");
			User userTest2 = new User("456","testname2","testUsername2", "00/00/0000", "female", "test@test.fr", "hometest", "locationtest", "linktest");
			User userTest3 = new User("789","testname3","testUsername3", "00/00/0000", "female", "test@test.fr", "hometest", "locationtest", "linktest");

			User userfindTest1 = User.find.where().eq("idfacebook","123").findUnique();
			User userfindTest2 = User.find.where().eq("idfacebook","456").findUnique();
			User userfindTest3 = User.find.where().eq("idfacebook","789").findUnique();

			if(userfindTest1 == null)
			{
				userTest1.save();
				System.out.println("user saved "+userTest1);	
			}
			else{
				userTest1.update();
				System.out.println("user updated"); 

			}

			if(userfindTest2 == null)
			{
				userTest2.save();
				System.out.println("user saved "+userTest2);	
			}
			else{
				userTest2.update();
				System.out.println("user updated"); 

			}

			if(userfindTest3 == null)
			{
				userTest3.save();
				System.out.println("user saved "+userTest3);	
			}
			else{
				userTest3.update();
				System.out.println("user updated"); 

			}


			Friend f1test = new Friend("123");
			Friend f2test = new Friend("456");
			Friend f3test = new Friend("789");

			Friend findfriend1 = Friend.find.where().eq("idfacebook", "123").findUnique();
			Friend findfriend2 = Friend.find.where().eq("idfacebook", "456").findUnique();
			Friend findfriend3 = Friend.find.where().eq("idfacebook", "789").findUnique();

			if(findfriend1==null)
			{
				f1test.save();
				user.friendList.add(f1test);
				user.update();
				System.out.println("Added new friend to user -- ");
			}
			else
			{
				user.friendList.add(findfriend1);
				user.update();
				System.out.println("Updated user's friend -- ");
			}

			if(findfriend2==null)
			{
				f2test.save();
				user.friendList.add(f2test);
				user.update();
				System.out.println("Added new friend to user -");
			}
			else
			{
				user.friendList.add(findfriend2);
				user.update();
				System.out.println("Updated user's friend -");
			}

			if(findfriend3==null)
			{
				f3test.save();
				user.friendList.add(f3test);
				user.update();
				System.out.println("Added new friend to user -");
			}
			else
			{
				user.friendList.add(findfriend3);
				user.update();
				System.out.println("Updated user's friend -");
			}

			User userfriendTest1 = User.find.where().eq("idfacebook","123").findUnique();
			if(userfriendTest1!=null)
			{
				System.out.println("IdUserFriend: "+userfriendTest1.name);
				if(!userTest1.userfriends.contains(userfriendTest1))
				{
					user.userfriends.add(userfriendTest1);
					user.update();	
					System.out.println("User updated 1");
				}

				if(!userfriendTest1.userfriends.contains(user))
				{
					userfriendTest1.userfriends.add(user);
					userfriendTest1.update();
					System.out.println("User updated 2");
				}
			}

			User userfriendTest2 = User.find.where().eq("idfacebook","456").findUnique();
			if(userfriendTest2!=null)
			{
				System.out.println("IdUserFriend: "+userfriendTest2.name);
				if(!userTest1.userfriends.contains(userfriendTest2))
				{
					user.userfriends.add(userfriendTest2);
					user.update();
					System.out.println("User updated 1");
				}

				if(!userfriendTest2.userfriends.contains(user))
				{
					userfriendTest2.userfriends.add(user);
					userfriendTest2.update();
					System.out.println("User updated 2");
				}
			}

			User userfriendTest3 = User.find.where().eq("idfacebook","789").findUnique();
			if(userfriendTest2!=null)
			{
				System.out.println("IdUserFriend: "+userfriendTest3.name);
				if(!userTest1.userfriends.contains(userfriendTest3))
				{
					user.userfriends.add(userfriendTest3);
					user.update();
					System.out.println("User updated 1");
				}

				if(!userfriendTest3.userfriends.contains(user))
				{
					userfriendTest3.userfriends.add(user);
					userfriendTest3.update();
					System.out.println("User updated 2");
				}
			}

		}
	


	/*List<User> userlist = userfinds.userfriends;

		User userfinds2 = User.find.where().eq("idfacebook", "798294398").findUnique();*/

	//System.out.println("nom: "+userfinds2.name);
	//List<User> userlist2 = userfinds2.userfriends;


	/*for(Friend t : test2){
			System.out.println("test: "+t.idfacebook);
		}*/

	/*for(User u : userlist)
		{
			System.out.println("frienduser: "+u.name);
		}*/

	return redirect(routes.BarList.BarsFromOSM());
}



public static Result javascriptRoutes() {
	response().setContentType("text/javascript");
	return ok(
			Routes.javascriptRouter("jsRoutes",
					// Routes

					controllers.routes.javascript.Routing.addUser(),
					controllers.routes.javascript.Routing.addinfouser(),
					controllers.routes.javascript.Routing.addLike(),
					controllers.routes.javascript.Routing.sendNote(),
					controllers.routes.javascript.Routing.rdfBar(),
					controllers.routes.javascript.Routing.rdfAllBar(),
					controllers.routes.javascript.Routing.rdfDrink(),

					controllers.routes.javascript.Routing.updatBeerinBar(),
					controllers.routes.javascript.Routing.jsonBar(),
					controllers.routes.javascript.Routing.jsonuser(),
					controllers.routes.javascript.Routing.jsonDrink(),
					controllers.routes.javascript.Routing.jsonAllBar()




					)
			);
}


public static Result addLike(String barname,String userid){


	Bar findbar = Bar.find.where().eq("name", barname).findUnique();
	User userfind = User.find.where().eq("idfacebook",userid).findUnique();

	findbar.likes.add(userfind);
	findbar.update();

	userfind.barlike.add(findbar);
	userfind.update();

	System.out.println("sizer of likes"+findbar.likes.size());
	System.out.println("nmae"+findbar.likes.get(0).name);

	System.out.println("sizelikes"+userfind.barlike.size());
	return redirect(routes.Application.bartemplate(barname));

}

public static Result addinfouser(String id){

	System.out.println("dans infouser");
	return redirect(routes.BarList.Application2(id));


}

public static Result jsonuser(String id){

	UserModelJSON.createUserModel(id);

	return redirect(routes.FileService.getFile(id+".json"));
	

}





public static Result sendNote(String userid, String barname){


	Bar bar = Bar.find.where().eq("name", barname).findUnique();
	bar.note+=1;
	User us = new User(userid);
	bar.plusone.add(us);

	bar.update();

	return redirect(routes.Application.bartemplate(barname));

}


public static Result updatBeerinBar(String barname, String beername, String newPrice){

	if(Character.isWhitespace(beername.charAt(0))){

		StringBuilder test = new StringBuilder(beername);
		test.deleteCharAt(0);
		beername=test.toString();
	}

	Bar bar = Bar.find.where().eq("name", barname).findUnique();

	String recieved = beername;

	for(BeerInBar bib :  bar.beerlist){

		String beerinbar = bib.drinkname;



		if(beerinbar.equals(recieved)){

			bib.prix=Integer.parseInt(newPrice);
			bib.update();

		}
	}

	bar.update();


	return redirect(routes.Application.bartemplate(barname));

}



public static Result rdfBar(String barname) throws IOException{


	GenerateRDF.generateRDFSingleBar(barname);

	return redirect(routes.FileService.getFile(barname+".rdf"));

}


public static Result jsonBar(String barname) throws IOException{


	GenerateJSON.generateJSONSingleBar(barname);

	return redirect(routes.FileService.getFile(barname+".json"));

}

public static Result rdfDrink(String drinkname) throws IOException{


	GenerateRDF.generateRDFSingleDrink(drinkname);

	return redirect(routes.FileService.getFile("Drink-"+drinkname+".rdf"));

}

public static Result jsonDrink(String drinkname) throws IOException{


	GenerateJSON.generateJSONSingleDrink(drinkname);

	return redirect(routes.FileService.getFile("Drink-"+drinkname+".json"));

}



public static Result rdfAllBar(String listbar) throws IOException{

	GenerateRDF.generateRDFAllBars(listbar);

	return ok();

	//	return ok();
}

public static Result jsonAllBar(String listbar) throws IOException{

	GenerateJSON.generateJSONAllBars(listbar);

	return redirect(routes.FileService.getFile("allbars.json"));

	//	return ok();
}








}
