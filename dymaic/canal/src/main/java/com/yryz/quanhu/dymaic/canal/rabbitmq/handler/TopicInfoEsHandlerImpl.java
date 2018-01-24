//package com.yryz.quanhu.dymaic.canal.rabbitmq.handler;
//
//import java.util.Optional;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.stereotype.Component;
//
//import com.yryz.quanhu.dymaic.canal.constant.CommonConstant;
//import com.yryz.quanhu.dymaic.canal.dao.ResourceInfoRepository;
//import com.yryz.quanhu.dymaic.canal.entity.CanalMsgContent;
//import com.yryz.quanhu.dymaic.canal.entity.ReleaseInfo;
//import com.yryz.quanhu.dymaic.canal.entity.ResourceInfo;
//import com.yryz.quanhu.dymaic.canal.entity.TopicInfo;
//import com.yryz.quanhu.dymaic.canal.entity.TopicPostInfo;
//
//@Component
//public class TopicInfoEsHandlerImpl implements SyncHandler{
//	@Resource
//	private SyncExecutor syncExecutor;
//	
//	@Resource
//	private ElasticsearchTemplate elasticsearchTemplate;
//	
//	@Resource
//	private ResourceInfoRepository resourceInfoRepository;
//	
//	@PostConstruct
//	private void register() {
//		syncExecutor.register(this);
//	}
//	
//	@Override
//	public Boolean watch(CanalMsgContent msg) {
//		if (CommonConstant.QuanHuDb.DB_NAME.equals(msg.getDbName())
//				&& (CommonConstant.QuanHuDb.TABLE_TOPIC.equals(msg.getTableName())
//				|| CommonConstant.QuanHuDb.TABLE_TOPIC_POST.equals(msg.getTableName())
//				|| CommonConstant.QuanHuDb.TABLE_RELEASE_INFO.equals(msg.getTableName()))) {
//			return true;
//		}
//		return false;
//	}
//
//	@Override
//	public void handler(CanalMsgContent msg) {
//		if (CommonConstant.QuanHuDb.DB_NAME.equals(msg.getDbName())
//				&& CommonConstant.QuanHuDb.TABLE_TOPIC.equals(msg.getTableName())) {
//			doTopicInfo(msg);
//		}
//		if(CommonConstant.QuanHuDb.DB_NAME.equals(msg.getDbName())
//				&& CommonConstant.QuanHuDb.TABLE_TOPIC_POST.equals(msg.getTableName())){
//			doTopicPostInfo(msg);
//		}
//		if(CommonConstant.QuanHuDb.DB_NAME.equals(msg.getDbName())
//				&& CommonConstant.QuanHuDb.TABLE_RELEASE_INFO.equals(msg.getTableName())){
//			doReleaseInfo(msg);
//		}
//	}
//	
//	/**
//	 * 话题
//	 * @param msg
//	 */
//	private void doTopicInfo(CanalMsgContent msg){
//		TopicInfo uinfoBefore = EntityParser.parse(msg.getDataBefore(),TopicInfo.class);
//		TopicInfo uinfoAfter = EntityParser.parse(msg.getDataAfter(),TopicInfo.class);
//		if (CommonConstant.EventType.OPT_UPDATE.equals(msg.getEventType())) {
//			Optional<ResourceInfo> uinfo = resourceInfoRepository.findById(uinfoBefore.getKid());
//			if(uinfo.isPresent()){
//				ResourceInfo resource=uinfo.get();
//				TopicInfo topicInfo = EntityParser.parse(resource.getTopicInfo(), msg.getDataAfter(),TopicInfo.class);
//				resource.setKid(topicInfo.getKid());
//				resource.setTopicInfo(topicInfo);
//				resource.setResourceType(1);//话题
//				resourceInfoRepository.save(resource);
//			}else{
//				ResourceInfo resource=new ResourceInfo();
//				resource.setKid(uinfoAfter.getKid());
//				resource.setTopicInfo(uinfoAfter);
//				resource.setResourceType(1);//话题
//				resourceInfoRepository.save(resource);
//			}
//		}
//		else if (CommonConstant.EventType.OPT_DELETE.equals(msg.getEventType())) {
//			resourceInfoRepository.deleteById(uinfoBefore.getKid());
//		}
//		else if (CommonConstant.EventType.OPT_INSERT.equals(msg.getEventType())) {
//			// 先执行了update则不执行insert
//			Optional<ResourceInfo> uinfo = resourceInfoRepository.findById(uinfoAfter.getKid());
//			if (!uinfo.isPresent()) {
//				ResourceInfo resource=new ResourceInfo();
//				resource.setKid(uinfoAfter.getKid());
//				resource.setTopicInfo(uinfoAfter);
//				resource.setResourceType(1);//话题
//				resourceInfoRepository.save(resource);
//			}
//		}
//	}
//	
//	/**
//	 * 帖子
//	 * @param msg
//	 */
//	private void doTopicPostInfo(CanalMsgContent msg){
//		TopicPostInfo uinfoBefore = EntityParser.parse(msg.getDataBefore(),TopicPostInfo.class);
//		TopicPostInfo uinfoAfter = EntityParser.parse(msg.getDataAfter(),TopicPostInfo.class);
//		if (CommonConstant.EventType.OPT_UPDATE.equals(msg.getEventType())) {
//			Optional<ResourceInfo> uinfo = resourceInfoRepository.findById(uinfoBefore.getKid());
//			if(uinfo.isPresent()){
//				ResourceInfo resource=uinfo.get();
//				TopicPostInfo topicPostInfo = EntityParser.parse(resource.getTopicPostInfo(), msg.getDataAfter(),TopicPostInfo.class);
//				resource.setKid(topicPostInfo.getKid());
//				resource.setTopicPostInfo(topicPostInfo);
//				resource.setResourceType(2);//帖子
//				resourceInfoRepository.save(resource);
//			}else{
//				ResourceInfo resource=new ResourceInfo();
//				resource.setKid(uinfoAfter.getKid());
//				resource.setTopicPostInfo(uinfoAfter);
//				resource.setResourceType(2);//帖子
//				resourceInfoRepository.save(resource);
//			}
//		}
//		else if (CommonConstant.EventType.OPT_DELETE.equals(msg.getEventType())) {
//			resourceInfoRepository.deleteById(uinfoBefore.getKid());
//		}
//		else if (CommonConstant.EventType.OPT_INSERT.equals(msg.getEventType())) {
//			// 先执行了update则不执行insert
//			Optional<ResourceInfo> uinfo = resourceInfoRepository.findById(uinfoAfter.getKid());
//			if (!uinfo.isPresent()) {
//				ResourceInfo resource=new ResourceInfo();
//				resource.setKid(uinfoAfter.getKid());
//				resource.setTopicPostInfo(uinfoAfter);
//				resource.setResourceType(2);//帖子
//				resourceInfoRepository.save(resource);
//			}
//		}
//	}
//	
//	/**
//	 * 文章
//	 */
//	private void doReleaseInfo(CanalMsgContent msg){
//		ReleaseInfo uinfoBefore = EntityParser.parse(msg.getDataBefore(),ReleaseInfo.class);
//		ReleaseInfo uinfoAfter = EntityParser.parse(msg.getDataAfter(),ReleaseInfo.class);
//		if (CommonConstant.EventType.OPT_UPDATE.equals(msg.getEventType())) {
//			Optional<ResourceInfo> uinfo = resourceInfoRepository.findById(uinfoBefore.getKid());
//			if(uinfo.isPresent()){
//				ResourceInfo resource=uinfo.get();
//				ReleaseInfo releaseInfo = EntityParser.parse(resource.getReleaseInfo(), msg.getDataAfter(),ReleaseInfo.class);
//				resource.setKid(releaseInfo.getKid());
//				resource.setReleaseInfo(releaseInfo);
//				resource.setResourceType(3);//文章
//				resourceInfoRepository.save(resource);
//			}else{
//				ResourceInfo resource=new ResourceInfo();
//				resource.setKid(uinfoAfter.getKid());
//				resource.setReleaseInfo(uinfoAfter);
//				resource.setResourceType(3);//文章
//				resourceInfoRepository.save(resource);
//			}
//		}
//		else if (CommonConstant.EventType.OPT_DELETE.equals(msg.getEventType())) {
//			resourceInfoRepository.deleteById(uinfoBefore.getKid());
//		}
//		else if (CommonConstant.EventType.OPT_INSERT.equals(msg.getEventType())) {
//			// 先执行了update则不执行insert
//			Optional<ResourceInfo> uinfo = resourceInfoRepository.findById(uinfoAfter.getKid());
//			if (!uinfo.isPresent()) {
//				ResourceInfo resource=new ResourceInfo();
//				resource.setKid(uinfoAfter.getKid());
//				resource.setReleaseInfo(uinfoAfter);
//				resource.setResourceType(3);//文章
//				resourceInfoRepository.save(resource);
//			}
//		}
//	}
//}
