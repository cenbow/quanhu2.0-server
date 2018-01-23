package com.yryz.quanhu.dymaic.canal.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.yryz.quanhu.dymaic.canal.entity.ReleaseInfo;

/**
 * ReleaseInfo Elasticsearch仓库
 *
 * @author jk
 */
public interface ReleaseInfoEsRepository extends ElasticsearchRepository<ReleaseInfo, Long>{

}
