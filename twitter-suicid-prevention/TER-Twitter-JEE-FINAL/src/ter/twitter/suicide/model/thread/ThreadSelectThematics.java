package ter.twitter.suicide.model.thread;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ter.twitter.suicide.hibernate.config.HibernateConfig;
import ter.twitter.suicide.model.hibernate.jpa.Thematics;

public class ThreadSelectThematics extends Thread {


	public static ArrayList<Thematics> listThematics = new ArrayList<Thematics>();
	public String category;
	public String subcategory;

	/**
	 * @param args
	 */
	public ThreadSelectThematics(String category,String subcategory) {
		this.category=category;
		this.subcategory=subcategory;
		
	
	}


	@SuppressWarnings("unchecked")
	public void run(){

		Session session = null;
		Transaction tx	= null;
		List<Thematics> thematiques = null;
		try
		{
			session = HibernateConfig.sessionFactory.openSession();
			tx		= session.getTransaction();
			tx.begin();
			Query query  = session.createQuery("Select sm from Thematics sm where sm.category= :cat and sm.subcategory= :subcat");

			query.setParameter("cat", this.category);
			query.setParameter("subcat", this.subcategory);

			session.flush();
			
			thematiques = query.list();
			System.out.println(thematiques.size());
			listThematics.addAll(thematiques);
			System.out.println(listThematics.size());

		}catch(HibernateException he){
			if(tx != null)tx.rollback();
			System.out.println("Not able to open session");
			he.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session!= null)
				session.close();
		}


	}




}
