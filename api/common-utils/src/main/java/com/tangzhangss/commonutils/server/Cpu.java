package com.tangzhangss.commonutils.server;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReUtil;
import com.tangzhangss.commonutils.utils.BaseUtil;
import com.tangzhangss.commonutils.utils.runtime.OSInfo;
import com.tangzhangss.commonutils.utils.runtime.RuntimeUtil;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * CPU相关信息
 */
public class Cpu {
    /**
     * 逻辑processor数
     */
    private int cpuProcessorNum;
    /**
     * cpu线程数
     */
    private int cpuCoreNum;
    /**
     * cpu的数量
     */
    private int cpuNum;
    /**
     * CPU总的使用率
     */
    private double total;

    /**
     * CPU系统使用率
     */
    private double sys;

    /**
     * CPU用户使用率
     */
    private double used;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;

    /**
     * CPU最大频率 单位Hz
     */
    private long maxFrequency;
    private long[] currentFrequency;

    public long getMaxFrequency() {
        return maxFrequency;
    }

    public void setMaxFrequency(long maxFrequency) {
        this.maxFrequency = maxFrequency;
    }

    public long[] getCurrentFrequency() {
        return currentFrequency;
    }

    public void setCurrentFrequency(long[] currentFrequency) {
        this.currentFrequency = currentFrequency;
    }

    public int getCpuNum(){
        return cpuNum;
    }


    public void setCpuNum(int cpuNum) {
        this.cpuNum = cpuNum;
    }

    public int getCpuProcessorNum() {
        return cpuProcessorNum;
    }

    public void setCpuProcessorNum(int cpuProcessorNum) {
        this.cpuProcessorNum = cpuProcessorNum;
    }

    public int getCpuCoreNum() {
        return cpuCoreNum;
    }

    public void setCpuCoreNum(int cpuCoreNum) {
        this.cpuCoreNum = cpuCoreNum;
    }

    public double getTotal() {
        return BaseUtil.round(NumberUtil.mul(total, 100),2);
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSys() {
        return BaseUtil.round(NumberUtil.mul(sys / total, 100), 2);
    }

    public void setSys(double sys) {
        this.sys = sys;
    }

    public double getUsed() {
        return BaseUtil.round(NumberUtil.mul(used / total, 100), 2);
    }

    public void setUsed(double used) {
        this.used = used;
    }

    public double getWait() {
        return BaseUtil.round(NumberUtil.mul(wait / total, 100), 2);
    }

    public void setWait(double wait) {
        this.wait = wait;
    }

    public double getFree() {
        return BaseUtil.round(NumberUtil.mul(free / total, 100), 2);
    }

    public void setFree(double free) {
        this.free = free;
    }
}
