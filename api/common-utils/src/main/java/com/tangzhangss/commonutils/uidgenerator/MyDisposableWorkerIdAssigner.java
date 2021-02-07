package com.tangzhangss.commonutils.uidgenerator;

import com.tangzhangss.commonutils.utils.BaseUtil;
import com.xfvape.uid.utils.DockerUtils;
import com.xfvape.uid.utils.NetUtils;
import com.xfvape.uid.worker.WorkerIdAssigner;
import com.xfvape.uid.worker.WorkerNodeType;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyDisposableWorkerIdAssigner implements WorkerIdAssigner {
    @Autowired
    private SysWorkerNodeDao workerNodeDao;

    @Override
    public long assignWorkerId() {
        // build worker node entity
        WorkerNodeEntity workerNodeEntity=null;

        //根据外网的Ip地址去查，同一个外面ip地址就一样的worker
        String ipv4=BaseUtil.getIPV4();
        if(StringUtils.isNotBlank(ipv4)){
            //同样的ip地址workerId可以一样
            workerNodeEntity = workerNodeDao.findFirstByHostName(ipv4);
        }
        if(null!=workerNodeEntity){
            workerNodeEntity.setModified(new Date());
        }else{
            workerNodeEntity = buildWorkerNode();
        }
        workerNodeDao.save(workerNodeEntity);
        return  workerNodeEntity.getId();
    }
    private WorkerNodeEntity buildWorkerNode() {
        WorkerNodeEntity workerNodeEntity = new WorkerNodeEntity();
        if (DockerUtils.isDocker()) {
            workerNodeEntity.setType(WorkerNodeType.CONTAINER.value());
            workerNodeEntity.setHostName(DockerUtils.getDockerHost());
            workerNodeEntity.setPort(DockerUtils.getDockerPort());
        } else {
            workerNodeEntity.setType(WorkerNodeType.ACTUAL.value());
            String ipv4 = BaseUtil.getIPV4();
            String ip = StringUtils.isBlank(ipv4)?NetUtils.getLocalAddress():ipv4;
            workerNodeEntity.setHostName(ip);
            workerNodeEntity.setPort(System.currentTimeMillis() + "-" + RandomUtils.nextInt(100000));
        }
        workerNodeEntity.setCreated(new Date());
        workerNodeEntity.setModified(new Date());
        workerNodeEntity.setLaunchDate(new Date());
        return workerNodeEntity;
    }
}
