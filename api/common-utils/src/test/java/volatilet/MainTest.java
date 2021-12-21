package volatilet;

public class MainTest {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();

        myThread.start();

        while(true){
//            System.out.println("flag==>"+myThread.flag);
            if(myThread.flag){
                System.out.println("flag==>"+myThread.flag);
            }
        }
    }
}
