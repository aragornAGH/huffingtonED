package pl.edu.agh.ed.dao;

import java.util.Date;

import org.hibernate.SessionFactory;

import pl.edu.agh.ed.objects.Author;
import pl.edu.agh.ed.objects.Category;
import pl.edu.agh.ed.objects.Post;

public class ManipulateData {
	static SessionFactory factory;

	public static void main(String[] args) {

		AuthorManipulate AM = new AuthorManipulate(FactoryMaker.getSessionFactory(Author.class));

		Author a = AM.getAuthorStartWith("Brian");
		System.out.println(a);
		CategoryManipulate CM = new CategoryManipulate(FactoryMaker.getSessionFactory(Category.class));
		

		Category category = CM.addCategory("kategoria3333");
		PostManipulate PM = new PostManipulate(FactoryMaker.getSessionFactory(Post.class));
		
		PM.addPost("tre��", new Date(), "www.wp.pl", "tytul", a, category, Boolean.TRUE);
	}
}
