package pl.edu.agh.ed.dao;

import java.util.Date;

import org.hibernate.SessionFactory;

import pl.edu.agh.ed.objects.Author;
import pl.edu.agh.ed.objects.Category;
import pl.edu.agh.ed.objects.Post;
import pl.edu.agh.ed.objects.Topic;

public class ManipulateData {
	static SessionFactory factory;

	public static void main(String[] args) {

		TopicManipulate TM = new TopicManipulate(FactoryMaker.getSessionFactory(Topic.class));
		TM.addTopic("key1,key2");
		
//		AuthorManipulate AM = new AuthorManipulate(FactoryMaker.getSessionFactory(Author.class));
//
//		Author a = AM.getAuthorStartWith("Brian");
//		CategoryManipulate CM = new CategoryManipulate(FactoryMaker.getSessionFactory(Category.class));
//		
//		Category category = CM.addCategory("kategoria3333");
//		PostManipulate PM = new PostManipulate(FactoryMaker.getSessionFactory(Post.class));
//		
//		PM.addPost("treœæ", new Date(), "www.wp.pl", "tytul", a, category, Boolean.TRUE);
	}
}
