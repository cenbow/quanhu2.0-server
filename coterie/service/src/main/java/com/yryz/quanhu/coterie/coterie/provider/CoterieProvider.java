package com.yryz.quanhu.coterie.coterie.provider;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;

import javax.imageio.ImageIO;

import com.yryz.quanhu.coterie.coterie.vo.*;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.vo.EventInfo;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.coterie.coterie.service.CoterieService;
import com.yryz.quanhu.coterie.coterie.until.ImageUtils;
import com.yryz.quanhu.coterie.coterie.until.ZxingHandler;
import com.yryz.quanhu.user.service.AccountApi;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import com.yryz.quanhu.score.vo.EventReportVo;

/**
 * 私圈服务实现
 * @author wt
 *
 */
@Service(interfaceClass=CoterieApi.class)
public class CoterieProvider implements CoterieApi {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CoterieService coterieService;

	@Reference
	private UserApi userApi;
	
	@Reference
	private AccountApi accountApi;
	
	@Value("${coterie.qr.url}")
	private String coterieQrUrl;
	
	@Reference
	private EventAPI eventAPI;
	
	/**
	 * 查询私圈信息列表
	 * @param coterieIdList 私圈ID集合
	 * @return
	 */
	@Override
	public Response<List<CoterieInfo>> queryListByCoterieIdList(List<Long> coterieIdList) {
		logger.info("queryListByCoterieIdList params:" + coterieIdList);
		if (CollectionUtils.isEmpty(coterieIdList)) {
			return ResponseUtils.returnListSuccess(Lists.newArrayList());
		}
		try {
			List<CoterieInfo> list=coterieService.findList(coterieIdList);
			setMemberNum(list);
			fillCustInfo(list);
			return ResponseUtils.returnListSuccess(list);
		} catch (Exception e) {
			logger.error("queryListByCoterieIdList", e);
			return ResponseUtils.returnException(e);
		}
	}
	
