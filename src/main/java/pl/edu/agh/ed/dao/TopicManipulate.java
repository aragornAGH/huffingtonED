package pl.edu.agh.ed.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import pl.edu.agh.ed.objects.Topic;

public class TopicManipulate {

	private SessionFactory factory;


	public TopicManipulate(SessionFactory factory) {
		this.factory = factory;
	}

	public  Topic addTopic(String keywords) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Topic topic = new Topic();
			topic.setKeywords(keywords);
			session.persist(topic);
			tx.commit();
			return topic;
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
