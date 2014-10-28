package pl.edu.agh.ed.objects;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "tags")
public class Tag {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name = "id")
	private int id;
	
	@Column (name = "name")
	private String name;
	
	@OneToMany
	private Set<PostTag> postTags;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<PostTag> getPostTags() {
		return postTags;
	}

	public void setPostTags(Set<PostTag> postTags) {
		this.postTags = postTags;
	}
}
