package ter.twitter.suicide.hibernate.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateConfig {

	public static ServiceRegistry serviceRegistry;
	public static SessionFactory sessionFactory;

	public static void init(){
		try{
			if(sessionFactory		== null && serviceRegistry == null){
				// This step will read hibernate.cfg.xml and prepare hibernate for use
				Configuration cfg	= new Configuration().configure();
				serviceRegistry 	= new ServiceRegistryBuilder().applySettings(
						cfg.getProperties()).buildServiceRegistry();
				sessionFactory 		= cfg.buildSessionFactory(serviceRegistry);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

	}


}
