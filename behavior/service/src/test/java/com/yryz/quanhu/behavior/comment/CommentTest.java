package com.yryz.quanhu.behavior.comment;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.behavior.comment.dao.redis.CommentCache;
import com.yryz.quanhu.behavior.comment.dto.CommentFrontDTO;
import com.yryz.quanhu.behavior.comment.dto.CommentSubDTO;
import com.yryz.quanhu.behavior.comment.entity.Comment;
import com.yryz.quanhu.behavior.comment.service.CommentApi;
import com.yryz.quanhu.behavior.comment.vo.CommentDetailVO;
import com.yryz.quanhu.behavior.comment.vo.CommentListInfoVO;
import com.yryz.quanhu.behavior.like.Service.LikeApi;
import com.yryz.quanhu.behavior.like.dto.LikeFrontDTO;
import com.yryz.quanhu.behavior.like.entity.Like;
import com.yryz.quanhu.behavior.like.vo.LikeInfoVO;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentTest {

    @Autowired
    private CommentCache commentCache;
    
    @Reference
    private CommentApi commentApi;
    
    @Reference(url="127.0.0.1")
    private LikeApi likeApi;
    
    //@Test
    public void saveComment(){
    	Comment comment = new Comment();
    	comment.setContentComment("呵呵你妹");
    	comment.setResourceId(380803544997888l);
    	comment.setTargetUserId(377909298962432l);
    	comment.setCreateUserId(378867848421376l);
    	
    	comment.setParentId(382370427920384l);
    	comment.setTopId(382370427920384l);
    	comment.setModuleEnum("1007");
    	comment.setCoterieId(380784645464064l);
    	Response<Comment> response = commentApi.accretion(comment);
    	System.out.println(JsonUtils.toFastJson(response));
    }
    
    //@Test
    public void like(){
    	Like like = new Like();
    	like.setUserId(378867848421376l);
    	like.setCoterieId(0l);
    	like.setModuleEnum("1010");
    	like.setResourceId(382370427920384l);
    	like.setResourceUserId(377199907938304l);
    	Response<Map<String,Object>> response = likeApi.dian(like);
    	System.out.println(JsonUtils.toFastJson(response));
    }
    
    public void updownComment(){
    	Comment comment = new Comment();
    	comment.setKid(367981230055424l);
    	
    	commentApi.updownSingle(comment);
    }
    
    //@Test
    public void likeList(){
    	LikeFrontDTO likeFrontDTO = new LikeFrontDTO();
    	likeFrontDTO.setCurrentPage(1);
    	likeFrontDTO.setPageSize(10);
    	likeFrontDTO.setResourceId(380803544997888l);
    	Response<PageList<LikeInfoVO>> response = likeApi.listLike(likeFrontDTO);
    	System.out.println(JsonUtils.toFastJson(response));
    }
    
    //@Test
    public void delComment(){
    	Comment comment = new Comment();
    	comment.setKid(382371761709056l);
    	comment.setCreateUserId(377199907938304l);
    	comment.setResourceId(380803544997888l);
    	Response<Map<String,Integer>> response = commentApi.delComment(comment);
    	System.out.println(JsonUtils.toFastJson(response));
    }
    
    //@Test
    public void commentDetail(){
    	CommentSubDTO commentSubDTO = new CommentSubDTO();
    	commentSubDTO.setCurrentPage(1);
    	commentSubDTO.setPageSize(10);
    	commentSubDTO.setUserId("378867848421376");
    	commentSubDTO.setKid(382370427920384l);
    	Response<CommentDetailVO> response = commentApi.queryCommentDetail(commentSubDTO);
    	System.out.println(JsonUtils.toFastJson(response));
    }
    
    @Test
    public void listComment(){
    	CommentFrontDTO commentFrontDTO = new CommentFrontDTO();
    	commentFrontDTO.setCurrentPage(1);
    	commentFrontDTO.setPageSize(10);
    	commentFrontDTO.setUserId("377199907938304");
    	commentFrontDTO.setResourceId(100218l);
    	Response<PageList<CommentListInfoVO>> response = commentApi.listComments(commentFrontDTO);
    	System.out.println(JsonUtils.toFastJson(response));
    }
    
    //@Test
    public void getLikeFlag(){
    	CommentFrontDTO commentFrontDTO = new CommentFrontDTO();
    	commentFrontDTO.setCurrentPage(1);
    	commentFrontDTO.setPageSize(10);
    	commentFrontDTO.setUserId("377199907938304");
    	commentFrontDTO.setResourceId(100218l);
    	Response<Map<String,Integer>> response = likeApi.getLikeFlagBatch(Lists.newArrayList(100218l), 359979376959488l);
    	System.out.println(JsonUtils.toFastJson(response));
    }
    
    //@Test
    public void addTest() throws InterruptedException {
        Comment comment = new Comment();
        comment.setModuleEnum("zhuanfa");
        comment.setResourceId(12927923729L);
        long start = 119349237980L;
        for (int i = 0; i < 100; i++) {
            start++;
            comment.setKid(start);
            comment.setCreateDate(new Date());
            commentCache.addCommentList(comment);
            Thread.sleep(10);
        }
    }

    //@Test
    public void delTest() throws InterruptedException {
        Comment comment = new Comment();
        comment.setModuleEnum("zhuanfa");
        comment.setResourceId(12927923729L);
        comment.setKid(119349238078L);

        commentCache.delCommentList(comment);
    }


   // @Test
    public void getTest() {
        getList(5, 5);
    }

    private void getList(Integer currentPage, Integer pageSize) {
        int start = 0;
        if (pageSize == null) {
            pageSize = 10;
        }
        if (currentPage != null && currentPage >= 1) {
            start = (currentPage - 1) * pageSize;
        }
        Comment comment = new Comment();
        comment.setModuleEnum("zhuanfa");
        comment.setResourceId(12927923729L);

        Set<Long> longs = null;//commentCache.rangeCommentList(comment, start, pageSize);
        Assert.assertEquals(5, longs.size());
    }

    
}
