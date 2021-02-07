package com.tangzhangss.commonutils.uidgenerator;

import com.tangzhangss.commonutils.base.SysBaseDao;
import org.springframework.data.repository.CrudRepository;

public interface SysWorkerNodeDao extends SysBaseDao<WorkerNodeEntity,Long>, CrudRepository<WorkerNodeEntity,Long> {
    WorkerNodeEntity findFirstByHostName(String localAddress);
}
