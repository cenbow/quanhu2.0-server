package com.yryz.quanhu.support.illegalwords.provider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.util.StringUtil;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.support.illegalWord.api.IllegalWordsApi;
import com.yryz.quanhu.support.illegalWord.entity.BasicIllegalWordsInfo;
import com.yryz.quanhu.support.illegalwords.entity.BasicIllegalWords;
import com.yryz.quanhu.support.illegalwords.service.IllegalWordsService;

@Service
public class IllegalWordsApiImpl implements IllegalWordsApi {
	private static final Logger logger = LoggerFactory.getLogger(IllegalWordsApiImpl.class);

	@Resource
	private IllegalWordsService illegalWordsService;

	@Override
	public Response<Set<String>> matchIllegalWords(String text) {
		if (StringUtil.isEmpty(text)) {
			return ResponseUtils.returnListSuccess(new HashSet<String>());
		}
		Set<String> set=illegalWordsService.getIllegalWords(text);
		return ResponseUtils.returnListSuccess(set);
	}

	@Override
	public Response<Boolean> checkIllegalWords(String text) {
		if (StringUtil.isEmpty(text)) {
			return ResponseUtils.returnObjectSuccess(false);
		}
		Boolean b=illegalWordsService.checkIllegalWords(text);
		return ResponseUtils.returnObjectSuccess(b);
	}


	@Override
	public Response<String> replaceIllegalWords(String text, String replaceWord) {
		if (StringUtil.isEmpty(text)) {
			return ResponseUtils.returnObjectSuccess("");
		}
		String result=illegalWordsService.relpaceIllegalWord(text, replaceWord);
		return ResponseUtils.returnObjectSuccess(result);
	}

		
	@Override
	public Response<Integer> delete(Long id) {
		if (id == null) {
			ResponseUtils.returnCommonException("id must not null");
		}
		int s=illegalWordsService.delete(id);
		return ResponseUtils.returnObjectSuccess(s);
	}

	@Override
	public Response<Integer> save(BasicIllegalWordsInfo record) {
		if (record == null || StringUtils.isEmpty(record.getWord()) || record.getId() == null) {
			ResponseUtils.returnCommonException("word,id must not null");
		}
		BasicIllegalWords w=GsonUtils.parseObj(record, BasicIllegalWords.class);
		int s=illegalWordsService.save(w);
		return ResponseUtils.returnObjectSuccess(s);
	}

	@Override
	public Response<Integer> update(BasicIllegalWordsInfo record) {
		if (record == null || StringUtils.isEmpty(record.getWord()) || record.getId() == null) {
			ResponseUtils.returnCommonException("word,id must not null");
		}
		BasicIllegalWords w=GsonUtils.parseObj(record, BasicIllegalWords.class);
		int s=illegalWordsService.update(w);
		return ResponseUtils.returnObjectSuccess(s);
	}

	@Override
	public Response<PageList<BasicIllegalWordsInfo>> listByParam(Integer pageNo, Integer pageSize, String word) {
		if (pageNo == null || pageSize == null || pageNo < 0 || pageSize < 0 || pageSize > 100) {
			ResponseUtils.returnCommonException("please check parameter pageNo,pageSize");
		}
		PageList<BasicIllegalWordsInfo> listPage = new PageList<>();
		listPage.setCurrentPage(pageNo);
		listPage.setPageSize(pageSize);
		Page<BasicIllegalWords> page = illegalWordsService.listByParam(pageNo, pageSize, word);
		List<BasicIllegalWordsInfo> infos = new ArrayList<>(page.getResult().size());
		for (BasicIllegalWords words : page.getResult()) {
			BasicIllegalWordsInfo info = GsonUtils.parseObj(words, BasicIllegalWordsInfo.class);
			infos.add(info);
		}
		listPage.setCount(page.getTotal());
		listPage.setEntities(infos);
		return ResponseUtils.returnObjectSuccess(listPage);
	}

}
