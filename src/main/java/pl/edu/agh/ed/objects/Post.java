package pl.edu.agh.ed.objects;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "content")
	private String content;

	@Column(name = "date")
	private Date date;

	@Column(name = "link")
	private String link;

	@Column(name = "title")
	private String title;

	private int author_id;

	private int category_id;

	@Column(name = "zrobiona")
	private Boolean zrobiona;

	public int getId() {
		return id;
	}

	public Post() {
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getAuthor() {
		return author_id;
	}

	public void setAuthor(int author) {
		this.author_id = author;
	}

	public int getCategory() {
		return category_id;
	}

	public void setCategory(int category) {
		this.category_id = category;
	}

	public Boolean getZrobiona() {
		return zrobiona;
	}

	public void setZrobiona(Boolean zrobiona) {
		this.zrobiona = zrobiona;
	}

}
