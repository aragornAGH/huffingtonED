package pl.edu.agh.ed.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import pl.edu.agh.ed.objects.Comment;
import pl.edu.agh.ed.objects.CommentTopic;
import pl.edu.agh.ed.objects.Topic;

public class CommentTopicManipulate {
	private SessionFactory factory;
	
	public CommentTopicManipulate(SessionFactory factory){
		this.factory = factory;
	}
	
	public CommentTopic addComment(Comment comment, Topic topic, double prob) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			CommentTopic commentTopic = new CommentTopic();
			commentTopic.setComment_id(comment);
			commentTopic.setTopic_id(topic);
			commentTopic.setProbability(prob);
			session.persist(commentTopic);
			tx.commit();
			return commentTopic;
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
