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

	private SessionFactory factory;

	public PostManipulate(SessionFactory factory) {
		this.factory = factory;
	}

	public Post addPost(String content, Date date, String link,
			String title, Author author, Category category, Boolean done) {
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
}