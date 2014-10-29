package pl.edu.agh.ed.dao;

import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import pl.edu.agh.ed.objects.Category;

public class CategoryManipulate {
	private SessionFactory factory;

	public CategoryManipulate(SessionFactory factory) {
		this.factory = factory;
	}

	public void addCategory(Category category) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.persist(category);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public Category addCategory(String name) {
		Category category = new Category();
		category.setCategoryName(name);
		addCategory(category);
		return category;
	}

	public Category getCategoryByName(String categoryName) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(Category.class);
			// Add restriction.
			cr.add(Restrictions.eq("categoryName", categoryName));

			for (Iterator iterator = cr.list().iterator(); iterator.hasNext();) {
				Category category = (Category) iterator.next();
				return category;
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
}