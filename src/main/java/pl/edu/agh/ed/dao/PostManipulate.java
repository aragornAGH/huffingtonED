package pl.edu.agh.ed.dao;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import pl.edu.agh.ed.objects.Author;
import pl.edu.agh.ed.objects.Category;
import pl.edu.agh.ed.objects.Post;

public class PostManipulate {
	private static SessionFactory factory = new AnnotationConfiguration().configure().
			addAnnotatedClass(Post.class).buildSessionFactory();

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		try {
			factory = new AnnotationConfiguration().configure()
					.addAnnotatedClass(Post.class).buildSessionFactory();

		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		PostManipulate PM = new PostManipulate();

		/* Add few employee records in database */
		
		/* List down all the employees */
		// AM.listEmployees();

	}
	
	public SessionFactory getFactory (){
		try {
			return new AnnotationConfiguration().configure()
					.addAnnotatedClass(Post.class).buildSessionFactory();

		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public Integer addPost(String content, Date date, String link, String title, Author author, Category category, Boolean done) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeID = null;
		try {
			tx = session.beginTransaction();
			Post post = new Post();
			post.setContent(content);
			post.setDate(date);
			post.setLink(link);
			post.setTitle(title);
			post.setAuthor(author.getId());
			post.setCategory(category.getId());
			post.setZrobiona(done);
			session.persist(post);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return employeeID;
	}
}