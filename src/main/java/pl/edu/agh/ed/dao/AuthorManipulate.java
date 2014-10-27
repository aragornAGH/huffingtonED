package pl.edu.agh.ed.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import pl.edu.agh.ed.objects.Author;

public class AuthorManipulate {
	private static SessionFactory factory;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		try {
			factory = new AnnotationConfiguration().configure().
			// addPackage("com.xyz") //add package if used.
					addAnnotatedClass(Author.class).buildSessionFactory();

		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		AuthorManipulate AM = new AuthorManipulate();

		/* Add few employee records in database */
		AM.addAuthor("www.onetaaa.pl", "Ali");

		/* List down all the employees */
		// AM.listEmployees();

	}

	public Integer addAuthor(String link, String name) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeID = null;
		try {
			tx = session.beginTransaction();
			Author author = new Author();
			author.setName(name);
			author.setLink(link);
			session.persist(author);
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

	/* Method to READ all the employees having salary more than 2000 */
	public void listEmployees() {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(Author.class);
			// Add restriction.
			cr.add(Restrictions.like("name", "Brian", MatchMode.START));

			for (Iterator iterator = cr.list().iterator(); iterator.hasNext();) {
				Author author = (Author) iterator.next();
				System.out.print("First Name: " + author.getName());
				System.out.print("\nLink: " + author.getLink() + "\n\n");
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
