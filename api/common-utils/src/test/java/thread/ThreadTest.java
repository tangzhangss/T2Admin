package thread;

public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                while (true){
                    Thread.sleep(1000);
                    if(Thread.interrupted()){
                        System.out.println("interrupted");
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("-----------执行完成");
        });
        thread.start();
        while (true){
            System.out.println(thread.getState());
            System.out.println(thread.isAlive());
            System.out.println(thread.isInterrupted());
            Thread.sleep(2000);

            thread.interrupt();
            Thread.sleep(2000);
        }

    }
}
