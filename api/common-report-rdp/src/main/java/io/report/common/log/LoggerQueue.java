package io.report.common.log;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
 
 
public class LoggerQueue {
    //队列大小
    public static final int QUEUE_MAX_SIZE = 10000;
    private static LoggerQueue alarmMessageQueue = new LoggerQueue();
    //阻塞队列
    @SuppressWarnings("rawtypes")
	private BlockingQueue blockingQueue = new LinkedBlockingQueue<>(QUEUE_MAX_SIZE);
 
    private LoggerQueue() {
    }
 
    public static LoggerQueue getInstance() {
        return alarmMessageQueue;
    }
 
    /**
     * 消息入队
     *
     * @param log
     * @return
     */
    @SuppressWarnings("unchecked")
	public boolean push(LoggerMessage log) {
        return this.blockingQueue.add(log);//队列满了就抛出异常，不阻塞
    }
    
    @SuppressWarnings("unchecked")
   	public boolean pushObj(Object log) {
       return this.blockingQueue.add(log);//队列满了就抛出异常，不阻塞
    }
 
    /**
     * 消息出队
     *
     * @return
     */
    public LoggerMessage poll() {
        LoggerMessage result = null;
        try {
            result = (LoggerMessage) this.blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