	/**
	 * 查询私圈信息
	 * @param
	 * @return
	 */
	@Override
	public Response<CoterieInfo> queryCoterieInfo(Long coterieId) {

		logger.info("CoterieApi.queryCoterieInfo params: " + coterieId);
		if (coterieId == null) {
			return ResponseUtils.returnCommonException("coterieId is not null");
		}
		try {
			CoterieInfo info = coterieService.find(coterieId);
			if (info != null) {
				fillCustInfo(Arrays.asList(info));
				setMemberNum(Arrays.asList(info));
			}
			return ResponseUtils.returnObjectSuccess(info);
		} catch (Exception e) {
			logger.error("queryCoterieInfo", e);
			return ResponseUtils.returnException(e);
		}
	}
	/**
	 * 设置私圈信息
	 * @param info  coterieId必填
	 */
	@Override
	public Response<CoterieInfo> modifyCoterieInfo(CoterieInfo info) {
		logger.info("CoterieApi.modifyCoterieInfo params:" + info);

		try {
			if (info == null ||  info.getCoterieId()==null) {
				return ResponseUtils.returnCommonException("私圈ID不存在");
			}
			String name = StringUtils.trim(info.getName());
			if (StringUtils.isNotEmpty(name)) {
				List<CoterieInfo> clist = coterieService.findByName(name);
				if (!clist.isEmpty()&&!clist.get(0).getCoterieId().equals(info.getCoterieId())) {
					return ResponseUtils.returnCommonException("私圈名称已存在");
				}
			}
			if (info.getJoinFee() != null && (info.getJoinFee() > 50000 || info.getJoinFee() < 0)) {
				return ResponseUtils.returnCommonException("加入私圈金额设置不正确。");
			}
			if (info.getConsultingFee()!=null && (info.getConsultingFee() > 10000 || info.getConsultingFee() < 0)) {
				return ResponseUtils.returnCommonException("私圈咨询费金额设置不正确。");
			}
			coterieService.modify(info);
			return ResponseUtils.returnObjectSuccess(coterieService.find(info.getCoterieId()));
		} catch (Exception e) {
			logger.error("modifyCoterieInfo", e);
			return ResponseUtils.returnException(e);
		}
	}
	/**
	 * 申请创建私圈
	 * @param info
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Response<CoterieInfo> applyCreate(CoterieBasicInfo info) {
		logger.info("CoterieApi.applyCreate params:" + info);
		try {
			checkApplyCreateParam(info);
			return ResponseUtils.returnObjectSuccess(coterieService.save(info));
		}catch (Exception e) {
			logger.error("applyCreate", e);
			return ResponseUtils.returnException(e);
		}
	}

	private void checkApplyCreateParam(CoterieBasicInfo info) {
		if (info == null) {
			throw new QuanhuException( "2007","对象为空","对象为空",null);
		}
		if (StringUtils.isEmpty(info.getIcon())) {
			throw new QuanhuException( "2007","参数错误","icon不能为空",null);
		}
		if (StringUtils.isEmpty(info.getIntro())) {
			throw new QuanhuException( "2007","参数错误","intro不能为空",null);
		}
		if (StringUtils.isEmpty(info.getName())) {
			throw new QuanhuException( "2007","参数错误","name不能为空",null);
		}
		if (StringUtils.isEmpty(info.getOwnerId())) {
			throw new QuanhuException( "2007","参数错误","ownerId不能为空",null);
		}
		if (info.getJoinFee()!=null && (info.getJoinFee()>50000 || info.getJoinFee()<0)) {//私圈单位为分，0表示免费，小于100的  单位必定错误
			throw new QuanhuException( "2007","参数错误","加入私圈金额设置不正确。",null);
		}
		List<CoterieInfo> coterieList = coterieService.findByName(StringUtils.trim(info.getName()));
		if (!coterieList.isEmpty()) {
			throw new QuanhuException( "2007","私圈名称已存在","私圈名称已存在");
		}
		Integer count=coterieService.findMyCreateCoterieCount(info.getOwnerId());
		if(count>=10)
		{
			throw new QuanhuException(ExceptionEnum.BusiException.getCode(),"最多只能申请10个私圈","最多只能申请10个私圈");
		}
		UserSimpleVO cust=ResponseUtils.getResponseData(userApi.getUserSimple(Long.valueOf(info.getOwnerId())));
		if(cust==null){
			throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "用户不存在","用户("+info.getOwnerId()+")不存在");
		}
		EventInfo param=new EventInfo();
		param.setUserId(info.getOwnerId());
		EventReportVo vo=ResponseUtils.getResponseData(eventAPI.getScoreFlowList(param));
		int level=1;
		if(vo==null){
			level=1;
		}else{
			level=Integer.valueOf(vo.getGrowLevel());
		}
		if(level<5){
			String msg="当前用户等级为"+level+",5级以上才能创建私圈";
			throw new QuanhuException(ExceptionEnum.BusiException.getCode(),msg,msg);
		}
	}
	/**
	 * 我创建的私圈 已上架和审核中和审核不通过
	 * @param custId
	 * @param
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Response<List<CoterieInfo>> getMyCreateCoterie(String custId,Integer pageNum,Integer pageSize) {
		logger.info("CoterieApi.getMyCreateCoterie params: " + custId    );
		if (StringUtils.isEmpty(custId)  ) {
			ResponseUtils.returnCommonException("用户id不能为空 ");
		}
		try {
			List<CoterieInfo> list = coterieService.findMyCreateCoterie(custId);
			List<CoterieInfo> shangjia=Lists.newArrayList();//上架
			List<CoterieInfo> shenhezhong=Lists.newArrayList();//审核中
			List<CoterieInfo> butongguo=Lists.newArrayList();//不通过
			for (int i = 0; i < list.size(); i++) {//写这么多垃圾代码为了排序
				CoterieInfo info=list.get(i);
				if(info.getShelveFlag()==10){//上架
					shangjia.add(info);
				}
				else if(info.getStatus()==10){//审核中
					shenhezhong.add(info);
				}else{
					butongguo.add(info);
				}
			}
			
			List<CoterieInfo> sortList=Lists.newArrayList();
			sortList.addAll(shangjia);
			sortList.addAll(shenhezhong);
			sortList.addAll(butongguo);
			//手动分页
			List<CoterieInfo> resultList=Lists.newArrayList();
			int start=(pageNum-1)*pageSize;
			for (int i = start; i < pageSize; i++) {
				if(i<sortList.size()){
					resultList.add(sortList.get(i));
				}
			}
			fillCustInfo(resultList);
			setMemberNum(resultList);
			return ResponseUtils.returnListSuccess(resultList);
		} catch (Exception e) {
			logger.error("getMyCreateCoterie", e);
			return ResponseUtils.returnException(e);
		}
	}
	/**
	 * 我加入的私圈
	 * @param custId
	 * @param
	 * @return
	 */
	@Override
	public Response<List<CoterieInfo>> getMyJoinCoterie(String custId){
		logger.info("CoterieApi.getMyJoinCoterie params: " + custId   );
		if (StringUtils.isEmpty(custId)  ) {
			ResponseUtils.returnCommonException("用户id不能为空");
		}
		try {
			List<CoterieInfo> list = coterieService.findMyJoinCoterie(custId );
			setMemberNum(list);
			fillCustInfo(list);
			return ResponseUtils.returnListSuccess(list);
		} catch (Exception e) {
			logger.error("getMyJoinCoterie", e);
			return ResponseUtils.returnException(e);
		}
	}
	
