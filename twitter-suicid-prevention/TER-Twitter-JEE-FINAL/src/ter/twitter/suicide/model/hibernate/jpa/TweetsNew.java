package ter.twitter.suicide.model.hibernate.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity  
@Table(name = "tweetsnew")
public class TweetsNew  implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id_tweet;
	@Lob
	private String content;
	private String username;
	@Lob
	private String hash_tag;
	private String location;

	@ManyToMany 
	@JoinTable(name="tweets_thematics", 
	joinColumns=@JoinColumn(name="id_tweet"),
	inverseJoinColumns={
		@JoinColumn(name="term"),
		@JoinColumn(name="subcategory")}
	)  
	private Collection<Thematics> tweetthematics;

	@Column(name="hurt")
	private double hurt;
	@Column(name="loneliness")
	private double loneliness;
	@Column(name="fear")
	private double fear;
	@Column(name="depression")
	private double depression;
	@Column(name="method")
	private double method;
	@Column(name="insults")
	private double insults;
	@Column(name="anorexia")
	private double anorexia;
	@Column(name="lonely")
	private double lonely;
	@Column(name="sentence")
	private double sentence;
	@Column(name="cyberbullying")
	private double cyberbullying;

	public TweetsNew () {

	}

	public TweetsNew (String id_tweet,String content,String username,String hash_tag, String location)
	{
		this.id_tweet		=	id_tweet;
		this.content		=	content;
		this.username		=	username;
		this.hash_tag		=	hash_tag;
		this.location		=	location;
		this.tweetthematics = 	new ArrayList<Thematics>();
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

	public double getHurt() {
		return hurt;
	}

	public void setHurt(double hurt) {
		this.hurt = hurt;
	}

	public double getLoneliness() {
		return loneliness;
	}

	public void setLoneliness(double loneliness) {
		this.loneliness = loneliness;
	}

	public double getFear() {
		return fear;
	}

	public void setFear(double fear) {
		this.fear = fear;
	}

	public double getDepression() {
		return depression;
	}

	public void setDepression(double depression) {
		this.depression = depression;
	}

	public double getMethod() {
		return method;
	}

	public void setMethod(double method) {
		this.method = method;
	}

	public double getInsults() {
		return insults;
	}

	public void setInsults(double insults) {
		this.insults = insults;
	}

	public double getAnorexia() {
		return anorexia;
	}

	public void setAnorexia(double anorexia) {
		this.anorexia = anorexia;
	}

	public double getLonely() {
		return lonely;
	}

	public void setLonely(double lonely) {
		this.lonely = lonely;
	}

	public double getSentence() {
		return sentence;
	}

	public void setSentence(double sentence) {
		this.sentence = sentence;
	}

	public double getCyberbullying() {
		return cyberbullying;
	}

	public void setCyberbullying(double cyberbullying) {
		this.cyberbullying = cyberbullying;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}