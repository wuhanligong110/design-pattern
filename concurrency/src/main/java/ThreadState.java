/**
 * @ClassName ThreadState
 * @Description TODO
 * @Author Hxb
 * @Date 2019/1/14 10:37
 * @Version 1.0
 **/
public class ThreadState {

    public static void main(String[] args) throws InterruptedException {

        Object lock = new Object();
        Thread a = new Thread( ()->{
            synchronized (lock){
                System.out.println("this is a thread!");
            }
        });
        a.start();
        while(!a.isInterrupted()){
            System.out.println(a.getState());//获取线程状态
        }
        Thread.sleep(10000);
        a.interrupt();
        a.start();//多次调用start 会导致java.lang.IllegalThreadStateException
    }

}
