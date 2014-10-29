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
import pl.edu.agh.ed.objects.Comment;
import pl.edu.agh.ed.objects.Post;
import pl.edu.agh.ed.objects.Tag;

public class CommentManipulate {

	private SessionFactory factory;

	public CommentManipulate(SessionFactory factory) {
		this.factory = factory;
	}

	public Comment addComment(String content, Date date, String title,
			Author author, Comment parentComment, Post post) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Comment comment = new Comment();
			comment.setContent(content);
			comment.setDate(date);
			comment.setTitle(title);
			comment.setAuthor(author);
			comment.setComment(comment);
			comment.setPost(post);
			session.persist(comment);
			tx.commit();
			return comment;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public Comment getCommentByName(Comment comment) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(Comment.class);
			// Add restriction.
			cr.add(Restrictions.eq("content", comment.getContent()));
			cr.add(Restrictions.eq("date", comment.getDate()));

			for (Iterator iterator = cr.list().iterator(); iterator.hasNext();) {
				Comment commentFinded = (Comment) iterator.next();
				return commentFinded;
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

	public void addComment(Comment comment) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.persist(comment);
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
