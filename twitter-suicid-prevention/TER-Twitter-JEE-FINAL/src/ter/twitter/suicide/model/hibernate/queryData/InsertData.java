package ter.twitter.suicide.model.hibernate.queryData;


import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ter.twitter.suicide.hibernate.config.HibernateConfig;
import ter.twitter.suicide.model.hibernate.jpa.Thematics;
import ter.twitter.suicide.model.hibernate.jpa.Tweets;

public class InsertData {

	public InsertData() {

	}

	public void insert_thmeatic(Thematics emp)
	{
		Session session=null;
		Transaction tx=null;
		try
		{
			session =HibernateConfig.sessionFactory.openSession();
			tx=session.getTransaction();
			tx.begin();
			session.save(emp);
			tx.commit();
			session.flush();
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
	}

	

	public void insert_thmeaticList(ArrayList<Thematics> arrayThem)
	{
		Session session=null;
		Transaction tx=null;
		arrayThem.remove(0);
		try
		{
			session =HibernateConfig.sessionFactory.openSession();
			tx=session.getTransaction();
			tx.begin();
			for(Thematics them : arrayThem){
				System.out.println(them.getTerm());
				session.save(them);
			}
			tx.commit();
			session.flush();
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
	}



	public void insert_tweet(Tweets tw)
	{
		Session session=null;
		Transaction tx=null;
		try
		{
			session =HibernateConfig.sessionFactory.openSession();
			tx=session.getTransaction();
			tx.begin();
			session.save(tw);
			tx.commit();
			session.flush();
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
	}

	

}