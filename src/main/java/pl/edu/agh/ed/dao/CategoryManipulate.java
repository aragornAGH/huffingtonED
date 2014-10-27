package pl.edu.agh.ed.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import pl.edu.agh.ed.objects.Author;
import pl.edu.agh.ed.objects.Category;

public class CategoryManipulate {
	private static SessionFactory factory = new AnnotationConfiguration().configure().
			addAnnotatedClass(Category.class).buildSessionFactory();

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		try {
			factory = new AnnotationConfiguration().configure()
					.addAnnotatedClass(Category.class).buildSessionFactory();

		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		CategoryManipulate CM = new CategoryManipulate();

		/* Add few employee records in database */
		CM.addCategory("kategoria");

		/* List down all the employees */
		// AM.listEmployees();

	}
	
	public SessionFactory getFactory (){
		try {
			return new AnnotationConfiguration().configure()
					.addAnnotatedClass(Category.class).buildSessionFactory();

		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public Category addCategory(String name) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeID = null;
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