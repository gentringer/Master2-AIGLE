package ter.twitter.suicide.model.hibernate.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity  
@Table(name = "thematics")
public class Thematics implements Serializable {


	private static final long serialVersionUID = 1L;
	@Id
	String term;
	String category;
	@Id
	@Column(name = "subcategory")
	String subcategory;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy="tweetthematics",cascade = CascadeType.ALL)
	private Collection<Tweets> tweets;

	public Thematics () {

	}

	public Thematics (String term, String category, String subcategory) {
		this.term=term;
		this.category=category;
		this.subcategory=subcategory;
		this.tweets = new ArrayList<Tweets>();


	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public Collection<Tweets> getTweets() {
		return tweets;
	}

	public void setTweets(Collection<Tweets> tweets) {
		this.tweets = tweets;
	}

	



}