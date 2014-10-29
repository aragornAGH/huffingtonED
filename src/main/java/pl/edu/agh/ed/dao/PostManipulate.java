package pl.edu.agh.ed.dao;

import java.util.Date;
import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import pl.edu.agh.ed.objects.Author;
import pl.edu.agh.ed.objects.Category;
import pl.edu.agh.ed.objects.Post;

public class PostManipulate {

	private SessionFactory factory;

	public PostManipulate(SessionFactory factory) {
		this.factory = factory;
	}

	public Integer addPost(String content, Date date, String link,
			String title, Author author, Category category, Boolean done) {
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
			post.setAuthor(author);
			post.setCategory(category);
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

	public boolean checkIfExistsLink(String site) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(Post.class);
			// Add restriction.
			cr.add(Restrictions.eq("link", site));

			for (Iterator iterator = cr.list().iterator(); iterator.hasNext();) {
				Post post = (Post) iterator.next();
				return true;
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}

	public void addPost(Post post) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.persist(post);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public Post getPostByLink(String link) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(Post.class);
			// Add restriction.
			cr.add(Restrictions.eq("link", link));

			for (Iterator iterator = cr.list().iterator(); iterator.hasNext();) {
				Post postFinded = (Post) iterator.next();
				return postFinded;
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