package ter.twitter.suicide.model.hibernate.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  
@Table(name = "results")
public class Results 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	@Column(name="content")
	private String content;
	@Column(name="class")
	private String classe;

	public Results () {}

	public Results (String content,String classe)
	{
		this.content		=	content;
		this.classe			=	classe;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}
}



