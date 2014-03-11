package com.mtpAdvisor.classes;



public class User {
	
    private String userid;
    private String username;
    
    
    public User()
    {
        
    }
    public User(String userid, String username) {
        super();
        this.userid = userid;
        this.username = username;
       
    }
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

    
   
}

