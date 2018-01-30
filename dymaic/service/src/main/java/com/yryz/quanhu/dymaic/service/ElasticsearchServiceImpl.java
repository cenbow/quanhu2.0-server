package com.yryz.quanhu.dymaic.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.PageModel;
import com.yryz.quanhu.dymaic.canal.entity.UserBaseInfo;
import com.yryz.quanhu.dymaic.dto.StarInfoDTO;
import com.yryz.quanhu.user.vo.StarInfoVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import com.yryz.quanhu.user.vo.UserStarSimpleVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.yryz.common.response.PageList;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.dymaic.canal.dao.CoterieInfoRepository;
import com.yryz.quanhu.dymaic.canal.dao.ResourceInfoRepository;
import com.yryz.quanhu.dymaic.canal.dao.UserRepository;
import com.yryz.quanhu.dymaic.canal.entity.CoterieInfo;
import com.yryz.quanhu.dymaic.canal.entity.ResourceInfo;
import com.yryz.quanhu.dymaic.canal.entity.UserInfo;
import com.yryz.quanhu.dymaic.vo.CoterieInfoVo;
import com.yryz.quanhu.dymaic.vo.ResourceInfoVo;
import com.yryz.quanhu.dymaic.vo.UserSimpleVo;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchServiceImpl.class);

    @Resource
    private UserRepository userRepository;
    @Resource
    private ResourceInfoRepository resourceInfoRepository;
    @Resource
    private CoterieInfoRepository coterieInfoRepository;

    @Override
    public PageList<UserSimpleVo> searchUser(String keyWord, Integer page, Integer size) {
        List<UserInfo> list = userRepository.search(keyWord, page, size);
        List<UserSimpleVo> rstList = Lists.newArrayList();
        for (int i = 0; i < list.size(); i++) {
            UserInfo info = list.get(i);
            UserSimpleVo vo = new UserSimpleVo();
            if (info.getUserBaseInfo() != null) {
                BeanUtils.copyProperties(info.getUserBaseInfo(), vo);
                rstList.add(vo);
            }
        }
        PageList<UserSimpleVo> pageList = new PageList<UserSimpleVo>();
        pageList.setEntities(rstList);
        pageList.setCount(null);
        pageList.setCurrentPage(page);
        pageList.setPageSize(size);
        return pageList;
    }

    @Override
    public Response<PageList<StarInfoVO>> searchStarUser(StarInfoDTO starInfoDTO) {
        logger.info("searchStarUser request, starInfoDTO: {}", GsonUtils.parseJson(starInfoDTO));
        Long tagId = starInfoDTO.getTagId();
        Integer pageNo = starInfoDTO.getCurrentPage();
        Integer pageSize = starInfoDTO.getPageSize();
        Long userId = starInfoDTO.getUserId();

        List<UserInfo> list = userRepository.searchStarUser(tagId, userId, pageNo, pageSize);
        //数据转换UserInfo ->  StarInfoVO
        List<StarInfoVO> starInfoVOList = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(list)) {
            for (UserInfo userInfo : list) {
                UserSimpleVO simpleVO = new UserSimpleVO();
                BeanUtils.copyProperties(userInfo.getUserBaseInfo(), simpleVO);

                StarInfoVO starInfoVO = new StarInfoVO();
                //用户数据
                starInfoVO.setUserInfo(simpleVO);
                //达人数据
                UserStarSimpleVo starSimpleVo = new UserStarSimpleVo();
                starInfoVO.setStarInfo(starSimpleVo);

                starInfoVOList.add(starInfoVO);
            }
        }

        PageList<StarInfoVO> pageList = new PageModel<StarInfoVO>().getPageList(starInfoVOList);
        logger.info("searchStarUser result, pageList: {}", GsonUtils.parseJson(pageList));
        return ResponseUtils.returnObjectSuccess(pageList);
    }


    @Override
    public PageList<ResourceInfoVo> searchTopicInfo(String keyWord, Integer page, Integer size) {
        List<ResourceInfo> list = resourceInfoRepository.searchTopicInfo(keyWord, page, size);
        List<ResourceInfoVo> rstList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ResourceInfo info = list.get(i);
            ResourceInfoVo vo = GsonUtils.parseObj(info, ResourceInfoVo.class);
            rstList.add(vo);

            if (info.getResourceType() == 1 && info.getTopicInfo() != null && info.getTopicInfo().getCreateUserId() != null) {
                Optional<UserInfo> user = userRepository.findById(info.getTopicInfo().getCreateUserId());
                if (user.isPresent()) {
                    UserSimpleVo userVo = new UserSimpleVo();
                    BeanUtils.copyProperties(user.get(), userVo);
                    vo.setCreateUserInfo(userVo);
                }
            }

            if (info.getResourceType() == 2 && info.getTopicPostInfo() != null && info.getTopicPostInfo().getCreateUserId() != null) {
                Optional<UserInfo> user = userRepository.findById(info.getTopicPostInfo().getCreateUserId());
                if (user.isPresent()) {
                    UserSimpleVo userVo = new UserSimpleVo();
                    BeanUtils.copyProperties(user.get(), userVo);
                    vo.setCreateUserInfo(userVo);
                }
            }
        }

        PageList<ResourceInfoVo> pageList = new PageList<ResourceInfoVo>();
        pageList.setEntities(rstList);
        pageList.setCount(null);
        pageList.setCurrentPage(page);
        pageList.setPageSize(size);
        return pageList;
    }

    @Override
    public PageList<ResourceInfoVo> searchReleaseInfo(String keyWord, Integer page, Integer size) {
        List<ResourceInfo> list = resourceInfoRepository.searchReleaseInfo(keyWord, page, size);
        List<ResourceInfoVo> rstList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ResourceInfo info = list.get(i);
            ResourceInfoVo vo = GsonUtils.parseObj(info, ResourceInfoVo.class);
            rstList.add(vo);

            if (info.getReleaseInfo() != null && info.getReleaseInfo().getCreateUserId() != null) {
                Optional<UserInfo> user = userRepository.findById(info.getReleaseInfo().getCreateUserId());
                if (user.isPresent()) {
                    UserSimpleVo userVo = new UserSimpleVo();
                    BeanUtils.copyProperties(user.get(), userVo);
                    vo.setCreateUserInfo(userVo);
                }
            }
        }

        PageList<ResourceInfoVo> pageList = new PageList<ResourceInfoVo>();
        pageList.setEntities(rstList);
        pageList.setCount(null);
        pageList.setCurrentPage(page);
        pageList.setPageSize(size);
        return pageList;
    }

    @Override
    public PageList<CoterieInfoVo> searchCoterieInfo(String keyWord, Integer page, Integer size) {
        List<CoterieInfo> list = coterieInfoRepository.search(keyWord, page, size);
        List<CoterieInfoVo> rstList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CoterieInfo info = list.get(i);
            CoterieInfoVo vo = new CoterieInfoVo();
            BeanUtils.copyProperties(info, vo);
            rstList.add(vo);
            if (vo.getCreateUserId() == null) {
                continue;
            }

            Optional<UserInfo> user = userRepository.findById(vo.getCreateUserId());
            if (user.isPresent()) {
                UserSimpleVo userVo = new UserSimpleVo();
                BeanUtils.copyProperties(user.get(), userVo);
                vo.setUser(userVo);
            }
        }
        PageList<CoterieInfoVo> pageList = new PageList<CoterieInfoVo>();
        pageList.setEntities(rstList);
        pageList.setCount(null);
        pageList.setCurrentPage(page);
        pageList.setPageSize(size);
        return pageList;
    }

}
