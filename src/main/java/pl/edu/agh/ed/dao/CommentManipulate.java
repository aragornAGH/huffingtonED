package pl.edu.agh.ed.dao;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import pl.edu.agh.ed.objects.Author;
import pl.edu.agh.ed.objects.Category;
import pl.edu.agh.ed.objects.Comment;
import pl.edu.agh.ed.objects.Post;

public class CommentManipulate {

private SessionFactory factory;
	
	public CommentManipulate(SessionFactory factory){
		this.factory = factory;
	}
	
	public Comment addComment(String content, Date date, String title, Author author, Comment parentComment, Post post) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Comment comment = new Comment();
			comment.setComment(comment);
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
}
