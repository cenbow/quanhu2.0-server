package com.yryz.quanhu.other.activity.dto;

import java.io.Serializable;

public class AdminConfigObjectDto implements Serializable {

     public AdminConfigContentDto coverPlan;
     public AdminConfigContentDto imgUrl;
     public AdminConfigContentDto videoUrl;
     public AdminConfigContentDto content;
     public AdminConfigContentDto content1;
     public AdminConfigContentDto content2;

     public AdminConfigContentDto getContent() {
          return content;
     }

     public void setContent(AdminConfigContentDto content) {
          this.content = content;
     }

     public AdminConfigContentDto getContent1() {
          return content1;
     }

     public void setContent1(AdminConfigContentDto content1) {
          this.content1 = content1;
     }

     public AdminConfigContentDto getContent2() {
          return content2;
     }

     public void setContent2(AdminConfigContentDto content2) {
          this.content2 = content2;
     }

     public AdminConfigContentDto getCoverPlan() {
          return coverPlan;
     }

     public void setCoverPlan(AdminConfigContentDto coverPlan) {
          this.coverPlan = coverPlan;
     }

     public AdminConfigContentDto getImgUrl() {
          return imgUrl;
     }

     public void setImgUrl(AdminConfigContentDto imgUrl) {
          this.imgUrl = imgUrl;
     }

     public AdminConfigContentDto getVideoUrl() {
          return videoUrl;
     }

     public void setVideoUrl(AdminConfigContentDto videoUrl) {
          this.videoUrl = videoUrl;
     }
}
