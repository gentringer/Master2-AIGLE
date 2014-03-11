package ter.twitter.suicide.model.twitter;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class GetTweetsHistory 
{
	
	public static List<Status> getHistory(String username)
	{
		Twitter twitter = new TwitterFactory().getInstance();
		List<Status> statuses = null;
		try 
		{
			statuses = twitter.getUserTimeline(username);
			System.out.println("Showing @" + username + "'s user timeline.");
			for (Status status : statuses) 
			{
				System.out.println(status.getUser().getName() + ":" +status.getText());
			}
		} 
		catch (TwitterException e) 
		{
			e.printStackTrace();
		}
		return statuses;
	}

}
