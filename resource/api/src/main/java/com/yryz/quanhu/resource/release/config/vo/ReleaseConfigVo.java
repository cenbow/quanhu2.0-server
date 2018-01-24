package com.yryz.quanhu.resource.release.config.vo;

import java.io.Serializable;

import com.yryz.quanhu.resource.release.config.entity.ReleaseConfig;

/**
* @author wangheng
* @date 2018年1月22日 下午2:52:21
*/
public class ReleaseConfigVo implements Serializable{

    private static final long serialVersionUID = 1L;
    
    /**  
    * @Fields coverPlan : 封面图
    */
    private ReleaseConfig coverPlan;
    /**  
    * @Fields title : 标题
    */
    private ReleaseConfig title;
    /**  
    * @Fields description : 简介
    */
    private ReleaseConfig description;
    /**  
    * @Fields text : 文字  
    */
    private ReleaseConfig text;
    /**  
    * @Fields img : 图片  
    */
    private ReleaseConfig img;
    /**  
    * @Fields audio : 音频  
    */
    private ReleaseConfig audio;
    /**  
    * @Fields video : 视频  
    */
    private ReleaseConfig video;
    /**  
    * @Fields label : 标签
    */
    private ReleaseConfig label;

    public ReleaseConfig getCoverPlan() {
        return coverPlan;
    }

    public void setCoverPlan(ReleaseConfig coverPlan) {
        this.coverPlan = coverPlan;
    }

    public ReleaseConfig getTitle() {
        return title;
    }

    public void setTitle(ReleaseConfig title) {
        this.title = title;
    }

    public ReleaseConfig getDescription() {
        return description;
    }

    public void setDescription(ReleaseConfig description) {
        this.description = description;
    }

    public ReleaseConfig getText() {
        return text;
    }

    public void setText(ReleaseConfig text) {
        this.text = text;
    }

    public ReleaseConfig getImg() {
        return img;
    }

    public void setImg(ReleaseConfig img) {
        this.img = img;
    }

    public ReleaseConfig getAudio() {
        return audio;
    }

    public void setAudio(ReleaseConfig audio) {
        this.audio = audio;
    }

    public ReleaseConfig getVideo() {
        return video;
    }

    public void setVideo(ReleaseConfig video) {
        this.video = video;
    }

    public ReleaseConfig getLabel() {
        return label;
    }

    public void setLabel(ReleaseConfig label) {
        this.label = label;
    }
}
