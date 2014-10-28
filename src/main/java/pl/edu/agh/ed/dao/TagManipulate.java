package pl.edu.agh.ed.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import pl.edu.agh.ed.objects.Tag;

public class TagManipulate {

	private SessionFactory factory;
	
	public TagManipulate(SessionFactory factory){
		this.factory = factory;
	}
	
	public Tag addComment(String name) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Tag tag = new Tag();
			tag.setName(name);
			session.persist(tag);
			tx.commit();
			return tag;
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
