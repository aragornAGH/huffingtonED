package pl.edu.agh.ed.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import pl.edu.agh.ed.objects.Post;
import pl.edu.agh.ed.objects.PostTag;
import pl.edu.agh.ed.objects.Tag;

public class PostTagManipulate {

private SessionFactory factory;
	
	public PostTagManipulate(SessionFactory factory){
		this.factory = factory;
	}
	
	public PostTag addComment(Post post, Tag tag) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			PostTag postTag = new PostTag();
			postTag.setPost_id(post);
			postTag.setTag_id(tag);
			session.persist(postTag);
			tx.commit();
			return postTag;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public void addPostTag(PostTag postTag) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.persist(postTag);
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
