package com.yryz.quanhu.behavior.comment;

import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.behavior.comment.dao.redis.CommentCache;
import com.yryz.quanhu.behavior.comment.entity.Comment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectionTest {

    @Autowired
    private CommentCache commentCache;

    @Test
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

    @Test
    public void delTest() throws InterruptedException {
        Comment comment = new Comment();
        comment.setModuleEnum("zhuanfa");
        comment.setResourceId(12927923729L);
        comment.setKid(119349238078L);

        commentCache.delCommentList(comment);
    }


    @Test
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

        Set<Long> longs = commentCache.rangeCommentList(comment, start, pageSize);
        Assert.assertEquals(5, longs.size());
    }


}
