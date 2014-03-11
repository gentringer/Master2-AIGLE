/**
 * 
 */
package ter.twitter.suicide.model.twitter;
import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
/**
 * @author Yasser
 *
 */
public  class AddQuery {
	
	HashtagEntity tags[];
	public AddQuery() {}
	// -> Methods
	//Function that allows to add a query
	public void searchQuery(Query query,QueryResult result,Twitter twitter)
	{
		try 
		{
			result = twitter.search(query);
		
	        for (Status status : result.getTweets()  )
	        { 
	        	tags = status.getHashtagEntities();
	        	System.out.println(tags);
	        }
	    } 
		catch (TwitterException e) 
		{
			e.printStackTrace();
		}
	}
}