	@Override
	public Response<List<CoterieInfo>> queryHotCoterieList(Integer pageNum, Integer pageSize){
		logger.info("CoterieApi.queryHotCoterieList pageNum: " + pageNum + ",pageSize:" + pageSize);
		if( pageNum==null || pageNum<1){
			pageNum=1;
		}
		if(pageSize==null || pageSize<1){
			pageSize=10;
		}
		try {
			List<CoterieInfo> list=coterieService.getRecommendCoterieList();
			if(list.size()<200){
				List<CoterieInfo> clist=coterieService.getOrderByMemberNum();
				for (int i = 0; i < clist.size(); i++) {
					if(list.size()<200){
						list.add(clist.get(i));
					}
				}
			}
			//手动分页
			int start=(pageNum-1)*pageSize;
			List<CoterieInfo> result=Lists.newArrayList();
			for (int i = start; i < pageSize; i++) {
				if(i<list.size()){
					result.add(list.get(i));
				}
			}
			fillCustInfo(result);
			setMemberNum(result);
			return ResponseUtils.returnListSuccess(result);
		}catch (Exception e) {
			logger.error("queryHotCoterieList", e);
			return ResponseUtils.returnException(e);
		}
	}
	
	/**
	 * 我加入的私圈
	 * @param custId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Response<List<CoterieInfo>> getMyJoinCoterie(String custId, Integer pageNum, Integer pageSize){
		logger.info("CoterieApi.getMyJoinCoterie pageNum: " + pageNum + ",pageSize:" + pageSize+",custId:"+custId);
		try {
			List<CoterieInfo> list=coterieService.findMyJoinCoterie(custId, pageNum, pageSize);
			setMemberNum(list);
			fillCustInfo(list);
			return ResponseUtils.returnListSuccess(list);
		}catch (Exception e) {
			logger.error("getMyJoinCoterie", e);
			return ResponseUtils.returnException(e);
		}
	}
	/**
	 * 我加入的私圈 数量
	 * @param custId
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Response<Integer> getMyJoinCoterieCount(String custId){
		logger.info("CoterieApi.getMyJoinCoterieCount custId: " + custId);
		try {
			return  ResponseUtils.returnObjectSuccess(coterieService.findMyJoinCoterieCount(custId));
		} catch (Exception e) {
			logger.error("getMyJoinCoterieCount", e);
			return ResponseUtils.returnException(e);
		}
	}
	
	/**
	 * 更新私圈主达人状态
	 * @param custId  圈主用户ID
	 * @param isExpert 是否达人，0否，1是
	 * @throws ServiceException
	 */
	@Override
	public void modifyCoterieExpert(String custId, Byte isExpert){
		logger.info("CoterieApi.modifyCoterieExpert custId: " + custId + ",isExpert" + isExpert);
		if(StringUtils.isEmpty(custId) || isExpert==null){
			throw QuanhuException.busiError("参数错误");
		}
		coterieService.modifyCoterieExpert(custId, isExpert);
	}

