package com.yryz.quanhu.support.activity.dto;

public class AdminConfigObjectDto {

     public AdminConfigContentDto coverPlan;
     public AdminConfigContentDto text;
     public AdminConfigContentDto imgUrl;
     public AdminConfigContentDto videoUrl;

     public AdminConfigContentDto getCoverPlan() {
          return coverPlan;
     }

     public void setCoverPlan(AdminConfigContentDto coverPlan) {
          this.coverPlan = coverPlan;
     }

     public AdminConfigContentDto getText() {
          return text;
     }

     public void setText(AdminConfigContentDto text) {
          this.text = text;
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
