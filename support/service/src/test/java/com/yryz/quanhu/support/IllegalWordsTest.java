package com.yryz.quanhu.support;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.support.illegalWord.api.IllegalWordsApi;
import com.yryz.quanhu.support.illegalWord.entity.BasicIllegalWordsInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IllegalWordsTest {
	@Autowired
	private IllegalWordsApi illegalWordsApi;;
	
	@Test
	public void listByParam(){
		Response<PageList<BasicIllegalWordsInfo>> rst=illegalWordsApi.listByParam(1, 20, "法");
		System.out.println(GsonUtils.parseJson(rst.getData()));
	}
	
	@Test
	public void replaceIllegalWords(){
//		Response<String> txt=illegalWordsApi.replaceIllegalWords("我是正常文字里面包含了发论工", "*");
		Response<Set<String>> res=illegalWordsApi.matchIllegalWords("我是正常文字里面包含了发论工");
		System.out.println(res);
	}
}
