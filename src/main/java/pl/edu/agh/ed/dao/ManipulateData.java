package pl.edu.agh.ed.dao;

import java.util.Date;

import org.dom4j.Comment;
import org.hibernate.SessionFactory;

import pl.edu.agh.ed.objects.Author;
import pl.edu.agh.ed.objects.Category;
import pl.edu.agh.ed.objects.Post;
import pl.edu.agh.ed.objects.Topic;

public class ManipulateData {
	static SessionFactory factory;

	public static void main(String[] args) {

		
		AuthorManipulate AM = new AuthorManipulate(FactoryMaker.getSessionFactory(Author.class));

		Author a = AM.getAuthorStartWith("Brian");
		CategoryManipulate CM = new CategoryManipulate(FactoryMaker.getSessionFactory(Category.class));
		
		Category category = CM.addCategory("kategoria2131");
		PostManipulate PM = new PostManipulate(FactoryMaker.getSessionFactory(Post.class));
		
		Post post = PM.addPost("treœæ", new Date(), "www.wp.pl", "tytul", a, category, Boolean.TRUE);
		

		CommentManipulate CommM = new CommentManipulate(FactoryMaker.getSessionFactory(Comment.class));
		CommM.addComment("content", new Date(), "tytu³", a, null, post);
	}
}
