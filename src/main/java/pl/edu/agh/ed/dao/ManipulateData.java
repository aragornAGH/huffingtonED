package pl.edu.agh.ed.dao;

import java.util.Date;

import org.hibernate.SessionFactory;

import pl.edu.agh.ed.objects.Author;
import pl.edu.agh.ed.objects.Category;

public class ManipulateData {
	static SessionFactory factory;

	public static void main(String[] args) {

		AuthorManipulate AM = new AuthorManipulate();

		factory = AM.getFactory();
		Author a = AM.getAuthorStartWith("Brian");
		System.out.println(a);
		CategoryManipulate CM = new CategoryManipulate();
		factory = CM.getFactory();

		Category category = CM.addCategory("kategoria3");
		System.out.println(category);
		PostManipulate PM = new PostManipulate();
		factory = PM.getFactory();
		
		PM.addPost("treœæ", new Date(), "www.wp.pl", "tytul", a, category, Boolean.TRUE);
	}
}
