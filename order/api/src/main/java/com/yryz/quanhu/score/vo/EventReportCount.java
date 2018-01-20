/**
* author:XXX
*/
package com.yryz.quanhu.score.vo;

import java.io.Serializable;

public class EventReportCount implements Serializable {

	private Integer likeCount = 0;

	private Integer favoriteCount = 0;

	private Integer shareCount = 0;

	private Integer viewCount = 0;

	private Integer commentCount = 0;

	public Integer realViewCount=0;

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(Integer favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getRealViewCount() {
		return realViewCount;
	}

	public void setRealViewCount(Integer realViewCount) {
		this.realViewCount = realViewCount;
	}
}