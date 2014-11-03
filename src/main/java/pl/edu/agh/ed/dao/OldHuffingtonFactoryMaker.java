package pl.edu.agh.ed.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public final class OldHuffingtonFactoryMaker {

	public static SessionFactory getSessionFactory(Class clazz) {
		return new AnnotationConfiguration().configure("/old_huffington.cfg.xml")
				.addAnnotatedClass(clazz.getClass()).buildSessionFactory();
	}
}
