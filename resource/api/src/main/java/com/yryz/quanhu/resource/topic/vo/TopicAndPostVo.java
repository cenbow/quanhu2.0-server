package com.yryz.quanhu.resource.topic.vo;

import java.io.Serializable;

public class TopicAndPostVo implements Serializable {

    private TopicVo topic;

    private  TopicPostVo post;

    public TopicVo getTopic() {
        return topic;
    }

    public void setTopic(TopicVo topic) {
        this.topic = topic;
    }

    public TopicPostVo getPost() {
        return post;
    }

    public void setPost(TopicPostVo post) {
        this.post = post;
    }
}
