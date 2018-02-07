package com.yryz.quanhu.user.provider;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.user.contants.Constants.ImgAuditStatus;
import com.yryz.quanhu.user.dto.UserImgAuditDTO;
import com.yryz.quanhu.user.dto.UserImgAuditFindDTO;
import com.yryz.quanhu.user.entity.UserImgAudit;
import com.yryz.quanhu.user.service.UserImgAuditApi;
import com.yryz.quanhu.user.service.UserImgAuditService;
import com.yryz.quanhu.user.vo.UserImgAuditVO;

@Service(interfaceClass=UserImgAuditApi.class)
public class UserImgAuditProvider implements UserImgAuditApi {
	private static final Logger logger = LoggerFactory.getLogger(UserImgAuditProvider.class);
	
	@Autowired
	private UserImgAuditService imgService;
	
	@Override
	public Response<Boolean> auditImg(UserImgAuditDTO record) {
		try {
			checkDTO(record);
			UserImgAudit audit = GsonUtils.parseObj(record, UserImgAudit.class);
			imgService.auditImg(audit,audit.getAuditStatus().intValue());
			return ResponseUtils.returnObjectSuccess(true);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("头像审核未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<Boolean> batchAuditImg(UserImgAuditDTO record) {
		try {
			checkDTOs(record);
			imgService.batchAuditImg(record);
			return ResponseUtils.returnObjectSuccess(true);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("头像审核未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<PageList<UserImgAuditVO>> listByParams(UserImgAuditFindDTO findDTO) {
		try {
			PageList<UserImgAuditVO> pageList = new PageList<>(findDTO.getPageNo(), findDTO.getPageSize(), null);
			Page<UserImgAudit> page = imgService.listByUserId(findDTO.getPageNo(), findDTO.getPageSize(), findDTO.getUserId(), findDTO.getAuditStatus());
			List<UserImgAuditVO> auditVOs = GsonUtils.parseList(page.getResult(), UserImgAuditVO.class);
			pageList.setCount(page.getTotal());
			pageList.setEntities(auditVOs);
			return ResponseUtils.returnObjectSuccess(pageList);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("头像审核查询未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}
	private void checkDTO(UserImgAuditDTO auditDTO){
		if (auditDTO == null){
			throw QuanhuException.busiError("传参不合法");
		}
		if(auditDTO.getKid() == null ){
			throw QuanhuException.busiError("kid为空");
		}
		if(auditDTO.getAuditStatus() == null || auditDTO.getAuditStatus() < ImgAuditStatus.SUCCESS.getStatus() || auditDTO.getAuditStatus() > ImgAuditStatus.FAIL.getStatus()){
			throw QuanhuException.busiError("操作类型不合法为空");
		}
	}
	
	private void checkDTOs(UserImgAuditDTO auditDTO){
		if (auditDTO == null){
			throw QuanhuException.busiError("传参不合法");
		}
		if(CollectionUtils.isEmpty(auditDTO.getKids())){
			throw QuanhuException.busiError("kid为空");
		}
		if(CollectionUtils.isEmpty(auditDTO.getUserIds())){
			throw QuanhuException.busiError("userIds为空");
		}
		if(auditDTO.getAuditStatus() == null || auditDTO.getAuditStatus() < ImgAuditStatus.SUCCESS.getStatus() || auditDTO.getAuditStatus() > ImgAuditStatus.FAIL.getStatus()){
			throw QuanhuException.busiError("操作类型不合法为空");
		}
	}
}
