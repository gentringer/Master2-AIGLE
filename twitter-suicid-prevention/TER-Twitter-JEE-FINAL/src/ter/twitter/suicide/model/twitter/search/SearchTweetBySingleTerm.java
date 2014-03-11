package ter.twitter.suicide.model.twitter.search;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ter.twitter.suicide.model.hibernate.jpa.Thematics;
import ter.twitter.suicide.model.hibernate.jpa.Tweets;
import ter.twitter.suicide.model.hibernate.queryData.InsertData;
import ter.twitter.suicide.model.hibernate.queryData.Update;
import ter.twitter.suicide.model.thread.ThreadSelectAllTweets;
import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class SearchTweetBySingleTerm {
	
	// -> Variables
	/**
	 * @param args
	 */
	public 	static	List<Status> resulttweet = new ArrayList<Status>();

	public static void getTweetText(Thematics them) throws TwitterException  {

		Twitter 	twitter = TwitterFactory.getSingleton();
		Query 		query   = new Query(them.getTerm());

		QueryResult result  = twitter.search(query);
		Update 		update  = new Update();

		for (Status status : result.getTweets()) 
		{
			resulttweet.add(status);
			InsertData inserter 		= new InsertData();
			HashtagEntity[] hashtag 	= status.getHashtagEntities();
			StringBuilder hashbuilder	= new StringBuilder("");
			int i = 0;
			for(HashtagEntity has : hashtag){
				if(i!=0){
					hashbuilder.append(",");
				}
				hashbuilder.append(has.getText());
				i++;
			}
			Date date   = status.getCreatedAt();
			String hash = hashbuilder.toString();
			
			Tweets tweet = new Tweets(String.valueOf(status.getId()),status.getText(),status.getUser().getScreenName(),hash,status.getUser().getLocation(),date);
			System.err.println("size : "+ThreadSelectAllTweets.listTweets.size());
			boolean test = false;
			System.out.println("id du tweet: "+String.valueOf(status.getId()));
			for(Tweets tw : ThreadSelectAllTweets.listTweets ){
				System.out.println(tw.getId_tweet());

				if(tw.getId_tweet().equals(String.valueOf(status.getId()))){
					test=true;
					System.out.println("now it will be true");
				}
			}
			if(!test){
				System.out.println("add id: "+tweet.getId_tweet());
				ThreadSelectAllTweets.listTweets.add(tweet);
				tweet.getTweetthematics().add(them);
				them.getTweets().add(tweet);
				inserter.insert_tweet(tweet);
				update.updateThematic(them);

			}
			else if(test){
				System.out.println("already exists");
				tweet.getTweetthematics().add(them);
				them.getTweets().add(tweet);
				//insert records
				update.updateThematic(them);
				update.updateTweet(tweet);
			}
		}

		System.out.println("size : "+ resulttweet.size());
	}

}
