package pl.edu.agh.ed.objects;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "authors")
public class Author implements Serializable {

	private static final long serialVersionUID = 2752471707053114715L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name = "id")
	private int id;

	@Column (name = "link")
	private String link;
	
	@Column (name = "name")
	private String name;
	
	@OneToMany
	private Set<Post> posts;
	
	@OneToMany
	private Set<Comment> comments;

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public Author() {
	}

	public Author(String link, String name) {
		this.link = link;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
