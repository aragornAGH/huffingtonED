package pl.edu.agh.ed.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
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

	public void mergePost(Post post) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.merge(post);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static boolean mergeAllPosts(SessionFactory from, SessionFactory to,
			int firstResult, int maxResults) {
		Session session = from.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("from Post")
					.setFirstResult(firstResult).setMaxResults(maxResults);

			boolean bool = mergePosts(to, query.list());

			tx.commit();
			return bool;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}

	private static boolean mergePosts(SessionFactory to, List<Post> posts) {
		if (posts == null || posts.size() == 0)
			return false;
		Session session = to.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (Post post : posts)
				session.merge(post);
			tx.commit();
			return true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}

	public Post addPost(String content, Date date, String link, String title,
			Author author, Category category, Boolean done) {
		Session session = factory.openSession();
		Transaction tx = null;
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
			return post;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
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