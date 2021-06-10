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
        /*
            以下为可选配置, 如未指定将采用默认值
         */
        //与指定日期的时间差(毫秒级)，41位，够用69年
        cachedUidGenerator.setTimeBits(41);
        cachedUidGenerator.setWorkerBits(10);
        cachedUidGenerator.setSeqBits(12);//每秒下的并发序列
        cachedUidGenerator.setEpochStr("2020-11-1");
        //RingBuffer size扩容参数, 可提高UID生成的吞吐量
        //默认:3， 原bufferSize=8192, 扩容后bufferSize= 8192 << 3 = 65536
        cachedUidGenerator.setBoostPower(3);
        cachedUidGenerator.setScheduleInterval(60L);
        return cachedUidGenerator;
    }
}
