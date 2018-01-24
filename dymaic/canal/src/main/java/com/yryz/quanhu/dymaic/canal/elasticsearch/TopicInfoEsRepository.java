package com.yryz.quanhu.dymaic.canal.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.yryz.quanhu.dymaic.canal.entity.TopicInfo;

/**
 * TopicInfo Elasticsearch仓库
 *
 * @author jk
 */
public interface TopicInfoEsRepository extends ElasticsearchRepository<TopicInfo, Long>{

}
