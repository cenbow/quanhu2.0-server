package com.yryz.quanhu.support.id.snowflake.service.impl;

import com.yryz.quanhu.support.id.snowflake.utils.DockerUtils;
import com.yryz.quanhu.support.id.snowflake.utils.NetUtils;
import com.yryz.quanhu.support.id.snowflake.dao.WorkerNodeDao;
import com.yryz.quanhu.support.id.snowflake.entity.WorkerNode;
import com.yryz.quanhu.support.id.snowflake.enums.WorkerNodeType;
import com.yryz.quanhu.support.id.snowflake.service.WorkerIdAssigner;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 
 * @author danshiyu
 *
 */
public class DisposableWorkerIdAssigner implements WorkerIdAssigner {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisposableWorkerIdAssigner.class);

    @Resource
    private WorkerNodeDao workerNodeDAO;

    /**
     * Assign worker id base on database.<p>
     * If there is host name & port in the environment, we considered that the node runs in Docker container<br>
     * Otherwise, the node runs on an actual machine.
     * 
     * @return assigned worker id
     */
    @Override
	@Transactional
    public long assignWorkerId() {
        // build worker node entity
        WorkerNode workerNodeEntity = buildWorkerNode();

        // add worker node for new (ignore the same IP + PORT)
        workerNodeDAO.addWorkerNode(workerNodeEntity);
        LOGGER.info("Add worker node:" + workerNodeEntity);

        return workerNodeEntity.getId();
    }

    /**
     * Build worker node entity by IP and PORT
     */
    private WorkerNode buildWorkerNode() {
        WorkerNode workerNodeEntity = new WorkerNode();
        if (DockerUtils.isDocker()) {
            workerNodeEntity.setType(WorkerNodeType.CONTAINER.value());
            workerNodeEntity.setHostName(DockerUtils.getDockerHost());
            workerNodeEntity.setPort(DockerUtils.getDockerPort());

        } else {
            workerNodeEntity.setType(WorkerNodeType.ACTUAL.value());
            workerNodeEntity.setHostName(NetUtils.getLocalAddress());
            workerNodeEntity.setPort(System.currentTimeMillis() + "-" + RandomUtils.nextInt(100000));
        }

        return workerNodeEntity;
    }
}
