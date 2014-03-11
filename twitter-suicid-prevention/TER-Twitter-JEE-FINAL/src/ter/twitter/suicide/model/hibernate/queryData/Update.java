package ter.twitter.suicide.model.hibernate.queryData;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ter.twitter.suicide.hibernate.config.HibernateConfig;
import ter.twitter.suicide.model.hibernate.jpa.Thematics;
import ter.twitter.suicide.model.hibernate.jpa.Tweets;

public class Update {

	public Update() {}

	public void updateThematic(Thematics emp)
	{
		Session session	= null;
		Transaction tx	= null;
		try
		{
			session 	= HibernateConfig.sessionFactory.openSession();
			tx			= session.getTransaction();
			tx.begin();
			session.update(emp);
			tx.commit();
			session.flush();
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
	}


	public void updateTweet(Tweets tw)
	{
		Session session = null;
		Transaction tx  = null;
		try
		{
			session 	= HibernateConfig.sessionFactory.openSession();
			tx			= session.getTransaction();
			tx.begin();
			session.update(tw);
			tx.commit();
			session.flush();
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
	}
}