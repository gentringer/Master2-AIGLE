package ter.twitter.suicide.model.twitter;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;


public class TweetSearch {
    
    public static void main(String[] args) {
        Twitter twitter = TwitterFactory.getSingleton();
        Query query = new Query("example");
        QueryResult result = null;
        AddQuery addQuery = new AddQuery();
        addQuery.searchQuery(query, result, twitter);
	}
}
