import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class UseClient {
    public static void main(String[] args) {
        int count = 200;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count,()->{
            System.out.println("开始获取单例！"+System.currentTimeMillis());
        });
        CountDownLatch latch = new CountDownLatch(count);
        for(int i = 0; i < count; i++){
            new Thread(()->{
                try {
                    cyclicBarrier.await();
//                    Object object = HungrySingleton.getInstance();
//                    Object object = LazySingletonOne.getInstance();
//                    Object object = LazySingletonTwo.getInstance();
                    Object object = LazySingletonThree.getInstance();
                    System.out.println(System.currentTimeMillis()+":"+object);
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        try {
            latch.await();
            System.out.println("并发单例模式获取完成！" + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
