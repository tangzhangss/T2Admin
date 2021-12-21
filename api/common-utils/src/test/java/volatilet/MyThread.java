package volatilet;

public class MyThread extends Thread{

//    public volatile boolean flag = false;
    public  boolean flag = false;

    @Override
    public void run() {
        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag=true;
        System.out.println("flag==>"+flag);
    }
}
