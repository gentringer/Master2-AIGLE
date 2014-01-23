package models;

import play.db.ebean.Model;

public class ResultQuery extends Model{
	
	
	public String[][] querystring;
	
	public ResultQuery(String[][] res){
		this.querystring=res;
	}
	
	

}
