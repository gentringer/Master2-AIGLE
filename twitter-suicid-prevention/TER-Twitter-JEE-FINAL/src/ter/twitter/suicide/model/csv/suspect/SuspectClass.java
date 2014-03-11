package ter.twitter.suicide.model.csv.suspect;

public class SuspectClass {
	// -> Variables
	public String idtweet;
	public String content;
	// -> Constructor(s)
	public SuspectClass(String idtweet,String content){
		this.idtweet=idtweet;
		this.content=content;
	}
	// -> Setters And Getters
	public String getIdtweet() {
		return idtweet;
	}

	public void setId(String idtweet) {
		this.idtweet = idtweet;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
