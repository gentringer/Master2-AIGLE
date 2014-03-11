package ter.twitter.suicide.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ter.twitter.suicide.hibernate.config.HibernateConfig;
import ter.twitter.suicide.model.hibernate.jpa.Thematics;
import ter.twitter.suicide.model.hibernate.jpa.Tweets;
import ter.twitter.suicide.model.hibernate.queryData.SelectSingle;
import ter.twitter.suicide.model.twitter.GetTweetsHistory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

/**
 * Servlet implementation class TweetDetailServlet
 */
@WebServlet("/TweetDetailServlet")
public class TweetDetailServlet extends HttpServlet {
	
	// -> Variables
	private static final long serialVersionUID = 1L;
	// -> Constructor(s)
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TweetDetailServlet() {
		super();
	}
	// -> Methods
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idTweet = request.getParameter("idtweet");
		HibernateConfig.init();
		SelectSingle selectsingle = new SelectSingle();
		List<Tweets> result = selectsingle.selectSingleTweet(idTweet);
		Tweets tweet = result.get(0);
		Twitter twitter = TwitterFactory.getSingleton();
		User user = null;
		try {
			user = twitter.showUser(tweet.getUsername());
		} catch (TwitterException e) {

			e.printStackTrace();
		}
		Collection<Thematics>thematics = tweet.getTweetthematics();

		JSONObject subcat = new JSONObject();
		int i =1;
		for(Thematics them: thematics){
			try {
				subcat.put(String.valueOf(i), them.getSubcategory());
			} catch (JSONException e) {

				e.printStackTrace();
			}
			i++;
		}
		ArrayList<String>subcategories= new ArrayList<String>();

		for(Thematics them: thematics)
		{
				subcategories.add(them.getSubcategory());
		}

		List<Status> historyStatus = GetTweetsHistory.getHistory(tweet.getUsername());
		
		ArrayList<String> tweetHistory = new ArrayList<String>();
	
		for(Status stat: historyStatus){
			tweetHistory.add(stat.getText());
		}


		JSONObject json = new JSONObject();
		try {
			json.put("name", user.getName());
			json.put("username", tweet.getUsername());
			json.put("subcategory", subcategories);
			json.put("image", user.getBiggerProfileImageURL());
			json.put("geolocalistion", user.getLocation());
			json.put("history", tweetHistory);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		out.print(json);
		out.flush();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
