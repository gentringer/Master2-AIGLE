package ter.twitter.suicide.model.thread;


import java.util.ArrayList;

import ter.twitter.suicide.model.hibernate.jpa.Tweets;
import ter.twitter.suicide.model.hibernate.queryData.SelectAll;

public class ThreadSelectAllTweets extends Thread {

	// -> Variables
	public static ArrayList<Tweets> listTweets = new ArrayList<Tweets>();
	public String category;
	public String subcategory;
	
	// -> Constructor(s)
	/**
	 * @param args
	 */
	public ThreadSelectAllTweets() {}

	// -> Methods
	public void run(){
		SelectAll select = new SelectAll();
		listTweets = select.existingTweets();
	}




}
