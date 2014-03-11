package ter.twitter.suicide.model.hibernate.queryData;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ter.twitter.suicide.hibernate.config.HibernateConfig;
import ter.twitter.suicide.model.hibernate.jpa.Thematics;
import ter.twitter.suicide.model.hibernate.jpa.Tweets;

public class SelectAll {


	public SelectAll() {}


	@SuppressWarnings("unchecked")
	public List<Thematics> existingsThematics(){

		Session session	= null;
		Transaction tx	= null;
		List<Thematics> thematiques = null;
		try
		{
			session 	= HibernateConfig.sessionFactory.openSession();
			tx			= session.getTransaction();
			tx.begin();
			thematiques = session.createQuery("from Thematics").list();  

			session.flush();

			System.out.println(thematiques.size());
		}catch(HibernateException he){
			if(tx != null)tx.rollback();
			System.out.println("Not able to open session");
			he.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null)
				session.close();
		}

		return thematiques;

	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Tweets> existingTweets(){
		ArrayList<Tweets> existing = new ArrayList<Tweets>();
		
		Session session	= null;
		Transaction tx	= null;
		try
		{
			session = HibernateConfig.sessionFactory.openSession();
			tx		= session.getTransaction();
			tx.begin();
			List<Tweets> listt = session.createQuery("from Tweets").list(); 
			
			for(Tweets tw : listt){
				existing.add(tw);
			}
			session.flush();

			for (Tweets tw : existing) {  
				System.out.println("tweet: "+tw.getContent());  
			}  
		}catch(HibernateException he){
			if(tx!=null)tx.rollback();
			System.out.println("Not able to open session");
			he.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session!=null)
				session.close();
		}
		
		return existing;
	}
	
	
	

}
