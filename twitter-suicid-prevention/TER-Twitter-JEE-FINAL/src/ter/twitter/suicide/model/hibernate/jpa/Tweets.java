package ter.twitter.suicide.model.hibernate.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity  
@Table(name = "tweets")
public class Tweets  implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id_tweet;
	@Lob
	private String content;
	private String username;
	@Lob
	private String hash_tag;
	private String location;
	@Column(name = "date_post")
	private Date date;

	@ManyToMany 
	@JoinTable(name="tweets_thematics", 
	joinColumns=@JoinColumn(name="id_tweet"),
	inverseJoinColumns={
		@JoinColumn(name="term"),
		@JoinColumn(name="subcategory")}
	)  
	private Collection<Thematics> tweetthematics;


	public Tweets () {

	}

	public Tweets (String id_tweet,String content,String username,
			String hash_tag, String location, Date date) {
		this.id_tweet=id_tweet;
		this.content=content;
		this.username=username;
		this.hash_tag=hash_tag;
		this.location=location;
		this.tweetthematics = new ArrayList<Thematics>();
		this.date=date;

	}

	public String getId_tweet() {
		return id_tweet;
	}

	public void setId_tweet(String id_tweet) {
		this.id_tweet = id_tweet;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHash_tag() {
		return hash_tag;
	}

	public void setHash_tag(String hash_tag) {
		this.hash_tag = hash_tag;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Collection<Thematics> getTweetthematics() {
		return tweetthematics;
	}

	public void setTweetthematics(Collection<Thematics> tweetthematics) {
		this.tweetthematics = tweetthematics;
	}

}