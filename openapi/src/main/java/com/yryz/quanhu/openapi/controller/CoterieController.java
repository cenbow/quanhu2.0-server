package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.entity.RequestHeader;
import com.yryz.common.response.Response;
import com.yryz.common.utils.StringUtils;
import com.yryz.common.utils.WebUtil;
import com.yryz.quanhu.coterie.service.CoterieApi;
import com.yryz.quanhu.coterie.vo.CoterieBasicInfo;
import com.yryz.quanhu.coterie.vo.CoterieInfo;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.user.service.AccountApi;
import com.yryz.quanhu.user.service.AuthApi;
import com.yryz.quanhu.user.service.UserApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wangtao
 * @version 2.0
 * @date 2018年1月20日 上午11:04:02
 * @Description 私圈接口
 */
@Api(description = "私圈接口")
@RestController
public class CoterieController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final int CHAR_51 = 3;
	private static final String CHAR_3F = "%3F";
	private static final String CHAR_63 = "?";
	@Reference
	private AccountApi accountApi;
	@Reference
	private UserApi userApi;
	@Reference
	private AuthApi authApi;
	@Reference
	private CoterieApi coterieApi ;


	@ApiOperation("发布私圈")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@GetMapping(value = "/{version}/coterieInfo/single")
	public Response<CoterieInfo> publish(@RequestBody CoterieBasicInfo info, String userId, HttpServletRequest request) {
		if (info.getJoinFee().equals(0)) {
			//免费加入方式，成员必须审核
			info.setJoinCheck((byte) 1);
		} else {
			//付费加入方式，成员必须不审核
			info.setJoinCheck((byte) 0);
		}
		return coterieApi.applyCreate(info );
	}
	
	@ApiOperation("设置私圈， 更新圈子的数据")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@GetMapping(value = "/{version}/coterieInfo/config")
	public Response<Boolean> config(String coterieId, @RequestBody CoterieInfo config, HttpServletRequest request) {
		Response<CoterieInfo> rpcRecord = coterieApi.queryCoterieInfo(coterieId);
		CoterieInfo  record=rpcRecord.getData();

		String tempStr;
		if (StringUtils.isNotBlank(tempStr = config.getIcon())) {
			record.setIcon(tempStr);
		}
		if (StringUtils.isNotBlank(tempStr = config.getName())) {
			record.setName(tempStr);
		}
		if (StringUtils.isNotBlank(tempStr = config.getQrUrl())) {
			record.setQrUrl(tempStr);
		}
		if (StringUtils.isNotBlank(tempStr = config.getIntro())) {
			record.setIntro(tempStr);
		}
		if (StringUtils.isNotBlank(tempStr = config.getOwnerIntro())) {
			record.setOwnerIntro(tempStr);
		}
		Integer tempNum;
		if ((tempNum = config.getMemberNum()) != null) {
			//设置最大可加入人数，暂不支持
			//record.setMemberNum(tempNum);
			//circleDBRecord.setMemberNum(tempNum);
		}
		if ((tempNum = config.getJoinFee()) != null) {
			//Assert.isTrue(config.getJoinFee() <= 100,"加入费用不能超过100！" );
			record.setJoinFee(tempNum);
		}
		if ((tempNum = config.getConsultingFee()) != null) {
			//Assert.isTrue(config.getConsultingFee() <= 100 , "咨询费用不能超过100悠然币！" );
			record.setConsultingFee(tempNum);
		}
		if (config.getJoinCheck() != null) {
			record.setJoinCheck(config.getJoinCheck());
		}
		 coterieApi.modifyCoterieInfo(record);
         return null;
	}
	
	/**
	 * 获取私圈详情
	 * @param
	 * @param
	 * @return
	 */
	@ApiOperation("获取私圈详情")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/coterieInfo/single")
	public Response<CoterieInfo> details(String coterieId, HttpServletRequest request) {
		//Assert.notNull(coterieId, "私圈id不能为null！");
		Response<CoterieInfo> coterieInfo = coterieApi.queryCoterieInfo(coterieId);
		CoterieInfo rpcCoterieInfo = coterieInfo.getData();
		try {
			if (rpcCoterieInfo != null) {
				String regroupQrUrl = coterieApi.regroupQr(rpcCoterieInfo).getData();
				coterieInfo.getData().setQrUrl(regroupQrUrl);
			}
		} catch (Exception e) {

			logger.error(String.format("query Coterie details error, coterieId=[%s]", coterieId), e);
		}
		return coterieInfo;

	}

	/**
	 * 获取我创建的私圈详情
	 * @return
	 */
	@ApiOperation("获取我创建的私圈详情")
	@PostMapping(value = "/{version}/coterieInfo/creator")
	@Deprecated
	public Response<List<CoterieInfo>> getMyCreateCoterie(HttpServletRequest request) {

		RequestHeader header = WebUtil.getHeader(request);
		String userid=header.getUserId();
		return coterieApi.getMyCreateCoterie(userid);
	}

	/**
	 * 获取我加入的私圈详情
	 * @return
	 */
	@ApiOperation("获取我加入的私圈详情")
	@PostMapping(value = "/{version}/coterieInfo/list/join")
	@Deprecated
	public Response<List<CoterieInfo>> getMyJoinCoterie(HttpServletRequest request) {

		RequestHeader header = WebUtil.getHeader(request);
		String userid=header.getUserId();
		return coterieApi.getMyJoinCoterie(userid);
	}
/**
 * 分页获取私圈列表
 * @return
 */
 @ApiOperation("分页获取私圈列表")
 @PostMapping(value = "/{version}/coterieInfo/list/")
 @Deprecated
  public Response<List<CoterieInfo>> queryPage(Integer currentPage, Integer pageSize, HttpServletRequest request){
			return coterieApi.queryPage(  currentPage,   pageSize);
  }
	/**
	 *  首页 热门 私圈
	 * @param
	 * @return
	 */
	@ApiOperation("获取热门私圈 ")
	@PostMapping(value = "/{version}/coterieInfo/list/recommend")
	@Deprecated
	public Response<List<CoterieInfo>> getRecommendCoterie(Integer currentPage, Integer pageSize, HttpServletRequest request)
	{

       return coterieApi.queryPageForApp(currentPage,pageSize);
	}



}
