package com.tangzhangss.commonutils.uidgenerator;

import com.xfvape.uid.impl.CachedUidGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CachedUidGeneratorConfig {
    @Bean
    public MyDisposableWorkerIdAssigner disposableWorkerIdAssigner(){
        return new MyDisposableWorkerIdAssigner();
    }
    @Bean
    public CachedUidGenerator cachedUidGenerator(MyDisposableWorkerIdAssigner disposableWorkerIdAssigner){
        CachedUidGenerator cachedUidGenerator = new CachedUidGenerator();
        cachedUidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner);
        //以下为可选配置, 如未指定将采用默认值
        cachedUidGenerator.setTimeBits(41);
        cachedUidGenerator.setWorkerBits(10);
        cachedUidGenerator.setSeqBits(12);
        cachedUidGenerator.setEpochStr("2020-11-1");
        cachedUidGenerator.setBoostPower(3);
        cachedUidGenerator.setScheduleInterval(60L);
        return cachedUidGenerator;
    }
}