	private void setMemberNum(List<CoterieInfo> infoList){
		if(infoList==null || infoList.isEmpty()){
			return;
		}
		for (int i = 0; i < infoList.size(); i++) {
			CoterieInfo coterie=infoList.get(i);
			if(coterie.getMemberNum()==null){
				coterie.setMemberNum(0);
			}
			coterie.setMemberNum(coterie.getMemberNum()+1);//私圈成员数量需要加上圈主
		}

	}

	private void fillCustInfo(List<CoterieInfo> infoList){
		if(infoList==null || infoList.isEmpty()){
			return;
		}
		Set<String> custIdSet=Sets.newHashSet();
		for (int i = 0; i < infoList.size(); i++) {
			if(infoList.get(i)==null){
				continue;
			}
			String custId=infoList.get(i).getOwnerId();
			if(StringUtils.isNotEmpty(custId)){
				custIdSet.add(custId);
			}
		}
		if(custIdSet.isEmpty()){
			return;
		}
		Response<Map<String,UserBaseInfoVO>> UserBaseInfoVOs= userApi.getUser(custIdSet);
		Map<String, UserBaseInfoVO> custMaps=UserBaseInfoVOs.getData();
		for (int i = 0; i < infoList.size(); i++) {
			CoterieInfo o=infoList.get(i);
			UserBaseInfoVO cust=custMaps.get(o.getOwnerId());
			if(cust!=null){
				o.setCustIcon(cust.getUserImg());
				o.setOwnerName(cust.getUserNickName());
                if (null == o.getUser()) {
                    o.setUser(new User());
                }
				o.getUser().setHeadImg(cust.getUserImg());
				o.getUser().setAuthStatus(cust.getAuthStatus().toString());
				o.getUser().setNickName(cust.getUserNickName());
			}
		}
	}
	/**
	 * 我的圈子数：创建的和参加的私圈数总和
	 * @param custId
	 * @return
	 */
	@Override
	public Response<Integer> getMyCoterieCount(String custId) {
		logger.info("CoterieApi.getMyCoterieCount custId: " + custId);
		try {
			Integer count=coterieService.findMyJoinCoterieCount(custId);
			count+=coterieService.findMyCreateCoterieCount(custId);
			return  ResponseUtils.returnObjectSuccess(count);
		}catch (Exception e) {
			logger.error("getMyCoterieCount", e);
			return ResponseUtils.returnException(e);
		}
	}
	
	
	/**
	 * @Override
	 * @Title: regroupQr
	 * @Description:  组装二维码
	 * @author
	 * @param @param info
	 * @throws
	 */
	@Override
	public Response<String> regroupQr(Long coterieId) {
		String result = null;

		if (null == coterieId) {
			return ResponseUtils.returnCommonException("coterieId 不能为空");
		}
		CoterieInfo info=coterieService.find(coterieId);
		if(info==null){
			return ResponseUtils.returnCommonException("私圈（"+coterieId+"）不存在");
		}
		Set<String> set = new HashSet<String>();
		set.add(info.getOwnerId());
		Map<String,UserBaseInfoVO> cust=ResponseUtils.getResponseData(userApi.getUser(set));
		UserBaseInfoVO user = cust.get(info.getOwnerId());
		if(user==null){
			return ResponseUtils.returnCommonException("用户（"+info.getOwnerId()+"）不存在");
		}
		try {
			BufferedImage base = ImageUtils.createBaseImage(300, 400, Color.WHITE);

			String frontend =coterieQrUrl+coterieId;
			ByteArrayOutputStream frontendQrOS = new ByteArrayOutputStream();
			ZxingHandler.encode2(frontend, 200, 200, frontendQrOS);
			BufferedImage i1 = ImageIO.read(new ByteArrayInputStream(frontendQrOS.toByteArray()));
			ImageUtils.overlapMiddleImage(base, i1);
			if (StringUtils.isNotBlank(user.getUserImg())) {
				try {
					BufferedImage i2 = ImageUtils.loadImageUrl(user.getUserImg());
					ImageUtils.overlapImage(base, ImageUtils.resize(i2, 45, 45), (base.getWidth() - 45) / 2, 280);
				} catch (Exception e) {
					logger.error("组装私圈二维码，添加用户头像异常", e);
				}
			}
			ImageUtils.middleAddText(base, info.getName(), 100, 20);

			ImageUtils.middleAddText(base, user.getUserNickName(), 350, 16);
			//ImageUtils.writeImageLocal("F:/合成123.png", base);

			// 转流
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(base, "png", os);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			// 文件上传至 oss服务器
			/**UploadResultDetail uploadResult = UploadUtil.uploadImg(is, new File(info.getQrUrl()).getName(),
			 AppRuntimeContext.getAppEnv().getAppName() + "/coterie", false);
			 if (null != uploadResult && "success".equals(uploadResult.getMsg())) {
			 result = uploadResult.getResouceUrl();
			 log.debug("重组私圈二维码图片成功，地址：" + uploadResult.getMsg() + result);
			 }*/

			// 生成的文件输出为 base64
			// in.available()返回文件的字节长度
			byte[] bytes = new byte[is.available()];
			// 将文件中的内容读入到数组中
			try {
				is.read(bytes);
			} finally {
				is.close();
			}

			// 将图片字节流数组转换为base64
			result = "data:image/png;base64," + new String(Base64.encodeBase64(bytes));
			//Base64Util.encodeToString(bytes);
		} catch (Exception e) {
			logger.error("组装私圈二维码图片异常！", e);
			return ResponseUtils.returnCommonException("组装私圈二维码图片异常！");
		}
		return ResponseUtils.returnObjectSuccess(result);
	}

