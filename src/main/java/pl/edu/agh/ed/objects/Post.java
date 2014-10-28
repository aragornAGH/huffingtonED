package pl.edu.agh.ed.objects;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

	@ManyToOne
	@JoinColumn(name = "author_id")
	private Author author;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@OneToMany
	private Set<Comment> comments;
	
	@OneToMany
	private Set<PostTag> postTags;
	
	@OneToMany
	private Set<PostTopic> postTopics;
	
	public Set<PostTopic> getPostTopics() {
		return postTopics;
	}

	public void setPostTopics(Set<PostTopic> postTopics) {
		this.postTopics = postTopics;
	}

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

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<PostTag> getPostTags() {
		return postTags;
	}

	public void setPostTags(Set<PostTag> postTags) {
		this.postTags = postTags;
	}

	public Boolean getZrobiona() {
		return zrobiona;
	}

	public void setZrobiona(Boolean zrobiona) {
		this.zrobiona = zrobiona;
	}

}
