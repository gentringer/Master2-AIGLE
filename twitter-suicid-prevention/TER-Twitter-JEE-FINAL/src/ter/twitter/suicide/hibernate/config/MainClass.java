package ter.twitter.suicide.hibernate.config;


import ter.twitter.suicide.model.hibernate.queryData.SelectAll;

public class MainClass {

	public static void main(String[] args) {
		try
		{
			SelectAll select = new SelectAll();
			select.existingsThematics();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
		}
	}

	
}