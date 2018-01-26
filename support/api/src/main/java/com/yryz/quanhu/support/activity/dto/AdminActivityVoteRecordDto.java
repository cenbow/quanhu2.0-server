package com.yryz.quanhu.support.activity.dto;



import com.yryz.quanhu.support.activity.entity.ActivityVoteRecord;

import java.util.List;
import java.util.Date;
public class AdminActivityVoteRecordDto extends ActivityVoteRecord {
    private String nickName;
    private String title;
    private Integer startVote;
    private Integer endVote;
    private Date beginCreateDate;	// 开始 参与时间
  	private Date endCreateDate;		// 结束  参与时间
  	private List<String> custIds;
	private Integer pageNo = 1;
	
	private Integer pageSize = 10;
	
    public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getStartVote() {
        return startVote;
    }

    public void setStartVote(Integer startVote) {
        this.startVote = startVote;
    }

    public Integer getEndVote() {
        return endVote;
    }

    public void setEndVote(Integer endVote) {
        this.endVote = endVote;
    }

    public List<String> getCustIds() {
        return custIds;
    }

    public void setCustIds(List<String> custIds) {
        this.custIds = custIds;
    }
}