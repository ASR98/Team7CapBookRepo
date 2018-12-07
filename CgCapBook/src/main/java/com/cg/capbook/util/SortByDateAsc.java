package com.cg.capbook.util;
import java.util.Comparator;
import com.cg.capbook.beans.Post;
public class SortByDateAsc implements Comparator<Post>{
	@Override
	public int compare(Post o1, Post o2) {
		return o1.getTimeOfPosting().compareTo(o2.getTimeOfPosting());
	}
}