package com.yryz.quanhu.dymaic.canal.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.coterie.coterie.vo.Coterie;
import com.yryz.quanhu.dymaic.canal.dao.CoterieInfoRepository;
import com.yryz.quanhu.dymaic.canal.entity.CoterieInfo;

@Component
public class CoterieDiffHandler implements DiffHandler {
	private static final Log logger = LogFactory.getLog(CoterieDiffHandler.class);
	@Resource
	private CoterieInfoRepository coterieInfoRepository;
	@Reference
	private CoterieApi coterieApi;
	@Resource
	private DiffExecutor diffExecutor;

	@Override
	public void handler() {
		String yesterday = DateUtils.getNextDay();
		Response<List<Long>> res = coterieApi.getKidByCreateDate(yesterday + " 00:00:00", yesterday + " 23:59:59");
		if (!res.success()) {
			logger.error("diff coterie error:" + res.getErrorMsg());
			return;
		}

		List<Long> diffList = new ArrayList<>();
		List<Long> idList = res.getData();
		for (int i = 0; i < idList.size(); i++) {
			long id = idList.get(i);
			Optional<CoterieInfo> info = coterieInfoRepository.findById(id);
			if (!info.isPresent()) {
				diffList.add(id);
			}
		}

		if (!diffList.isEmpty()) {
			Response<List<Coterie>> resList = coterieApi.getByKids(diffList);
			if (resList.success()) {
				List<Coterie> clist = resList.getData();
				List<CoterieInfo> list = new ArrayList<>();
				for (int i = 0; i < clist.size(); i++) {
					Coterie c = clist.get(i);
					CoterieInfo info = GsonUtils.parseObj(c, CoterieInfo.class);
					info.setKid(c.getCoterieId());
					info.setCoterieName(c.getName());
					info.setState(c.getStatus());
					list.add(info);
				}
				coterieInfoRepository.saveAll(list);
			}
		}
	}

	@PostConstruct
	public void register() {
		diffExecutor.register(this);		
	}
}
