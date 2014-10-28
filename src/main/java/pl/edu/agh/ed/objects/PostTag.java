package pl.edu.agh.ed.objects;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="post_tags")
public class PostTag implements Serializable{
	@EmbeddedId
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post_id;
	
	@EmbeddedId
	@ManyToOne
	@JoinColumn(name = "tag_id")
	private Tag tag_id;

	public Post getPost_id() {
		return post_id;
	}

	public void setPost_id(Post post_id) {
		this.post_id = post_id;
	}

	public Tag getTag_id() {
		return tag_id;
	}

	public void setTag_id(Tag tag_id) {
		this.tag_id = tag_id;
	}

	
}
