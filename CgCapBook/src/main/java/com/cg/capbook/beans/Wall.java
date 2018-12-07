package com.cg.capbook.beans;
import java.util.Map;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
@Embeddable
public class Wall {
	@OneToMany
	private Map<Integer, Post> posts;
	//paracon
	public Wall(Map<Integer, Post> posts) {
		super();
		this.posts = posts;
	}
	//defcon
	public Wall() {
		super();
	}
	//GnS
	public Map<Integer, Post> getPosts() {
		return posts;
	}
	public void setPosts(Map<Integer, Post> posts) {
		this.posts = posts;
	}
}