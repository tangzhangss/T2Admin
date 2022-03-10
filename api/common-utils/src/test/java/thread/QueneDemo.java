package thread;

import cn.hutool.core.util.RandomUtil;
import com.tangzhangss.commonutils.utils.BaseUtil;

import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

public class QueneDemo {

    static class Task{
        public int i;
        public long time;

        public Task(int i, long time) {
            this.i = i;
            this.time = time;
        }
    }

    public final static LinkedList<Task> taskQuene = new LinkedList();

    public static void main(String[] args) {

        taskQuene.add(new Task(1,5000));
        taskQuene.add(new Task(2,15000));
        taskQuene.add(new Task(3,5000));
        taskQuene.add(new Task(4,5000));
        taskQuene.add(new Task(5,5000));
        taskQuene.add(new Task(6,5000));

        long wait = 10000;


        //任务运行
        Thread taskRunThread = new Thread(()->{

            while(true){
                if(taskQuene.size()==0)continue;
                long startTime = System.currentTimeMillis();
                Task task = taskQuene.pop();

                System.out.println(BaseUtil.string("任务",task.i,"开始执行时间:",new Date().toString()));
                try {
                    //任务执行
                    Thread.sleep(task.time);

                    long endTime = System.currentTimeMillis();

                    if(endTime-startTime<wait)Thread.sleep(endTime-startTime);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        taskRunThread.start();

        //任务操作
        Thread taskOp = new Thread(()->{
            for (int i =100;i<120;i++){
                taskQuene.add(new Task(i, RandomUtil.randomInt(5000,20000)));
                System.out.println(BaseUtil.string("新增了一个任务",i));
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        taskOp.start();

    }
}
