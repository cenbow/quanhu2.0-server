package com.yryz.quanhu.demo.job;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author xiepeng
 * @version 1.0
 * @data 2018/1/17 0017 08
 */

/**
使用引导
1，确认项目POM依赖中引入以下配置
    <dependency>
        <groupId>com.dangdang</groupId>
        <artifactId>elastic-job-lite-core</artifactId>
    </dependency>
    <dependency>
        <groupId>com.dangdang</groupId>
        <artifactId>elastic-job-lite-spring</artifactId>
    </dependency>

2，使用spring xml方式配置注册中心及定时任务
   确认boot启动类引入了xml配置  @ImportResource("classpath:META-INF/spring.xml")
   elasticJob的springxml配置使用方式，详细见spring.xml示例或参考官方文档

3，application.properties配置
   job.reg.address=127.0.0.1:2181   zookeeper注册地址
   job.reg.namespace=demo-job       项目级空间命名，同一个项目或服务使用同一管理空间
*/


public class DemoJob implements SimpleJob{
    private static final Log logger = LogFactory.getLog(DemoJob.class);
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("elastic job demo..." + shardingContext.getJobName());

        switch (shardingContext.getShardingItem()) {
            case 0:
                // do something by sharding item 0
                break;
            case 1:
                // do something by sharding item 1
                break;
            default:
                // do something by sharding default
                break;
        }
    }
}