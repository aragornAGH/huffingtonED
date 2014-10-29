package pl.edu.agh.ed.dao;

import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import pl.edu.agh.ed.objects.Author;

public class AuthorManipulate {

	private SessionFactory factory;
	
	public AuthorManipulate(SessionFactory factory){
		this.factory = factory;
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

	public Author getAuthorStartWith(String name) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(Author.class);
			// Add restriction.
			cr.add(Restrictions.like("name", name, MatchMode.START));

			for (Iterator iterator = cr.list().iterator(); iterator.hasNext();) {
				Author author = (Author) iterator.next();
				return author;
			}
			tx.commit();
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
