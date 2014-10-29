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
@Table(name = "topics")
public class Topic implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "keywords")
	private String keywords;

	@OneToMany
	private Set<PostTopic> postTopics;
	
	@OneToMany
	private Set<CommentTopic> commentTopics;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Set<PostTopic> getPostTopics() {
		return postTopics;
	}

	public void setPostTopics(Set<PostTopic> postTopics) {
		this.postTopics = postTopics;
	}

	public Set<CommentTopic> getCommentTopics() {
		return commentTopics;
	}

	public void setCommentTopics(Set<CommentTopic> commentTopics) {
		this.commentTopics = commentTopics;
	}
}
