package ter.twitter.suicide.model.hibernate.queryData;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ter.twitter.suicide.hibernate.config.HibernateConfig;
import ter.twitter.suicide.model.hibernate.jpa.Thematics;
import ter.twitter.suicide.model.hibernate.jpa.Tweets;

public class SelectSingle {

	
	public SelectSingle() {
	
	}

	@SuppressWarnings("unchecked")
	public List<Tweets> selectSingleTweet(String id){

		Session session	 = null;
		Transaction tx	 = null;
		List<Tweets> obs = new ArrayList<Tweets>();
		try
		{
			session 	 = HibernateConfig.sessionFactory.openSession();
			tx			 = session.getTransaction();
			tx.begin();

			Query query  = session.createQuery("Select sm from Tweets sm inner join sm.tweetthematics p where sm.id_tweet= :idtw");
			query.setParameter("idtw", id);


			obs = query.list();
			session.flush();

			System.out.println(id + " size : "+obs.size());
			
			for(Tweets tw : obs){
				System.out.println(tw.getTweetthematics().size());
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
		
		return obs;
	}
	
	@SuppressWarnings("unchecked")
	public List<Thematics> selectThematicByCategory(String category,String subcategory){

		Session session		= null;
		Transaction tx		= null;
		List<Thematics> obs = new ArrayList<Thematics>();
		try
		{
			session = HibernateConfig.sessionFactory.openSession();
			tx 		= session.getTransaction();
			tx.begin();

			Query query = session.createQuery("Select sm from Thematics sm where sm.category= :cat and sm.subcategory= :subc");
			query.setParameter("cat", category);
			query.setParameter("subc", subcategory);


			obs = query.list();
			session.flush();

			System.out.println(" size : "+ obs.size());

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

		return obs;
	}
}