	@Override
	public Response<List<Long>> getKidByCreateDate(String startDate, String endDate) {
		logger.info("CoterieApi.getKidByCreateDate startDate:" + startDate+",endDate:"+endDate);
		try {
			List<Long> kidList=coterieService.getKidByCreateDate(startDate, endDate);
			return  ResponseUtils.returnObjectSuccess(kidList);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<List<Coterie>> getByKids(List<Long> kidList) {
		logger.info("CoterieApi.getByKids kidList:" + kidList);
		try {
			List<com.yryz.quanhu.coterie.coterie.entity.Coterie> list=coterieService.getByKids(kidList);
			List<Coterie> rstList=GsonUtils.parseList(list, Coterie.class);
			return  ResponseUtils.returnObjectSuccess(rstList);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<List<CoterieInfo>> queryPage(Integer pageNum, Integer pageSize){
		logger.info("CoterieApi.queryPage pageNum:" + pageNum+",pageSize:"+pageSize);
		try {
			List<CoterieInfo> list=coterieService.findPage(pageNum, pageSize);
			setMemberNum(list);
			fillCustInfo(list);
			return ResponseUtils.returnListSuccess(list);
		} catch (Exception e) {
			logger.error("queryPage", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<List<CoterieInfo>> getCreateCoterie(String custId, Integer pageNum, Integer pageSize) {
		logger.info("CoterieApi.getCreateCoterie custId:"+custId+",pageNum:" + pageNum+",pageSize:"+pageSize);
		try {
			List<CoterieInfo> list=coterieService.findCreateCoterie(custId, pageNum, pageSize);
			setMemberNum(list);
			fillCustInfo(list);
			return ResponseUtils.returnListSuccess(list);
		} catch (Exception e) {
			logger.error("getCreateCoterie", e);
			return ResponseUtils.returnException(e);
		}
	}

}