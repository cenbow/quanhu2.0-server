package com.yryz.quanhu.behavior.collection.entity;

import com.yryz.common.entity.GenericEntity;
import java.util.Date;
/**
 * 
  * @ClassName: CollectionInfo
  * @Description: 收藏表实体类
  * @author jiangzhichao
  * @date 2018-01-26 17:57:44
  *
 */
public class CollectionInfo extends GenericEntity{
	
	/**
	 * 资源ID
	 */	 
    private  Long resourceId;
    
	/**
	 * 资源类型
	 */	 
    private  String moduleEnum;
    
	/**
	 * 私圈Id
	 */	 
    private  Long coterieId;
    
	/**
	 * 发布用户id
	 */	 
    private  Long userId;
    
	/**
	 * 标题
	 */	 
    private  String title;
    
	/**
	 * 内容
	 */	 
    private  String content;
    
	/**
	 * 图片地址
	 */	 
    private  String imgUrl;
    
	/**
	 * 0正常，1已删除
	 */	 
    private  Integer delFlag;
    
	/**
	 * appId
	 */	 
    private  String appId;
    

	public Long getResourceId() {
		return this.resourceId;
	}
	
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
		
	public String getModuleEnum() {
		return this.moduleEnum;
	}
	
	public void setModuleEnum(String moduleEnum) {
		this.moduleEnum = moduleEnum;
	}
		
	public Long getCoterieId() {
		return this.coterieId;
	}
	
	public void setCoterieId(Long coterieId) {
		this.coterieId = coterieId;
	}
		
	public Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
		
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
		
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
		
	public String getImgUrl() {
		return this.imgUrl;
	}
	
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
		
	public Integer getDelFlag() {
		return this.delFlag;
	}
	
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
		
	public String getAppId() {
		return this.appId;
	}
	
	public void setAppId(String appId) {
		this.appId = appId;
	}
		
}