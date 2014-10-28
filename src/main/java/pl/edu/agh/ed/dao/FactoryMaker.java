package pl.edu.agh.ed.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public final class FactoryMaker {
	
	public static SessionFactory getSessionFactory (Class clazz){
		return  new AnnotationConfiguration().configure().
				addAnnotatedClass(clazz.getClass()).buildSessionFactory();
	}
}
