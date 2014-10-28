package pl.edu.agh.ed.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import pl.edu.agh.ed.objects.Author;
import pl.edu.agh.ed.objects.Category;

public class CategoryManipulate {
private SessionFactory factory;
	
	public CategoryManipulate(SessionFactory factory){
		this.factory = factory;
	}
		
	public Category addCategory(String name) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Category category = new Category();
			category.setCategoryName(name);
			session.persist(category);
			tx.commit();
			return category;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}
}