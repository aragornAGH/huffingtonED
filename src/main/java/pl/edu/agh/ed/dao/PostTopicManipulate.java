package pl.edu.agh.ed.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import pl.edu.agh.ed.objects.Post;
import pl.edu.agh.ed.objects.PostTopic;
import pl.edu.agh.ed.objects.Topic;

public class PostTopicManipulate {

	private SessionFactory factory;

	public PostTopicManipulate(SessionFactory factory) {
		this.factory = factory;
	}

	public PostTopic addComment(Post post, Topic topic, double prob) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			PostTopic postTopic = new PostTopic();
			postTopic.setPost_id(post);
			postTopic.setTopic_id(topic);
			postTopic.setPrecision(prob);
			session.persist(postTopic);
			tx.commit();
			return postTopic;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public void addPostTopic(PostTopic postTopic) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.persist(postTopic);
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
